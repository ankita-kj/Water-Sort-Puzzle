package no.uib.inf101.sample.controller;

import org.junit.jupiter.api.Test;

public class GamePanelTest {

    @Test
    public void testApplyDifficulty(){
        GamePanel gamePanel = new GamePanel();
        gamePanel.applyDifficulty(5, 2, 1, 4);
    }

    @Test
    public void testNextPuzzle(){
        GamePanel gamePanel = new GamePanel();
        gamePanel.nextPuzzle();
    }
}
