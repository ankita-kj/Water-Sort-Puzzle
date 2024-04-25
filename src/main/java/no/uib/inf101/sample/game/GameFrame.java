package no.uib.inf101.sample.game;

import javax.swing.*;
import no.uib.inf101.sample.controller.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GameFrame class:
 * Represents the main frame of the game
 * Contains a GamePanel where the game is displayed
 */
public class GameFrame implements KeyListener {
    private GamePanel gamePanel;

    /**
     * Constructs a new GameFrame
     * Creates a GamePanel, and adds it to the frame
     */
    public GameFrame() {
        JFrame frame = new JFrame("Water sort puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        gamePanel = new GamePanel();
        frame.getContentPane().add(gamePanel);
        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleInput(e.getKeyCode());
    }

    // The overrides below are not used
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
