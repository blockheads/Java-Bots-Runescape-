package FirstDreambotScript;


import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.depositbox.DepositBox;
import org.dreambot.api.methods.map.Area;

import java.util.Random;

/**
 * Created by Potatoes on 7/27/2016.
 */
public class MoonExecutor {

    private Main script;
    private final Area moonSettlement = new Area(3166,3481,3163,3478);
    private boolean reachedDestination = false;

    public MoonExecutor(Main main)
    {
        this.script = main;
    }

    public void Execute()
    {
        if(script.getClient().getCurrentWorld() != 301)
        {

            script.getWorldHopper().hopWorld(301);
            script.sleep(5000);
            script.log("hopped");
        }
        else if(!moonSettlement.contains(script.getLocalPlayer()) && !reachedDestination)
        {
            script.getWalking().walk(moonSettlement.getRandomTile());
            script.sleep(Calculations.random(500,1500));
        }
        else
        {

            if(!reachedDestination && moonSettlement.contains(script.getLocalPlayer()))
            {
                reachedDestination=true;
            }



            Bank bank = script.getBank();

            if(script.getInventory().isFull())
            {
                if(bank.isOpen())
                    bank.close();

                script.getWalking().walk(moonSettlement.getRandomTile());
                script.sleep(Calculations.random(500,1000) );
                for (int i = 0; i < script.getInventory().fullSlotCount(); i++)
                {
                    Random rand = new Random();

                    script.getInventory().drop(script.getInventory().getItemInSlot(i).getID());
                    script.getWalking().walk(moonSettlement.getRandomTile());

                }


            }
            else
            {
                bank.openClosest();
                script.sleepUntil(() -> bank.isOpen(),Calculations.random(1500,2000));

                if(bank.isOpen())
                {
                    if(bank.getWithdrawMode() != BankMode.NOTE)
                    {
                        bank.setWithdrawMode(BankMode.NOTE);
                    }

                    for(int i = 0; i < script.getInventory().emptySlotCount(); i++)
                    {
                        if(bank.getItemInSlot(i) != null)
                        {
                            bank.withdrawAll(bank.getItemInSlot(i).getID());
                            script.sleep(Calculations.random(100,500));
                        }

                    }


                }

            }



        }



    }


}
