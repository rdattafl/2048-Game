package org.cis120.Game2048;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    @Test
    public void test2048ResetButton() {
        Game2048 g = new Game2048();

        g.makeMove("Up");
        g.makeMove("Left");
        g.makeMove("Down");
        g.makeMove("Right");
        g.reset();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i == 1) && (j == 0)) {
                    assertEquals(g.getBoardValue(i, j), 2);
                } else if ((i == 2) && (j == 3)) {
                    assertEquals(g.getBoardValue(i, j), 4);
                } else {
                    assertEquals(g.getBoardValue(i, j), 0);
                }
            }
        }

    }

    @Test
    public void test2048UndoButton() {
        Game2048 g = new Game2048();


        g.makeMove("Down");
        g.makeMove("Right");
        g.makeMove("Right");

        Integer[][] savedBoard = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                savedBoard[i][j] = g.getBoardValue(i, j);
            }
        }

        g.makeMove("Up");
        g.undo();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(g.getBoardValue(i, j), savedBoard[i][j]);
            }
        }


    }

    @Test
    public void test2048SaveButton() {
        Game2048 g = new Game2048();

        g.makeMove("Down");
        g.makeMove("Left");
        g.save();

        File checkFile = new File("./saved2048Game.txt");
        assertTrue(checkFile.exists());

    }

    @Test
    public void test2048LoadButton() {
        Game2048 g = new Game2048();
        Game2048 g2 = new Game2048();

        g.makeMove("Right");
        g.makeMove("Up");
        g.printGameState();

        Integer[][] savedBoard = new Integer[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                savedBoard[i][j] = g.getBoardValue(i, j);
            }
        }

        int savedScore = g.getScore();

        g.save();
        g2.load();
        g2.undo();
        g2.printGameState();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(g2.getBoardValue(i, j), savedBoard[i][j]);
            }
        }

        assertEquals(g2.getScore(), savedScore);

    }

    @Test
    public void test2048InstructionsWorkCheck() {
        Game2048 g = new Game2048();

        g.makeMove("Down");
        g.makeMove("Left");
        g.instructions();

    }

}
