package mysql;

import com.janus.GameSettings;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Gabriel Hannason
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MySQLController {

    public static final ExecutorService SQL_SERVICE = Executors.newSingleThreadExecutor();
    private static MySQLController CONTROLLER;
    private static MySQLDatabase[] DATABASES = new MySQLDatabase[2];
    /*private static Store STORE = new Store();*/
    /*private static Motivote VOTE;*/

    public MySQLController() {
        /* DATABASES */
        DATABASES = new MySQLDatabase[]{


        };

	/*	VOTE = new Motivote(new Voting(), "http://runeunity.org/vote/", "0ae2dc6c");
		VOTE.start();*/

        MySQLProcessor.process();
    }

    public static void toggle() {
        if (GameSettings.MYSQL_ENABLED) {
            MySQLProcessor.terminate();
            /*VOTE.terminate();*/
            CONTROLLER = null;
            DATABASES = null;
            GameSettings.MYSQL_ENABLED = false;
        } else if (!GameSettings.MYSQL_ENABLED) {
            init();
            GameSettings.MYSQL_ENABLED = true;
        }
    }

/*	public static Store getStore() {
		return STORE;
	}*/

    public static void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CONTROLLER = new MySQLController();
    }

    /* NON STATIC CLASS START */

    public static MySQLController getController() {
        return CONTROLLER;
    }

    public MySQLDatabase getDatabase(Database database) {
        return DATABASES[database.ordinal()];
    }


    public enum Database {
		/*HIGHSCORES,
		RECOVERY,
		GRAND_EXCHANGE;*/
    }

    private static class MySQLProcessor {

        private static boolean running;

        private static void terminate() {
            running = false;
        }

        public static void process() {
            if (running) {
                return;
            }
            running = true;
            SQL_SERVICE.submit(new Runnable() {
                public void run() {
                    try {
                        while (running) {
                            if (!GameSettings.MYSQL_ENABLED) {
                                terminate();
                                return;
                            }
                            for (MySQLDatabase database : DATABASES) {

                                if (!database.active) {
                                    continue;
                                }

                                if (database.connectionAttempts >= 5) {
                                    database.active = false;
                                }

                                Connection connection = database.getConnection();
                                try {
                                    connection.createStatement().execute("/* ping */ SELECT 1");
                                } catch (Exception e) {
                                    database.createConnection();
                                }
                            }
                            Thread.sleep(25000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
