package com.pokewords.framework.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author johnny850807 (waterball)
 */
public class SocketClient implements Client {
    private Socket socket;
    private int id;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketClient(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public String getIp() {
        return socket.getInetAddress().getHostAddress();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void broadcast(byte[] rawMessage) throws IOException {
        outputStream.write(rawMessage);
        outputStream.flush();
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
