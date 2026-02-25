package bookstoremgmt.repository;

import java.io.*;
import java.util.*;
import bookstoremgmt.model.Book;
import bookstoremgmt.model.OrderDetail;

/**
 * Xu ly doc/ghi file orderdetail.txt voi cau truc du lieu Map.
 * Key = orderId, Value = List<OrderDetail>
 *
 * @author Nguyen Tran Duc Anh
 */
public class OrderDetailRepository {
    private static final String FILE_ORDERDETAILS = "src/resource/data/orderdetail.txt";

    /**
     * Luu tat ca chi tiet don hang vao file.
     * Dinh dang: orderId|bookId|quantity|totalPrice
     */
    public boolean saveOrderDetails(Map<String, List<OrderDetail>> orderDetailsMap) {
        if (orderDetailsMap == null) {
            System.err.println("Order detail map is null. Cannot save.");
            return false;
        }

        File file = new File(FILE_ORDERDETAILS);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.err.println("Could not create directory: " + parentDir.getPath());
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, List<OrderDetail>> entry : orderDetailsMap.entrySet()) {
                String orderId = entry.getKey();
                if (orderId == null || orderId.trim().isEmpty()) {
                    System.err.println("Skipping entry with null/empty orderId.");
                    continue;
                }

                List<OrderDetail> details = entry.getValue();
                if (details == null) {
                    continue; // bo qua neu khong co chi tiet nao
                }

                for (OrderDetail detail : details) {
                    if (detail == null || detail.getBook() == null || detail.getBook().getId() == null) {
                        System.err.println("Skipping invalid OrderDetail for orderId: " + orderId);
                        continue;
                    }

                    writer.write(orderId + "|" + detail.getBook().getId() + "|" + detail.getQuantity() + "|"
                            + detail.getTotalPrice());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing order detail file: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Doc file orderdetail.txt thanh Map<orderId, List<OrderDetail>>.
     */
    public Map<String, List<OrderDetail>> loadOrderDetails() {
        Map<String, List<OrderDetail>> orderDetailsMap = new HashMap<>();
        File file = new File(FILE_ORDERDETAILS);

        if (!file.exists()) {
            System.err.println("OrderDetail file not found. Returning empty map: " + FILE_ORDERDETAILS);
            return orderDetailsMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                String orderId = parts[0];
                String bookId = parts[1];
                if (orderId == null || orderId.trim().isEmpty() || bookId == null || bookId.trim().isEmpty()) {
                    System.err.println("Skipping line with missing orderId/bookId: " + line);
                    continue;
                }

                try {
                    int quantity = Integer.parseInt(parts[2]);
                    if (quantity <= 0) {
                        System.err.println("Skipping invalid quantity (<=0): " + line);
                        continue;
                    }

                    Book dummyBook = new Book();
                    dummyBook.setId(bookId);
                    OrderDetail detail = new OrderDetail(dummyBook, quantity);

                    orderDetailsMap.computeIfAbsent(orderId, k -> new ArrayList<>()).add(detail);

                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid quantity format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading order detail file: " + e.getMessage());
        }

        return orderDetailsMap;
    }
}
