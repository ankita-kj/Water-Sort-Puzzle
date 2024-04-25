package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Color;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.Command;
import no.uib.inf101.sample.model.Position;
import no.uib.inf101.sample.model.WaterTube;

public class CommandTest {
    @Test
    public void testExecute() {
        WaterTube from = new WaterTube(new Position(0, 0), 100, 200);
        WaterTube to = new WaterTube(new Position(150, 0), 100, 200);
        from.add(2, Color.PINK);
        Command command = new Command(from, to, Color.PINK, 1);
        command.execute();
        assertEquals(1, from.countTopColor());
        assertEquals(1, to.countTopColor());
        assertEquals(Color.PINK, from.topColor());
        assertEquals(Color.PINK, to.topColor());
    }

    @Test
    public void testReverse() {
        WaterTube from = new WaterTube(new Position(0, 0), 100, 200);
        WaterTube to = new WaterTube(new Position(150, 0), 100, 200);
        to.add(2, Color.WHITE);
        Command command = new Command(from, to, Color.WHITE, 1);
        command.reverse();
        assertEquals(1, from.countTopColor());
        assertEquals(1, to.countTopColor());
        assertEquals(Color.WHITE, from.topColor());
        assertEquals(Color.WHITE, to.topColor());
    }
}
