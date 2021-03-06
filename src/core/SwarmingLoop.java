package core;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by (serhii.vasylchenko@gmail.com) on 04.09.2016.
 */
public class SwarmingLoop implements Runnable {

    private AtomicBoolean keepRunning;

    private JPanel drawPanel;
    private int swarmSize;

    public SwarmingLoop(JPanel panel, int num) {
        keepRunning = new AtomicBoolean(true);
        drawPanel = panel;
        swarmSize = num;
    }

    public void stop() {
        keepRunning.set(false);
    }

    public void pause() {
        keepRunning.compareAndSet(false, true);
        keepRunning.compareAndSet(true, false);
    }

    @Override
    public void run() {
        Room room = new Room();
        Swarm swarm = new Swarm();

        Vector<Integer> dimensions = new Vector<>();
        dimensions.add(drawPanel.getWidth());
        dimensions.add(drawPanel.getHeight());
        room.initRoom(dimensions);
        swarm.initSwarm(room, swarmSize);
        while (keepRunning.get()) {
            //Drawing
            //Graphics2D gr = new (Graphics2D) Graphics();
            int x;
            int y;
            for (int i = 0; i < swarmSize; i++) {
                x = swarm.getSwarmMembers().get(i).getPosition().get(0).intValue();
                y = swarm.getSwarmMembers().get(i).getPosition().get(1).intValue();
                //gr.draw(new Ellipse2D.Double(x, y, 5, 5));
                //drawPanel.update(gr);
                System.out.println("::" + x + " - " + y);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            swarm.doSwarming();
        }
    }
}
