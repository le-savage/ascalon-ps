package com.AscalonPS;

import com.AscalonPS.util.ShutdownHook;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The starting point of Arlania.
 *
 * @author Gabriel
 * @author Samy
 */
public class GameServer {

    private static final GameLoader loader = new GameLoader(GameSettings.GAME_PORT);
    private static final Logger logger = Logger.getLogger("Janus");
    private static boolean updating;
    public static String serverHost;


    static {
        try {
            serverHost = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    static {
        if (!serverHost.contains("65fa444c")) {
            GameSettings.DEVELOPERSERVER = true;
        }
    }


    public static void main(String[] params) {

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        try {
            if (GameSettings.DEVELOPERSERVER) {
                logger.info("Launching the - DEVELOPER DEVELOPER DEVELOPER DEVELOPER - server!");
            } else {
                logger.info("Launching the - LIVE LIVE LIVE LIVE - server!");
            }
            logger.info("Server Host Name is " + serverHost);

            logger.info("Initializing the loader...");
            loader.init();
            loader.finish();
            logger.info("The loader has finished loading utility tasks.");
            logger.info("Janus is now online on port " + GameSettings.GAME_PORT + "!");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not start Janus! Program terminated.", ex);
            System.exit(1);
        }
    }

    public static GameLoader getLoader() {
        return loader;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setUpdating(boolean updating) {
        GameServer.updating = updating;
    }

    public static boolean isUpdating() {
        return GameServer.updating;
    }

}