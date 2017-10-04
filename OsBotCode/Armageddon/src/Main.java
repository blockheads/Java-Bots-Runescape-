import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;


/**
 * Created by Josh on 7/22/2016.
 */
@ScriptManifest(author = "Armageddon", info = "Killer", name = "Goblin BeastMode", version = 1.00, logo = "")
public class Main extends Script
{

    private ArmageddonMachine goblinKiller;
    private ArmageddonPaint armageddonPaint;
    private boolean scriptLoop = false;
    private ArmagoblinGui gui;

    @Override
    public void onStart() throws InterruptedException
    {
        gui = new ArmagoblinGui();
        gui.setVisible(true);
        goblinKiller = new ArmageddonMachine();
        goblinKiller.SetScript(this);
        super.onStart();
    }

    @Override
    public int onLoop() throws InterruptedException
    {
        if(gui.isStarted())
        {
            generateStart();
            armageddonPaint = new ArmageddonPaint();
            scriptLoop = true;
            gui.setStarted(false);

        }
        if(scriptLoop)
        {
            int sleep = goblinKiller.RunStateMachine();
            log(goblinKiller.GetCurrentState());
            return sleep;
        }

        return 1000;
    }

    @Override
    public void onPaint(Graphics2D g) {
        super.onPaint(g);

        armageddonPaint.GeneratePaint(g);

    }

    public void generateStart()
    {
        ArmageddonStartGeneration.setWidth(10);
        ArmageddonStartGeneration.setLength(10);
        ArmageddonStartGeneration.GenerateStart();
        ArmageddonBase.setStartArea(ArmageddonStartGeneration.getStartArea());
    }
}
