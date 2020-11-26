package server.controller.databasecontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDBController {
    private Connection jdbc_connection;
    private PreparedStatement statement;

    public OrderDBController(Connection jdbc_connection) {
        this.jdbc_connection = jdbc_connection;
        this.statement = null;
    }
}
