package gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private static final int CELL_SIZE = 10;
    private static final int WORLD_SIZE = 50;
    private final World world = new World(WORLD_SIZE);

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int worldPanelSize = CELL_SIZE * WORLD_SIZE;
        setSize(worldPanelSize, worldPanelSize + 75);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        final JLabel generationLabel = new JLabel("Generation #0");
        generationLabel.setName("GenerationLabel");

        final JLabel aliveLabel = new JLabel("Alive: 0");
        aliveLabel.setName("AliveLabel");

        final JPanel worldPanel = new WorldPanel(world, CELL_SIZE);

        final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        final GameRunner gameRunner = new GameRunner(world, generationLabel, aliveLabel, worldPanel);

        final JButton stepButton = new JButton("Step");
        stepButton.addActionListener(actionEvent -> {
            world.step();
            gameRunner.draw();
        });

        final JToggleButton playToggleButton = new JToggleButton("Start/Pause");
        playToggleButton.setName("PlayToggleButton");
        playToggleButton.addActionListener(actionEvent -> {
            if (gameRunner.isRunning) {
                gameRunner.isRunning = false;
                stepButton.setEnabled(true);
            } else {
                gameRunner.isRunning = true;
                stepButton.setEnabled(false);
            }
        });

        final JButton resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(actionEvent -> {
            if (gameRunner.isRunning) {
                // stop the simulation
                playToggleButton.doClick();
            }
            world.newRandom();
            gameRunner.draw();
        });

        add(topPanel, BorderLayout.NORTH);
        topPanel.add(playToggleButton);
        topPanel.add(resetButton);
        topPanel.add(stepButton);
        topPanel.add(textPanel);
        textPanel.add(generationLabel);
        textPanel.add(aliveLabel);
        add(worldPanel, BorderLayout.CENTER);

        setVisible(true);

        final Thread gameThread = new Thread(gameRunner);
        gameThread.start();
    }

    public static void main(final String[] args) {
        new GameOfLife();
    }
}
