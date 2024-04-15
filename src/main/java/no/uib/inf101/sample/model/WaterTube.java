package no.uib.inf101.sample.model;

import java.awt.*;

public class WaterTube extends Cylinder {
    public Color[] colours;
    public boolean isSelected;

    public WaterTube(Position position, int width, int height) {
        super(position, width, height);
        colours = new Color[4];
        for (int i = 0; i < 4; i++) {
            colours[i] = Color.BLACK;
        }
        isSelected = false;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Color topColour() {
        for (int i = 0; i < 4; i++) {
            if (colours[i] != Color.BLACK)
                return colours[i];
        }
        return Color.BLACK;
    }

    public int countRoom() {
        int freeCount = 0;
        for (int i = 0; i < 4; i++) {
            if (colours[i] == Color.BLACK)
                freeCount++;
        }
        return freeCount;
    }

    public int countTopColour() {
        for (int i = 0; i < 4; i++) {
            if (colours[i] != Color.BLACK) {
                int count = 1;
                for (int j = i + 1; j < 4 && colours[i] == colours[j]; j++) {
                    count++;
                }
                return count;
            }
        }
        return 0;
    }

    public void remove(int count) {
        int removed = 0;
        for (int i = 0; i < 4 && removed != count; i++) {
            if (colours[i] != Color.BLACK) {
                colours[i] = Color.BLACK;
                removed++;
            }
        }
    }

    public void add(int count, Color colour) {
        int addFrom = 0;
        for (int i = 0; i < 4; i++) {
            if (colours[i] != Color.BLACK) {
                addFrom = i - 1;
                break;
            } else if (i == 3 && colours[i] == Color.BLACK) {
                addFrom = i;
                break;
            }
        }
        int addCount = 1;
        for (int j = addFrom; j >= 0 && addCount <= count; j--) {
            addCount++;
            colours[j] = colour;
        }
    }

    public boolean fourOfSameColour() {
        for (int i = 1; i < 4; i++) {
            if (colours[i] != colours[0]) {
                return false;
            }
        }
        return true;
    }

    public Color[] getColours() {
        return colours;
    }

    public void setColours(Color[] colours) {
        this.colours = colours;
    }


}
