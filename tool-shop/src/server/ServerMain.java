package server;

import server.controller.servercontroller.ServerController;

public class ServerMain {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(8099);
        serverController.runServer();
    }
}
