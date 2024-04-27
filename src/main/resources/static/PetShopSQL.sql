

-- ******* HUOM! *******

-- TÄSSÄ ON DROP TABLET JOKASEN CREATEN YHTEYDESSÄ TESTIÄ VARTEN ETTEI TUU CONFLICTEJA. SIT KUN OIKEESTI KÄYTETÄÄN, NI NOI PITÄÄ POISTAA.

-- ******* HUOM! *******



BEGIN TRANSACTION;

CREATE DATABASE "petshopTiimi1DB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;



-- Table: Customer
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (customerId SERIAL PRIMARY KEY NOT NULL, username VARCHAR, password VARCHAR, role VARCHAR);
INSERT INTO Customer (username, password, role) VALUES ('Lolli', 'jkop12j43klj4234234fddsfsd', 'customer');

-- Table: Manufacturer
DROP TABLE IF EXISTS Manufacturer;
CREATE TABLE Manufacturer (manufacturerId SERIAL PRIMARY KEY NOT NULL, name VARCHAR, country VARCHAR, businessIdentityCode VARCHAR);
INSERT INTO Manufacturer (name, country, businessIdentityCode) VALUES ( 'Doggy Stuff', 'Finland', '1234567-1');
INSERT INTO Manufacturer (name, country, businessIdentityCode) VALUES ( 'Catty Stuff', 'Sweden', '1234568-2');
INSERT INTO Manufacturer (name, country, businessIdentityCode) VALUES ( 'Squeaky Stuff', 'Norway', '1234569-3');

-- Table: ProductType
DROP TABLE IF EXISTS ProductType;
CREATE TABLE ProductType (productTypeId SERIAL PRIMARY KEY, productTypeValue VARCHAR);
INSERT INTO ProductType (productTypeValue) VALUES ('Clothing');
INSERT INTO ProductType (productTypeValue) VALUES ('Toy');

-- Table: Product
DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
    productId SERIAL PRIMARY KEY NOT NULL, 
    name VARCHAR, 
    productTypeId SERIAL REFERENCES ProductType (productTypeId) ON UPDATE CASCADE NOT NULL, 
    color VARCHAR, 
    size VARCHAR, 
    price DECIMAL (5, 2) NOT NULL, 
    inStock INTEGER, 
    manufacturerId SERIAL REFERENCES Manufacturer (manufacturerid) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Product (name, productTypeId, color, size, price, inStock, manufacturerId) VALUES ('Dog Booties', 0, 'Green', 'M', 12.12, 10, 0);
INSERT INTO Product (name, productTypeId, color, size, price, inStock, manufacturerId) VALUES ('Cat Collar', 0, 'Red', 'S', 12.99, 11, 1);
INSERT INTO Product (name, productTypeId, color, size, price, inStock, manufacturerId) VALUES ('Squeaky Toy, Generic', 1, 'Orange', 'S', 3.5, 111, 2);

-- Table: Reservation
DROP TABLE IF EXISTS Reservation;
CREATE TABLE Reservation (
    reservationId SERIAL PRIMARY KEY NOT NULL, 
    created DATE NOT NULL, 
    delivered DATE, 
    canceled DATE, 
    customerId SERIAL REFERENCES Customer (customerId) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Reservation (created, delivered, canceled, customerId) VALUES ('1994-10-27', '1994-10-27', '1994-10-27', 1);

-- Table: Reservation_product
DROP TABLE IF EXISTS Reservation_product;
CREATE TABLE Reservation_product (
    reservation_productId SERIAL PRIMARY KEY NOT NULL,
    count INTEGER, 
    productId SERIAL REFERENCES Product (productId) ON UPDATE CASCADE NOT NULL, 
    reservationId SERIAL REFERENCES Reservation (reservationId) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Reservation_product (reservation_productId, count, productId, reservationId) VALUES (1, 3, 1, 1);

COMMIT TRANSACTION;

