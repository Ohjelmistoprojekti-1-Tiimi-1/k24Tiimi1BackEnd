

-- ******* HUOM! *******

-- TÄSSÄ ON DROP TABLET JOKASEN CREATEN YHTEYDESSÄ TESTIÄ VARTEN ETTEI TUU CONFLICTEJA. SIT KUN OIKEESTI KÄYTETÄÄN, NI NOI PITÄÄ POISTAA.

-- ******* HUOM! *******



BEGIN TRANSACTION;

-- CREATE DATABASE "petshopTiimi1DB"
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LOCALE_PROVIDER = 'libc'
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;



DROP TABLE IF EXISTS Reservation_product;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS ProductType;
DROP TABLE IF EXISTS Manufacturer;
DROP TABLE IF EXISTS Customer;



-- Table: Customer
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (customerid SERIAL PRIMARY KEY NOT NULL, username VARCHAR, password VARCHAR, role VARCHAR);
INSERT INTO Customer (username, password, role) VALUES ('user', '$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue', 'USER');
INSERT INTO Customer (username, password, role) VALUES ('admin', '$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW', 'ADMIN');

-- Table: Manufacturer
DROP TABLE IF EXISTS Manufacturer;
CREATE TABLE Manufacturer (manufacturerid SERIAL PRIMARY KEY NOT NULL, name VARCHAR, country VARCHAR, businessIdentityCode VARCHAR);
INSERT INTO Manufacturer (name, country, businessidentitycode) VALUES ('Doggy Stuff', 'Finland', '1234567-1');
INSERT INTO Manufacturer (name, country, businessidentitycode) VALUES ('Catty Stuff', 'Sweden', '1234568-2');
INSERT INTO Manufacturer (name, country, businessidentitycode) VALUES ('Squeaky Stuff', 'Norway', '1234569-3');

-- Table: ProductType
DROP TABLE IF EXISTS ProductType;
CREATE TABLE ProductType (producttypeid SERIAL PRIMARY KEY, producttypevalue VARCHAR);
INSERT INTO ProductType (producttypevalue) VALUES ('Clothing');
INSERT INTO ProductType (producttypevalue) VALUES ('Toy');

-- Table: Product
DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
    productid SERIAL PRIMARY KEY NOT NULL, 
    name VARCHAR, 
    color VARCHAR, 
    size VARCHAR, 
    price DECIMAL (5, 2) NOT NULL, 
    instock INTEGER, 
    producttypeid INTEGER REFERENCES ProductType (producttypeid) ON UPDATE CASCADE NOT NULL, 
    manufacturerid INTEGER REFERENCES Manufacturer (manufacturerid) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Product (name, color, size, price, instock, producttypeid, manufacturerid) VALUES ('Dog Booties', 'Green', 'M', 12.12, 25, 1, 1);
INSERT INTO Product (name, color, size, price, instock, producttypeid, manufacturerid) VALUES ('Cat Collar', 'Red', 'S', 12.99, 111, 1, 2);
INSERT INTO Product (name, color, size, price, instock, producttypeid, manufacturerid) VALUES ('Squeaky Toy, Generic', 'Orange', 'S', 3.5, 226, 2, 3);

-- Table: Reservation
DROP TABLE IF EXISTS Reservation;
CREATE TABLE Reservation (
    reservationid SERIAL PRIMARY KEY NOT NULL, 
    created DATE NOT NULL, 
    delivered DATE, 
    canceled DATE, 
    customerid INTEGER REFERENCES Customer (customerid) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Reservation (created, delivered, customerid) VALUES ('2023-10-27', '2023-11-13', 1);

-- Table: Reservation_product
DROP TABLE IF EXISTS Reservation_product;
CREATE TABLE Reservation_product (
    reservation_productid SERIAL PRIMARY KEY NOT NULL,
    count INTEGER, 
    productid INTEGER REFERENCES Product (productid) ON UPDATE CASCADE NOT NULL, 
    reservationid INTEGER REFERENCES Reservation (reservationid) ON UPDATE CASCADE NOT NULL
    );
INSERT INTO Reservation_product (count, productId, reservationid) VALUES (3, 1, 1);
INSERT INTO Reservation_product (count, productId, reservationid) VALUES (1, 3, 1);




COMMIT TRANSACTION;

