package io.github.MoYuSOwO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("HaHaHaHa");
        Canvas canvas = new Canvas(500, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);   // 设置线条颜色
        gc.setLineWidth(2);        // 设置线条粗细
        gc.strokeLine(0, 0, 0, 200); // 从 (0,0) 到 (100,100) 画一条红线
        Label l = new Label("Hello, JavaFX");
        Button b = new Button("新游戏");
        Button b1 = new Button("新游戏1");
        Button b2 = new Button("新游戏2");
        Label l1 = new Label("Hello, JavaFX1");
        Label l2 = new Label("");
        Label l3 = new Label("Hello, JavaFX3");
        HBox hb = new HBox(b, b1, b2);
        Scene scene = new Scene(new BorderPane(canvas, hb, l, l1, l2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}