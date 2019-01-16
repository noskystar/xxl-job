//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xxl.job.admin.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtil {
    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);
    private static final String ANYHOST = "0.0.0.0";
    private static final String LOCALHOST = "127.0.0.1";
    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static volatile String LOCAL_ADDRESS = null;

    public IpUtil() {
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
            String name = address.getHostAddress();
            return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
        } else {
            return false;
        }
    }

    private static InetAddress getFirstValidAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = (InetAddress)addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable var5) {
                                    logger.error("Failed to retriving ip address, " + var5.getMessage(), var5);
                                }
                            }
                        }
                    } catch (Throwable var6) {
                        logger.error("Failed to retriving ip address, " + var6.getMessage(), var6);
                    }
                }
            }
        } catch (Throwable var7) {
            logger.error("Failed to retriving ip address, " + var7.getMessage(), var7);
        }

        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var4) {
            logger.error("Failed to retriving ip address, " + var4.getMessage(), var4);
        }

        logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return null;
    }

    private static String getAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        } else {
            InetAddress localAddress = getFirstValidAddress();
            LOCAL_ADDRESS = localAddress.getHostAddress();
            return LOCAL_ADDRESS;
        }
    }

    public static String getIp() {
        return getAddress();
    }

    public static String getIpPort(int port) {
        String ip = getIp();
        return getIpPort(ip, port);
    }

    public static String getIpPort(String ip, int port) {
        return ip == null ? null : ip.concat(":").concat(String.valueOf(port));
    }

    public static Object[] parseIpPort(String address) {
        String[] array = address.split(":");
        String host = array[0];
        int port = Integer.parseInt(array[1]);
        return new Object[]{host, port};
    }
}
