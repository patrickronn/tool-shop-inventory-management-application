# project-ensf-607-608
Submitted by: Patrick Linang

## Description
A small retail shop that sells tools requires an application to manage inventory of different types of tools it sells. The store owner wants to be able to modify the store’s inventory by adding new tools, and deleting tools. The owner also wants to be able to search the inventory for tools by tool name, and by tool id. Currently, the information about tools available in the shop and suppliers is stored in two text files.

## Approach
In this project, I designed a framework from scratch in order to design a inventory management system. My distributed system uses the client-server architectural patern as well as the MVC design pattern. This is a Java application with a GUI interface and JDBC for connecting to a MySQL server.

## Instructions/Files
- For my UML class/architecture diagrams and database schema design, please see `preproject_linang_patrick` directory
- For tool shop server project files, please see `tool_shop_server_linang_patrick` directory
- For tool shop client project files, please see `tool_shop_client_linang_patrick` directory
    - Please note that I developed my GUIs using IntelliJ GUI Designer. If
    you experience errors with client view/GUIs please add a dependency to the file `forms_rt.jar`
    (a similar fashion to how you add the JDBC jar file)
    - This file is located in `tool-shop-client/lib/forms_rt.jar`

![CustomerManagementGUI](https://user-images.githubusercontent.com/61221025/115328543-ff237680-a14d-11eb-8e0c-f8424153a678.png)

*Screenshot: Customer Management GUI*


![CustomerManagementGUI2](https://user-images.githubusercontent.com/61221025/115328811-76590a80-a14e-11eb-86ff-c0a71da95d16.png)

*Screenshot: Inventory Management Menu*
