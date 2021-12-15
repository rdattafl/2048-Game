package org.cis120.Game2048;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * This class is a model for game2048.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Game2048 {

    private Integer[][] board;
    private Integer score = 0;
    private LinkedList<Integer> savedScores;
    private LinkedList<Integer[][]> savedStates;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Game2048() {
        reset();
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int numToAdd) {
        score = Integer.sum(score, numToAdd);
    }

    public int getBoardValue(int i, int j) {
        return board[i][j];
    }

    public void setBoardValue(int i, int j, int newBoardValue) {
        board[i][j] = newBoardValue;
    }

    public int getSavedStatesSize() {
        return savedStates.size();
    }

    /**
     * The method makeMove adjusts the current game state (including all relevant fields)
     * based on the direction string that is given as an input to the method.
     */
    public void makeMove(String direction) {

        boolean isComplete = false;
        int[][] combined = new int[4][4];
        Integer [][] currentState = new Integer[4][4];

        switch (direction) {
            case "Up":
                // handle up movement
                while (!isComplete) {
                    isComplete = true; // while loop only ends if no block
                                       // undergoes addition or moves

                    for (int i = 1; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            // Check whether the current square is empty
                            if (this.getBoardValue(i, j) == 0) {
                                continue;
                            }

                            // Check if block should join block above
                            if ((this.getBoardValue(i - 1, j) ==
                                    this.getBoardValue(i, j)) &&
                                    (combined[i - 1][j] == 0)) {
                                combined[i - 1][j] = 1; // No more blocks should merge with
                                                        // that square during the up move
                                                        // execution

                                this.setBoardValue(i - 1, j,
                                        2 * this.getBoardValue(i - 1, j));
                                this.setBoardValue(i, j, 0);
                                this.updateScore(this.getBoardValue(i - 1, j));
                                isComplete = false;

                            // Check if block can move up
                            } else if (this.getBoardValue(i - 1, j) == 0) {
                                this.setBoardValue(i - 1, j,
                                        this.getBoardValue(i, j));

                                this.setBoardValue(i, j, 0);
                                isComplete = false;

                            }
                        }
                    }
                }

                this.isGameOver(); // Checks whether game instance should be reset

                // Insert random generate call after completing all movements
                this.randomGenerate();

                // Update SavedState LinkedList using currentState Integer array
                for (int i = 0; i < 4; i++) {
                    System.arraycopy(board[i], 0, currentState[i], 0, 4);
                }

                if (currentState != savedStates.peek()) {
                    savedStates.push(currentState);

                    // Update savedScores LinkedList
                    savedScores.push(score);
                }

                break;

            case "Down":
                // handle down movement
                while (!isComplete) {
                    isComplete = true; // while loop only ends if no block
                    // undergoes addition or moves

                    for (int i = 2; i > -1; i--) {
                        for (int j = 0; j < 4; j++) {
                            // Check whether the current square is empty
                            if (this.getBoardValue(i, j) == 0) {
                                continue;
                            }

                            // Check if block should join block below
                            if ((this.getBoardValue(i + 1, j) ==
                                    this.getBoardValue(i, j)) &&
                                    (combined[i + 1][j] == 0)) {
                                combined[i + 1][j] = 1; // No more blocks should merge with
                                // that square during the up move
                                // execution

                                this.setBoardValue(i + 1, j,
                                        2 * this.getBoardValue(i + 1, j));
                                this.setBoardValue(i, j, 0);
                                this.updateScore(this.getBoardValue(i + 1, j));
                                isComplete = false;

                                // Check if block can move down
                            } else if (this.getBoardValue(i + 1, j) == 0) {
                                this.setBoardValue(i + 1, j,
                                        this.getBoardValue(i, j));

                                this.setBoardValue(i, j, 0);
                                isComplete = false;

                            }
                        }
                    }
                }

                this.isGameOver(); // Checks whether game instance should be reset

                // Insert random generate call after completing all movements
                this.randomGenerate();

                // Update SavedState LinkedList using currentState Integer array
                for (int i = 0; i < 4; i++) {
                    System.arraycopy(board[i], 0, currentState[i], 0, 4);
                }

                if (currentState != savedStates.peek()) {
                    savedStates.push(currentState);

                    // Update savedScores LinkedList
                    savedScores.push(score);
                }

                break;

            case "Left":
                // handle left movement
                while (!isComplete) {
                    isComplete = true; // while loop only ends if no block
                    // undergoes addition or moves

                    for (int j = 1; j < 4; j++) {
                        for (int i = 0; i < 4; i++) {
                            // Check whether the current square is empty
                            if (this.getBoardValue(i, j) == 0) {
                                continue;
                            }

                            // Check if block should join block to the left
                            if ((this.getBoardValue(i, j - 1) ==
                                    this.getBoardValue(i, j)) &&
                                    (combined[i][j - 1] == 0)) {
                                combined[i][j - 1] = 1; // No more blocks should merge with
                                // that square during the up move
                                // execution

                                this.setBoardValue(i, j - 1,
                                        2 * this.getBoardValue(i, j - 1));
                                this.setBoardValue(i, j, 0);
                                this.updateScore(this.getBoardValue(i, j - 1));
                                isComplete = false;

                                // Check if block can move up
                            } else if (this.getBoardValue(i, j - 1) == 0) {
                                this.setBoardValue(i, j - 1,
                                        this.getBoardValue(i, j));

                                this.setBoardValue(i, j, 0);
                                isComplete = false;

                            }
                        }
                    }
                }

                this.isGameOver(); // Checks whether game instance should be reset

                // Insert random generate call after completing all movements
                this.randomGenerate();

                // Update SavedState LinkedList using currentState Integer array
                for (int i = 0; i < 4; i++) {
                    System.arraycopy(board[i], 0, currentState[i], 0, 4);
                }

                if (currentState != savedStates.peek()) {
                    savedStates.push(currentState);

                    // Update savedScores LinkedList
                    savedScores.push(score);
                }

                break;

            case "Right":
                // handle right movement
                while (!isComplete) {
                    isComplete = true; // while loop only ends if no block
                    // undergoes addition or moves

                    for (int j = 2; j > -1; j--) {
                        for (int i = 0; i < 4; i++) {
                            // Check whether the current square is empty
                            if (this.getBoardValue(i, j) == 0) {
                                continue;
                            }

                            // Check if block should join block to the right
                            if ((this.getBoardValue(i, j + 1) ==
                                    this.getBoardValue(i, j)) &&
                                    (combined[i][j + 1] == 0)) {
                                combined[i][j + 1] = 1; // No more blocks should merge with
                                // that square during the up move
                                // execution

                                this.setBoardValue(i, j + 1,
                                        2 * this.getBoardValue(i, j + 1));
                                this.setBoardValue(i, j, 0);
                                this.updateScore(this.getBoardValue(i, j + 1));
                                isComplete = false;

                                // Check if block can move to the right
                            } else if (this.getBoardValue(i, j + 1) == 0) {
                                this.setBoardValue(i, j + 1,
                                        this.getBoardValue(i, j));

                                this.setBoardValue(i, j, 0);
                                isComplete = false;

                            }
                        }
                    }
                }

                this.isGameOver(); // Checks whether game instance should be reset

                // Insert random generate call after completing all movements
                this.randomGenerate();

                // Update SavedState LinkedList using currentState Integer array
                for (int i = 0; i < 4; i++) {
                    System.arraycopy(board[i], 0, currentState[i], 0, 4);
                }

                if (currentState != savedStates.peek()) {
                    savedStates.push(currentState);

                    // Update savedScores LinkedList
                    savedScores.push(score);
                }

                break;

            default:
                throw new IllegalArgumentException("Error: Invalid directional input");
        }

    }

    /**
     * isGameOver iterates through all elements of the board and checks whether
     * the board is fully filled up and, if so, whether all adjacent squares are
     * different numbers. If this is the case, then change the value of gameOver
     * to true and reset the board.
     */

    public void isGameOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Must make sure to avoid ArrayIndexOutOfBounds exceptions
                if ((j != 3) && (Objects.equals(board[i][j], board[i][j + 1]))) {
                    return; // gameOver is still false
                } else if ((i != 3) && (Objects.equals(board[i][j], board[i + 1][j]))) {
                    return; // gameOver is still false
                }
            }
        }

        gameOver = true;
        reset();

    }

    /**
     * randomGenerate selects at random one of the indices that currently contains
     * a 0 and places a 2 within that randomly selected index.
     */

    public void randomGenerate() {

        LinkedList<Integer> freeIndices = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    freeIndices.add(4 * i + j);
                }
            }
        }

        // Randomly select the index where the new 2 will go
        Random ran = new Random();
        int randomValue = ran.nextInt(freeIndices.size());
        Integer pos = freeIndices.get(randomValue);

        // Place the 2 in the randomly selected index
        board[pos / 4][pos % 4] = 2;

    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n-------------");
            }
        }
        System.out.println("\n");
        System.out.println("\n");

        System.out.println(this.getSavedStatesSize() + "\n");
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Integer[4][4];

        // Initialize board to same position each time
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }

        board[1][0] = 2;
        board[2][3] = 4;

        savedStates = new LinkedList<>();
        savedStates.push(board);

        score = 0;
        savedScores = new LinkedList<>();
        savedScores.push(score);

        gameOver = false;
    }

    /**
     * undo() returns the game state to the previous execution and changes
     * all relevant fields
     */
    public void undo() {
        if (this.getSavedStatesSize() <= 1) {
            reset();
        } else {
            savedStates.pop();
            savedScores.pop();



            for (int i = 0; i < 4; i++) {
                System.arraycopy(savedStates.peek()[i], 0, board[i], 0, 4);
            }

            board = savedStates.peek();
            score = savedScores.peek();

        }
    }

    /**
     * Create a new file called saved2048Game.txt that will store the
     * full LinkedList savedStates, which includes all states (including
     * the state that the game is currently on). Each row of each entry's
     * board will be iterated through, and the score will be printed with
     * one line of whitespace above and below subsequent board states.
     */
    public void save() {
        try {
            FileWriter savedGame = new FileWriter("./saved2048Game.txt");
            BufferedWriter writer = new BufferedWriter(savedGame);

            while (this.getSavedStatesSize() > 0) {
                Integer[][] currentBoard = savedStates.pop();
                Integer currentScore = savedScores.pop();

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (j != 3) {
                            writer.write(currentBoard[i][j] + " ");
                        } else {
                            writer.write(currentBoard[i][j] + "\n");
                        }

                    }
                }

                writer.write("\n");
                writer.write(String.valueOf(currentScore));
                writer.write("\n\n");

            }

            writer.close();

        } catch (IOException e) {
            // Generate a new error window
            JOptionPane.showMessageDialog(null, "Not able to write " +
                    "file to saved2048Game.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load the most recently saved "saved2048Game.txt" file that exists
     * in the relative path the program will search through and store the
     * saved LinkedList into the appropriate game_2048 field, as well as the
     * current game state and score into their fields.
     */
    public void load() {
        try {

            FileReader savedGame = new FileReader("./saved2048Game.txt");
            BufferedReader br = new BufferedReader(savedGame);
            Scanner brScanner = new Scanner(br);

            // Add board values and score to new game state iteration

            while (brScanner.hasNextLine()) {

                for (int i = 0; i < 4; i++) {
                    if (brScanner.hasNextLine()) {
                        String line = brScanner.nextLine();
                        if (line.matches("\\d+\\s\\d+\\s\\d+\\s\\d+")) {
                            for (int j = 0; j < 4; j++) {
                                board[i][j] = Integer.parseInt(line.split(" ")[j]);
                            }
                        }
                    }
                }

                savedStates.push(board);
                brScanner.nextLine();

                score = Integer.parseInt(brScanner.nextLine());
                savedScores.add(score);

                if (brScanner.hasNextLine()) {
                    brScanner.nextLine();
                }

            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not find valid" +
                    "saved2048Game.txt file to load", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void instructions() {
        JFrame instructionFrame = new JFrame("Instructions");
        instructionFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JTextArea text = new JTextArea("Press the arrow keys to merge boxes of the same " +
                "value. Keep going until the 4x4 grid is completely filled and can't be changed " +
                "with any arrow key press. Try and see how high of a score you can get! (If " +
                "loading a new game, please press Undo once to retrieve the old score.)",
                5, 20);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setPreferredSize(new Dimension(300, 150));
        instructionFrame.getContentPane().add(text, BorderLayout.CENTER);

        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.pack();
        instructionFrame.setVisible(true);

    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Game2048 g = new Game2048();

        g.printGameState();

        g.makeMove("Right");
        g.printGameState();

        g.makeMove("Down");
        g.printGameState();

        g.undo();
        g.printGameState();

        g.makeMove("Up");
        g.printGameState();

        g.makeMove("Left");
        g.printGameState();

        g.makeMove("Right");
        g.printGameState();

        g.makeMove("Down");
        g.printGameState();

        g.makeMove("Up");
        g.printGameState();

        g.makeMove("Left");
        g.printGameState();

        g.makeMove("Right");
        g.printGameState();

        g.makeMove("Down");
        g.printGameState();

        g.makeMove("Up");
        g.printGameState();

        g.makeMove("Left");
        g.printGameState();

    }
}
