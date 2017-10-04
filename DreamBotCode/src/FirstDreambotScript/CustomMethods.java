package FirstDreambotScript;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Potatoes on 7/19/2016.
 * this class is made to override the AbstractScript in a way, but just implement my own methods it is used in other classes
 * bad class name but whatever not going to change it
 */


public class CustomMethods{

    private AbstractScript script;
    private Random random;

    public CustomMethods(AbstractScript passscript){

        script = passscript;
        random = new Random();
    }


    //this is to find the closest gameobject contained in a area pretty simple stuff
    public GameObject GetClosestGameObjectInArea(List<GameObject> passedObjects, String objectName,Area boundingArea){

        GameObject returnObj = null;
        double lowestDistance = 9999;

        for(GameObject currObj: passedObjects){
            if(boundingArea.contains(currObj) && currObj.exists() && script.getMap().canReach(currObj) && currObj.getName().equals(objectName)){
                double distance = currObj.distance(script.getLocalPlayer());
                if(distance < lowestDistance){
                    returnObj = currObj;
                    lowestDistance = distance;
                }

            }
        }


        return returnObj;
    }

    //this function is pointless why did i make it? hehehee thats a secret
    public GameObject GetClosetGameObjectFromIntArray(int passedArray[]){

        //initializing it to a null value at first
        GameObject returnObj = null;

        //all this does is finds the closest object based on each integer value and compares them to find the closests
        for(int x=0;x<passedArray.length; x++){

            GameObject tempObj = script.getGameObjects().closest(passedArray[x]);

            //got some fat nested if's, would of shortened it but I think its fine in this scenario
            //because I wanted null checks so it just jumps out of func since this is running on every loop
            if(tempObj != null)
                if(returnObj == null)
                    returnObj = tempObj;
                //if there already is a closest object then compare distances to find the closest
                else if(tempObj.distance() < returnObj.distance())
                    returnObj = tempObj;
        }

        return returnObj;
    }

    public GameObject GetRandomObjectTypeFromList(List<GameObject> passedObjects, String objectNames[]){

        //null initializer for return value
        GameObject returnObj = null;
        //initializing our refined list
        ArrayList<GameObject> refinedList = new ArrayList<GameObject>();

        //go through all the game objects that were passed
        for(GameObject indexObj: passedObjects){
            //now go through all the strings that were passed
            for(String objectName: objectNames)
                //if the indexObj has the same name as one of the strings passed add it to our refined list
                if(indexObj.getName().equals(objectName)){
                    refinedList.add(indexObj);

                }

        }

        //select a object randomly based on the size of the refined objects
        //checks if there is a object in it
        if(refinedList.size() != 0){
            returnObj = refinedList.get(random.nextInt(refinedList.size()));
        }

        return returnObj;
    }



    public GameObject GetNextRandomObjectInRange(String objectName,int range,int thresholdRange,GameObject excludedObject){

        List<GameObject> nearbyObjects = script.getGameObjects().all();
        ArrayList<GameObject> refinedObjList = new ArrayList<GameObject>();
        ArrayList<GameObject> finalObjList = new ArrayList<GameObject>();
        GameObject returnObj = null;

        double lowestDistance = 99999;

        //go through all nearby objects
        for(GameObject currObj: nearbyObjects){
            //difference here is it checks if the object is contained by the area
            if(currObj.getName().equals(objectName) && currObj.distance(script.getLocalPlayer()) <= range && !(currObj.equals(excludedObject))){
                refinedObjList.add(currObj);
                if(currObj.distance(script.getLocalPlayer()) < lowestDistance)
                    lowestDistance = currObj.distance(script.getLocalPlayer());
            }

        }

        //now lets go through and check the objects are within threshold range of the lowest distance object
        for(GameObject distanceObj: refinedObjList){
            if(distanceObj.distance(script.getLocalPlayer()) - thresholdRange > lowestDistance)
                finalObjList.add(distanceObj);
        }

        //choosing a random value from the trees within threshold range
        returnObj = finalObjList.get(random.nextInt(finalObjList.size()));
        script.log("Game distance from tree: " + returnObj.distance(script.getLocalPlayer()));
        return returnObj;
    }

    //CLONE OF METHOD ABOVE BUT HANDLES SELECTING OBJECTS WITHIN A BOUND RANGE
    public GameObject GetNextRandomObjectInBoundedRange(String objectName,int range,int thresholdRange,GameObject excludedObject,Area boundedRange){

        List<GameObject> nearbyObjects = script.getGameObjects().all();
        ArrayList<GameObject> refinedObjList = new ArrayList<GameObject>();
        ArrayList<GameObject> finalObjList = new ArrayList<GameObject>();
        GameObject returnObj = null;

        double lowestDistance = 99999;

        //go through all nearby objects
        for(GameObject currObj: nearbyObjects){
            //difference here is it checks if the object is contained by the area
            if(boundedRange.contains(currObj) && currObj.exists() && script.getMap().canReach(currObj) && currObj.getName().equals(objectName) && currObj.distance(script.getLocalPlayer()) <= range && !(currObj.equals(excludedObject)) ){
                refinedObjList.add(currObj);

                if(currObj.distance(script.getLocalPlayer()) < lowestDistance){
                    lowestDistance = currObj.distance(script.getLocalPlayer());
                }

            }

        }

        //now lets go through and check the objects are within threshold range of the lowest distance object
        for(GameObject distanceObj: refinedObjList){
            if(distanceObj.distance(script.getLocalPlayer()) - thresholdRange <= lowestDistance)
                finalObjList.add(distanceObj);
        }

        //choosing a random value from the trees within threshold range
        if(!finalObjList.isEmpty())
            returnObj = finalObjList.get(random.nextInt(finalObjList.size()));

        return returnObj;
    }

    public boolean isPlayerInArea(Player player, Area area){

        if(player.getX() <= area.getBoundingBox().getMaxX() && player.getX() >= area.getBoundingBox().getMinX() && player.getY() <= area.getBoundingBox().getMaxY() && player.getY() >= area.getBoundingBox().getMinY())
            return true;

        return false;


    }



}
