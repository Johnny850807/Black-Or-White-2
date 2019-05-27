package com.pokewords.framework.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author johnny850807 (waterball)
 */
public interface Client {

    String getIp();

    int getId();

    void broadcast(byte[] rawMessage) throws IOException;

    InputStream getInputStream();

    OutputStream getOutputStream();
}
