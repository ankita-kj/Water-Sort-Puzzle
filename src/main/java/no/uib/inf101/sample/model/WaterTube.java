package no.uib.inf101.sample.model;

import java.awt.*;

/**
 * WaterTube class:
 * Represents a single water tube consisting of four colors
 */
public class WaterTube extends Cylinder {
    /**
     * The current four colors shown in the water tube
     */
    public Color[] colors;
    /**
     * If the water tube is currently selected the position is offset to move it up
     */
    public boolean isSelected;

    /**
     * Creates a test tube and fills it with block (empty)
     * 
     * @param position Position to place the water tube
     * @param width Width of the water tube
     * @param height Height of the water tube 
     */
    public WaterTube(Position position, int width, int height) {
        super(position, width, height);
        colors = new Color[4];
        for (int i = 0; i < 4; i++) {
            colors[i] = Color.BLACK;
        }
        isSelected = false;
    }

    /**
     * Changes the current selection state of the water tube 
     * 
     * @param isSelected True to make the test tube move upward, false to use default position
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * Checks whether the water tube is currently selected
     * 
     * @return True if the water tube is selected, false otherwise
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Finds the top color of the test tube if there is one
     * 
     * @return BLACK, or the first top color that is not black
     */
    public Color topColor() {
        for (int i = 0; i < 4; i++) {
            if (colors[i] != Color.BLACK)
                return colors[i];
        }
        return Color.BLACK;
    }

    /**
     * Gets how many positions are empty (Black) in the test tube
     * 
     * @return A number between 0 and 4
     */
    public int countRoom() {
        int freeCount = 0;
        for (int i = 0; i < 4; i++) {
            if (colors[i] == Color.BLACK)
                freeCount++;
        }
        return freeCount;
    }

    /**
     * Gets the number of occurrences of the top not black color
     * 
     * @return  A number between 0 and 4
     */
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

    /**
     * Removes count number of non-black elements by turning the black
     * 
     * @param count The number of non-black elements to remove
     */
    public void remove(int count) {
        int removed = 0;
        for (int i = 0; i < 4 && removed != count; i++) {
            if (colors[i] != Color.BLACK) {
                colors[i] = Color.BLACK;
                removed++;
            }
        }
    }

    /**
     * Finds the point to start inserting at, and then inserts
     * upward to fill count number of places with the color
     * 
     * @param count Number of elements to add of color
     * @param color The color of the elements to add
     */
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

    /**
     * Test if the elements all match in the test tube. This is also true if it is empty
     * 
     * @return True if the elements in the test tube are all the same
     */
    public boolean fourOfSameColor() {
        for (int i = 1; i < 4; i++) {
            if (colors[i] != colors[0]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets all the colors represented in this test tube.
     * 
     * @return The array of colors for this object
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * Overwrites the current colors with the new colors array
     * 
     * @param colors The colors to apply
     */
    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}
