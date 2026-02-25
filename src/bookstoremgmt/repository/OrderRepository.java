package bookstoremgmt.repository;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import bookstoremgmt.model.Order;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */

public class OrderRepository {
    private static final String FILE_ORDERS = "src/resource/data/order.txt";

    /**
     * Save all orders to file.
     * Each line: orderId|customerId|orderDate|status
     */
    public boolean saveOrders(Map<String, Order> ordersMap) {
        if (ordersMap == null) {
            System.err.println("Order map is null. Cannot save.");
            return false;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ORDERS))) {
            for (Order order : ordersMap.values()) {
                writer.write(order.getOrderId() + "|" +
                        order.getCustomerId() + "|" + // <-- FIXED: Use getCustomerId()
                        order.getOrderDate().toString() + "|" +
                        order.getStatus().name());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[Error] Failed to save orders to file.");
            return false;
        }

        return true;
    }

    public Map<String, Order> loadOrders() {
        Map<String, Order> ordersMap = new LinkedHashMap<>();
        File file = new File(FILE_ORDERS);

        if (!file.exists()) {
            System.err.println("[Warning] Order file not found. A new one will be created when saving: " + FILE_ORDERS);
            return ordersMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    String orderId = parts[0];
                    String customerId = parts[1];
                    LocalDate orderDate = LocalDate.parse(parts[2]);
                    Order.OrderStatus status = Order.OrderStatus.valueOf(parts[3]);

                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setCustomerId(customerId);
                    order.setOrderDate(orderDate);
                    order.setStatus(status);

                    ordersMap.put(orderId, order);
                } else {
                    System.err.println("Skipping malformed line in orders.txt: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("[Warning] Order file not found. A new one will be created when saving.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[Error] Failed to read orders from file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Error] Failed to parse order data.");
        }

        return ordersMap;
    }
}
