

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
CREATE TABLE Customer (customerId BIGINT PRIMARY KEY NOT NULL, username VARCHAR, password VARCHAR, role VARCHAR);

-- Table: Manufacturer
DROP TABLE IF EXISTS Manufacturer;
CREATE TABLE Manufacturer (manufacturerId BIGINT PRIMARY KEY NOT NULL, name VARCHAR, country VARCHAR, businessIdentityCode VARCHAR);
INSERT INTO Manufacturer (manufacturerId, name, country, businessIdentityCode) VALUES (0, 'Doggy Stuff', 'Finland', '1234567-1');
INSERT INTO Manufacturer (manufacturerId, name, country, businessIdentityCode) VALUES (1, 'Catty Stuff', 'Sweden', '1234568-2');
INSERT INTO Manufacturer (manufacturerId, name, country, businessIdentityCode) VALUES (2, 'Squeaky Stuff', 'Norway', '1234569-3');

-- Table: ProductType
DROP TABLE IF EXISTS ProductType;
CREATE TABLE ProductType (productTypeId BIGINT PRIMARY KEY, productTypeValue VARCHAR);
INSERT INTO ProductType (productTypeId, productTypeValue) VALUES (0, 'Clothing');
INSERT INTO ProductType (productTypeId, productTypeValue) VALUES (1, 'Toy');

-- Table: Product
DROP TABLE IF EXISTS Product;
CREATE TABLE Product (productId BIGINT PRIMARY KEY NOT NULL, name VARCHAR, productType BIGINT REFERENCES ProductType (productTypeId) ON UPDATE CASCADE NOT NULL, color VARCHAR, size VARCHAR, price DECIMAL (5, 2) NOT NULL, inStock INTEGER, manufacturer BIGINT REFERENCES Manufacturer (manufacturerId) ON UPDATE CASCADE NOT NULL);
INSERT INTO Product (productId, name, productType, color, size, price, inStock, manufacturer) VALUES (0, 'Dog Booties', 0, 'Green', 'M', 12.12, 10, 0);
INSERT INTO Product (productId, name, productType, color, size, price, inStock, manufacturer) VALUES (1, 'Cat Collar', 0, 'Red', 'S', 12.99, 11, 1);
INSERT INTO Product (productId, name, productType, color, size, price, inStock, manufacturer) VALUES (2, 'Squeaky Toy, Generic', 1, 'Orange', 'S', 3.5, 111, 2);

-- Table: Reservation
DROP TABLE IF EXISTS Reservation;
CREATE TABLE Reservation (reservationId BIGINT PRIMARY KEY NOT NULL, created DATE NOT NULL, delivered DATE, canceled DATE, customer BIGINT REFERENCES Customer (customerId) ON UPDATE CASCADE NOT NULL);

-- Table: Reservation_product
DROP TABLE IF EXISTS Reservation_product;
CREATE TABLE Reservation_product (count INTEGER, product BIGINT REFERENCES Product (productId) ON UPDATE CASCADE NOT NULL, reservation BIGINT REFERENCES Reservation (reservationId) ON UPDATE CASCADE NOT NULL, PRIMARY KEY (product, reservation));

COMMIT TRANSACTION;

