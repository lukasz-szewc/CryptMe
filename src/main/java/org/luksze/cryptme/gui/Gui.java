package org.luksze.cryptme.gui;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.luksze.cryptme.AppCipherFile;

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
        primaryStage.setTitle("simple-encryption-tool");
        GridPane gridPane = constructGridPane();
        gridPane.add(createSelectFileButton(), 0, 0);
        final HBox hBox = new HBox(createSelectedFileText());
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 0, 1);
        final PasswordField passwordField = new PasswordField();
        gridPane.add(new VBox(new Label("Password:"), passwordField), 0, 2);
        final HBox child = new HBox(10, constructEncryptButton(passwordField), constructDecryptButton(passwordField));
        child.setAlignment(Pos.CENTER);
        gridPane.add(child, 0, 3);
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.ALICEBLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(new Scene(gridPane, 400, 300));
        primaryStage.show();
    }

    private Button constructEncryptButton(final PasswordField passwordField) {
        Button encryptButton = new Button("Encrypt");
        encryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppCipherFile appCipherFile = new AppCipherFile();
                Path source = filePick.selectedFile.toPath();
                Path path = Paths.get(source.getParent().toString(), source.getFileName() + ".enc");
                appCipherFile.encrypt(source, path, passwordField.getText());
            }
        });
        return encryptButton;
    }

    private Button constructDecryptButton(final PasswordField passwordField) {
        Button decryptButton = new Button("Decrypt");
        decryptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppCipherFile appCipherFile = new AppCipherFile();
                Path source = filePick.selectedFile.toPath();
                Path path = Paths.get(source.getParent().toString(), source.getFileName() + ".dec");
                appCipherFile.decrypt(source, path, passwordField.getText());
            }
        });
        return decryptButton;
    }

    private Text createSelectedFileText() {
        Text text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
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
                stringProperty.setValue(file.getName());
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
