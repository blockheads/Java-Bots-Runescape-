import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.input.mouse.MiniMapTileDestination;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Potatoes on 7/25/2016.
 */
public class ArmageddonPaint extends Base {

    //autism
    final int[] XP_TABLE =
            {
                    0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154,
                    1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018,
                    5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
                    16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224,
                    41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721,
                    101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254,
                    224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428,
                    496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
                    1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068,
                    2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294,
                    4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614,
                    8771558, 9684577, 10692629, 11805606, 13034431, 200000000
            };

    private final Image guiImg = ImageHelper.getImage("http://i.imgur.com/O1wc0IH.png");
    private int beginningXP[];
    private int currentXP;
    private int gainedXP;
    private int beggingLevel[];
    private int currentLevel[];
    private int levelsGained[];
    private int nextLevelXp;
    private int currentLevelXP;
    private Skill selectedSkill[];
    private double percentTNL;
    private int XPbarWidth[];
    private long timeBegan;
    private long timeRan;

    public ArmageddonPaint(){
        selectedSkill = new Skill[]{Skill.ATTACK,Skill.STRENGTH,Skill.DEFENCE};
        beginningXP = new int[3];
        XPbarWidth = new int[3];
        currentLevel = new int[3];
        beggingLevel = new int[3];
        levelsGained = new int[3];
        for(int i=0;i<=2;i++) {
            beginningXP[i] = script.getSkills().getExperience(selectedSkill[i]);
            beggingLevel[i] = script.getSkills().getStatic(selectedSkill[i]);
        }
        timeBegan = System.currentTimeMillis();

    }

    public void GeneratePaint(Graphics2D g){

        Calculations();

        g.setColor(Color.cyan);
        Area startArea = ArmageddonStartGeneration.getStartArea();
        for(Position position : startArea.getPositions()){
            if ( position.isOnMiniMap(script.getBot())){
                MiniMapTileDestination miniMapTileDestination = new MiniMapTileDestination(script.getBot(), position);
                g.drawOval(miniMapTileDestination.getBoundingBox().x,miniMapTileDestination.getBoundingBox().y,2,2);
            }
        }

        g.setColor(new Color(255,204,204));
        g.fillRect(138,453,96,20);
        g.fillRect(257,428,96,20);
        g.fillRect(257,453,96,20);
        g.setColor(Color.RED);
        g.fillRect(138,453,XPbarWidth[0],20);
        g.fillRect(257,428,XPbarWidth[1],20);
        g.fillRect(257,453,XPbarWidth[2],20);
        g.drawImage(guiImg,0,-30,null);

        g.setColor(Color.DARK_GRAY);
        g.drawString("" + currentLevel[0] + "(" + levelsGained[0] + ")",148,468);
        g.drawString("" + currentLevel[1] + "(" + levelsGained[1] + ")",267,443);
        g.drawString("" + currentLevel[2] + "(" + levelsGained[2] + ")",267,468);
        g.drawString("" + gainedXP,148,444);
        g.drawString(ft(timeRan),45,448);




    }

    public void Calculations(){

        gainedXP = 0;
        for(int i=0;i<=2;i++){

            currentLevel[i] = script.getSkills().getStatic(selectedSkill[i]);
            levelsGained[i] = currentLevel[i] - beggingLevel[i];
            currentLevelXP = script.getSkills().getExperienceForLevel(currentLevel[i]);
            nextLevelXp = XP_TABLE[currentLevel[i] + 1];
            currentXP = script.getSkills().getExperience(selectedSkill[i]);
            gainedXP += currentXP - beginningXP[i];
            percentTNL = (currentXP - currentLevelXP) / (nextLevelXp - currentLevelXP);
            float temp1 = currentXP - currentLevelXP;
            float temp2 = nextLevelXp - currentLevelXP;
            float temp3 = temp1 / temp2;
            timeRan = System.currentTimeMillis() - this.timeBegan;

            XPbarWidth[i] = (int)((96 * (temp3)));
        }


    }

    private String ft(long duration)
    {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
}
