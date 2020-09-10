package mysql;

import com.janus.GameSettings;
import com.janus.model.definitions.NpcDefinition;
import com.janus.world.World;

public class BossTaskYeller {


    public static long time = System.currentTimeMillis();

    public static void bossTaskYeller() {
        NpcDefinition currentBossName = NpcDefinition.forId(GameSettings.CURRENT_BOSS);
        if (System.currentTimeMillis() - time > 1800000) {
            World.sendMessage("This weeks boss assignment is @blu@"+currentBossName.getName()+"@bla@!");
        }
    }

}
