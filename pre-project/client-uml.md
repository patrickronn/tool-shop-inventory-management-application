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

interface ActionListener
interface Serializable 
package client {
    package ccontroller {
        package clientcontroller {
            class ClientController
            class Message implements Serializable {
                -text: String
                -inventory: Inventory
                -supplierList: SupplierList
                -customerList: CustomerList 
            }
        }
        package cmodelcontroller {
            class InvModelController
            class CustModelController
        }
        package cviewcontroller {
            class ViewController
            class CustomerGUIListener implements ActionListener
            class InventoryGUIListener implements ActionListener
        }
    }
}

ClientController o--- InvModelController
ClientController o-- CustModelController
ClientController o--- ViewController
ViewController ...> InventoryGUIListener
ViewController ..> CustomerGUIListener

Message <. ClientController : creates

ClientController .> ToolShopServer : connects via socket
ClientController <.. ServerController : "   socket comms."


@enduml
```

## UML Diagram for client.cmodel and client.cview
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

package client {
    package ccontroller {
        package cmodelcontroller {
            class InvModelController
            class CustModelController
        }
        package cviewcontroller {
            class ViewController
            class CustomerGUIListener
            class InventoryGUIListener
        }
    }

    package model {
        package inventorymodel {
        }

        package customermodel {
        }
    }

    package view {
        class CustomerGUI
        class InventoryGUI
    }
}

InvModelController ..> inventorymodel : controls
CustModelController ..> customermodel : controls

ViewController ..> InventoryGUIListener
ViewController ...> CustomerGUIListener

InventoryGUI <.. InventoryGUIListener : listens for button presses
CustomerGUI <. CustomerGUIListener

@enduml
```