package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class WaterTubeTest {

    @Test
    public void testSetSelected() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        tube.setSelected(true);
        assertTrue(tube.isSelected());
        tube.setSelected(false);
        assertFalse(tube.isSelected());
    }

    @Test
    public void testTopColour() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        assertEquals(Color.BLACK, tube.topColor());
        tube.add(1, Color.WHITE);
        assertEquals(Color.WHITE, tube.topColor());
    }

    @Test
    public void testCountRoom() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        assertEquals(4, tube.countRoom());
        tube.add(2, Color.PINK);
        assertEquals(2, tube.countRoom());
    }

    @Test
    public void testCountTopColour() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        tube.add(2, Color.PINK);
        tube.add(1, Color.WHITE);
        assertEquals(1, tube.countTopColor());
    }

    @Test
    public void testRemove() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        tube.add(2, Color.PINK);
        tube.remove(1);
        assertEquals(1, tube.countTopColor());
    }

    @Test
    public void testAdd() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        tube.add(2, Color.PINK);
        assertEquals(2, tube.countTopColor());
    }

    @Test
    public void testFourOfSameColour() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        tube.add(3, Color.PINK);
        assertFalse(tube.fourOfSameColor());
        tube.add(1, Color.PINK);
        assertTrue(tube.fourOfSameColor());
    }

    @Test
    public void testGetColors() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        assertEquals(4, tube.getColors().length);
    }

    @Test
    public void testSetColors() {
        WaterTube tube = new WaterTube(new Position(0, 0), 100, 200);
        Color[] newColors = { Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE };
        tube.setColors(newColors);
        assertArrayEquals(newColors, tube.getColors());
    }
}
