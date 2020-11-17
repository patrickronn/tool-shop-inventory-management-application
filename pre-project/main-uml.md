# Tool Shop Project - ENSF 607/608
UML diagram name: Main Tool Shop UML 
<br>
Created by: Patrick Linang
<br>
Course: ENSF 607

This UML represents the overall higher-level design of the tool shop system

## UML Diagram for Tool Shop System
## PlantUML code
```plantuml
@startuml
skinparam classAttributeIconSize 0

package inventory {
    package iview
    package icontroller
    package imodel
}

package customer {
    package cview
    package ccontroller
    package cmodel
}

cloud {
    package server {
        package scontroller
        package inventorymodel
        package customermodel
    }
    
    database shop_db
}

icontroller --- scontroller
ccontroller --- scontroller
scontroller --> shop_db

hide members
@enduml
```