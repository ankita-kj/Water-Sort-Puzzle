package no.uib.inf101.model;

import no.uib.inf101.sample.model.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testConstructor() {
        Position position = new Position(10, 20);
        assertEquals(10, position.x);
        assertEquals(20, position.y);
    }

    @Test
    public void testCopyConstructor() {
        Position original = new Position(10, 20);
        Position copy = new Position(original);
        assertEquals(original.x, copy.x);
        assertEquals(original.y, copy.y);
    }

    @Test
    public void testSetPosition() {
        Position position = new Position(10, 20);
        position.setPosition(30, 40);
        assertEquals(30, position.x);
        assertEquals(40, position.y);
    }

    @Test
    public void testAdd() {
        Position position = new Position(10, 20);
        position.add(new Position(5, 10));
        assertEquals(15, position.x);
        assertEquals(30, position.y);
    }

    @Test
    public void testDistanceTo() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(3, 4);
        assertEquals(5.0, position1.distanceTo(position2), 0.0001);
    }

    @Test
    public void testMultiply() {
        Position position = new Position(2, 3);
        position.multiply(2);
        assertEquals(4, position.x);
        assertEquals(6, position.y);
    }

    @Test
    public void testSubtract() {
        Position position = new Position(10, 20);
        position.subtract(new Position(5, 10));
        assertEquals(5, position.x);
        assertEquals(10, position.y);
    }
}
