package org.luksze.sillyfedor.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("SillyFedor");
        GridPane gridPane = constructGridPane();
        primaryStage.setScene(new Scene(gridPane, 770, 250));
        primaryStage.show();
    }

    private GridPane constructGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
//        grid.setPrefSize(400, 500);
        return grid;
    }
}
