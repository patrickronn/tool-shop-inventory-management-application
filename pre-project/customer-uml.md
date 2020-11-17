# Lab 3 Task 1 UML Diagram
Created by: Patrick Linang

Below is my updated UML diagram which is based on the UML discussed in lecture.
My program for this task uses this class design rather than my design from the
previous lab.

## UML Diagram for Tool Shop App
![UML Tool Shop](toolshop_uml.png)
## PlantUML code for creating UML
```plantuml
@startuml
skinparam classAttributeIconSize 0

package toolshop {
package frontend {
class MenuCLI
}

package backend {
class FileMgr
class Shop
class Inventory
class SupplierList
class Item
class Supplier
class Order
class OrderLine
}
}
MenuCLI ...> Shop : "  menu for"
FileMgr <. Shop : loads data with
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