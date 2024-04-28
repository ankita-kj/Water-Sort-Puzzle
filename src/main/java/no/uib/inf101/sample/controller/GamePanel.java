package no.uib.inf101.sample.controller;

import javax.swing.*;
import no.uib.inf101.sample.game.Difficulty;
import no.uib.inf101.sample.model.Cylinder;
import no.uib.inf101.sample.model.Position;
import no.uib.inf101.sample.model.CreatePuzzle;
import no.uib.inf101.sample.view.GameView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * GamePanel class:
 * The primary of game interaction. Manages the buttons,
 * and controls the puzzle by mouse interactions
 */
public class GamePanel extends JPanel implements MouseListener {

    private CreatePuzzle puzzle;
    private Difficulty difficulty;
    private boolean gameOver;
    private Cylinder restartButton;
    private Cylinder changeDifficultyButton;
    private Cylinder undoButton;
    private Cylinder nextButton;
    private GameView gameView;

    /**
     * Initialises the game with a default puzzle
     */
    public GamePanel() {
        difficulty = new Difficulty(this);
        addMouseListener(this);
        puzzle = new CreatePuzzle(10, 4, 2, 3);
        puzzle.newPuzzle();
        gameOver = false;
        setPreferredSize(new Dimension(GameView.PANEL_WIDTH, GameView.PANEL_HEIGHT));
        setBackground(new Color(51, 204, 255));
        nextButton = new Cylinder(GameView.PANEL_WIDTH / 2 - 30, GameView.PANEL_HEIGHT / 2 + 40, 60, 30);
        restartButton = new Cylinder(10, 10, 85, 30);
        changeDifficultyButton = new Cylinder(100, 10, 105, 30);
        undoButton = new Cylinder(210, 10, 80, 30);
        gameView = new GameView(puzzle.getWaterTubeList(), puzzle, restartButton, changeDifficultyButton, undoButton,
                nextButton);
    }

    /**
     * Pressing Esc will quit, R will trigger reset,
     * and D will show the difficulty panel
     *
     * @param keyCode The key that was pressed
     */
    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_R) {
            puzzle.reset();
            repaint();
        } else if (keyCode == KeyEvent.VK_D) {
            difficulty.show();
        } else if (keyCode == KeyEvent.VK_U) {
            puzzle.undo();
            repaint();
        }
    }

    /**
     * Creates a new puzzle with the specified properties and removes the old one
     *
     * @param tubeCount       The total number of water tubes to include
     * @param emptyCount      The number of water tubes that will be empty at the
     *                        end of the solution
     * @param emptyAtEndCount The number of water tubes that begin empty at the end
     * @param colorCount      The number of different colors that can be randomly
     *                        placed
     */
    public void applyDifficulty(int tubeCount, int emptyCount, int emptyAtEndCount, int colorCount) {
        CreatePuzzle newPuzzle = new CreatePuzzle(tubeCount, emptyCount, emptyAtEndCount, colorCount);
        newPuzzle.newPuzzle();
        puzzle = newPuzzle;
        gameOver = false;
        gameView = new GameView(puzzle.getWaterTubeList(), puzzle, restartButton, changeDifficultyButton, undoButton,
                nextButton);
        repaint();
    }

    /**
     * Clears game over state and generates a new puzzle with current settings
     */
    public void nextPuzzle() {
        puzzle.newPuzzle();
        gameOver = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON1
                && changeDifficultyButton.isPositionInside(mousePosition)) {
            difficulty.show();
        } else if (gameOver) {
            if (nextButton.isPositionInside(mousePosition)) {
                nextPuzzle();
            }
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            if (restartButton.isPositionInside(mousePosition)) {
                puzzle.reset();
                repaint();
            } else if (undoButton.isPositionInside(mousePosition)) {
                puzzle.undo();
            } else {
                puzzle.handleClick(mousePosition);
                if (puzzle.gameWon()) {
                    gameOver = true;
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            puzzle.clearSelection();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameView.paint(g, gameOver);
    }

    // None of the overrides below are used
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}