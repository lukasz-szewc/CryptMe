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
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.luksze.sillyfedor.CipherFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        gridPane.add(createSelectFileButton(), 0, 0);
        gridPane.add(createSelectedFileText(), 0, 1);
        final PasswordField passwordField = new PasswordField();
        gridPane.add(passwordField, 0, 2);
        gridPane.add(constructEncryptButton(passwordField), 0, 3);
        gridPane.add(constructDecryptButton(passwordField), 1, 3);
        primaryStage.setScene(new Scene(gridPane, 770, 250));
        primaryStage.show();
    }

    private Button constructEncryptButton(final PasswordField passwordField) {
        Button encryptButton = new Button("Encrypt");
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CipherFile cipherFile = new CipherFile();
                Path source = filePick.selectedFile.toPath();
                Path path = Paths.get(source.getParent().toString(), source.getFileName() + ".enc");
                cipherFile.encrypt(source, path, passwordField.getText());
            }
        });
        return encryptButton;
    }

    private Button constructDecryptButton(final PasswordField passwordField) {
        Button decryptButton = new Button("Decrypt");
        decryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CipherFile cipherFile = new CipherFile();
                Path source = filePick.selectedFile.toPath();
                Path path = Paths.get(source.getParent().toString(), source.getFileName() + ".dec");
                cipherFile.decrypt(source, path, passwordField.getText());
            }
        });
        return decryptButton;
    }

    private Text createSelectedFileText() {
        Text text = new Text();
        text.textProperty().bind(filePick.stringProperty);
        text.visibleProperty().bind(filePick.fileSelected);
        return text;
    }

    private Button createSelectFileButton() {
        final Button selectFileButton = new Button("Select file encrypt/decrypt...");
        selectFileButton.setOnAction(new FilePickEventHandler());
        return selectFileButton;
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

    private class FilePickEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            filePick.display();
        }
    }
}
