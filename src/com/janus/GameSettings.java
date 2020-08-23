package com.janus;

import com.janus.model.Position;
import com.janus.net.security.ConnectionHandler;
import com.janus.util.Misc;

import java.math.BigInteger;

public class GameSettings {

    /**
     * Time For Saving Players
     */

    public static final int charcterSavingInterval = 60000;
    /**
     * The game port
     */
    public static final int GAME_PORT = 43594;
    /**
     * The game version
     */
    public static final int GAME_VERSION = 13;
    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGIN_THRESHOLD = 100;
    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGOUT_THRESHOLD = 100;
    /**
     * The maximum amount of players who can receive rewards on a single game
     * sequence.
     */
    public static final int VOTE_REWARDING_THRESHOLD = 15;
    /**
     * The maximum amount of connections that can be active at a time, or in
     * other words how many clients can be logged in at once per connection.
     * (0 is counted too)
     */
    public static final int CONNECTION_AMOUNT = 6;
    /**
     * The throttle interval for incoming connections accepted by the
     * {@link ConnectionHandler}.
     */
    public static final long CONNECTION_INTERVAL = 1000;
    /**
     * The number of seconds before a connection becomes idle.
     */
    public static final int IDLE_TIME = 15;
    /**
     * The keys used for encryption on login
     */
    public static final BigInteger RSA_MODULUS = new BigInteger("127048629410177568132745837405512405495629717586780737322677319371236449155841898668411940128611658015069017801122466945914062727680876479615883209354712135863653074434143395498392620180663621384185843356496365676879787891757819708416125917625189734733790284252729350812231864282638880775736941668733640160531");
    public static final BigInteger RSA_EXPONENT = new BigInteger("55422025210302065937517291691505472644682515160725613002090756114931700335937928819311670603428272441411845978856069205437190279104453631929116149232370486982607948579100747706711922710855471901429903384093366415879813893129750059104977799838626687126042043765266715289244452303359023069164571556762364330017");
    /**
     * The maximum amount of messages that can be decoded in one sequence.
     */
    public static final int DECODE_LIMIT = 30;
    /**
     * Processing the engine
     */
    public static final int ENGINE_PROCESSING_CYCLE_RATE = 600;
    public static final int GAME_PROCESSING_CYCLE_RATE = 600;

    /** GAME **/
    /**
     * The default position
     */
    public static final Position DEFAULT_POSITION = new Position(2704, 5349);
    public static final Position INSTANCE_ARENA = new Position(2717, 5324);
    public static final int MAX_STARTERS_PER_IP = 2;
    /**
     * Untradeable items
     * Items which cannot be traded or staked
     */
    public static final int[] UNTRADEABLE_ITEMS =
            {
                    6759, 7584, 19890, 19670, 12852, 14666, 13727, 21001, 21002, 21003, 21004, 21005, 21006, 21007, 21008, 21009, 21010, 21011, 21012, 21013, 21014, 21015, 21016, 21017, 21018, 21019, 21020, 21021, 21022, 21023, 21024, 21025, 7774, 14090, 14091, 11995, 11996, 11997, 11978, 12001, 12002, 12003, 12004, 12005, 12006, 11990, 11991, 11992, 11993, 11994, 11989, 11988, 11987, 11986, 11985, 11984, 11983, 11982, 11981, 11979, 20079, 20103, 20080, 20081, 20082, 20083, 14914, 20086, 14916, 20087, 20088, 20089, 20090, 20085,
                    18349, 18351, 18353, 20072, 19634, 19635, 19642, 19643, 19644, 19711, 19712, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804,
                    14022, 14020, 14021, 6570, 14019, 20747,
            };
    public static final String[] INVALID_NAMES = {"mod", "moderator", "admin", "administrator", "owner", "developer",
            "supporter", "dev", "developer", "nigga", "0wn3r", "4dm1n", "m0d", "adm1n", "a d m i n", "m o d",
            "o w n e r"};
    public static final int
            ATTACK_TAB = 0,
            SKILLS_TAB = 1,
            QUESTS_TAB = 2,
            ACHIEVEMENT_TAB = 14,
            INVENTORY_TAB = 3,
            EQUIPMENT_TAB = 4,
            PRAYER_TAB = 5,
            MAGIC_TAB = 6,
            SUMMONING_TAB = 13,
            FRIEND_TAB = 8,
            IGNORE_TAB = 9,
            CLAN_CHAT_TAB = 7,
            LOGOUT = 10,
            OPTIONS_TAB = 11,
            EMOTES_TAB = 12;
    /**
     * Dzone activation
     */
    public static boolean DZONEON = false;
    /**
     * Dev server?
     */
    public static boolean DEVELOPERSERVER = false;
    /**
     * Are the MYSQL services enabled?
     */
    public static boolean MYSQL_ENABLED = true;
    /**
     * Is it currently bonus xp?
     */
    public static boolean BONUS_EXP = Misc.isWeekend();
    /**
     * Unsellable items
     * Items which cannot be sold to shops
     */
    public static int UNSELLABLE_ITEMS[] = new int[]{
            6759, 13204, 19890, 19670, 12852, 14666, 13727, 21001, 21002, 21003, 21004, 21005, 21006, 21007, 21008, 21009, 21010, 21011, 21012, 21013, 21014, 21015, 21016, 21017, 21018, 21019, 21020, 21021, 21022, 21023, 21024, 21025, 18349, 18351, 18353, 995, 18349, 18351, 18353, 20072, 19634, 19635, 19642, 19643, 19644, 19711, 19712, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 6570, 14019, 20747, 19785, 19786, 19787, 19788, 19789, 19790, 19803, 19804, 8839, 8840, 8841, 8842, 19711, 19712
    };
}
