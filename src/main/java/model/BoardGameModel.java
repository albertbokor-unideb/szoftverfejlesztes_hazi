package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class BoardGameModel {
    public static final int BOARD_SIZE = 4;
    private boolean [] [] board = new boolean[BOARD_SIZE][BOARD_SIZE];

    public BoardGameModel(boolean[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = true;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j]) {
                    sb.append('+');
                } else {
                    sb.append('-');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void takeFromBoard(position args[]){
        if(canSelect(args)){
            for(position cell : args){
                this.board[cell.col()][cell.row()]=false; //checks if cell already empty
            }
        }
    }

    public boolean canSelect(position args[]){
        if (args.length>BOARD_SIZE || args.length<1) {return false;}
        for(position cell : args){
            if(!this.board[cell.col()][cell.row()]){return false;}   //checks if cell already empty
        }
        if(!areAdjacent(args)){ return false;}
        return true;
    }


    public static boolean areAdjacent(position args[]){
        //TODO: sanity refactor
        if(args.length ==1){return true;}
        if(args.length >1){
            if(sameRow(args)){  //horizontal
                ArrayList<Integer> posList= new ArrayList<Integer>();
                for(position cell : args){
                    posList.add(Integer.valueOf(cell.col()));
                }
                Collections.sort(posList);    //sorts ascending
                if(
                    posList.get(posList.size() - 1) - posList.get(0)
                            != posList.size()-1
                ){
                    return false;   //if distance between first and last isn't len-1 then it's not adjacent
                }
            }
            if(sameCol(args)){  //vertical
                ArrayList<Integer> posList= new ArrayList<Integer>();
                for(position cell : args){
                    posList.add(Integer.valueOf(cell.row()));
                }
                Collections.sort(posList);    //sorts ascending
                if(
                        posList.get(posList.size() - 1) - posList.get(0)
                                != posList.size()-1
                ){
                    return false;   //if distance between first and last isn't len-1 then it's not adjacent
                }
            }
        }
        return false;
    }

    public static boolean sameRow(position args[]){
        if(args.length ==1){return true;}
        if(args.length >1){
            for (int i=1; i<args.length; i++){
                if(args[i].row() != args[0].row()){
                    return false;}
            }
            return true;
        }
        return false;
    }
    public static boolean sameCol(position args[]){
        if(args.length ==1){return true;}
        if(args.length >1){
            for (int i=1; i<args.length; i++){
                if(args[i].col() != args[0].col()){
                    return false;}
            }
            return true;
        }
        return false;
    }

}
