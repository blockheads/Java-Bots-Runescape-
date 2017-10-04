/**
 * Created by Potatoes on 7/25/2016.
 */
public class ReturnToStartState extends ArmageddonBase implements State{

    @Override
    public int Run() {

        script.getWalking().webWalk(startArea);

        return random(200,1500);
    }

    @Override
    public State GetNextState() {

        if(startArea.contains(script.myPlayer()))
        {
            return npcTargetState;
        }

        return this;
    }
}
