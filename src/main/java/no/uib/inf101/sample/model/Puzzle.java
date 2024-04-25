//Used Chat-GPT as a helping tool for the puzzle class

package no.uib.inf101.sample.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.uib.inf101.sample.view.GameView;

/**
 * Puzzle class:
 * Represents a puzzle with a collection of water tubes
 */
public class Puzzle {
    /**
     * All the possible colors that can be used for the game
     */
    public static final Color[] possibleColors = { Color.WHITE, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW,
            Color.CYAN, Color.PINK, Color.MAGENTA, new Color(255, 102, 102) };

    private List<WaterTube> waterTubeList;
    private List<Command> commandHistory;
    private WaterTube selected;
    private int tubeCount;
    private int emptyCount;
    private int emptyAtEndCount;
    private int colorCount;
    private Color[] initialState;
    private GameView gameView;

    /**
     * Prepares the puzzle ready for a new puzzle to be created
     * 
     * @param tubeCount       The total number of water tubes to include
     * @param emptyCount      The number of water tubes that will be empty at the
     *                        end of the solution
     * @param emptyAtEndCount The number of water tubes that begin empty at the end
     * @param colorCount      The number of different colors that can be randomly
     *                        placed
     */
    public Puzzle(int tubeCount, int emptyCount, int emptyAtEndCount, int colorCount) {
        waterTubeList = new ArrayList<>();
        commandHistory = new ArrayList<>();
        this.tubeCount = tubeCount;
        this.emptyCount = emptyCount;
        this.emptyAtEndCount = emptyAtEndCount;
        this.colorCount = colorCount;
        gameView = new GameView(waterTubeList, null, selected, selected, selected, selected);
    }

    /**
     * Generates a new puzzle by clearing the old one, and then using the
     * specified parameters for the class to generate a new set of water tubes
     */
    public void newPuzzle() {
        waterTubeList.clear();
        commandHistory.clear();
        List<Color> colorSelection = Arrays.asList(possibleColors.clone());
        Collections.shuffle(colorSelection);
        // Calculate the spacing to be used for visual elements
        int fitTubes = (tubeCount % 2 == 0) ? tubeCount / 2 : (tubeCount + 1) / 2;
        int tubeWidth = Math.min(GameView.PANEL_WIDTH / fitTubes, 150);
        int tubeHeight = 200;
        int x = 0, y = 100;
        // Create all the test tubes split over up to two rows.
        for (int i = 0; i < tubeCount; i++) {
            waterTubeList.add(new WaterTube(new Position(x, y), tubeWidth, tubeHeight));
            x += tubeWidth;
            if (x + tubeWidth > GameView.PANEL_WIDTH) {
                x = 0;
                y += tubeHeight;
            }
        }
        // Create a random selection of color counts to apply colors randomly
        int[] colorCounts = new int[colorCount];
        int totalColors = 0;
        for (int i = 0; i < tubeCount - emptyCount; i++) {
            colorCounts[(int) (Math.random() * colorCounts.length)] += 4;
            totalColors += 4;
        }
        // Distribute all the colors into the tubes randomly
        while (totalColors > 0) {
            int colorID;
            do {
                colorID = (int) (Math.random() * colorCounts.length);
            } while (colorCounts[colorID] == 0);
            int waterTubeID;
            do {
                waterTubeID = (int) (Math.random() * (waterTubeList.size() - emptyAtEndCount));
            } while (waterTubeList.get(waterTubeID).countRoom() == 0);
            waterTubeList.get(waterTubeID).add(1, colorSelection.get(colorID));
            totalColors--;
            colorCounts[colorID]--;
        }
        saveInitialState();
    }

    /**
     * Clears any currently selected water tube if there is one
     */
    public void clearSelection() {
        if (selected != null) {
            selected.setSelected(false);
        }
        selected = null;
    }

    /**
    * Handles a mouse click event on the puzzle.
    * If a water tube is clicked, it either selects the tube or pours water into it,
    * depending on the current selection and the contents of the clicked tube.
    * 
    * @param mousePosition The position of the mouse click
    */
    public void handleClick(Position mousePosition) {
        WaterTube waterTubeAtMouse = getWaterTubeAt(mousePosition);
        if (waterTubeAtMouse == null)
            return;
        if (selected == null) {
            if (waterTubeAtMouse.topColor() != Color.BLACK) {
                selected = waterTubeAtMouse;
                selected.setSelected(true);
            }
        } else if (selected != waterTubeAtMouse) {
            pourTube(selected, waterTubeAtMouse);
            clearSelection();
        }
    }

    /**
     * Checks if the game is won by testing if all the test tubes have
     * four of the same color
     * 
     * @return True if the game has been won
     */
    public boolean gameWon() {
        for (WaterTube waterTube : waterTubeList) {
            if (!waterTube.fourOfSameColor()) {
                return false;
            }
        }
        return true;
    }

    private WaterTube getWaterTubeAt(Position mousePosition) {
        for (WaterTube waterTube : waterTubeList) {
            if (waterTube.isPositionInside(mousePosition)) {
                return waterTube;
            }
        }
        return null;
    }

    /**
     * Tries to pour from one tube into the other
     * If any requirement is not met the pour fails and nothing happens
     * If the pour succeeds the command is logged into the command history for undo
     * 
     * @param from The water tube to pour from
     * @param to   The water tube to pour into
     * @return True if the pour was successful
     */
    public boolean pourTube(WaterTube from, WaterTube to) {
        if (from.topColor() == Color.BLACK || to.countRoom() == 0
                || (to.topColor() != Color.BLACK && from.topColor() != to.topColor())) {
            return false;
        }
        int removeCount = Math.min(from.countTopColor(), to.countRoom());
        Command command = new Command(from, to, from.topColor(), removeCount);
        commandHistory.add(command);
        command.execute();
        return true;
    }

    /**
     * Will reverse the most recent command and remove it from the command history
     * Does nothing if there are no commands to undo
     */
    public void undo() {
        if (commandHistory.size() == 0)
            return;
        clearSelection();
        Command command = commandHistory.get(commandHistory.size() - 1);
        commandHistory.remove(commandHistory.size() - 1);
        command.reverse();
    }

    /**
     * Checks if there are any commands in the command history to undo
     * 
     * @return True if undo can be called
     */
    public boolean isUndoAvailable() {
        return commandHistory.size() != 0;
    }

    private void saveInitialState() {
        initialState = new Color[waterTubeList.size() * 4];
        int saveIndex = 0;
        for (int i = 0; i < waterTubeList.size(); i++) {
            Color[] colors = waterTubeList.get(i).getColors();
            for (int j = 0; j < 4; j++, saveIndex++) {
                initialState[saveIndex] = colors[j];
            }
        }
    }

    /**
     * Resets back to the initial state of the current puzzle
     * Clears any selection and command history then,
     * forces all the saved data into the test tubes
     */
    public void reset() {
        commandHistory.clear();
        clearSelection();
        int saveIndex = 0;
        for (int i = 0; i < waterTubeList.size(); i++) {
            Color[] colorData = new Color[4];
            for (int j = 0; j < 4; j++, saveIndex++) {
                colorData[j] = initialState[saveIndex];
            }
            waterTubeList.get(i).setColors(colorData);
        }
    }

    /**
     * Retrieves the list of water tubes in the puzzle
     * 
     * @return The list of water tubes
     */
    public List<WaterTube> getWaterTubeList() {
        return waterTubeList;
    }

    /**
     * Retrieves the command history of the puzzle
     * 
     * @return The list of commands representing the history of actions performed in
     *         the puzzle
     */
    public List<Command> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Retrieves the currently selected water tube
     * 
     * @return The selected water tube, or null if no tube is selected
     */
    public WaterTube getSelectedTube() {
        return selected;
    }

    /**
     * Paints the water tubes in the puzzle
     * 
     * @param g The graphics context used for painting
     */
    public void paint(Graphics g) {
        waterTubeList.forEach(waterTube -> gameView.paintWaterTube(g, waterTube));
    }
}
