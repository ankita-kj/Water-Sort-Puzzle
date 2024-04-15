package no.uib.inf101.sample.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import no.uib.inf101.sample.model.Cylinder;
import no.uib.inf101.sample.model.Puzzle;
import no.uib.inf101.sample.model.WaterTube;

public class GameView {
    public static final int PANEL_WIDTH = 1100;
    public static final int PANEL_HEIGHT = 650;

    private List<WaterTube> waterTubeList;
    private Puzzle puzzle;
    private Cylinder nextButton;
    private Cylinder restartButton;
    private Cylinder changeDifficultyButton;
    private Cylinder undoButton;
    private String gameOverMessage = "Great job! You beat the puzzle!";

    public GameView(List<WaterTube> waterTubeList, Puzzle puzzle, Cylinder restartButton,
            Cylinder changeDifficultyButton, Cylinder undoButton, Cylinder nextButton) {
        this.waterTubeList = waterTubeList;
        this.puzzle = puzzle;
        this.restartButton = restartButton;
        this.changeDifficultyButton = changeDifficultyButton;
        this.undoButton = undoButton;
        this.nextButton = nextButton;
    }

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

    public void paintWaterTube(Graphics g, WaterTube watertube) {
        int sectionWidth = watertube.width / 3;
        int sectionHeight = watertube.height / 6;
        int offset = watertube.isSelected ? 0 : sectionHeight;
        g.setColor(watertube.colours[3]);
        g.fillOval(watertube.position.x + sectionWidth,
                watertube.position.y + (3) * sectionHeight + offset + sectionHeight / 2, sectionWidth, sectionHeight);
        g.setColor(Color.WHITE);
        g.drawOval(watertube.position.x + sectionWidth,
                watertube.position.y + (3) * sectionHeight + offset + sectionHeight / 2, sectionWidth, sectionHeight);
        for (int i = 0; i < watertube.colours.length; i++) {
            g.setColor(watertube.colours[i]);
            g.fillRect(watertube.position.x + sectionWidth, watertube.position.y + (i) * sectionHeight + offset,
                    sectionWidth, sectionHeight);
        }
        g.setColor(Color.WHITE);
        g.drawRect(watertube.position.x + sectionWidth, watertube.position.y + offset, sectionWidth, sectionHeight * 4);
        g.setColor(watertube.colours[3]);
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
