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

/'
package inventoryclient {
    package iview
    package icontroller
    package imodel
}

package customerclient {
    package cview
    package ccontroller
    package cmodel
}
'/

package client {
    package view
    package ccontroller {
        package clientcontroller
        package cviewcontroller
        package cmodelcontroller
    }
    package model
}

package server {
    package scontroller {
        package servercontroller
        package smodelcontroller
        package databasecontroller
    }
    package smodel {
        package inventorymodel
        package customermodel
    }
}

clientcontroller --- servercontroller : " +comms. through socket"

servercontroller <.. smodelcontroller
smodelcontroller .> databasecontroller

smodelcontroller ..> smodel


hide members
@enduml
```