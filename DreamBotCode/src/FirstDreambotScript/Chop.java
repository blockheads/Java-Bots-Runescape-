package FirstDreambotScript;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.event.CameraEvent;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.Location;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.core.Instance;


import java.util.Random;

/**
 * Created by Potatoes on 7/19/2016.
 * This class contains all of the actions that we take!
 * I named it chop because im retarded!
 */


public class Chop{

    //tree id's 1276,1278
    private final int treeIDs[] = {1276,1278};
    private final String bankBoothNames[] = {"Bank"};


    //heres a bunch of private values that get initialized here
    //definetly want to have acesss to changing these in the gui!
    private AbstractScript script;
    private CustomMethods customMethods;
    private Random random;
    private GameObject depositBox;
    private Tile startLocation;
    private boolean nextTreeQued;
    private boolean treeSelected;
    private GameObject nextTree;
    private GameObject currentTree;
    private int selectedBoundingBoxHeight;
    private int selectedBoundingBoxWidth;
    private String selectedTreeType;
    private int selectedRange;
    private int selectedThresholdRange;
    private Area boundingBox;
    private boolean waiting;
    private Timer timer;
    private boolean movedMouse;
    private EpicWoodcutterGui gui;
    private Timer treeTimer;
    private boolean canOutOfBounds;
    private Timer changeTreeTimer;
    private Timer otherTreeTimer;
    private boolean stuck;
    private boolean changedTrees;
    private Timer attemptCutChangedTrees;
    private boolean shouldTimerReset;

    public Chop(AbstractScript main,EpicWoodcutterGui passedGui){
        this.script = main;
        this.gui = passedGui;
        customMethods = new CustomMethods(script);
        random = new Random();
        nextTreeQued = false;
        nextTree = null;
        waiting = false;
        movedMouse = false;
        treeTimer = new Timer();
        changeTreeTimer = new Timer();
        otherTreeTimer = new Timer();
        canOutOfBounds = false;
        stuck = false;
        shouldTimerReset = false;


//      just initializing this right now
//      these values are varied slightly later on for antiban

        selectedTreeType = gui.getTreeSelector();
        script.log("Tree Selected: " + gui.getTreeSelector());
        selectedRange = gui.getRangeSize();
        selectedBoundingBoxHeight = gui.getBoundingBoxHeight();
        selectedBoundingBoxWidth = gui.getBoundingBoxWidth();
        selectedThresholdRange = gui.getThresholdRange();

    }

    public void ChopLog(){

        //checking to see if we should be walking
        if(script.getWalking().isRunEnabled() && script.getWalking().getRunEnergy() <= random.nextInt(10) + 35)
            script.getWalking().toggleRun();

        GameObject closestTree = customMethods.GetClosestGameObjectInArea(script.getGameObjects().all(),selectedTreeType,boundingBox);

        //checks if there are trees around, if not then sleep
        if(closestTree == null){
            script.sleep(random.nextInt(432));
        }
        else if(!script.getLocalPlayer().isMoving()){
            if(nextTree != null && nextTree.exists()){
                currentTree = nextTree;
                nextTreeQued = false;
            }
            else if(!treeSelected && ( nextTree == null || !nextTree.exists())){
                currentTree = closestTree;
            }

            if(currentTree.exists()){
                currentTree.interact("Chop down");
                script.sleepUntil(()->script.getLocalPlayer().isMoving(),Calculations.random(235,1276));

            }

            if(shouldTimerReset)
                changeTreeTimer.reset();
            //this will check if we are arriving at the tree
            if(changeTreeTimer.elapsed() > Calculations.random(6000,8000)){
                stuck = true;
                changedTrees = false;
                attemptCutChangedTrees = null;
            }

            shouldTimerReset = false;

        }
        else if(currentTree != null && currentTree.exists() && script.getLocalPlayer().isMoving()){

            script.sleep(Calculations.random(563,1892));
            canOutOfBounds = true;
            shouldTimerReset = true;

        }
        //error handling weird situations
        else{
            treeSelected = false;
        }


//        script.log("current tree distance: " + currentTree.distance(script.getLocalPlayer()));
//        script.log("next tree distance: " + nextTree.distance(script.getLocalPlayer()));

    }

    public void Bank(){

        canOutOfBounds = false;

        //bank location will be final, and lets find the nearest bank
        final BankLocation bankLocation = script.getBank().getClosestBankLocation();

        //a string for the action to use at the bank booth
        String tempAction = null;

        //if we are within 6 tiles of the bank, bank it up Also added Z scenario for lumby bank
        if((bankLocation.getCenter().distance(script.getLocalPlayer()) <= 6 + Calculations.random(-1,1)) && bankLocation.getCenter().getZ() == script.getLocalPlayer().getZ()){

            if(script.getBank().isOpen()){
                //deposits every type of log in the bank!
                for(String log: Main.GetLogList())
                    script.getBank().depositAll(log);
                int smallAntiBanGen = random.nextInt(20);
                if(smallAntiBanGen >= 5)
                    script.getBank().close();
                else{
                    //clicks nearest tile on minimap based on path to get back to bounding box
                    script.getWalking().clickTileOnMinimap(script.getWalking().getClosestTileOnMap(boundingBox.getRandomTile()));
                    script.sleep(random.nextInt(720) + 543);
                }

            }
            else{

                //selects a random deposit box near the player
                depositBox = customMethods.GetRandomObjectTypeFromList(script.getGameObjects().all(),new String[]{"Bank booth"});

                if(depositBox != null){
                    //now increment through the actions on the bank booth and see if they are available
                    //then check if the action is one on the private list already defined and do it
                    //there is probably a less cpu intensive way to do this, but again im lazy
                    for(String action: depositBox.getActions())
                        for(String name: bankBoothNames)
                            if(name.equals(action)){
                            tempAction = action;
                        }
                    if(tempAction != null){
                        depositBox.interact(tempAction);
                        script.sleepUntil(() -> script.getBank().isOpen(), random.nextInt(965) + 376);
                    }

                }

            }
        }
        //otherwise lets just walk and chill ;)
        else{
            //checking to see if the player should toggle run
            if(!script.getWalking().isRunEnabled() && script.getWalking().getRunEnergy() >= random.nextInt(23) + 4)
                script.getWalking().toggleRun();

            script.getWalking().walk(bankLocation.getCenter());
            script.sleep(random.nextInt(1379) + 787);
        }

    }

    public void Return(){

        //checking to see if the player should toggle run
        if(!script.getWalking().isRunEnabled() && script.getWalking().getRunEnergy() >= random.nextInt(20) + 5)
            script.getWalking().toggleRun();

        if(startLocation != null){
            script.getWalking().walk(boundingBox.getRandomTile());
            script.sleep(random.nextInt(1379) + 747);
        }
        //error handling for dumbos who start it inside the bank or somewhere not near a tree
        else{
            script.log("You need to start this script near a tree!");
            script.stop();
        }

    }

    public void Sleep(){

        canOutOfBounds = false;
        stuck = false;
        shouldTimerReset = true;

        currentTree = customMethods.GetClosestGameObjectInArea(script.getGameObjects().all(),selectedTreeType,boundingBox);

        //prep our next tree
        if(nextTree == null || !nextTreeQued  || !nextTree.exists()){

            nextTree = null;
            nextTree = GetNextTree(currentTree);
            if(nextTree != null && nextTree.exists()){
                nextTreeQued = true;
                int rotateRand = random.nextInt(100);

                if(!nextTree.isOnScreen() || rotateRand <= 30) {
                    CameraEvent cameraEvent = new CameraEvent(Instance.getInstance(), nextTree);
                    cameraEvent.start();
                    script.getCamera().rotateToEntityEvent(nextTree);

                    script.sleepUntil(() -> cameraEvent.hasFinished(), Calculations.random(234, 543));

                    if (script.getMouse().isMouseInScreen() && nextTree.isOnScreen() && currentTree.exists()) {
                        movedMouse = true;
                        script.getMouse().move(nextTree.getClickablePoint());
                    }
                }

            if(!movedMouse)
                script.sleep(Calculations.random(234,2876));
            else
                script.sleepUntil(()->!currentTree.exists(),Calculations.random(234,2876));

            }


        }

        if(otherTreeTimer.elapsed() > Calculations.random(1576,5324)  && script.getCamera().getPitch() < Calculations.random(130,180)){
            int tempRanda = random.nextInt(100);
            if(tempRanda <= 40){
                int tempRand = Calculations.random(180,383);
                script.getCamera().rotateToPitch(tempRand);
            }

        }


        //if its been a while select another tree or spin camera around
        //this is just basic antiban honestly
        if(treeTimer.elapsed() > Calculations.random(1867,5987)){
            Random tempRand = new Random();
            if(tempRand.nextInt(100) <=  10){
                int chooseAntiBan = tempRand.nextInt(10);
                if(chooseAntiBan <= 3){
                    nextTreeQued = false;
                }
                else if(chooseAntiBan == 4){
                    script.getCamera().rotateTo(script.getCamera().getX() + Calculations.random(-100,100),script.getCamera().getY() + Calculations.random(-100,100));
                }
                else if(chooseAntiBan == 5){
                    script.getCamera().rotateTo(script.getCamera().getX() + Calculations.random(-100,100),script.getCamera().getY());
                }
                else if(chooseAntiBan == 6){
                    script.getCamera().rotateTo(script.getCamera().getX(),script.getCamera().getY() + Calculations.random(-100,100));
                }
                else if(chooseAntiBan == 7 || chooseAntiBan == 8)
                    script.getMouse().moveMouseOutsideScreen();
                else
                    script.getCamera().rotateToEntity(currentTree);
            }


            treeTimer.reset();

        }


    }



    public void Combat(){
        //just run to nearest bank if in combat
        script.getWalking().walk(script.getBank().getClosestBankLocation().getCenter());

        if(!script.getWalking().isRunEnabled() && script.getWalking().getRunEnergy() >= random.nextInt(5) + 10)
            script.getWalking().toggleRun();

        script.sleep(random.nextInt(2576) + 154);

        waiting = true;
        timer = new Timer();

    }

    //handles the area where the player starts
    public void StartGeneration(){

        //setting our start tile
        startLocation = script.getLocalPlayer().getTile();

        //generating a bounding box so our chopper doesn't go too far
        //top left -> bottom right x1y1x2y2 just making a box pretty self explanatory
        boundingBox = new Area(startLocation.getX() - selectedBoundingBoxWidth/2,startLocation.getY() - selectedBoundingBoxHeight/2,startLocation.getX() + selectedBoundingBoxWidth/2,startLocation.getY() + selectedBoundingBoxHeight/2);
        script.log("You have registered a start tile at: " + startLocation.toString());
        script.log("You have generated a bounding box " + "x1: " + (startLocation.getX() - selectedBoundingBoxWidth/2) + " y1: " + (startLocation.getY() - selectedBoundingBoxHeight/2) + " x2: " + (startLocation.getX() + selectedBoundingBoxWidth/2) + " y2: " + (startLocation.getY() + selectedBoundingBoxHeight/2));


    }

    public void waiting(){

        script.log("timer value: " + timer.elapsed());
        int tempTimerval = random.nextInt(6234) + 3634;
        script.sleepUntil(() -> timer.elapsed() >= tempTimerval,random.nextInt(3626) + 1678);
        if(timer.elapsed() >= tempTimerval)
            waiting = false;

    }

    public void UnStuck(){

        //first lets try and grab a new tree
        canOutOfBounds = true;
        //making this so you have a larger change to select father away trees
        selectedThresholdRange = 10;

        if(!changedTrees){
            if(nextTree != null)
                nextTree = GetNextTree(nextTree);

            if(currentTree != null)
                currentTree = GetNextTree(currentTree);


            if(nextTree == null && currentTree == null){
                nextTree = customMethods.GetClosestGameObjectInArea(script.getGameObjects().all(),selectedTreeType,boundingBox);
                currentTree = GetNextTree(nextTree);
            }
            else if(nextTree == null)
                nextTree = GetNextTree(currentTree);
            else if(currentTree == null)
                currentTree = GetNextTree(nextTree);


            if(attemptCutChangedTrees != null)
                attemptCutChangedTrees.reset();
            else
                attemptCutChangedTrees = new Timer();
        }

        if(attemptCutChangedTrees != null && attemptCutChangedTrees.elapsed() < Calculations.random(432,1120)){
            nextTree.interact("Chop down");
            script.sleepUntil(()->script.getLocalPlayer().isAnimating(),Calculations.random(5432,10220));
        }
        else if (attemptCutChangedTrees != null && attemptCutChangedTrees.elapsed() < Calculations.random(21320, 31320)){
            currentTree.interact("Chop down");
            script.sleepUntil(()->script.getLocalPlayer().isAnimating(),Calculations.random(5432,10220));
        }
        else{
            Return();
            script.sleep(Calculations.random(3025,6056));
            attemptCutChangedTrees.reset();
        }



        //make sure to call this last
        changeTreeTimer.reset();
        selectedThresholdRange = gui.getThresholdRange();
        canOutOfBounds = false;

    }

    public boolean getWaiting(){return waiting;}

    public Tile getStartLocation(){return startLocation;}

    public boolean isPlayerInBoundingBox(){

        return customMethods.isPlayerInArea(script.getLocalPlayer(),boundingBox);
    }

    //not actually using this right now because its pretty pointless T_T
    private GameObject GetTree(){

        GameObject tree = customMethods.GetClosetGameObjectFromIntArray(treeIDs);
        return tree;
    }

    private GameObject GetNextTree(GameObject excludedObject){

        GameObject nextTree = customMethods.GetNextRandomObjectInBoundedRange(selectedTreeType,selectedRange,selectedThresholdRange,excludedObject,boundingBox);
        return nextTree;
    }

    public String getSelectedTreeType() {
        return selectedTreeType;
    }

    public void setSelectedTreeType(String selectedTreeType) {
        this.selectedTreeType = selectedTreeType;
    }

    public boolean isCanOutOfBounds() {
        return canOutOfBounds;
    }

    public GameObject getCurrentTree() {
        return currentTree;
    }

    public GameObject getNextTree() {
        return nextTree;
    }

    public boolean isStuck() {
        return stuck;
    }
}
