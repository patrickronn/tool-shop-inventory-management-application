# Toolshop Project - ENSF 607/608
UML diagram name: Server UML 
<br>
Created by: Patrick Linang
<br>
Course: ENSF 607

This UML captures the class diagram for the server-side of the system and the  

## UML Diagram for server.smodel
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

package server {
    package smodelcontroller {
        class InvModelController
        class CustModelController
    }

    package smodel {
        package inventorymodel {
            class InventoryManager
            class Inventory
            class SupplierList
            class Item
            class Supplier
            class Order
            class OrderLine
        }
    
        package customermodel {
            class CustomerManager {
                -customerList: CustomerList
                +addNewCustomer(id: String, fname: String, lname: String, ... ): void
                +removeCustomer(id: String): void

                +updateCustomerFirstName(id: String, fname: String): void
                +updateCustomerLastName(id: String, lname: String): void
                +updateCustomer...(id: String, ...): void

                +getCustomerFirstName(id: String): String
                +getCustomerLastName(id: String): String
                +getCustomer...(id: String): String

            }
            class CustomerList {
                -customers: LinkedHashSet<Customer>
                +addNewCustomer(customer: Customer): void
                +updateExistingCustomer(customer: Customer): void
                +removeCustomer(customer: Customer): void
                +searchCustomer(id: int): Customer
                +searchCustomer(name: String): Customer
                +searchCustomer(type: char): Customer
            }
            class Customer {
                - clientId: int
                - firstName: String
                - lastName: String
                - address: String
                - postalCode: String
                - phoneNum: String
                - customerType: char
            }
        }
    }
}


InvModelController ..> InventoryManager : controls
CustModelController ...> CustomerManager : controls

CustomerManager o-- "1" CustomerList
CustomerList o-- "*" Customer


InventoryManager o-- "1" Inventory
InventoryManager o-- "1   " SupplierList
Inventory o-- " * " Item
SupplierList o-- " * " Supplier
Item "*" - "1" Supplier : "     "
Inventory o--- "1" Order

Order "1" - "*" OrderLine
Item "1" --o OrderLine
@enduml
```

## UML Diagram for server.sconntroller
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

interface Runnable
interface Serializable


package server {
    package scontroller {
        package servercontroller {
            /'
            interface Subject {
                +register(Observer o): void
                +remove(Observer o): void
                +notifyObservers(): void
            }
            class InventoryUpdater implements Subject {
                -inventoryObservers: ArrayList
                +remove(Observer o): void
                +notifyObservers(): void
            }
            class SupplierUpdater implements Subject {
                -supplierObservers: ArrayList
                +register(Observer o): void
                +remove(Observer o): void
                +notifyObservers(): void
            }
            class CustomerUpdater implements Subject {
                -customerObservers: ArrayList
                +register(Observer o): void
                +remove(Observer o): void
                +notifyObservers(): void
            }
            '/

            class ServerController implements Runnable {
                -clientSocket: Socket
                -socketIn: ObjectInputStream
                -socketOut: ObjectOutputStream
                -inventoryController: InvModelController
                -customerController: CustModelController
                +createMessage(): Message
                +sendMessage(message: Message, socketOut: OutputStream): void
                +readMessage(message: Message): void
                +run(): void
            }

            class ToolShopServer {
                -serverControllers: ExecutorService
            }

            class Message implements Serializable {
                -text: String
                -inventory: Inventory
                -supplierList: SupplierList
                -customerList: CustomerList 
            }
        }

        package smodelcontroller {
            class InvModelController {
                - inventoryManager: InventoryManager
                -inventoryDBController: InvDBController
                +updateInvModel(inventory: Inventory, supplierList: SupplierList): void
            }
            class CustModelController {
                -customerManager: CustomerManager
                -custDBController: InvDBController
                +updateCustModel(customerList: CustomerList): void
            }
        }
        
        package databasecontroller {
            class InvDBController {
                +readFromItemDB(query: String): LinkedHashSet<Item>
                +readFromSupplierDB(query: String): LinkedHashSet<Supplier>
                +updateItemDB(query: String): void
                +updateSupplierDB(query: String): void
                +insertToItemDB(query: String): void
                +insertToSupplierDB(query: String): void
                +deleteFromItemDB(query: String): void
                +deleteFromSupplierDB(query: String): void
            }
            class CustDBController {
                +readFromCustomerDB(query: String): LinkedHashSet<Supplier>
                +updateCustomerDB(query: String): void
                +insertToCustomerDB(query: String): void
                +deleteFromCustomerDB(query: String): void
            }
        }
    }
    
    Message <. ServerController  : creates
    ServerController o--- "1" InvModelController
    ServerController o--- "1" CustModelController
    
    InvModelController o-- "1" InvDBController
    CustModelController o-- "1" CustDBController

    ToolShopServer *--- " *  " ServerController : run on thread pool

}

    Client ..> ToolShopServer : establishes socket connection
    ClientController <.... ServerController : socket comms.


@enduml
```