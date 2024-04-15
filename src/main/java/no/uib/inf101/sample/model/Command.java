package no.uib.inf101.sample.model;

import java.awt.*;

public class Command {
    private Color colour;
    private WaterTube to;
    private WaterTube from;
    private int removeCount;

    public Command(WaterTube from, WaterTube to, Color colour, int removeCount) {
        this.from = from;
        this.to = to;
        this.colour = colour;
        this.removeCount = removeCount;
    }

    public void execute() {
        from.remove(removeCount);
        to.add(removeCount, colour);
    }

    public void reverse() {
        to.remove(removeCount);
        from.add(removeCount, colour);
    }
}
