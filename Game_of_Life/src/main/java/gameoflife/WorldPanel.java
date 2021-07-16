package gameoflife;

import javax.swing.*;
import java.awt.*;

class WorldPanel extends JPanel {
    private final World world;
    private final int cell_size;
    private final int panel_size;
    private final int[] cellPositions;

    WorldPanel(final World world, final int cellSize) {
        this.world = world;
        this.cell_size = cellSize;
        panel_size = cell_size * world.size;
        cellPositions = new int[world.size];
        for (int i = 0; i < cellPositions.length; i++) {
            cellPositions[i] = i * cell_size;
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);  // clear the panel

        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);

        for (int y = 0; y < world.size; y++) {
            final int pos = cellPositions[y];
            g2.drawLine(0, pos, panel_size, pos);
            g2.drawLine(pos, 0, pos, panel_size);

            for (int x = 0; x < world.size; x++) {
                if (world.world[y][x]) {
                    g2.fillRect(x * cell_size, pos, cell_size, cell_size);
                }
            }
        }
        g2.drawLine(0, panel_size, panel_size, panel_size);
        g2.drawLine(panel_size, 0, panel_size, panel_size);
    }
}
