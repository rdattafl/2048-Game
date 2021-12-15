package org.cis120.Game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a game_2048 object, which is the model for the game.
 * As the user presses different arrow keys, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a KeyListener) and the view (with
 * its paintComponent method and the status JLabel).
 */

public class GameBoard extends JPanel {

    private final Game2048 new2048; // model for the game (will be a game_2048 object)
    private final JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the board area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        new2048 = new Game2048(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for arrow key presses. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int p = e.getKeyCode();

                switch (p) {
                    case KeyEvent.VK_UP:
                        // up code
                        new2048.makeMove("Up");
                        new2048.printGameState();
                        break;
                    case KeyEvent.VK_DOWN:
                        // down code
                        new2048.makeMove("Down");
                        new2048.printGameState();
                        break;
                    case KeyEvent.VK_LEFT:
                        // left code
                        new2048.makeMove("Left");
                        new2048.printGameState();
                        break;
                    case KeyEvent.VK_RIGHT:
                        // right code
                        new2048.makeMove("Right");
                        new2048.printGameState();
                        break;
                    default:
                        break;
                }

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        new2048.reset();
        status.setText("Score: 0");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Undoes the most recent move done by the user.
     * If LinkedList only has one element, undo() is
     * equivalent to reset().
     */
    public void undo() {
        new2048.undo();
        updateStatus();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

    }

    /**
     * Create a new file called saved2048Game.txt that will store the
     * full LinkedList savedStates, which includes all states (including
     * the state that the game is currently on). Each row of each entry's
     * board will be iterated through, and the score will be printed with
     * one line of whitespace above and below subsequent board states.
     */
    public void save() {
        new2048.save();
        updateStatus();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Load the most recently saved "saved2048Game.txt" file that exists
     * in the relative path the program will search through and store the
     * saved LinkedList into the appropriate game_2048 field, as well as the
     * current game state and score into their fields.
     */
    public void load() {
        new2048.load();
        updateStatus();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

    }

    /**
     * Open a separate window with text detailing the (brief) instructions of how to play 2048.
     */
    public void instructions() {
        new2048.instructions();
        updateStatus();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (!new2048.getGameOver()) {
            status.setText("Score: " + new2048.getScore());
        } else {
            status.setText("Score: " + 0);
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0, 300, 400, 300);

        // Draws all the numbers to put on the 4x4 girds
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = new2048.getBoardValue(i, j);
                if (number != 0) {
                    g.setFont(new Font("Comic Sans", 1, 24));
                    g.drawString(String.valueOf(number), 100 * j + 50, 100 * i + 50);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
