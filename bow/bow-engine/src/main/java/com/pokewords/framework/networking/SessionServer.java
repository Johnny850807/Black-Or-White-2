package com.pokewords.framework.networking;

import com.pokewords.framework.commons.utils.NetUtility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author johnny850807 (waterball)
 */
public class SessionServer {
    private String ip;
    private final int port;
    private List<ClientListener> clientListeners = new ArrayList<>(2);
    private List<Client> clients = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;
    private boolean running = false;

    public SessionServer(int port) {
        this.port = port;
        this.ip = NetUtility.getIp();
    }

    public void addClientListener(ClientListener clientListener) {
        clientListeners.add(clientListener);
    }

    public void removeClientListener(ClientListener clientListener) {
        clientListeners.remove(clientListener);
    }

    public void launchServer() {
        running = true;
        initServerSocket();
        while (running)
            acceptNextClient();
    }

    private void initServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptNextClient() {
        try {
            int nextId = clientListeners.size();
            SocketClient nextClient = new SocketClient(nextId, serverSocket.accept());
            clients.add(nextClient);
            clientListeners.forEach(clientListener -> clientListener.onClientConnected(nextClient));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public Client getClientById(int clientId) {
        return clients.stream().filter(c -> c.getId() == clientId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The client with the id " + clientId + " is not found."));
    }

    public void broadcastToAllClients(byte[] rawMessage) {
        for (Client client : clients) {
            try {
                client.broadcast(rawMessage);
            } catch (IOException e) {
                e.printStackTrace();
                clients.remove(client);
                clientListeners.forEach(clientListener -> clientListener.onClientDisconnected(client));
            }
        }
    }

    public void shutDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ClientListener {
        void onClientConnected(Client client);

        void onClientDisconnected(Client client);
    }

}
