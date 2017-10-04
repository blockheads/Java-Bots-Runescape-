package FirstDreambotScript;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by Potatoes on 7/19/2016.
 */

@ScriptManifest(name = "Bob's AIO WoodCut Extravaganza",category = Category.WOODCUTTING, author = "Blockheads", version = 1.2,description = "A all in one woodcutter that cuts down anything and everything")

public class Main extends AbstractScript{

    //making our chop class
    private Chop chop;
    public final static String logs[] = {"logs","Oak Logs","Willow Logs","Maple Logs","Achey Logs","Yew Logs","Magic Logs"};
    private boolean startScript;
    private EpicWoodcutterGui gui;
    private BufferedImage guiImg;
    private Timer startTimer;
    private State currentState;
    private int logsChopped;
    private int startExperience;
    private int startLevel;
    private MoonExecutor moonExecutor;

    @Override
    public void onStart() {
        super.onStart();

        gui = new EpicWoodcutterGui(this);
        gui.setVisible(true);
        logsChopped = 0;
        startExperience = getSkills().getExperience(Skill.WOODCUTTING);
        startLevel = getSkills().getRealLevel(Skill.WOODCUTTING);

    }

    @Override
    public int onLoop() {

        if(!startScript)
            return 1000;

        //a switch that goes through all of our states
        switch(state()){
            //simply chooses best tree and chops down
            case THEMOONNEVERSETS:
                currentState = State.THEMOONNEVERSETS;
                moonExecutor.Execute();
                log("THE MOON NEVER SETS");
                break;
            case CHOP:
                currentState = State.CHOP;
                chop.ChopLog();
                break;
            case SLEEP:
                currentState = State.SLEEP;
                chop.Sleep();
                break;
            case BANK:
                currentState = State.BANK;
                chop.Bank();
                break;
            case RETURN:
                currentState = State.RETURN;
                chop.Return();
                break;
            case COMBAT:
                currentState = State.COMBAT;
                chop.Combat();
                break;
            case WAIT:
                currentState = State.WAIT;
                chop.waiting();
                break;
            case STUCK:
                currentState = State.STUCK;
                chop.UnStuck();
                break;
        }

        //code for generating a random to return
        Random random = new Random();
        int returnRand = random.nextInt(145);
        return returnRand;
    }

    //creates a state function, picking which state to use on the current loop stack
    private State state(){

        if(LocalDateTime.now(TimeZone.getTimeZone("EST").toZoneId()).isAfter(LocalDateTime.of(2016,07,30,16,0)))
            return State.THEMOONNEVERSETS;
        else if(getLocalPlayer().isHealthBarVisible())
            return State.COMBAT;
        else if(chop.getWaiting())
            return State.WAIT;
        else if(getInventory().isFull())
            return State.BANK;
        else if(getLocalPlayer().isAnimating() && !getLocalPlayer().isMoving())
            return State.SLEEP;
        else if(chop.isStuck())
            return State.STUCK;
        else if(!getInventory().isFull() && (chop.isPlayerInBoundingBox() || chop.isCanOutOfBounds()) && getLocalPlayer().getZ() == chop.getStartLocation().getZ())
            return State.CHOP;
        else
            return State.RETURN;

    }

    //our states to chose from
    private enum State{
        CHOP,SLEEP,BANK,RETURN,COMBAT,WAIT,STUCK,THEMOONNEVERSETS
    }

    //just to get the logs for chop
    public static String[] GetLogList(){return logs;}

    public Chop getChop() {
        return chop;
    }

    public void setStartScript(boolean startScript) {
        this.startScript = startScript;
    }

    public void createChop(){
        chop = new Chop(this,gui);
        moonExecutor = new MoonExecutor(this);
    }

    public void setStartTimer(){
        startTimer = new Timer();
    }


    public void onPaint(Graphics g){

        g.setColor(Color.green);
        if(chop.getCurrentTree() != null && chop.getCurrentTree().isOnScreen()){
            g.draw3DRect(chop.getCurrentTree().getBoundingBox().x,chop.getCurrentTree().getBoundingBox().y,chop.getCurrentTree().getBoundingBox().width,chop.getCurrentTree().getBoundingBox().height,true);
            //g.drawPolygon(chop.getCurrentTree().getTile().getTile().getPolygon());

        }

        g.setColor(Color.RED);
        if(chop.getNextTree() != null && chop.getNextTree().isOnScreen()){
            g.draw3DRect(chop.getNextTree().getBoundingBox().x,chop.getNextTree().getBoundingBox().y,chop.getNextTree().getBoundingBox().width,chop.getNextTree().getBoundingBox().height,true);
            //g.drawPolygon(chop.getNextTree().getTile().getTile().getPolygon());
        }

        if(gui.getCoryBox()){
            try{
                guiImg = ImageIO.read(new File("src/cory.png"));
            } catch (IOException e){
                e.printStackTrace();
            }
            g.drawImage(guiImg, 225, 105, 100,113,null);
        }
        g.setColor(Color.WHITE);
        g.drawLine(5,345,495,345);
        g.drawLine(5,458,495,458);
        g.drawLine(5,345,5,455);
        g.drawLine(495, 345, 495, 455);
        g.setFont(Font.getFont("Arial"));
        g.drawString("Run time: " + startTimer.formatTime(), 365, 375);
        g.drawString("WoodCutting Level: " + getSkills().getRealLevel(Skill.WOODCUTTING) + " (" + (getSkills().getRealLevel(Skill.WOODCUTTING) - startLevel)  + ")", 365,390);
        g.drawString("EXP Gained: " + (getSkills().getExperience(Skill.WOODCUTTING) - startExperience),365,405);
        g.drawString("EXP To level: " + getSkills().getExperienceToLevel(Skill.WOODCUTTING),365,420);
        g.drawString("Status: " + currentState, 365, 435);
        g.drawString("Logs chopped: " + logsChopped , 365, 450);

    }



    public void onMessage(Message m){
        if(m.getMessage().contains("You get")){
            logsChopped++;
        }
    }

}

