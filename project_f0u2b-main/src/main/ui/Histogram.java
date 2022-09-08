package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Histogram extends JPanel {
    int topArea;
    int bottomArea;
    int width;
    int dimHeight;
    int middle;
    HashMap<String, Integer> maps;
    private static final int BAR_WIDTH = 50;
    private static final int INCREMENT = 1;

    // creates a histogram for all the people and their balances
    public Histogram(int width, HashMap<String, Integer> maps, int dimHeight, int middle, int topArea, int bottomArea) {
        this.width = width;
        this.maps = maps;
        this.dimHeight = dimHeight;
        this.middle = middle;
        this.topArea = topArea;
        this.bottomArea = bottomArea;
    }

    // EFFECTS: paints the parts of the transaction onto the histogram scaled by their balance amount
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 10;
        for (Map.Entry<String, Integer> entry : maps.entrySet()) {
            if (entry.getValue() < 0) {
                int height = Math.abs(entry.getValue()) * INCREMENT;
                g.fillRect(x, middle, BAR_WIDTH - (BAR_WIDTH / 5), height);
                g.drawString(entry.getKey(), x, middle + height + 10);
                g.drawString(String.valueOf(entry.getValue()), x, middle - 5);
            } else {
                int height = entry.getValue() * INCREMENT;
                g.fillRect(x, middle - height, BAR_WIDTH - (BAR_WIDTH / 5), height);
                g.drawString(entry.getKey(), x, middle + 10);
                g.drawString(String.valueOf(entry.getValue()), x, middle - height - 5);
            }
            x += BAR_WIDTH;
        }
    }
}
