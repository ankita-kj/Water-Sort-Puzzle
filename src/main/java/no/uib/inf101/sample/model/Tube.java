package no.uib.inf101.sample.model;

import java.awt.Color;

public interface Tube {
    /**
     * Changes the current selection state of a tube
     * 
     * @param isSelected True to make the tube move upward, false to use default
     *                   posistion
     */
    void setSelected(boolean isSelected);

    /**
     * Checks whether the tube is currently selected
     * 
     * @return True if the tube is selected, false otherwise
     */
    boolean isSelected();

    /**
     * Finds the top color of the tube if there is one
     * 
     * @return BLACK, or the first top color that is not black
     */
    Color topColor();

    /**
     * Gets how many positions are empty (black) in the tube
     * 
     * @return A number between 0 and 4
     */
    int countRoom();

    /**
     * Gets the number of occurrences of the top not black color
     * 
     * @return A number between 0 and 4
     */
    int countTopColor();

    /**
     * Removes count number of non-black elements by turning the black
     * 
     * @param count The number of non-black elements to remove
     */
    void remove(int count);

    /**
     * Finds the point to start inserting at, and then inserts
     * upward to fill count number of places with the color
     * 
     * @param count Number of elements to add of color
     * @param color The color of the elements to add
     */
    void add(int count, Color color);

    /**
     * Test if the elements all match in the tube
     * Also true if the tube is empty
     * 
     * @return True if the elements in the tube are all the same
     */
    boolean fourOfSameColor();

    /**
     * Gets all the colors represented in the tube
     * 
     * @return The array of colors for this object
     */
    Color[] getColors();

    /**
     * Overwrites the current colors with the new colors array
     * 
     * @param colors The colors to apply
     */
    void setColors(Color[] colors);
}
