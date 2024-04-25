//Used Chat-GPT as a helping tool to create a slider panel

package no.uib.inf101.sample.game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import no.uib.inf101.sample.controller.GamePanel;
import no.uib.inf101.sample.model.Puzzle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Difficulty class:
 * Acts as a separate JFrame. Allows access to changing the four different 
 * properties for generating puzzles
 */
public class Difficulty implements ChangeListener, ActionListener {
    private GamePanel gamePanel;
    private JFrame frame;
    private JSlider tubesSlider;
    private JSlider emptySlider;
    private JSlider emptyAtEndSlider;
    private JSlider colorsSlider;
    private JLabel tubesLabel;
    private JLabel emptyLabel;
    private JLabel emptyAtEndLabel;
    private JLabel colorsLabel;
    private JButton startButton;
    private boolean loaded;

    /**
     * Creates a JFrame ready to be shown with show()
     * 
     * @param gamePanel Reference to the game panel for creating new games
     */
    public Difficulty(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        frame = new JFrame("Difficulty");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().add(createDifficultyPanel());
        frame.pack();
    }

    private JPanel createDifficultyPanel() {
        loaded = false;
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(250, 270));
        tubesSlider = createSlider(2, 20, 10);
        emptySlider = createSlider(1, tubesSlider.getValue() / 2, 4);
        emptyAtEndSlider = createSlider(0, emptySlider.getValue(), 2);
        colorsSlider = createSlider(1, Puzzle.possibleColors.length, 3);
        tubesLabel = createLabel("");
        emptyLabel = createLabel("");
        emptyAtEndLabel = createLabel("");
        colorsLabel = createLabel("");
        startButton = createButton("Start");
        loaded = true;
        updateText();

        panel.add(tubesLabel);
        panel.add(tubesSlider);
        panel.add(emptyLabel);
        panel.add(emptySlider);
        panel.add(emptyAtEndLabel);
        panel.add(emptyAtEndSlider);
        panel.add(colorsLabel);
        panel.add(colorsSlider);
        panel.add(startButton);

        return panel;
    }

    private JSlider createSlider(int min, int max, int startValue) {
        JSlider slider = new JSlider(min, max);
        slider.setValue(startValue);
        slider.addChangeListener(this);
        slider.setForeground(new Color(255, 196, 0));
        slider.setBackground(Color.LIGHT_GRAY);
        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(255, 196, 0));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(141, 24, 24));
        button.setForeground(new Color(255, 196, 0));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (!loaded)
            return;
        emptySlider.setMaximum(tubesSlider.getValue() / 2);
        emptyAtEndSlider.setMaximum(emptySlider.getValue());
        updateText();
    }

    /**
     * Shows the difficulty frame
     */
    public void show() {
        frame.setVisible(true);
    }

    private void updateText() {
        tubesLabel.setText("Tubes: " + tubesSlider.getValue());
        emptyLabel.setText("Empty: " + emptySlider.getValue());
        emptyAtEndLabel.setText("Empty At End: " + emptyAtEndSlider.getValue());
        colorsLabel.setText("Colours: " + colorsSlider.getValue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gamePanel.applyDifficulty(tubesSlider.getValue(), emptySlider.getValue(),
                emptyAtEndSlider.getValue(), colorsSlider.getValue());
        frame.setVisible(false);
    }
}
