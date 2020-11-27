USE project_ensf_607_608;

-- DROP TABLE PURCHASES;
-- DROP TABLE ORDERLINE;
-- DROP TABLE TOOLSORDER;
-- DROP TABLE ELECTRICAL;
-- DROP TABLE TOOL;
-- DROP TABLE INTERNATIONAL;
-- DROP TABLE SUPPLIER;
-- DROP TABLE CUSTOMER;

-- IMPORT Steps:
-- 1. suppliers.csv into SUPPLIER then into ELECTRICAL
-- 2. items.csv into TOOL then into ELECTRICAL
-- 3. customers.csv into CUSTOMER
-- 4. purchases.csv into PURCHASES
-- 5. orders.csv into TOOLSORDER
-- 6. orderlines.csv into ORDERLINE


CREATE TABLE CUSTOMER ( -- AKA CLIENT
	CustomerId INT AUTO_INCREMENT,	-- auto increment for generating ID values
    LName VARCHAR(20) NOT NULL,
    FName VARCHAR(20) NOT NULL,
    Type CHAR NOT NULL,
    PhoneNum VARCHAR(12) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(7) NOT NULL,
    PRIMARY KEY(CustomerId)
);

CREATE TABLE SUPPLIER (
	SupplierId INT AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Type VARCHAR(255) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    CName VARCHAR(255) NOT NULL,
    Phone VARCHAR(255) NOT NULL,
    PRIMARY KEY(SupplierId)
);

CREATE TABLE INTERNATIONAL ( -- specialization of SUPPLIER
	SupplierId INT NOT NULL,
    ImportTax DECIMAL(3, 2) NOT NULL, -- e.g. value 1.25 rate
    FOREIGN KEY(SupplierId) REFERENCES SUPPLIER(SupplierId) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(SupplierId)
);

CREATE TABLE TOOL (
	ToolId INT AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Type VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    SupplierId INT NOT NULL, # NOT NULL since TOOL has total participation in SUPPLIED_BY relationship (EER Diagram)
    FOREIGN KEY(SupplierId) REFERENCES SUPPLIER(SupplierId) ON UPDATE CASCADE, # By default, ON DELETE RESTRICT (e.g. must handle deleting the tool first before you delete the supplier)
    PRIMARY KEY(ToolId),
	CHECK(Quantity > 0),
    CHECK(Price > 0.00)
);

CREATE TABLE ELECTRICAL ( -- specialization of TOOL
	ToolId INT NOT NULL,
    PowerType VARCHAR(255) NOT NULL,
    FOREIGN KEY(ToolId) REFERENCES TOOL(ToolId) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(ToolId)
);

CREATE TABLE TOOLSORDER ( -- aka ORDER
	OrderId INT NOT NULL,
    Date DATE NOT NULL,
    PRIMARY KEY(OrderId),
    CHECK(OrderId>=10000), -- ensure id is 5-digits
    CHECK(OrderId<99999)
);

CREATE TABLE ORDERLINE (
	OrderId INT NOT NULL,
    ToolId INT NOT NULL,
    SupplierId INT NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY(OrderId) REFERENCES TOOLSORDER(OrderId) ON DELETE CASCADE ON UPDATE CASCADE, # Assumption an order is composed of order lines
    FOREIGN KEY(ToolId) REFERENCES TOOL(ToolId) ON DELETE CASCADE ON UPDATE CASCADE,	     # If order gets deleted it wouldn't have order lines
    FOREIGN KEY(SupplierId) REFERENCES SUPPLIER(SupplierId) ON DELETE CASCADE ON UPDATE CASCADE, # if tool gets deleted, you wouldn't know what to order
    PRIMARY KEY(OrderId, ToolId),
    CHECK(Quantity>0)
);

CREATE TABLE PURCHASES (
	CustomerId INT NOT NULL,
    ToolId INT NOT NULL,
    Date DATE NOT NULL,
    FOREIGN KEY(CustomerId) REFERENCES CUSTOMER(CustomerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(ToolId) REFERENCES TOOL(ToolId) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(CustomerId, ToolId, Date)
);