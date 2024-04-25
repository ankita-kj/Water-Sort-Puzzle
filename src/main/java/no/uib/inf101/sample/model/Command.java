package no.uib.inf101.sample.model;

import java.awt.*;

/**
 * Command class:
 * A class to represent a single action that has been applied to the
 * water tubes
 */
public class Command {
    private Color color;
    private WaterTube to;
    private WaterTube from;
    private int removeCount;

    /**
     * @param from        The water tube the color is coming from
     * @param to          The water tube the color is going to
     * @param color       The color that is to be applied
     * @param removeCount The count of colors that are being transferred
     */
    public Command(WaterTube from, WaterTube to, Color color, int removeCount) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.removeCount = removeCount;
    }

    /**
     * Executes the command by removing the specified number from the
     * "from" and transferring that same number of the color to the "to"
     */
    public void execute() {
        from.remove(removeCount);
        to.add(removeCount, color);
    }

    /**
     * Performs the reverse of command by removing the specified number from the
     * "to" and transferring that same number of the color to the "from"
     */
    public void reverse() {
        to.remove(removeCount);
        from.add(removeCount, color);
    }
}
