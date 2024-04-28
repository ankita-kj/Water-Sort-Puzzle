package no.uib.inf101.sample.model;

public interface Puzzle {
    /**
     * Generates a new puzzle
     */
    void newPuzzle();

    /**
     * Clears any currently selected water tube if there is one
     */
    void clearSelection();

    /**
     * Handles mouse click on the puzzle
     * 
     * @param mousePosition Position of the mouse click
     */
    void handleClick(Position mousePosition);

    /**
     * Checks if the game is won
     * 
     * @return True if the game has been won, false otherwise
     */
    boolean gameWon();

    /**
     * Tries to pour from one water tube into another
     * 
     * @param from The water tube to pour from
     * @param to   The water tube to pour into
     * @return True if the pour was successful, false otherwise
     */
    boolean pourTube(WaterTube from, WaterTube to);

    /**
     * Will reverse the most recent command and remove it from the command history
     * Does nothing if there are no commands to undo
     */
    void undo();

    /**
     * Checks if there are any commands in the command history to undo
     * 
     * @return True if undo can be called, false otherwise
     */
    boolean isUndoAvailable();

    /**
     * Resets back to the inital state of the current puzzle
     */
    void reset();
}
