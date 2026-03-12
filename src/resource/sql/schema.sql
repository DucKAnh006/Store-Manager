-- Enable SQL Server Authentication (Mixed Mode)
EXEC sys.xp_instance_regwrite N'HKEY_LOCAL_MACHINE', N'Software\Microsoft\MSSQLServer\MSSQLServer', N'LoginMode', REG_DWORD, 2;
GO

-- Create the Database named BookManager
CREATE DATABASE BookManager;
GO

-- Switch to the newly created Database
USE BookManager;
GO

-- Create SQL Login and User (Password matches the Java configuration)
CREATE LOGIN BookManager WITH PASSWORD = 'ADMIN@12345';
CREATE USER BookManager FOR LOGIN BookManager;
GO

-- Grant full management permissions (db_owner role) to this user
ALTER ROLE db_owner ADD MEMBER BookManager;
GO

CREATE TABLE BM_Account (
    account_id VARCHAR(10) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    account VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
);

CREATE TABLE BM_Customer (
    customer_id VARCHAR(10) PRIMARY KEY,
    phone_number VARCHAR(15) NOT NULL,
    loyalty_point INT DEFAULT 0,

    FOREIGN KEY (customer_id) REFERENCES BM_Account (account_id)
);

CREATE TABLE BM_Address (
    address_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id VARCHAR(10) NOT NULL,
    address VARCHAR(255) NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES BM_Customer(customer_id)
);

CREATE TABLE BM_Supplier (
    supplier_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE BM_Product (
    product_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    category VARCHAR(100),
    status VARCHAR(50), 
    total_sales INT DEFAULT 0, 
    total_star_ratings INT DEFAULT 0,
    number_of_ratings INT DEFAULT 0,
    average_rating DECIMAL(3, 2) DEFAULT 0.00,
    discount INT DEFAULT 0,
    supplier_id VARCHAR(10) NOT NULL,

    FOREIGN KEY (supplier_id) REFERENCES BM_Supplier (supplier_id)
);

CREATE TABLE BM_Author (
    author_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE BM_Books (
    product_id VARCHAR(10) PRIMARY KEY,
    author_id VARCHAR(10) NOT NULL,
    publisher VARCHAR(150) NOT NULL,
    year_published INT CHECK (year_published <= YEAR(GETDATE())),
    language VARCHAR(50) NOT NULL,
    description TEXT NOT NULL, 

    FOREIGN KEY (author_id) REFERENCES BM_Author (author_id),
    FOREIGN Key (product_id) REFERENCES BM_Product (product_id)
);

CREATE TABLE BM_Stationery (
    product_id VARCHAR(10) PRIMARY KEY,
    manufacturer VARCHAR(255) NOT NULL,
    material VARCHAR(255) NOT NULL,

    FOREIGN KEY (product_id) REFERENCES BM_Product (product_id)
);

CREATE TABLE BM_Combo (
    combo_id VARCHAR(10) PRIMARY KEY,

    FOREIGN KEY (combo_id) REFERENCES BM_Product (product_id)

);

CREATE TABLE BM_ComboDetail (
    quantity INT CHECK (quantity > 0),
    combo_product_id VARCHAR(10) NOT NULL,
    combo_id VARCHAR(10) NOT NULL,

    PRIMARY KEY (product_id, combo_id),

    FOREIGN KEY (combo_id) REFERENCES BM_Combo (combo_id),
    FOREIGN KEY (combo_product_id) REFERENCES BM_Product (product_id) 
);

CREATE TABLE BM_Cart (
    customer_id VARCHAR(10) NOT NULL,
    cart_id VARCHAR(10) PRIMARY KEY NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES BM_Customer (customer_id)
);

CREATE TABLE BM_CartItem (
    cart_id VARCHAR(10) NOT NULL,
    product_id VARCHAR(10) NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10, 2) NOT NULL,
    selected BIT DEFAULT 1,

    PRIMARY KEY (cart_id, product_id),

    FOREIGN KEY (cart_id) REFERENCES BM_CART (cart_id),
    FOREIGN KEY (product_id) REFERENCES BM_Product (product_id)
);

CREATE TABLE BM_Voucher (
    code VARCHAR(10) PRIMARY KEY,
    is_percentage BIT NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL,
    minimum_order_amount DECIMAL(10, 2) NOT NULL,
    expiration_date DATE
);

CREATE TABLE BM_Order (
    order_id VARCHAR(10) NOT NULL,
    customer_id VARCHAR(10),
    order_date DATE,
    status INT,
    voucher VARCHAR(10),

    FOREIGN KEY (voucher) REFERENCES BM_Voucher (code),
    FOREIGN KEY (customer_id) REFERENCES BM_Customer (customer_id)
);

CREATE TABLE BM_OrderDetail (
    product_id VARCHAR(10),
    order_id VARCHAR(10),
    total_price DECIMAL(10, 2) NOT NULL,
    purchased_price DECIMAL(10, 2) NOT NULL,
    quantity INT,

    PRIMARY KEY (product_id, order_id),

    FOREIGN KEY (product_id) REFERENCES BM_Product (product_id),
    FOREIGN KEY (order_id) REFERENCES BM_Order (order_id)
);

CREATE TABLE BM_UsedVoucher (
    customer_id VARCHAR(10) NOT NULL,
    code VARCHAR(10) NOT NULL,
    is_used BIT NOT NULL DEFAULT 0,

    PRIMARY KEY (customer_id, code),

    FOREIGN KEY (customer_id) REFERENCES BM_Customer (customer_id),
    FOREIGN KEY (code) REFERENCES BM_Voucher (code)
);

CREATE TABLE BM_Feedback (
    feedback_id VARCHAR(10) PRIMARY KEY,
    product_id VARCHAR(10) NOT NULL,
    customer_id VARCHAR(10) NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment_text TEXT,
    comment_date DATE,

    FOREIGN KEY (product_id) REFERENCES BM_Product (product_id),
    FOREIGN KEY (customer_id) REFERENCES BM_Customer (customer_id)
);
