package gameoflife;

import java.util.Random;

public class World {
    private static final int[][] neighborOffsets = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };
    final int size;
    final boolean[][] world;
    final boolean[][] nextWorld;
    int generationNumber = 0;
    int nLivingCells = 0;

    World(final int size) {
        this.size = size;
        world = new boolean[this.size][this.size];
        nextWorld = new boolean[this.size][this.size];
        newRandom();
    }

    void newRandom() {
        generationNumber = 0;
        nLivingCells = 0;
        final Random random = new Random();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                final boolean isAlive = random.nextBoolean();
                world[y][x] = isAlive;
                if (isAlive) {
                    nLivingCells++;
                }
            }
        }
    }

    void step() {
        generationNumber++;
        nLivingCells = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                final int n = getNAliveNeighbors(x, y);
                final boolean nextGenAlive;
                if (world[y][x]) {
                    nextGenAlive = n == 2 || n == 3;
                } else {
                    nextGenAlive = n == 3;
                }
                nextWorld[y][x] = nextGenAlive;
                if (nextGenAlive) {
                    nLivingCells++;
                }
            }
        }
        for (int y = 0; y < size; y++) {
            System.arraycopy(nextWorld[y], 0, world[y], 0, size);
        }
    }

    int getNAliveNeighbors(final int x, final int y) {
        int n = 0;
        for (final int[] offset : neighborOffsets) {
            final int neighborX = correctNeighborPosition(x + offset[0]);
            final int neighborY = correctNeighborPosition(y + offset[1]);
            if (world[neighborY][neighborX]) {
                n++;
            }
        }
        return n;
    }

    int correctNeighborPosition(final int pos) {
        // Go to the other side if the position is over the edge.
        return pos > -1 ? pos % size : size + pos;
    }
}
