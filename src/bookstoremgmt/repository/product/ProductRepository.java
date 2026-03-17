package bookstoremgmt.repository.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import bookstoremgmt.model.product.*;
import bookstoremgmt.model.catalog.*;
import bookstoremgmt.util.DatabaseConnection;

public class ProductRepository {
    private DatabaseConnection dataConnection = new DatabaseConnection(); // Initialize the DatabaseConnection object

    public List<Product> basicSearch(String search) throws SQLException {
        List<Product> resultList = new ArrayList<>();
        String keyword = "%" + search + "%";
        String selectQuery = "SELECT "
                + "p.product_id, p.name AS product_name, p.price, p.stock_quantity, "
                + "p.category, p.product_type, p.total_sales, p.total_star_ratings, "
                + "p.number_of_ratings, p.average_rating, p.discount, "
                + "sup.supplier_id, sup.name AS supplier_name, "
                + "bk.publisher, bk.status AS book_status, bk.year_published, bk.language, bk.description, "
                + "au.author_id, au.name AS author_name, "
                + "st.manufacturer, st.material "
                + "FROM BM_Product p "
                + "INNER JOIN BM_Supplier sup ON p.supplier_id = sup.supplier_id "
                + "LEFT JOIN BM_Books bk ON p.product_id = bk.product_id "
                + "LEFT JOIN BM_Author au ON bk.author_id = au.author_id "
                + "LEFT JOIN BM_Stationery st ON p.product_id = st.product_id "
                + "LEFT JOIN BM_Combo cb ON p.product_id = cb.combo_id "
                + "WHERE (p.product_id LIKE ? OR p.name LIKE ? OR sup.name LIKE ? OR au.name LIKE ?)";
        String selectComboDetailsQuery = "SELECT "
                + "p.product_id, p.name AS product_name, p.price, p.stock_quantity, "
                + "p.category, p.product_type, p.total_sales, p.total_star_ratings, "
                + "p.number_of_ratings, p.average_rating, p.discount, "
                + "sup.supplier_id, sup.name AS supplier_name, "
                + "bk.publisher, bk.status AS book_status, bk.year_published, bk.language, bk.description, "
                + "au.author_id, au.name AS author_name, "
                + "st.manufacturer, st.material "
                + "FROM BM_Product p "
                + "INNER JOIN BM_Supplier sup ON p.supplier_id = sup.supplier_id "
                + "LEFT JOIN BM_Books bk ON p.product_id = bk.product_id "
                + "LEFT JOIN BM_Author au ON bk.author_id = au.author_id "
                + "LEFT JOIN BM_Stationery st ON p.product_id = st.product_id "
                + "LEFT JOIN BM_Combo cb ON p.product_id = cb.combo_id "
                + "LEFT JOIN BM_ComboDetail cbd ON cb.combo_id = cbd.combo_id"
                + "WHERE (cbd.combo_id = ?)";
        try (Connection connection = dataConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement query = connection.prepareStatement(selectQuery)) {
                query.setString(1, keyword);
                query.setString(2, keyword);
                query.setString(3, keyword);
                query.setString(4, keyword);

                try (ResultSet rs = query.executeQuery()) {
                    while (rs.next()) {
                        int productType = rs.getInt("product_type");

                        Supplier supplier = new Supplier(rs.getString("supplier_id"), rs.getString("supplier_name"));

                        Product product = null;
                        switch (productType) {
                            case 0: // Book
                                Author author = new Author(rs.getString("author_id"), rs.getString("author_name"));

                                product = new Book(rs.getString("product_id") , rs.getString("product_name"), author, rs.getDouble("price"), rs.getInt("stock_quantity"),
                                                rs.getString("category"), rs.getInt("book_status"), rs.getString("publisher"), rs.getInt("year_published"),
                                                rs.getString("language"), rs.getString("description"), rs.getInt("discount"), rs.getInt("total_sales"), rs.getInt("total_star_ratings"),
                                                rs.getInt("number_of_ratings"), rs.getDouble("average_rating"), supplier);
                            break;
                            case 1: // Stationery
                                product = new Stationery(rs.getString("product_id") , rs.getString("product_name"), rs.getDouble("price"), rs.getInt("stock_quantity"),
                                                rs.getString("category"), rs.getInt("discount"), rs.getInt("total_sales"), rs.getInt("total_star_ratings"),
                                                rs.getInt("number_of_ratings"), rs.getDouble("average_rating"), rs.getString("manufacturer"), rs.getString("material"), supplier);
                            break;
                            case 2: // Combo
                                Map<Product, Integer> listRes = new HashMap<>();
                                PreparedStatement comboDetailQuery = connection.prepareStatement(selectComboDetailsQuery);
                                String comboId = rs.getString("product_id");
                                comboDetailQuery.setString(1, comboId);

                                try (ResultSet comboRs = comboDetailQuery.executeQuery()) {
                                    int productDetailType = rs.getInt("product_type");

                                    Supplier detailSupplier = new Supplier(rs.getString("supplier_id"), rs.getString("supplier_name"));

                                    Product detail = null;
                                    switch (productType) {
                                        case 0: // Book
                                            Author detailAuthor = new Author(rs.getString("author_id"), rs.getString("author_name"));

                                            product = new Book(rs.getString("product_id") , rs.getString("product_name"), detailAuthor, rs.getDouble("price"), rs.getInt("stock_quantity"),
                                                            rs.getString("category"), rs.getInt("book_status"), rs.getString("publisher"), rs.getInt("year_published"),
                                                            rs.getString("language"), rs.getString("description"), rs.getInt("discount"), rs.getInt("total_sales"), rs.getInt("total_star_ratings"),
                                                            rs.getInt("number_of_ratings"), rs.getDouble("average_rating"), detailSupplier);
                                        break;
                                        case 1: // Stationery
                                            product = new Stationery(rs.getString("product_id") , rs.getString("product_name"), rs.getDouble("price"), rs.getInt("stock_quantity"),
                                                            rs.getString("category"), rs.getInt("discount"), rs.getInt("total_sales"), rs.getInt("total_star_ratings"),
                                                            rs.getInt("number_of_ratings"), rs.getDouble("average_rating"), rs.getString("manufacturer"), rs.getString("material"), detailSupplier);
                                        break;
                                    }
                                } catch (SQLException e) {
                                    throw new SQLException("Cannot add Details to combo", e);
                                }
                            break;
                        }

                        if (product != null) {
                            resultList.add(product);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new SQLException("Cannot execute the demand.", e);
            }
        } catch (SQLException e) {
            throw new SQLException("Cannot execute the demand.", e);
        }

        return resultList;
    }
}
