package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.Cylinder;
import no.uib.inf101.sample.model.Position;

public class CylinderTest {

    @Test
    public void testConstructorWithPosition() {
        Position position = new Position(10, 20);
        Cylinder cylinder = new Cylinder(position, 30, 40);
        assertEquals(10, cylinder.getPosition().x);
        assertEquals(20, cylinder.getPosition().y);
        assertEquals(30, cylinder.getWidth());
        assertEquals(40, cylinder.getHeight());
    }

    @Test
    public void testConstructorWithCoordinates() {
        Cylinder cylinder = new Cylinder(10, 20, 30, 40);
        assertEquals(10, cylinder.getPosition().x);
        assertEquals(20, cylinder.getPosition().y);
        assertEquals(30, cylinder.getWidth());
        assertEquals(40, cylinder.getHeight());
    }

    @Test
    public void testGetWidth() {
        Cylinder cylinder = new Cylinder(10, 20, 30, 40);
        assertEquals(30, cylinder.getWidth());
    }

    @Test
    public void testGetHeight() {
        Cylinder cylinder = new Cylinder(10, 20, 30, 40);
        assertEquals(40, cylinder.getHeight());
    }

    @Test
    public void testGetPosition() {
        Position position = new Position(10, 20);
        Cylinder cylinder = new Cylinder(position, 30, 40);
        assertEquals(position, cylinder.getPosition());
    }

    @Test
    public void testGetCentre() {
        Cylinder cylinder = new Cylinder(10, 20, 30, 40);
        Position expectedCentre = new Position(25, 40);
        assertEquals(expectedCentre, cylinder.getCentre());
    }

    @Test
    public void testIsPositionInside_Inside() {
        Cylinder cylinder = new Cylinder(new Position(10, 10), 100, 200);
        Position insidePosition = new Position(50, 50);
        assertTrue(cylinder.isPositionInside(insidePosition));
    }

    @Test
    public void testIsPositionInside_Outside() {
        Cylinder cylinder = new Cylinder(new Position(10, 10), 100, 200);
        Position outsidePosition = new Position(200, 200);
        assertFalse(cylinder.isPositionInside(outsidePosition));
    }

    @Test
    public void testIsIntersecting_Intersecting() {
        Cylinder cylinder1 = new Cylinder(new Position(10, 10), 100, 200);
        Cylinder cylinder2 = new Cylinder(new Position(50, 50), 100, 200);
        assertTrue(cylinder1.isIntersecting(cylinder2));
    }

    @Test
    public void testIsIntersecting_NotIntersecting() {
        Cylinder cylinder1 = new Cylinder(new Position(10, 10), 100, 200);
        Cylinder cylinder2 = new Cylinder(new Position(150, 250), 100, 200);
        assertFalse(cylinder1.isIntersecting(cylinder2));
    } 
}
