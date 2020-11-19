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

package client {
    package view
    package ccontroller {
        package clientcontroller
        package viewcontroller
        package cmodelcontroller
    }
    package cmessagemodel
}

package server {
    package scontroller {
        package servercontroller
        package modelcontroller
        package databasecontroller
    }
    package model {
        package messagemodel
    }
}

view <.. viewcontroller : updates
cmessagemodel <. cmodelcontroller : updates
viewcontroller <.. clientcontroller 
clientcontroller .> cmodelcontroller

clientcontroller ...> servercontroller : " comms. through socket"

servercontroller <.. modelcontroller
modelcontroller .> databasecontroller
modelcontroller ..> model : updates

hide members
@enduml
```