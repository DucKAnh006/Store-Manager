package bookstoremgmt.service;

import bookstoremgmt.model.Order;
import bookstoremgmt.model.OrderDetail;
import java.util.List;

/**
 *
 * @author Nguyen Tran Duc Anh
 */

public interface IOrderService {

    Order createOrder(String customerId, List<OrderDetail> details) throws Exception;

    Order getOrderById(String orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByCustomerId(String customerId);

    void updateOrderStatus(String orderId, Order.OrderStatus newStatus) throws Exception;

    Order addBookToOrder(String orderId, String bookId, int quantity) throws Exception;

    Order removeBookFromOrder(String orderId, String bookId) throws Exception;

    Order updateBookQuantityInOrder(String orderId, String bookId, int newQuantity) throws Exception;

    List<Order> getPendingOrders();
}