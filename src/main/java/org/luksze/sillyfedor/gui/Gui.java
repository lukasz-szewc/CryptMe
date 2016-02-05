package org.luksze.sillyfedor.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Gui extends Application {

    private File file;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("SillyFedor");
        GridPane gridPane = constructGridPane();

        Button encryptButton = new Button("Select file to encrypt...");
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                final FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource CipherFile");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null && file.exists() && file.isFile()) {
                    Gui.this.file = file;
//                    appStage.emitEvent(new FileHasBeenSelectedEvent());
                }
            }
        });
        gridPane.add(encryptButton, 0, 0);

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
