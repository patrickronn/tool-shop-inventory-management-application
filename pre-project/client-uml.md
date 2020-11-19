# Toolshop Project - ENSF 607/608
UML diagram name: Client UML 
<br>
Created by: Patrick Linang
<br>
Course: ENSF 607

## UML Diagram for client.ccontroller
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

package client {
    package ccontroller {
        package clientcontroller {
            class ClientController
        }
        package cmodelcontroller {
            class ModelController

            class Deserializer
            class Serializer
        }
        package viewcontroller {
            class ViewController
            class CustomerViewController
            class InventoryViewController
        }
    }

    package cmessagemodel {
            class CustomerList {
                -customers: LinkedHashSet<Customer>
                +addNewCustomer(customer: Customer): void
                +updateExistingCustomer(customer: Customer): void
                +removeCustomer(customer: Customer): void
                +searchCustomer(id: int): Customer
                +searchCustomer(name: String): Customer
                +searchCustomer(type: char): Customer
            }
            abstract class Customer {
                - clientId: int
                - firstName: String
                - lastName: String
                - address: String
                - postalCode: String
                - phoneNum: String
                - customerType: char
            }
            class ResidentialCustomer extends Customer
            class CommercialCustomer extends Customer
     
            class Inventory
            class Order
            class OrderLine

            abstract class Item
            class ElectricalItem extends Item
            class NonElectricalItem extends Item
    }

    package view {
        class CustomerManagementGUI
        class InventoryManagementGUI
    }

}

/' modelcontroller '/
ModelController o-- ViewController
ModelController o- ClientController
Serializer "1" --o ModelController
Deserializer "1" --o ModelController
Inventory "1" -o ModelController
CustomerList "1" -o ModelController

/' viewcontroller and view '/
ViewController *-- "1" InventoryViewController
ViewController *-- "1" CustomerViewController
CustomerViewController o-- "1" CustomerManagementGUI
InventoryViewController o-- "1" InventoryManagementGUI

/' cmodel '/
Inventory o-- " * " Item
Order "1" -o Inventory
CustomerList o-- " * " Customer
Order "1" -- " * " OrderLine
OrderLine o- "1" Item


server.ServerController <... ClientController :"   outgoing server conn."

hide members
@enduml
```