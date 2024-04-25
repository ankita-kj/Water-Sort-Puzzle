package no.uib.inf101.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.Position;
import no.uib.inf101.sample.model.Puzzle;
import no.uib.inf101.sample.model.WaterTube;

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
    void clearSelection() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        WaterTube selectedTube = puzzle.getWaterTubeList().get(0);
        puzzle.handleClick(selectedTube.getPosition());
        assertNotNull(puzzle.getSelectedTube());
        puzzle.clearSelection();
        assertNull(puzzle.getSelectedTube());
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
        Color[] initialState = tube.getColors().clone();
        puzzle.undo();
        assertArrayEquals(initialState, tube.getColors());
    }

    @Test
    public void testPourTube() {
        Puzzle puzzle = new Puzzle(4, 2, 1, 3);
        WaterTube fromTube = new WaterTube(new Position(0, 0), 100, 200);
        WaterTube toTube = new WaterTube(new Position(150, 0), 100, 200);
        fromTube.add(2, Color.WHITE);
        fromTube.add(1, Color.pink);
        boolean pourResult = puzzle.pourTube(fromTube, toTube);
        assertTrue(pourResult);
        assertEquals(1, toTube.countTopColor());
        assertEquals(2, fromTube.countTopColor());
        assertFalse(puzzle.getCommandHistory().isEmpty());
        assertTrue(puzzle.isUndoAvailable());
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
        assertEquals(1, toTubeEmpty.countTopColor());
        assertEquals(2, fromTube.countTopColor());
        toTubeSameColor.add(1, Color.WHITE);
        boolean pourResultToSameColor = puzzle.pourTube(fromTube, toTubeSameColor);
        assertTrue(pourResultToSameColor);
        assertEquals(3, toTubeSameColor.countTopColor());
        assertEquals(0, fromTube.countTopColor());
    }

    @Test
    public void testReset() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        Color[][] initialColors = new Color[3][4];
        for (int i = 0; i < puzzle.getWaterTubeList().size(); i++) {
            WaterTube tube = puzzle.getWaterTubeList().get(i);
            initialColors[i] = Arrays.copyOf(tube.getColors(), tube.getColors().length);
        }
        WaterTube tube1 = puzzle.getWaterTubeList().get(0);
        WaterTube tube2 = puzzle.getWaterTubeList().get(1);
        tube1.add(2, Color.WHITE);
        tube2.add(1, Color.PINK);
        puzzle.handleClick(new Position(0, 0));
        puzzle.reset();
        assertTrue(puzzle.getCommandHistory().isEmpty());
        assertNull(puzzle.getSelectedTube());
        for (int i = 0; i < puzzle.getWaterTubeList().size(); i++) {
            WaterTube tube = puzzle.getWaterTubeList().get(i);
            assertArrayEquals(initialColors[i], tube.getColors());
        }
    }

    @Test
    void getWaterTubeList() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        assertNotNull(puzzle.getWaterTubeList());
    }

    @Test
    void getCommandHistory() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        assertNotNull(puzzle.getCommandHistory());
    }

    @Test
    void getSelectedTube() {
        Puzzle puzzle = new Puzzle(3, 1, 1, 3);
        puzzle.newPuzzle();
        assertNull(puzzle.getSelectedTube());
    }
}
