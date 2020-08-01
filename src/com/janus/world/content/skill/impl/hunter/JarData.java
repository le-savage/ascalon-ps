package com.janus.world.content.skill.impl.hunter;

import com.janus.model.Item;
import com.janus.model.ItemRarity;
import com.janus.util.Misc;

public enum JarData {

    BABY_IMPLING_JAR(11238, new Item(1755).setRarity(ItemRarity.COMMON), new Item(1734).setRarity(ItemRarity.COMMON), new Item(1733).setRarity(ItemRarity.COMMON), new Item(946).setRarity(ItemRarity.COMMON), new Item(1985).setRarity(ItemRarity.COMMON), new Item(2347).setRarity(ItemRarity.COMMON), new Item(1759).setRarity(ItemRarity.COMMON), new Item(1927).setRarity(ItemRarity.UNCOMMON), new Item(319).setRarity(ItemRarity.UNCOMMON), new Item(2007).setRarity(ItemRarity.UNCOMMON), new Item(1779).setRarity(ItemRarity.UNCOMMON), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(401).setRarity(ItemRarity.UNCOMMON), new Item(1438).setRarity(ItemRarity.UNCOMMON), new Item(2355).setRarity(ItemRarity.RARE), new Item(1607).setRarity(ItemRarity.RARE), new Item(1743).setRarity(ItemRarity.RARE), new Item(379).setRarity(ItemRarity.RARE), new Item(1761).setRarity(ItemRarity.RARE)),
    YOUNG_IMPLING_JAR(11240, new Item(1353).setRarity(ItemRarity.COMMON), new Item(1097).setRarity(ItemRarity.UNCOMMON), new Item(1157).setRarity(ItemRarity.COMMON), new Item(1539).setRarity(ItemRarity.COMMON).setAmount(5), new Item(8778).setRarity(ItemRarity.COMMON), new Item(2293).setRarity(ItemRarity.COMMON), new Item(1783).setRarity(ItemRarity.COMMON).setAmount(4), new Item(133).setRarity(ItemRarity.UNCOMMON).setAmount(3), new Item(453).setRarity(ItemRarity.COMMON), new Item(7936).setRarity(ItemRarity.COMMON), new Item(7936).setRarity(ItemRarity.COMMON), new Item(2359).setRarity(ItemRarity.RARE), new Item(1777).setRarity(ItemRarity.UNCOMMON), new Item(361).setRarity(ItemRarity.UNCOMMON), new Item(361).setRarity(ItemRarity.UNCOMMON), new Item(231).setRarity(ItemRarity.COMMON), new Item(1761).setRarity(ItemRarity.COMMON)),
    GOURMET_IMPLING_JAR(11242, new Item(365).setRarity(ItemRarity.COMMON), new Item(361).setRarity(ItemRarity.COMMON), new Item(2011).setRarity(ItemRarity.COMMON), new Item(2327).setRarity(ItemRarity.COMMON), new Item(1897).setRarity(ItemRarity.COMMON), new Item(2293).setRarity(ItemRarity.COMMON), new Item(5004).setRarity(ItemRarity.COMMON), new Item(1883).setRarity(ItemRarity.UNCOMMON), new Item(247).setRarity(ItemRarity.UNCOMMON), new Item(380).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(386).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(7754).setRarity(ItemRarity.UNCOMMON), new Item(7178).setRarity(ItemRarity.UNCOMMON), new Item(7188).setRarity(ItemRarity.UNCOMMON), new Item(10137).setRarity(ItemRarity.RARE), new Item(7179).setRarity(ItemRarity.RARE).setAmount(6), new Item(374).setRarity(ItemRarity.RARE).setAmount(3), new Item(10136).setRarity(ItemRarity.RARE), new Item(5406).setRarity(ItemRarity.RARE), new Item(2007).setRarity(ItemRarity.COMMON), new Item(5970).setRarity(ItemRarity.COMMON)),
    EARTH_IMPLING_JAR(11244, new Item(6032).setRarity(ItemRarity.COMMON), new Item(557).setRarity(ItemRarity.COMMON).setAmount(32), new Item(6032).setRarity(ItemRarity.COMMON), new Item(5535).setRarity(ItemRarity.COMMON), new Item(1440).setRarity(ItemRarity.COMMON), new Item(1442).setRarity(ItemRarity.COMMON), new Item(444).setRarity(ItemRarity.COMMON), new Item(5104).setRarity(ItemRarity.COMMON), new Item(2353).setRarity(ItemRarity.COMMON), new Item(1784).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(454).setRarity(ItemRarity.UNCOMMON).setAmount(6), new Item(5294).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(447).setRarity(ItemRarity.UNCOMMON), new Item(1273).setRarity(ItemRarity.UNCOMMON), new Item(1487).setRarity(ItemRarity.UNCOMMON), new Item(5311).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(1606).setRarity(ItemRarity.RARE).setAmount(2), new Item(448).setRarity(ItemRarity.RARE).setAmount(3), new Item(6035).setRarity(ItemRarity.RARE).setAmount(2), new Item(5303).setRarity(ItemRarity.VERY_RARE), new Item(1603).setRarity(ItemRarity.VERY_RARE)),
    ESSENCE_IMPLING_JAR(11246, new Item(562).setRarity(ItemRarity.COMMON).setAmount(4), new Item(555).setRarity(ItemRarity.COMMON).setAmount(22), new Item(558).setRarity(ItemRarity.COMMON).setAmount(25), new Item(556).setRarity(ItemRarity.COMMON).setAmount(30), new Item(559).setRarity(ItemRarity.COMMON).setAmount(28), new Item(554).setRarity(ItemRarity.COMMON).setAmount(50), new Item(1448).setRarity(ItemRarity.COMMON), new Item(7937).setRarity(ItemRarity.COMMON).setAmount(35), new Item(1437).setRarity(ItemRarity.COMMON).setAmount(20), new Item(564).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4695).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4696).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4698).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4694).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4699).setRarity(ItemRarity.RARE).setAmount(4), new Item(4697).setRarity(ItemRarity.RARE).setAmount(4), new Item(565).setRarity(ItemRarity.RARE).setAmount(7), new Item(566).setRarity(ItemRarity.RARE).setAmount(11), new Item(563).setRarity(ItemRarity.RARE).setAmount(13), new Item(563).setRarity(ItemRarity.RARE).setAmount(13), new Item(560).setRarity(ItemRarity.RARE).setAmount(13), new Item(1442).setRarity(ItemRarity.RARE).setAmount(4)),
    ECLECTIC_IMPLING_JAR(11248, new Item(1391).setRarity(ItemRarity.VERY_RARE), new Item(1273).setRarity(ItemRarity.COMMON), new Item(2493).setRarity(ItemRarity.RARE), new Item(10083).setRarity(ItemRarity.RARE), new Item(562).setRarity(ItemRarity.COMMON), new Item(1199).setRarity(ItemRarity.UNCOMMON), new Item(1213).setRarity(ItemRarity.RARE), new Item(5970).setRarity(ItemRarity.COMMON), new Item(231).setRarity(ItemRarity.COMMON), new Item(4527).setRarity(ItemRarity.UNCOMMON), new Item(444).setRarity(ItemRarity.UNCOMMON), new Item(450).setRarity(ItemRarity.RARE).setAmount(10), new Item(556).setRarity(ItemRarity.COMMON).setAmount(43), new Item(2358).setRarity(ItemRarity.RARE).setAmount(5), new Item(7937).setRarity(ItemRarity.UNCOMMON).setAmount(30), new Item(237).setRarity(ItemRarity.UNCOMMON), new Item(1601).setRarity(ItemRarity.VERY_RARE), new Item(5759).setRarity(ItemRarity.RARE), new Item(7208).setRarity(ItemRarity.RARE), new Item(8779).setRarity(ItemRarity.COMMON).setAmount(4), new Item(5321).setRarity(ItemRarity.RARE).setAmount(3)),
    NATURE_IMPLING_JAR(11250, new Item(5100).setRarity(ItemRarity.COMMON), new Item(5104).setRarity(ItemRarity.COMMON), new Item(5281).setRarity(ItemRarity.COMMON), new Item(5294).setRarity(ItemRarity.COMMON), new Item(5295).setRarity(ItemRarity.RARE), new Item(5297).setRarity(ItemRarity.UNCOMMON), new Item(5299).setRarity(ItemRarity.UNCOMMON), new Item(5298).setRarity(ItemRarity.UNCOMMON), new Item(5303).setRarity(ItemRarity.VERY_RARE), new Item(5304).setRarity(ItemRarity.VERY_RARE), new Item(5313).setRarity(ItemRarity.UNCOMMON), new Item(5286).setRarity(ItemRarity.UNCOMMON), new Item(3051).setRarity(ItemRarity.RARE), new Item(3000).setRarity(ItemRarity.RARE), new Item(219).setRarity(ItemRarity.VERY_RARE), new Item(5974).setRarity(ItemRarity.UNCOMMON), new Item(6016).setRarity(ItemRarity.COMMON), new Item(1513).setRarity(ItemRarity.COMMON), new Item(253).setRarity(ItemRarity.COMMON), new Item(269).setRarity(ItemRarity.VERY_RARE)),
    MAGPIE_IMPLING_JAR(11252, new Item(1681).setRarity(ItemRarity.COMMON), new Item(1682).setRarity(ItemRarity.UNCOMMON).setAmount(3), new Item(1731).setRarity(ItemRarity.COMMON), new Item(1732).setRarity(ItemRarity.UNCOMMON).setAmount(3), new Item(2568).setRarity(ItemRarity.COMMON), new Item(2569).setRarity(ItemRarity.UNCOMMON).setAmount(3), new Item(3391).setRarity(ItemRarity.UNCOMMON), new Item(2570).setRarity(ItemRarity.UNCOMMON), new Item(4097).setRarity(ItemRarity.UNCOMMON), new Item(4095).setRarity(ItemRarity.UNCOMMON), new Item(1215).setRarity(ItemRarity.RARE), new Item(1185).setRarity(ItemRarity.RARE), new Item(5541).setRarity(ItemRarity.COMMON), new Item(1747).setRarity(ItemRarity.COMMON), new Item(2363).setRarity(ItemRarity.UNCOMMON), new Item(1603).setRarity(ItemRarity.UNCOMMON), new Item(1755).setRarity(ItemRarity.COMMON), new Item(1734).setRarity(ItemRarity.COMMON), new Item(1733).setRarity(ItemRarity.COMMON), new Item(946).setRarity(ItemRarity.COMMON), new Item(1985).setRarity(ItemRarity.COMMON), new Item(2347).setRarity(ItemRarity.COMMON), new Item(1759).setRarity(ItemRarity.COMMON), new Item(1927).setRarity(ItemRarity.UNCOMMON), new Item(319).setRarity(ItemRarity.UNCOMMON), new Item(2007).setRarity(ItemRarity.UNCOMMON), new Item(1779).setRarity(ItemRarity.UNCOMMON), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(401).setRarity(ItemRarity.UNCOMMON), new Item(1438).setRarity(ItemRarity.UNCOMMON), new Item(2355).setRarity(ItemRarity.RARE), new Item(1607).setRarity(ItemRarity.RARE), new Item(1743).setRarity(ItemRarity.RARE), new Item(379).setRarity(ItemRarity.RARE), new Item(1601).setRarity(ItemRarity.RARE), new Item(985).setRarity(ItemRarity.RARE), new Item(987).setRarity(ItemRarity.RARE), new Item(993).setRarity(ItemRarity.VERY_RARE), new Item(5300).setRarity(ItemRarity.VERY_RARE), new Item(12121).setRarity(ItemRarity.RARE)),
    NINJA_IMPLING_JAR(11254, new Item(6328).setRarity(ItemRarity.COMMON), new Item(6328).setRarity(ItemRarity.COMMON), new Item(10606).setRarity(ItemRarity.COMMON), new Item(6328).setRarity(ItemRarity.COMMON), new Item(3391).setRarity(ItemRarity.COMMON), new Item(4097).setRarity(ItemRarity.COMMON), new Item(4095).setRarity(ItemRarity.COMMON), new Item(1333).setRarity(ItemRarity.UNCOMMON), new Item(1347).setRarity(ItemRarity.UNCOMMON), new Item(1215).setRarity(ItemRarity.UNCOMMON), new Item(6313).setRarity(ItemRarity.COMMON), new Item(892).setRarity(ItemRarity.COMMON).setAmount(40), new Item(811).setRarity(ItemRarity.COMMON).setAmount(40), new Item(868).setRarity(ItemRarity.COMMON).setAmount(20), new Item(805).setRarity(ItemRarity.COMMON).setAmount(25), new Item(9342).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(9194).setRarity(ItemRarity.COMMON).setAmount(4), new Item(5100).setRarity(ItemRarity.COMMON), new Item(5104).setRarity(ItemRarity.COMMON), new Item(5281).setRarity(ItemRarity.COMMON), new Item(5294).setRarity(ItemRarity.COMMON), new Item(5295).setRarity(ItemRarity.RARE), new Item(5297).setRarity(ItemRarity.UNCOMMON), new Item(5299).setRarity(ItemRarity.UNCOMMON), new Item(5298).setRarity(ItemRarity.UNCOMMON), new Item(5303).setRarity(ItemRarity.VERY_RARE), new Item(5304).setRarity(ItemRarity.VERY_RARE), new Item(5313).setRarity(ItemRarity.UNCOMMON), new Item(5286).setRarity(ItemRarity.UNCOMMON), new Item(3051).setRarity(ItemRarity.RARE), new Item(3000).setRarity(ItemRarity.RARE), new Item(219).setRarity(ItemRarity.VERY_RARE), new Item(5974).setRarity(ItemRarity.UNCOMMON), new Item(1755).setRarity(ItemRarity.COMMON), new Item(1734).setRarity(ItemRarity.COMMON), new Item(1733).setRarity(ItemRarity.COMMON), new Item(946).setRarity(ItemRarity.COMMON), new Item(1985).setRarity(ItemRarity.COMMON), new Item(2347).setRarity(ItemRarity.COMMON), new Item(1759).setRarity(ItemRarity.COMMON), new Item(1927).setRarity(ItemRarity.UNCOMMON), new Item(319).setRarity(ItemRarity.UNCOMMON), new Item(2007).setRarity(ItemRarity.UNCOMMON), new Item(1779).setRarity(ItemRarity.UNCOMMON), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(401).setRarity(ItemRarity.UNCOMMON), new Item(1438).setRarity(ItemRarity.UNCOMMON), new Item(2355).setRarity(ItemRarity.RARE), new Item(1607).setRarity(ItemRarity.RARE), new Item(1743).setRarity(ItemRarity.RARE), new Item(379).setRarity(ItemRarity.RARE), new Item(6016).setRarity(ItemRarity.COMMON), new Item(1513).setRarity(ItemRarity.COMMON), new Item(253).setRarity(ItemRarity.COMMON)),
    DRAGON_IMPLING_JAR(11256, new Item(1704).setRarity(ItemRarity.RARE), new Item(4093).setRarity(ItemRarity.RARE), new Item(5547).setRarity(ItemRarity.VERY_RARE), new Item(1704).setRarity(ItemRarity.RARE), new Item(1683).setRarity(ItemRarity.RARE), new Item(11212).setRarity(ItemRarity.COMMON).setAmount(2), new Item(9341).setRarity(ItemRarity.COMMON).setAmount(2), new Item(1215).setRarity(ItemRarity.UNCOMMON), new Item(11230).setRarity(ItemRarity.UNCOMMON).setAmount(10), new Item(11232).setRarity(ItemRarity.UNCOMMON).setAmount(5), new Item(11237).setRarity(ItemRarity.COMMON).setAmount(5), new Item(9193).setRarity(ItemRarity.COMMON).setAmount(2), new Item(535).setRarity(ItemRarity.COMMON).setAmount(5), new Item(5316).setRarity(ItemRarity.RARE), new Item(537).setRarity(ItemRarity.UNCOMMON).setAmount(10), new Item(1615).setRarity(ItemRarity.COMMON), new Item(5300).setRarity(ItemRarity.COMMON), new Item(7219).setRarity(ItemRarity.UNCOMMON).setAmount(6), new Item(562).setRarity(ItemRarity.COMMON).setAmount(3), new Item(555).setRarity(ItemRarity.COMMON).setAmount(12), new Item(558).setRarity(ItemRarity.COMMON).setAmount(6), new Item(556).setRarity(ItemRarity.COMMON).setAmount(10), new Item(559).setRarity(ItemRarity.COMMON).setAmount(18), new Item(554).setRarity(ItemRarity.COMMON).setAmount(50), new Item(1448).setRarity(ItemRarity.COMMON), new Item(7937).setRarity(ItemRarity.COMMON).setAmount(25), new Item(1437).setRarity(ItemRarity.COMMON).setAmount(5), new Item(564).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4695).setRarity(ItemRarity.UNCOMMON).setAmount(3), new Item(4696).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4698).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(4694).setRarity(ItemRarity.UNCOMMON).setAmount(4), new Item(4699).setRarity(ItemRarity.RARE).setAmount(3), new Item(4697).setRarity(ItemRarity.RARE).setAmount(5), new Item(565).setRarity(ItemRarity.RARE).setAmount(3), new Item(1755).setRarity(ItemRarity.COMMON), new Item(1734).setRarity(ItemRarity.COMMON), new Item(1733).setRarity(ItemRarity.COMMON), new Item(946).setRarity(ItemRarity.COMMON), new Item(1985).setRarity(ItemRarity.COMMON), new Item(2347).setRarity(ItemRarity.COMMON), new Item(1759).setRarity(ItemRarity.COMMON), new Item(1927).setRarity(ItemRarity.UNCOMMON), new Item(319).setRarity(ItemRarity.UNCOMMON), new Item(2007).setRarity(ItemRarity.UNCOMMON), new Item(1779).setRarity(ItemRarity.UNCOMMON), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(401).setRarity(ItemRarity.UNCOMMON), new Item(1438).setRarity(ItemRarity.UNCOMMON), new Item(2355).setRarity(ItemRarity.RARE), new Item(1607).setRarity(ItemRarity.RARE), new Item(1743).setRarity(ItemRarity.RARE), new Item(379).setRarity(ItemRarity.RARE), new Item(566).setRarity(ItemRarity.RARE).setAmount(6), new Item(563).setRarity(ItemRarity.RARE).setAmount(11), new Item(563).setRarity(ItemRarity.RARE).setAmount(13), new Item(560).setRarity(ItemRarity.RARE).setAmount(17), new Item(1442).setRarity(ItemRarity.RARE).setAmount(7)),
    KINGLY_IMPLING_JAR(15517, new Item(15511).setRarity(ItemRarity.VERY_RARE), new Item(15509).setRarity(ItemRarity.VERY_RARE), new Item(15507).setRarity(ItemRarity.VERY_RARE), new Item(15505).setRarity(ItemRarity.VERY_RARE), new Item(15503).setRarity(ItemRarity.VERY_RARE), new Item(1305).setRarity(ItemRarity.UNCOMMON), new Item(1250).setRarity(ItemRarity.UNCOMMON), new Item(7158).setRarity(ItemRarity.VERY_RARE), new Item(2366).setRarity(ItemRarity.RARE), new Item(2366).setRarity(ItemRarity.COMMON), new Item(1617).setRarity(ItemRarity.COMMON), new Item(1618).setRarity(ItemRarity.COMMON).setAmount(1), new Item(1705).setRarity(ItemRarity.COMMON).setAmount(1), new Item(1683).setRarity(ItemRarity.COMMON), new Item(1684).setRarity(ItemRarity.COMMON).setAmount(1), new Item(989).setRarity(ItemRarity.COMMON), new Item(1615).setRarity(ItemRarity.UNCOMMON), new Item(1616).setRarity(ItemRarity.UNCOMMON).setAmount(1), new Item(1631).setRarity(ItemRarity.UNCOMMON), new Item(1632).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(9341).setRarity(ItemRarity.UNCOMMON).setAmount(20), new Item(9342).setRarity(ItemRarity.UNCOMMON).setAmount(10), new Item(2364).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(9194).setRarity(ItemRarity.RARE).setAmount(5), new Item(1615).setRarity(ItemRarity.UNCOMMON), new Item(6571).setRarity(ItemRarity.VERY_RARE), new Item(365).setRarity(ItemRarity.COMMON), new Item(361).setRarity(ItemRarity.COMMON), new Item(2011).setRarity(ItemRarity.COMMON), new Item(2327).setRarity(ItemRarity.COMMON), new Item(1897).setRarity(ItemRarity.COMMON), new Item(2293).setRarity(ItemRarity.COMMON), new Item(5004).setRarity(ItemRarity.COMMON), new Item(1883).setRarity(ItemRarity.UNCOMMON), new Item(247).setRarity(ItemRarity.UNCOMMON), new Item(380).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(386).setRarity(ItemRarity.UNCOMMON).setAmount(2), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(7754).setRarity(ItemRarity.UNCOMMON), new Item(1755).setRarity(ItemRarity.COMMON), new Item(1734).setRarity(ItemRarity.COMMON), new Item(1733).setRarity(ItemRarity.COMMON), new Item(946).setRarity(ItemRarity.COMMON), new Item(1985).setRarity(ItemRarity.COMMON), new Item(2347).setRarity(ItemRarity.COMMON), new Item(1759).setRarity(ItemRarity.COMMON), new Item(1927).setRarity(ItemRarity.UNCOMMON), new Item(319).setRarity(ItemRarity.UNCOMMON), new Item(2007).setRarity(ItemRarity.UNCOMMON), new Item(1779).setRarity(ItemRarity.UNCOMMON), new Item(7170).setRarity(ItemRarity.UNCOMMON), new Item(401).setRarity(ItemRarity.UNCOMMON), new Item(1438).setRarity(ItemRarity.UNCOMMON), new Item(2355).setRarity(ItemRarity.RARE), new Item(1607).setRarity(ItemRarity.RARE), new Item(1743).setRarity(ItemRarity.RARE), new Item(379).setRarity(ItemRarity.RARE), new Item(7178).setRarity(ItemRarity.UNCOMMON), new Item(7188).setRarity(ItemRarity.UNCOMMON), new Item(10137).setRarity(ItemRarity.RARE), new Item(7179).setRarity(ItemRarity.RARE).setAmount(6), new Item(374).setRarity(ItemRarity.RARE).setAmount(3), new Item(10136).setRarity(ItemRarity.RARE), new Item(5406).setRarity(ItemRarity.RARE), new Item(2007).setRarity(ItemRarity.COMMON));

    JarData(int jarId, Item... loot) {
        this.jarId = jarId;
        this.loot = loot;
    }

    public Item[] loot;
    public int jarId;

    public static JarData forJar(int jar) {
        for (JarData jars : JarData.values()) {
            if (jars.jarId == jar) {
                return jars;
            }
        }
        return null;
    }

    public static int getLootRarity(JarData data, int rarity) {
        int k = 0;
        for (Item items : data.loot) {
            if (items.rarity.rarity == rarity) {
                k++;
            }
        }
        return k;
    }

    public static int getRar() {
        if (Misc.getRandom(20) >= 16)
            return 1;
        else if (Misc.getRandom(50) >= 47)
            return 2;
        else if (Misc.getRandom(60) >= 58)
            return 3;
        return 0;
    }
}
