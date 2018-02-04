package org.luksze.cryptme.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.luksze.cryptme.AppCipherFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GuiSec extends Application {

    private FilePick filePick;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("CryptMe");

        MyCustomScene first = new MyCustomScene("first");
        MyCustomScene second = new MyCustomScene("second");
        MyCustomScene third = new MyCustomScene("third");

        Scene firstScene = new Scene(first, 300, 300, true, SceneAntialiasing.BALANCED);
        Scene secondScene = new Scene(second , 300, 300, true, SceneAntialiasing.BALANCED);
        Scene thirdScene = new Scene(third,  300, 300, true, SceneAntialiasing.BALANCED);

        first.assing(event -> primaryStage.setScene(secondScene));
        second.assing(event -> primaryStage.setScene(thirdScene));
        third.assing(event -> primaryStage.setScene(firstScene));


        primaryStage.setScene(firstScene);
        primaryStage.show();
    }


    private Node confirmation() {
        Node node = new Text("Completed!");
        node.setVisible(false);
        node.setStyle("-fx-text-fill: #069b2c");
        return node;
    }

    private HBox encryptDecryptButtonsBox() {
        Button encryptButton = new Button("Encrypt");
        encryptButton.setStyle("-fx-text-fill: #069b2c");
        encryptButton.setOnAction(event -> encryptButton.fireEvent(new EncryptEvent()));

        Button decryptButton = new Button("Decrypt");
        decryptButton.setStyle("-fx-text-fill: #20489b");

        HBox hBox = new HBox(10, encryptButton, decryptButton);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
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

    private static class UserDecision {
        public void decisionToEncrypt() {

        }

        public void decisionToDecrypt() {

        }
    }


    private static class EncryptEvent extends Event {

        public static final EventType<Event> ENCRYPT_SELECTION = new EventType<>("ENCRYPT_SELECTION");

        public EncryptEvent() {
            super(ENCRYPT_SELECTION);
        }
    }

    private static class MyCustomScene extends GridPane {
        private final Button button;

        public MyCustomScene(String abc ) {
            setAlignment(Pos.CENTER);
            button = new Button(abc);

            add(button, 0, 0);

        }


        public void assing(EventHandler<ActionEvent> value) {
            button.setOnAction(value);
        }
    }
}
