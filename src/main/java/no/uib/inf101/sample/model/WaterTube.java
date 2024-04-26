package no.uib.inf101.sample.model;

import java.awt.*;

/**
 * WaterTube class:
 * Represents a single water tube consisting of four colors
 */
public class WaterTube extends Cylinder implements Tube {

    /**
     * The current four colors shown in the water tube
     */
    public Color[] colors;
    /**
     * If the water tube is currently selected the posistion is offset to move it up
     */
    public boolean isSelected;

    /**
     * Creates a water tube and fills it with block (empty)
     * 
     * @param position Position to place the water tube
     * @param width    Width of the water tube
     * @param height   Height of the water tube
     */
    public WaterTube(Position position, int width, int height) {
        super(position, width, height);
        colors = new Color[4];
        for (int i = 0; i < 4; i++) {
            colors[i] = Color.BLACK;
        }
        isSelected = false;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public Color topColor() {
        for (int i = 0; i < 4; i++) {
            if (colors[i] != Color.BLACK)
                return colors[i];
        }
        return Color.BLACK;
    }

    @Override
    public int countRoom() {
        int freeCount = 0;
        for (int i = 0; i < 4; i++) {
            if (colors[i] == Color.BLACK)
                freeCount++;
        }
        return freeCount;
    }

    @Override
    public int countTopColor() {
        for (int i = 0; i < 4; i++) {
            if (colors[i] != Color.BLACK) {
                int count = 1;
                for (int j = i + 1; j < 4 && colors[i] == colors[j]; j++) {
                    count++;
                }
                return count;
            }
        }
        return 0;
    }

    @Override
    public void remove(int count) {
        int removed = 0;
        for (int i = 0; i < 4 && removed != count; i++) {
            if (colors[i] != Color.BLACK) {
                colors[i] = Color.BLACK;
                removed++;
            }
        }
    }

    @Override
    public void add(int count, Color color) {
        int addFrom = 0;
        for (int i = 0; i < 4; i++) {
            if (colors[i] != Color.BLACK) {
                addFrom = i - 1;
                break;
            } else if (i == 3 && colors[i] == Color.BLACK) {
                addFrom = i;
                break;
            }
        }
        int addCount = 1;
        for (int j = addFrom; j >= 0 && addCount <= count; j--) {
            addCount++;
            colors[j] = color;
        }
    }

    @Override
    public boolean fourOfSameColor() {
        for (int i = 1; i < 4; i++) {
            if (colors[i] != colors[0]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Color[] getColors() {
        return colors;
    }

    @Override
    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}
