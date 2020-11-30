USE project_ensf_607_608;

SELECT * FROM CUSTOMER;
SELECT * FROM TOOL;
SELECT * FROM SUPPLIER;
SELECT * FROM PURCHASES;
SELECT * FROM TOOLSORDER;
SELECT * FROM ORDERLINE;


-- (2) BASIC RETRIEVAL:
-- Retrieve the first name, last name, and address of the customers with the last name 'Smith'
SELECT FName, LName, Address FROM CUSTOMER WHERE LName = 'Smith';

-- (3) RETRIEVAL WITH ORDERED RESULTS:
-- Retrieve the tool id, name, and price of 'Non-Electrical' tools ordered by price (ascending)
SELECT ToolId, Name, Price 
FROM TOOL
WHERE Type = 'Non-Electrical'
ORDER BY Price;

-- (4) USING NESTED QUERIES: (most complex nested query I can think of)
-- Retrieve the first and last name of customers who purchased items from supplier 'Grab Bag Inc.'
SELECT Fname, Lname
FROM CUSTOMER
WHERE CustomerId IN
	(SELECT CustomerId
     FROM PURCHASES
     WHERE ToolId IN
		(SELECT ToolId
         FROM TOOL
         WHERE SupplierId IN
			(SELECT SupplierId
			 FROM SUPPLIER
             WHERE Name = 'Grab Bag Inc.')));

SELECT C.Fname, C.Lname, P.*
FROM CUSTOMER AS C JOIN PURCHASES AS P
ON C.CustomerId=P.CustomerId;

-- (5) USING JOINED TABLES:
-- Retrieve Tool name, power type, and quantity of all electrical tools
SELECT T.Name, E.PowerType, T.Quantity
FROM ELECTRICAL AS E JOIN TOOL AS T
ON E.ToolId = T.ToolId;

-- (6) UPDATE OPERATION:
-- Update tool order with order id from 10000 to 12345
SELECT * FROM TOOLSORDER WHERE OrderId = 10000;
SELECT * FROM ORDERLINE WHERE OrderId; -- recall trigger: on update cascade


UPDATE TOOLSORDER SET OrderId = 12345 WHERE OrderId = 10000;


SELECT * FROM TOOLSORDER WHERE OrderId = 10000 OR OrderId = 12345;
SELECT * FROM ORDERLINE;
-- UPDATE TOOLSORDER SET OrderId = 10000 WHERE OrderId = 12345;



--  (7) DELETE OPERATION:
-- Delete tool with tool id 1040
SELECT * FROM TOOL WHERE ToolId = 1040; -- Electrical type
SELECT * FROM ELECTRICAL WHERE ToolId = 1040; -- recall trigger: on delete cascade


DELETE FROM TOOL WHERE TOOLId = 1040;


-- INSERT INTO TOOL VALUES (1040, 'Inny Outies', 'Electrical', 219, 3.45, 8010);
-- INSERT INTO ELECTRICAL VALUES (1040, '250 W');
