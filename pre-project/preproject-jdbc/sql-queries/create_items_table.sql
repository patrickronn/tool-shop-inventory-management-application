USE preproject_ensf_607;

CREATE TABLE ITEM (
	Id INT NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(6, 2) NOT NULL,
    SupplierId INT NOT NULL
);