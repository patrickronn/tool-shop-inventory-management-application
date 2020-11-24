USE project_ensf_607_608;

CREATE TABLE Customer (
	CustomerId INT,
    LName VARCHAR(20) NOT NULL,
    FName VARCHAR(20) NOT NULL,
    Type VARCHAR(255) NOT NULL,
    PhoneNum VARCHAR(12) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(7),
    PRIMARY KEY(ClientId)
);