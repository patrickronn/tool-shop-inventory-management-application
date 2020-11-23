import server.controller.modelcontroller.Deserializer;
import server.controller.modelcontroller.Serializer;
import server.controller.servercontroller.ServerController;

public class ServerMain {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(8099);
        Serializer serializer = new Serializer();
        Deserializer deserializer = new Deserializer();

        serverController.runServer();
    }
}
