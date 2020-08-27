package sim.app.cwts.gc1;

import sim.app.tutorial.Student;
import sim.app.tutorial.Students;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;
import sim.portrayal.simple.CircledPortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.MovablePortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;

public class AcademiaWithUI extends GUIState<Academia> {
    public Display2D display;
    public JFrame displayFrame;
    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();

    public static void main(String[] args) {
        var vid = new AcademiaWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
    }

    public AcademiaWithUI() {
        super(new Academia(System.currentTimeMillis()));
    }

    public AcademiaWithUI(Academia state) {
        super(state);
    }

    public static String getName() {
        return "Grand Challenge simulation";
    }

    @Override
    public Object getSimulationInspectedObject() {
        return state;
    }

    @Override
    public Inspector getInspector() {
        Inspector i = super.getInspector();
        i.setVolatile(true);
        return i;
    }

    public void setupPortrayals() {
        Academia academia = state;
        // tell the portrayals what to portray and how to portray them
        yardPortrayal.setField(academia.yard);
        yardPortrayal.setPortrayalForAll(
                new MovablePortrayal2D(
                        new CircledPortrayal2D(
                                new LabelledPortrayal2D(
                                        new OvalPortrayal2D() {
                                            public void draw(Researcher object, Graphics2D graphics, DrawInfo2D info) {
                                                Researcher researcher = object;
                                                if (researcher.getStrategy() == Researcher.Strategy.RESEARCH)
                                                    paint = new Color(255, 0, 0);
                                                else if (researcher.getStrategy() == Researcher.Strategy.PROPOSAL)
                                                    paint = new Color(0,255,0);
                                                else
                                                    paint = new Color(0, 0, 255);
                                                super.draw(object, graphics, info);
                                            }
                                        },
                                        5.0, null, Color.black, true),
                                0, 5.0, Color.green, true)));


        // reschedule the displayer
        display.reset();
        display.setBackdrop(Color.white);
        // redraw the display
        display.repaint();
    }

    @Override
    public void start() {
        super.start();
        setupPortrayals();
    }

    @Override
    public void load(Academia state) {
        super.load(state);
        setupPortrayals();
    }

    @Override
    public void init(Controller c) {
        super.init(c);
        display = new Display2D(600, 600, this);
        display.setClipping(false);
        displayFrame = display.createFrame();
        displayFrame.setTitle("Academia Display");
        c.registerFrame(displayFrame); // so the frame appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach(yardPortrayal, "Yard");
    }

    @Override
    public void quit() {
        super.quit();
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = null;
        display = null;
    }
}
