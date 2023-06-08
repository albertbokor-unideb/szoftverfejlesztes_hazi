package boardgame.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    @Test
    void isCellFull() {
        var board = new BoardGameModel();
        assertEquals(true, board.isCellFull(0,0).get());

        ArrayList<Position> coordinateList = new ArrayList<>();
        coordinateList.add(new Position(0,0));
        board.takeFromBoard(coordinateList);
        assertEquals(false, board.isCellFull(0,0).get());


        assertEquals(true, board.isCellFull(3,2).get());
        coordinateList.clear();
        coordinateList.add(new Position(3,2));
        board.takeFromBoard(coordinateList);
        assertEquals(false, board.isCellFull(3,2).get());
    }

    @Test
    void isGameover() {
        var board = new BoardGameModel();

        var coordinateList = new ArrayList<Position>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                coordinateList.add(new Position(i,j));
            }
            assertEquals (false,board.isGameover());
            board.takeFromBoard(coordinateList);
            coordinateList.clear();
        }

        assertEquals(true, board.isGameover());
    }

    @Test
    void takeFromBoard() {
        var board = new BoardGameModel();
        var coordinateList = new ArrayList<Position>();

        Player playerBefore=board.getCurrentPlayer();
        String stringBefore=board.toString();
        coordinateList.add(new Position(5,5));
        board.takeFromBoard(coordinateList);
        assertEquals(playerBefore,board.getCurrentPlayer());
        assertTrue(stringBefore.equals(board.toString()));

        coordinateList.clear();
        String stringExpected="+++-\n+++-\n+++-\n+++-\n";
        coordinateList.clear();
        coordinateList.add(new Position(0,3));
        coordinateList.add(new Position(1,3));
        coordinateList.add(new Position(2,3));
        coordinateList.add(new Position(3,3));
        board.takeFromBoard(coordinateList);

        assertNotEquals(playerBefore,board.getCurrentPlayer());
        assertTrue(stringExpected.equals(board.toString()));
    }

    @Test
    void canSelect() {

    }

    @Test
    void areAdjacent() {
        var board = new BoardGameModel();
        var coordinateList = new ArrayList<Position>();

        assertEquals(false,BoardGameModel.areAdjacent(coordinateList));

        coordinateList.add(new Position(2,3));
        assertEquals(true,BoardGameModel.areAdjacent(coordinateList));

        coordinateList.add(new Position(3,3));
        assertEquals(true,BoardGameModel.areAdjacent(coordinateList));

        coordinateList.add(new Position(0,3));
        assertEquals(false,BoardGameModel.areAdjacent(coordinateList));

    }

    @Test
    void areSameRow() {
        var board = new BoardGameModel();
        var coordinateList = new ArrayList<Position>();

        assertEquals(false,BoardGameModel.areSameRow(coordinateList));

        coordinateList.add(new Position(2,3));
        assertEquals(true,BoardGameModel.areSameRow(coordinateList));

        coordinateList.add(new Position(2,2));
        assertEquals(true,BoardGameModel.areSameRow(coordinateList));

        coordinateList.add(new Position(0,0));
        assertEquals(false,BoardGameModel.areSameRow(coordinateList));
    }

    @Test
    void areSameCol() {
        var board = new BoardGameModel();
        var coordinateList = new ArrayList<Position>();

        assertEquals(false,BoardGameModel.areSameCol(coordinateList));

        coordinateList.add(new Position(2,3));
        assertEquals(true,BoardGameModel.areSameCol(coordinateList));

        coordinateList.add(new Position(0,3));
        assertEquals(true,BoardGameModel.areSameCol(coordinateList));

        coordinateList.add(new Position(0,0));
        assertEquals(false,BoardGameModel.areSameCol(coordinateList));
    }

}