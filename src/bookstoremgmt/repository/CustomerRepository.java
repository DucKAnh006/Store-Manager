
package bookstoremgmt.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bookstoremgmt.model.Customer;

/**
 *
 * @author FA25_PRO192_SE2002_Group4
 */
public class CustomerRepository {
    private static String FILE_CUSTOMERS = "src/resource/data/customer.txt";

    public boolean saveCustomers(List<Customer> customers) {

        if (customers == null) {
            System.err.println("Customer list is null. Cannot save.");
            return false; // Save failed
        }

        File file = new File(FILE_CUSTOMERS);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        } // tạo file customer.txt nếu chưa tồn tại

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CUSTOMERS))) {

            for (Customer customer : customers) {

                if (customer == null) {
                    System.err.println("Skipping null Customer object.");
                    continue; // Skip null object
                }

                String id = (customer.getId() != null) ? customer.getId() : "";
                String name = (customer.getName() != null) ? customer.getName() : "";
                String phoneNumber = (customer.getPhoneNumber() != null) ? customer.getPhoneNumber() : "";
                // xử lí các trường hợp null để tránh lỗi khi ghi file

                writer.write(id + "|" + name + "|" + phoneNumber);
                writer.newLine(); // viết vào file customer.txt
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Save file not success");
            return false;
        }

        return true;
    }

    public List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(FILE_CUSTOMERS);

        // kiểm tra file tồn tại hay không
        if (!file.exists()) {
            System.err.println("Customer file not found. Returning empty list: " + FILE_CUSTOMERS);
            return customers; // Return empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CUSTOMERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip dòng trống
                }

                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    if (!parts[0].trim().isEmpty() && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                        customers.add(
                                new Customer(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    }
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Read file not success");
        }
        return customers; // trả về danh sách customers đọc được từ file
    }
}
