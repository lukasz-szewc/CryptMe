package org.luksze.sillyfedor.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Gui extends Application {

    private FilePick filePick;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        filePick = new FilePick(primaryStage);

        primaryStage.setTitle("SillyFedor");
        GridPane gridPane = constructGridPane();

        final Button encryptButton = new Button("Select file to encrypt...");
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                filePick.display();
            }
        });

        gridPane.add(encryptButton, 0, 0);

        Text text = new Text();
        text.setText("Please select file...");
        text.textProperty().bind(filePick.stringProperty);
        text.visibleProperty().bind(filePick.fileSelected);
        gridPane.add(text, 0, 1);

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

    private static class FilePick {
        private final Stage primaryStage;
        private final FileChooser fileChooser;
        private final StringProperty stringProperty;
        private final SimpleBooleanProperty fileSelected;
        private File selectedFile;

        public FilePick(Stage stage) {
            primaryStage = stage;
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open file you want to encrypt/decrypt");
            stringProperty = new SimpleStringProperty();
            fileSelected = new SimpleBooleanProperty(FALSE);
        }

        public void display() {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null && file.exists() && file.isFile()) {
                stringProperty.setValue(file.getAbsolutePath());
                fileSelected.setValue(TRUE);
                selectedFile = file;
            } else {
                selectedFile = null;
                fileSelected.setValue(FALSE);
            }
        }
    }
}
