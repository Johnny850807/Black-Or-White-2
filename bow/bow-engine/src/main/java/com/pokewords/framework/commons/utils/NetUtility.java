package com.pokewords.framework.commons.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtility {
    public static String getIp() {
        try {
           return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
