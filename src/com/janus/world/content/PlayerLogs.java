package com.janus.world.content;

import com.janus.GameServer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PlayerLogs {

    /**
     * Log file path
     **/
    private static final String FILE_PATH = "./data/saves/logs/";
    private static final String CHAT_LOG_PATH = "./data/saves/chatlogs/";
    private static final String TRADE_LOG_PATH = "./data/saves/tradelogs/";
    private static final String DAILY_REWARD_DIRECTORY = "./data/saves/daily_rewards/";

    /**
     * Fetches system time and formats it appropriately
     *
     * @return Formatted time
     */
    private static String getTime() {
        Date getDate = new Date();
        String timeFormat = "M/d/yy hh:mma";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return "[" + sdf.format(getDate) + "]\t";
    }

    /**
     * Writes formatted string to text file
     *
     * @param file     - File to write data to
     * @param ORE_DATA - Data to written
     * @throws IOException
     */
    public static void log(String file, String writable) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                FileWriter fw = new FileWriter(FILE_PATH + file + ".txt", true);
                if (fw != null) {
                    fw.write(getTime() + writable + "\t");
                    fw.write(System.lineSeparator());
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void createDailyRewardFile(String writable) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                FileWriter fw = new FileWriter(DAILY_REWARD_DIRECTORY + LocalDate.now() + ".txt", true);
                if (fw != null) {
                    fw.write(getTime() + writable + "\t");
                    fw.write(System.lineSeparator());
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void logTrade(String name, String writable) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                FileWriter fw = new FileWriter(TRADE_LOG_PATH + name + ".txt", true);
                if (fw != null) {
                    fw.write(getTime() + writable + "\t");
                    fw.write(System.lineSeparator());
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void logMasterChatLog(String file, String writable) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                FileWriter fw = new FileWriter(CHAT_LOG_PATH + file + ".txt", true);
                if (fw != null) {
                    fw.write(getTime() + writable + "\t");
                    fw.write(System.lineSeparator());
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void readTradeLog(String name) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(TRADE_LOG_PATH + name + ".txt"));
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                //DiscordMessenger.sendTradeLog(line);
            }
            r.close();
            File file = new File(name);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void eraseFile(String name) {
        try {
            File file = new File(name);
            file.delete();
            log(name,
                    "\t <----------------- File automatically cleaned ----------------->");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
