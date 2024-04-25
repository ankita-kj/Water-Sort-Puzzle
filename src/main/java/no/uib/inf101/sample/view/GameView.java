package no.uib.inf101.sample.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import no.uib.inf101.sample.model.Cylinder;
import no.uib.inf101.sample.model.Puzzle;
import no.uib.inf101.sample.model.WaterTube;

/**
 * GameView class:
 * provides the graphical representation of the game, including water tubes,
 * buttons, and game over screen
 */
public class GameView {
    /**
     * Width of the game panel
     */
    public static final int PANEL_WIDTH = 1100;
    /**
     * Height of the game panel
     */
    public static final int PANEL_HEIGHT = 650;
    private List<WaterTube> waterTubeList;
    private Puzzle puzzle;
    private Cylinder nextButton;
    private Cylinder restartButton;
    private Cylinder changeDifficultyButton;
    private Cylinder undoButton;
    private String gameOverMessage = "Great job! You beat the puzzle!";

    /**
     * Constructs a GameView with the specified parameter
     * 
     * @param waterTubeList          The list of water tubes in the game
     * @param puzzle                 The puzzle object representing the game state
     * @param restartButton          The button for restarting the game
     * @param changeDifficultyButton The button for changing the game difficulty
     * @param undoButton             The button for undoing the last move
     * @param nextButton             The button for moving on to the next puzzle
     */
    public GameView(List<WaterTube> waterTubeList, Puzzle puzzle, Cylinder restartButton,
            Cylinder changeDifficultyButton, Cylinder undoButton, Cylinder nextButton) {
        this.waterTubeList = waterTubeList;
        this.puzzle = puzzle;
        this.restartButton = restartButton;
        this.changeDifficultyButton = changeDifficultyButton;
        this.undoButton = undoButton;
        this.nextButton = nextButton;
    }

    /**
     * Paints the graphical representation of the game
     * 
     * @param g        The Graphics object to paint on
     * @param gameOver A flag indicating whether the game is over
     */
    public void paint(Graphics g, boolean gameOver) {
        for (WaterTube waterTube : waterTubeList) {
            paintWaterTube(g, waterTube);
        }
        if (gameOver) {
            drawGameOver(g);
            drawButtons(g, "Next", nextButton, true);
            drawButtons(g, "Restart(R)", restartButton, false);
            drawButtons(g, "Difficulty(D)", changeDifficultyButton, true);
            drawButtons(g, "Undo(U)", undoButton, false);
        } else {
            drawButtons(g, "Restart(R)", restartButton, true);
            drawButtons(g, "Difficulty(D)", changeDifficultyButton, true);
            drawButtons(g, "Undo(U)", undoButton, puzzle.isUndoAvailable());
        }
        drawTitle(g);
    }

    /**
     * Paints the graphical representation of a water tube
     * 
     * @param g         The Graphics object to paint on
     * @param watertube The WaterTube object to paint
     */
    public void paintWaterTube(Graphics g, WaterTube watertube) {
        int sectionWidth = watertube.width / 3;
        int sectionHeight = watertube.height / 6;
        int offset = watertube.isSelected ? 0 : sectionHeight;
        g.setColor(watertube.colors[3]);
        g.fillOval(watertube.position.x + sectionWidth,
                watertube.position.y + (3) * sectionHeight + offset + sectionHeight / 2, sectionWidth, sectionHeight);
        g.setColor(Color.WHITE);
        g.drawOval(watertube.position.x + sectionWidth,
                watertube.position.y + (3) * sectionHeight + offset + sectionHeight / 2, sectionWidth, sectionHeight);
        for (int i = 0; i < watertube.colors.length; i++) {
            g.setColor(watertube.colors[i]);
            g.fillRect(watertube.position.x + sectionWidth, watertube.position.y + (i) * sectionHeight + offset,
                    sectionWidth, sectionHeight);
        }
        g.setColor(Color.WHITE);
        g.drawRect(watertube.position.x + sectionWidth, watertube.position.y + offset, sectionWidth, sectionHeight * 4);
        g.setColor(watertube.colors[3]);
        g.fillRect(watertube.position.x + sectionWidth + 1, watertube.position.y + (3) * sectionHeight + offset + 1,
                sectionWidth - 1, sectionHeight);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(99, 99, 99, 179));
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.PINK);
        g.fillRect(0, PANEL_HEIGHT / 2 - 20, PANEL_WIDTH, 40);
        g.setColor(Color.PINK);
        g.drawRect(0, PANEL_HEIGHT / 2 - 20, PANEL_WIDTH, 40);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(gameOverMessage);
        g.drawString(gameOverMessage, PANEL_WIDTH / 2 - strWidth / 2, PANEL_HEIGHT / 2 + 10);
        drawButtons(g, "Next", nextButton, true);
    }

    private void drawButtons(Graphics g, String text, Cylinder cylinder, boolean isEnabled) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(isEnabled ? Color.PINK : new Color(88, 88, 88));
        g.fillRect(cylinder.position.x, cylinder.position.y, cylinder.width, cylinder.height);
        g.setColor(Color.PINK);
        g.drawRect(cylinder.position.x, cylinder.position.y, cylinder.width, cylinder.height);
        g.setColor(Color.WHITE);
        int strWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, cylinder.position.x + cylinder.width / 2 - strWidth / 2, cylinder.position.y + 22);
    }

    private void drawTitle(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        int strWidth = g.getFontMetrics().stringWidth("Water sort puzzle!");
        g.drawString("Water sort puzzle!", PANEL_WIDTH / 2 - strWidth / 2 + 60, 70);
    }
}
