package boardgame;

import boardgame.model.BoardGameModel;


import boardgame.model.Position;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class BoardGameController {

    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();
    private ArrayList<Position> selectedGridPositions;

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        selectedGridPositions = new ArrayList<>();
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(40);
        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.isCellFull(i, j));
                    }
                    @Override
                    protected Paint computeValue() {
                        if (model.isCellFull(i, j).get()) {
                            return Color.DARKGREY;
                        };
                        return Color.TRANSPARENT;
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleClickOnSquare);
        return square;
    }

    @FXML
    public void quitGame(ActionEvent e){
        Platform.exit();
    }

    @FXML
    private void handleClickOnSquare(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        square.getStyleClass().add("selected");
        selectedGridPositions.add(new Position(row,col));

        System.out.printf("(%d,%d) selected\n", row, col); ////////////////////////////////////////////////////////////
        System.out.printf(selectedGridPositions.toString()+"\n");//////////////////////////////////////////////////////
        //add clicked positions to a list
    }

    @FXML
    public void takeFromBoard(ActionEvent e){

        System.out.printf("taking...\n"); ////////////////////////////////////////////////////////////
        if(model.canSelect(selectedGridPositions)){
            model.takeFromBoard(selectedGridPositions);
            System.out.printf("taken!\n"); ////////////////////////////////////////////////////////////
        }

        for (Position position : selectedGridPositions){
            var square = getSquare(position);
            System.out.println(square.getStyleClass()+"\n");
        }
        clearSelection();
        System.out.printf("selector reset!\n"); ////////////////////////////////////////////////////////////
    }

    private void clearSelection() {
        for (Position position : selectedGridPositions){
            var square = getSquare(position);
            System.out.println(square.getStyleClass());//////////////////////////////////////////////////////
            square.getStyleClass().remove("selected");
        }
        selectedGridPositions.clear();
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            System.out.printf(child.toString()+"\n");////////////////////////////////////////////////
        }
        for (var child : board.getChildren()) {
            System.out.printf("got square!\n");///////////////////////////////////////////////////
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }
}
