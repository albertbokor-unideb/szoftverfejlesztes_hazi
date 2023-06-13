package boardgame.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class BoardGameModel {
    public static final int BOARD_SIZE = 4;
    private final ReadOnlyBooleanWrapper[][] board = new ReadOnlyBooleanWrapper[BOARD_SIZE][BOARD_SIZE];
    private Player currentPlayer;
    public BoardGameModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyBooleanWrapper(Boolean.TRUE);
            }
        }
        currentPlayer = Player.PLAYER_1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].get()) {
                    sb.append('+');
                } else {
                    sb.append('-');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public ReadOnlyBooleanProperty isCellFull(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }
    public boolean isGameover() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].get()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void changePlayer() {
        switch (currentPlayer) {
            case PLAYER_1 -> currentPlayer = Player.PLAYER_2;
            case PLAYER_2 -> currentPlayer = Player.PLAYER_1;
        }
    }

    public void takeFromBoard(ArrayList<Position> args) {
        if(canSelect(args)){
            /*igen, ezt az ellenőrzést jelenlegi implementációban duplán végzi,
            * megoldhatnám ugy is hogy ez metódus is visszatérit egy siker bool-t,
            * de gondolom nem annyira jellemző ugy csinálni Javaban
            */
            for (Position cell: args) {
                board[cell.row()][cell.col()].setValue(false);
            }
            changePlayer();
        }
    }
    public boolean canSelect(ArrayList < Position > args) {
        if (args.size() > BOARD_SIZE || args.size() < 1) {
            return false;
        }
        for (Position cell: args) {
            if (cell.col() >= BOARD_SIZE || cell.col() < 0) {
                return false;
            }
            if (cell.row() >= BOARD_SIZE || cell.row() < 0) {
                return false;
            }
        }
        for (Position cell: args) {
            if (!this.board[cell.row()][cell.col()].get()) {
                return false;
            } //checks if cell already empty
        }
        return areAdjacent(args);
    }


    public static boolean areAdjacent(ArrayList < Position > args) {
        if (args.size() == 1) {
            return true;
        }
        if (args.size() > 1) {
            boolean linear=false;
            ArrayList < Integer > posList = new ArrayList <> ();
            if (areSameRow(args)) { //horizontal
                linear=true;
                for (Position cell: args) {
                    posList.add(cell.col());
                }
            } else if (areSameCol(args)) { //vertical
                linear=true;
                for (Position cell: args) {
                    posList.add(cell.row());
                }
            }
            if(linear) {
                Collections.sort(posList); //sorts ascending
                return posList.get(posList.size() - 1) - posList.get(0) ==
                        posList.size() - 1; //if distance between first and last isn't len-1 then it's not adjacent
            }
        }
        return false;
    }

    public static boolean areSameRow(ArrayList < Position > args) {
        if (args.size() == 1) {
            return true;
        }
        if (args.size() > 1) {
            for (int i = 1; i < args.size(); i++) {
                if (args.get(i).row() != args.get(0).row()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public static boolean areSameCol(ArrayList < Position > args) {
        if (args.size() == 1) {
            return true;
        }
        if (args.size() > 1) {
            for (int i = 1; i < args.size(); i++) {
                if (args.get(i).col() != args.get(0).col()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }

}
