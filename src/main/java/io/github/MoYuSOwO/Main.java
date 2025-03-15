package io.github.MoYuSOwO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    private static final int boardRow = 20, boardCol = 20;
    private static final double pieceRadius = 15.0;
    private static final double canvasEdge = 20.0;
    private static final double allowClickError = 0.25;
    @Override
    public void start(Stage stage) {
        GameLogic game = new GameLogic(boardRow, boardCol);
        stage.setTitle("五子棋");
        BorderPane backPane = new BorderPane();
        Pane boardPane = new Pane();
        Pane bottomPane = new Pane();
        HBox buttonBox = new HBox();
        Canvas boardCanvas = new Canvas();
        Label l = new Label("本回合黑方行棋");
        l.setFont(Font.font(20));
        Button newStart = new Button("新对局");
        Button exit = new Button("退出游戏");
        backPane.setCenter(boardPane);
        backPane.setBottom(bottomPane);
        backPane.setTop(buttonBox);
        boardPane.getChildren().add(boardCanvas);
        bottomPane.getChildren().add(l);
        buttonBox.getChildren().addAll(newStart, exit);
        Scene scene = new Scene(backPane, 960, 720);
        stage.setScene(scene);
        stage.show();
        boardCanvas.heightProperty().bind(boardPane.heightProperty());
        boardCanvas.widthProperty().bind(boardPane.widthProperty());
        boardCanvas.setOnMouseClicked(event -> {
            if (game.checkResult() == GameLogic.GameStatus.PLAYING) {
                int row = calculateRow(event.getY(), boardCanvas.getGraphicsContext2D());
                int col = calculateCol(event.getX(), boardCanvas.getGraphicsContext2D());
                GameLogic.MoveStatus result = game.move(row, col);
                if (result == GameLogic.MoveStatus.BLACKMOVED) {
                    l.setText("本回合白方行棋");
                    drawChess(row, col, boardCanvas.getGraphicsContext2D(), GameLogic.Pieces.BLACK);
                }
                else if (result == GameLogic.MoveStatus.WHITEMOVED) {
                    l.setText("本回合黑方行棋");
                    drawChess(row, col, boardCanvas.getGraphicsContext2D(), GameLogic.Pieces.WHITE);
                }
                if (game.checkResult() == GameLogic.GameStatus.WHITEWIN) {
                    l.setText("白方胜！");
                }
                else if (game.checkResult() == GameLogic.GameStatus.BLACKWIN) {
                    l.setText("黑方胜！");
                }
                else if (game.checkResult() == GameLogic.GameStatus.DRAW) {
                    l.setText("平局！");
                }
            }
        });
        drawBoard(boardCanvas.getGraphicsContext2D());
    }

    private void drawChess(int row, int col, GraphicsContext gc, GameLogic.Pieces currentPlayer) {
        double height = gc.getCanvas().getHeight() - canvasEdge;
        double width = gc.getCanvas().getWidth() - canvasEdge;
        double singleHeight = (height - canvasEdge) / (double)(boardCol - 1);
        double singleWidth = (width - canvasEdge) / (double)(boardRow - 1);
        if (currentPlayer == GameLogic.Pieces.WHITE) {
            gc.setFill(Color.WHITE);
        }
        else {
            gc.setFill(Color.BLACK);
        }
        gc.fillOval(canvasEdge+col*singleWidth-pieceRadius, canvasEdge+row*singleHeight-pieceRadius, pieceRadius*2, pieceRadius*2);
    }

    private int calculateRow(double clickY, GraphicsContext gc) {
        double height = gc.getCanvas().getHeight() - canvasEdge;
        double singleHeight = (height - canvasEdge) / (double)(boardCol - 1);
        double posY = (clickY - canvasEdge) / singleHeight;
        if (Math.abs(posY - Math.round(posY)) < allowClickError) {
            return (int)Math.round(posY);
        }
        return -1;
    }

    private int calculateCol(double clickX, GraphicsContext gc) {
        double width = gc.getCanvas().getWidth() - canvasEdge;
        double singleWidth = (width - canvasEdge) / (double)(boardRow - 1);
        double posX = (clickX - canvasEdge) / singleWidth;
        if (Math.abs(posX - Math.round(posX)) < allowClickError) {
            return (int)Math.round(posX);
        }
        return -1;
    }

    private static void drawBoard(GraphicsContext gc) {
        double height = gc.getCanvas().getHeight() - canvasEdge;
        double width = gc.getCanvas().getWidth() - canvasEdge;
        double singleHeight = (height - canvasEdge) / (double)(boardCol - 1);
        double singleWidth = (width - canvasEdge) / (double)(boardRow - 1);
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        double pos = canvasEdge;
        for (int i = 0; i < boardCol; i++) {
            gc.strokeLine(pos, canvasEdge, pos, height);
            pos += singleWidth;
        }
        pos = canvasEdge;
        for (int i = 0; i < boardRow; i++) {
            gc.strokeLine(canvasEdge, pos, width, pos);
            pos += singleHeight;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}