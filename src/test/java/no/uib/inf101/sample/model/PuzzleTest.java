package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PuzzleTest {

    @Test
    public void testNewPuzzle() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        List<WaterTube> waterTubeList = puzzle.getWaterTubeList();
        assertNotNull(waterTubeList);
        assertEquals(3, waterTubeList.size());
    }

    @Test
    public void testHandleClick() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        WaterTube selectedTube = puzzle.getWaterTubeList().get(0);
        puzzle.handleClick(selectedTube.getPosition());
        assertTrue(selectedTube.isSelected());
    }

    
    @Test
    public void testGameWon() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        List<WaterTube> waterTubeList = puzzle.getWaterTubeList();
        for (WaterTube tube : waterTubeList) {
            tube.add(4, Color.PINK);
        }
        assertTrue(puzzle.gameWon());
    }

    @Test
    public void testUndo() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        WaterTube tube = puzzle.getWaterTubeList().get(0);
        tube.add(1, Color.PINK);
        tube.add(1, Color.WHITE);
        tube.add(1, Color.ORANGE);
        tube.add(1, Color.CYAN);
        Color[] initialState = tube.getColours().clone();
        puzzle.undo();
        assertArrayEquals(initialState, tube.getColours());
    }

    /*@Test
    public void testIsUndoAvailable() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        assertFalse(puzzle.isUndoAvailable());
        WaterTube tube1 = puzzle.getWaterTubeList().get(0);
        Position clickPosition = new Position(tube1.getPosition().x + 10, tube1.getPosition().y + 10); // Adjust the position as needed
        puzzle.handleClick(clickPosition);
        assertTrue(puzzle.isUndoAvailable());
    }*/
    

    @Test
    public void testPourTube() {
        Puzzle puzzle = new Puzzle(4, 2, 1, 3);
        WaterTube fromTube = new WaterTube(new Position(0, 0), 100, 200);
        WaterTube toTube = new WaterTube(new Position(150, 0), 100, 200);
        fromTube.add(2, Color.WHITE);
        fromTube.add(1, Color.pink);
        boolean pourResult = puzzle.pourTube(fromTube, toTube);
        assertTrue(pourResult);
        assertEquals(1, toTube.countTopColour());
        assertEquals(2, fromTube.countTopColour());
    }

    @Test
    public void testPourTubeOnlyEmptyOrSameColor() {
    Puzzle puzzle = new Puzzle(4, 2, 1, 3);
    WaterTube fromTube = new WaterTube(new Position(0, 0), 100, 200);
    WaterTube toTubeEmpty = new WaterTube(new Position(150, 0), 100, 200);
    WaterTube toTubeSameColor = new WaterTube(new Position(300, 0), 100, 200);
    fromTube.add(2, Color.WHITE);
    fromTube.add(1, Color.PINK);
    boolean pourResultToEmpty = puzzle.pourTube(fromTube, toTubeEmpty);
    assertTrue(pourResultToEmpty);
    assertEquals(1, toTubeEmpty.countTopColour()); 
    assertEquals(2, fromTube.countTopColour()); 
    toTubeSameColor.add(1, Color.WHITE); 
    boolean pourResultToSameColor = puzzle.pourTube(fromTube, toTubeSameColor);
    assertTrue(pourResultToSameColor);
    assertEquals(3, toTubeSameColor.countTopColour());
    assertEquals(0, fromTube.countTopColour()); 
}

}
