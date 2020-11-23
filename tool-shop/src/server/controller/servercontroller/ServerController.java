package server.controller.servercontroller;

import server.controller.modelcontroller.Deserializer;
import server.controller.modelcontroller.ModelController;
import server.controller.modelcontroller.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerController {
    /**
     * Waits for requests to come in through the network.
     */
    private ServerSocket serverSocket;

    /**
     * Used to accept client connection.
     */
    private Socket clientSocket;

    /**
     * Controls the maximum # of clients that can connect in parallel.
     */
    private final static int MAX_CONCURRENT_CLIENTS = 3;

    /**
     * Thread pool for communicating with multiple clients parallel.
     */
    private ExecutorService clientPool;

    /**
     * Instantiates a server to a specified port and a thread pool.
     * @param portNumber name of port to bind (e.g. 8099)
     */
    public ServerController(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            clientPool = Executors.newFixedThreadPool(MAX_CONCURRENT_CLIENTS);
            System.out.println("Server: now running on " + serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.err.println("Error creating server");
        }
    }

    public void runServer() {
        try {
            while (true) {
                // Wait for new client connection
                clientSocket = serverSocket.accept();
                System.out.println("Server: a new player client has connected on " + clientSocket.getRemoteSocketAddress());
                addClientToPool();
            }
        } catch (IOException e) {
            System.out.println("Server: IO error occurred with new client connection");
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public void addClientToPool() {
        ModelController modelController = new ModelController(this, new Serializer(), new Deserializer());
        clientPool.execute(modelController);
    }

    public void shutdown() {
        clientPool.shutdown();
        System.out.println("Server: now shutting down.");
        try {
            clientPool.awaitTermination(1, TimeUnit.HOURS);
            serverSocket.close();
            clientSocket.close();
        } catch (InterruptedException e) {
            System.out.println("Server: a thread was interrupted while attempting to shutdown.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Server: error while shutting down socket comms.");
            System.exit(1);
        }
    }

    public OutputStream getClientSocketOutStream() {
        try {
            return clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server.");
            System.exit(1);
        }
        return null;
    }

    public InputStream getClientSocketInStream() {
        try {
            return clientSocket.getInputStream();
        } catch (IOException e) {
            System.err.println("Error connecting to server");
            System.exit(1);
        }
        return null;
    }}