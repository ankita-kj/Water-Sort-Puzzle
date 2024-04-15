package no.uib.inf101.sample.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.uib.inf101.sample.controller.GamePanel;
import no.uib.inf101.sample.view.GameView;

public class Puzzle {
    public static final Color[] possibleColours = { Color.WHITE, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW,
            Color.CYAN, Color.PINK, Color.MAGENTA, new Color(255, 102, 102) };

    private List<WaterTube> waterTubeList;
    private List<Command> commandHistory;
    private WaterTube selected;
    private int tubeCount;
    private int emptyCount;
    private int emptyAtEndCount;
    private int colourCount;
    private Color[] initialState;
    private GameView gameView;

    public Puzzle(int tubeCount, int emptyCount, int emptyAtEndCount, int colourCount) {
        waterTubeList = new ArrayList<>();
        commandHistory = new ArrayList<>();
        this.tubeCount = tubeCount;
        this.emptyCount = emptyCount;
        this.emptyAtEndCount = emptyAtEndCount;
        this.colourCount = colourCount;
        gameView = new GameView(waterTubeList, null, selected, selected, selected, selected);
    }

    public void newPuzzle() {
        waterTubeList.clear();
        commandHistory.clear();
        List<Color> colourSelection = Arrays.asList(possibleColours.clone());
        Collections.shuffle(colourSelection);
        int fitTubes = (tubeCount % 2 == 0) ? tubeCount / 2 : (tubeCount + 1) / 2;
        int tubeWidth = Math.min(GamePanel.PANEL_WIDTH / fitTubes, 150);
        int tubeHeight = 200;
        int x = 0, y = 100;
        for (int i = 0; i < tubeCount; i++) {
            waterTubeList.add(new WaterTube(new Position(x, y), tubeWidth, tubeHeight));
            x += tubeWidth;
            if (x + tubeWidth > GamePanel.PANEL_WIDTH) {
                x = 0;
                y += tubeHeight;
            }
        }
        int[] colourCounts = new int[colourCount];
        int totalColours = 0;
        for (int i = 0; i < tubeCount - emptyCount; i++) {
            colourCounts[(int) (Math.random() * colourCounts.length)] += 4;
            totalColours += 4;
        }
        while (totalColours > 0) {
            int colourID;
            do {
                colourID = (int) (Math.random() * colourCounts.length);
            } while (colourCounts[colourID] == 0);
            int waterTubeID;
            do {
                waterTubeID = (int) (Math.random() * (waterTubeList.size() - emptyAtEndCount));
            } while (waterTubeList.get(waterTubeID).countRoom() == 0);
            waterTubeList.get(waterTubeID).add(1, colourSelection.get(colourID));
            totalColours--;
            colourCounts[colourID]--;
        }
        saveInitialState();
    }

    public void clearSelection() {
        if (selected != null) {
            selected.setSelected(false);
        }
        selected = null;
    }

    public void handleClick(Position mousePosition) {
        WaterTube waterTubeAtMouse = getWaterTubeAt(mousePosition);
        if (waterTubeAtMouse == null)
            return;
        if (selected == null) {
            if (waterTubeAtMouse.topColour() != Color.BLACK) {
                selected = waterTubeAtMouse;
                selected.setSelected(true);
            }
        } else if (selected != waterTubeAtMouse) {
            pourTube(selected, waterTubeAtMouse);
            clearSelection();
        }
    }

    public boolean gameWon() {
        for (WaterTube waterTube : waterTubeList) {
            if (!waterTube.fourOfSameColour()) {
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

    boolean pourTube(WaterTube from, WaterTube to) {
        if (from.topColour() == Color.BLACK || to.countRoom() == 0
                || (to.topColour() != Color.BLACK && from.topColour() != to.topColour())) {
            return false;
        }
        int removeCount = Math.min(from.countTopColour(), to.countRoom());
        Command command = new Command(from, to, from.topColour(), removeCount);
        commandHistory.add(command);
        command.execute();
        return true;
    }

    public void undo() {
        if (commandHistory.size() == 0)
            return;
        clearSelection();
        Command command = commandHistory.get(commandHistory.size() - 1);
        commandHistory.remove(commandHistory.size() - 1);
        command.reverse();
    }

    public boolean isUndoAvailable() {
        return commandHistory.size() != 0;
    }

    private void saveInitialState() {
        initialState = new Color[waterTubeList.size() * 4];
        int saveIndex = 0;
        for (int i = 0; i < waterTubeList.size(); i++) {
            Color[] colours = waterTubeList.get(i).getColours();
            for (int j = 0; j < 4; j++, saveIndex++) {
                initialState[saveIndex] = colours[j];
            }
        }
    }

    public void reset() {
        commandHistory.clear();
        clearSelection();
        int saveIndex = 0;
        for (int i = 0; i < waterTubeList.size(); i++) {
            Color[] colourData = new Color[4];
            for (int j = 0; j < 4; j++, saveIndex++) {
                colourData[j] = initialState[saveIndex];
            }
            waterTubeList.get(i).setColours(colourData);
        }
    }

    public List<WaterTube> getWaterTubeList() {
        return waterTubeList;
    }

    public void paint(Graphics g) {
        waterTubeList.forEach(waterTube -> gameView.paintWaterTube(g, waterTube));
    }

}
