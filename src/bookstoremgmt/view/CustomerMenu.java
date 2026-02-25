package bookstoremgmt.view;

import java.util.List;
import java.util.ArrayList;
import bookstoremgmt.service.impl.CustomerServiceImpl;
import bookstoremgmt.model.Customer;
import bookstoremgmt.model.Order;

/**
 *
 * @author Nguyen Tran Duc Anh
 */
public class CustomerMenu extends BaseMenu {
    private final CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Override
    public void display() {
       while (true) {
            printLine();
            printMenuHeader();
            printMenuOptions();
            printSeparator();
            int choice = getIntInput("Enter your choice: ", 0, getMenuOptions().length);
            if (choice == 0) {
                // back to main menu
                break;
            }
            handleMenuChoice(choice);
        }
    }

    @Override
    protected String getMenuTitle() {
        return "Customer Menu";
    }

    @Override
    protected String[] getMenuOptions() {
        return new String[] {
                " Add customer",
                " Update customer",
                " Delete customer",
                " Search customer",
                " Show all customers",
                " Show customer orders history",
        };
    }

    @Override
    protected void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addCustomer();
                break;
            case 2:
                updateCustomer();
                break;
            case 3:
                deleteCustomer();
                break;
            case 4:
                searchCustomer();
                break;
            case 5:
                showAllCustomers();
                break;
            case 6:
                showCustomerOrdersHistory();
                break;
            default:
                System.out.println("Invalid option. Please try again (0-6).");
                break;
        }
    }

    // Xu li thao tac them customer (1)
    protected void addCustomer() {
        String name, phoneNumber;
        System.out.println("============Adding a new customer============");
        // nhap ten
        do {
            name = getStringInput("Please enter customer name: ");
            if (!name.matches("^[A-Za-z ]+$")) {
                // kiem tra ten co chua chu so hoac ki tu dac biet khong.
                System.out.println("Name invalid — do not include digits or special characters.");
            }
        } while (!name.matches("^[A-Za-z ]+$"));

        // nhap so dien thoai
        do {
            phoneNumber = getStringInput("Please enter customer phone number: ");
            if (!phoneNumber.matches("^[0-9]{9,15}$")) {
                // kiem tra so dien thoai chi chua chu so va do dai tu 9-15
                System.out.println("Phone number invalid — only digits allowed, length 9-15.");
            }
        } while (!phoneNumber.matches("^[0-9]{9,15}$"));

        // kiem tra so dien thoai da ton tai chua
        if (customerService.exists(phoneNumber)){
            System.out.println("Customer with phone number " + phoneNumber + " already exists.");
            System.out.println("Customer added unsuccessfully.");
            return;
        }

        // confirm
        boolean added = getConfirmation("Are you sure you want to add this customer?: ");
        if (added == true) {
            Customer newCustomer = new Customer(phoneNumber, name, phoneNumber);
            if(!customerService.add(newCustomer)) {
                System.out.println("Customer added unsuccessfully.");
                pauseScreen();
                return;
            }
            showSuccess("Customer added successfully.");
            pauseScreen();
        } else {
            System.out.println("Operation cancelled.");
            pauseScreen();
        }
    }

    // xu li thao tac cap nhat customer (2)
    // chi duoc phep cap nhat ten customer do so dien thoai la khoa chinh
    protected void updateCustomer() {
        System.out.println("============Update Customer============");
        String customerId = getStringInput("Please enter customer ID to update: ");

        Customer existingCustomer = customerService.getById(customerId);
        if (existingCustomer == null) {
            System.out.println("Customer with ID " + customerId + " not found.");
            pauseScreen();
            return;
        } // kiem tra id co ton tai khong 

        String name = existingCustomer.getName();
        System.out.println("Current Customer Details:\n " + existingCustomer);
        // hien thi thong tin customer hien tai, chua cap nhat gi ca
        do {
            name = getStringInput("Please enter new customer name: ");
            if (!name.matches("^[A-Za-z ]+$")) {
                // kiem tra ten co chua chu so hoac ki tu dac biet khong.
                // matches(".*\\d.*") kiem tra co chua chu so
                // matches(".*[^\\p{Alnum}\\s].*") kiem tra co chua ki tu dac biet
                System.out.println("Name invalid — do not include digits or special characters.");
            }
        } while (!name.matches("^[A-Za-z ]+$"));

        boolean updated = getConfirmation("Are you sure you want to update this customer?: ");
        if (updated) {
            existingCustomer.setName(name);
            // cap nhat ten moi
            if(!customerService.update(existingCustomer))
            {
                System.out.println("Customer updated unsuccessfully.");
                pauseScreen();
                return;
            }
            showSuccess("Customer updated successfully.");
            pauseScreen();
        } else {
            System.out.println("Operation cancelled.");
            pauseScreen();
        }
    }

    // xu li thao tac delete customer (3)
    protected void deleteCustomer() {
        System.out.println("============Delete Customer============");
        String customerId = getStringInput("Please enter customer ID to delete: ");
        // nhap id customer can xoa
        // kiem tra id co ton tai khong
        Customer existingCustomer = customerService.getById(customerId);
        if (existingCustomer == null) {
            System.out.println("Customer with ID " + customerId + " not found.");
            pauseScreen();
            return;
        }
        System.out.println("Current Customer Details: " + existingCustomer);
        // hien thi thong tin customer can xoa
        boolean deleted = getConfirmation("Are you sure you want to delete this customer?: ");
        if (deleted) {
            if(!customerService.delete(customerId)){
                System.out.println("Customer deleted unsuccessfully.");
                pauseScreen();
                return;
            }
            showSuccess("Customer deleted successfully.");
            pauseScreen();
        } else {
            System.out.println("Operation cancelled.");
            pauseScreen();
        }
    }

    // xu li thao tac tim kiem customer (4)
    protected void searchCustomer() {
        System.out.println("============Search Customer============");
        String phoneNumber = getStringInput("Please enter customer phone number to search: ");
        Customer existingCustomer = customerService.getById(phoneNumber);
        if (existingCustomer == null) {
            System.out.println("Customer with phone number " + phoneNumber + " not found.");
            pauseScreen();
            return;
        }
        System.out.println("Customer Details: " + existingCustomer);
        pauseScreen();
    }

    // xu li thao tac hien thi tat ca customer (5)
    protected void showAllCustomers() {
        System.out.println("============All Customer============");
        printSeparator();
        List<Customer> customers = new ArrayList<>(customerService.getAll()) ;
        if (customers.isEmpty()) {
            System.out.println("Customer data list is empty.");
            pauseScreen();
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        printLine();
        pauseScreen();
    }

    // xu li thao tac hien thi lich su don hang cua customer (6)
    protected void showCustomerOrdersHistory() {
        System.out.println("============Customer Orders History============");
        String customerId = getStringInput("Please enter customer ID to view orders history: ");
        // nhap id customer can xem lich su don hang
        Customer existingCustomer = customerService.getById(customerId);
        if (existingCustomer == null) {
            System.out.println("Customer with ID " + customerId + " not found.");
            pauseScreen();
            return;
        } // kiem tra id co ton tai khong

        System.out.println("Orders History for Customer ID " + customerId + ":");
        // hien thi lich su don hang cua customer
        printSeparator();
        if (existingCustomer.getOrderHistory().isEmpty()) {
            System.out.println("No orders found for this customer.");
        } else {
            for (Order order : existingCustomer.getOrderHistory()) {
                System.out.printf("Order ID: %-7s | Date: %-12s | Total Amount: %-10.2f | Status: %s\n",
                        order.getOrderId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus());
            } // hien thi chi tiet tung don hang
        }
        pauseScreen();
    }

}
