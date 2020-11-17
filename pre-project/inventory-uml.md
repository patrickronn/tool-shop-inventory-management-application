# Toolshop Project - ENSF 607/608
UML diagram name: Inventory Management System UML 
<br>
Created by: Patrick Linang
<br>
Course: ENSF 607

This UML captures the inventory package and the 

## UML Diagram for Inventory Management System
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

package inventory {
    package icontroller {
    }
}

package customer {
    package ccontroller {
    }
}

package server {
    package scontroller {
    class ServerController
    class InvModelController
    class CustModelController
    class InvDBController
    class CustDBController
    }

    package inventorymodel {
    class Shop
    class Inventory
    class SupplierList
    class Item
    class Supplier
    class Order
    class OrderLine
    }

    package customermodel {
        class Customer
    }
}

inventory.icontroller.ClientController ..> ServerController : "    socket comms."
customer.ccontroller.ClientController ..> ServerController

ServerController o-- "1" InvModelController
ServerController o-- "1" CustModelController
CustModelController o- "1" CustDBController
InvDBController "1" --o InvModelController 


InvModelController ..> Shop : controls
CustModelController ...> Customer : controls

Shop o-- "1" Inventory
Shop o-- "1   " SupplierList
Inventory o-- " * " Item
SupplierList o-- " * " Supplier
Item "*" - "1" Supplier : "     "
Inventory o--- "1" Order

Order "1" - "*" OrderLine
Item "1" --o OrderLine
hide members
@enduml
```