package com.company;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import java.io.File;
import java.sql.Timestamp;

public class Main extends Application{

    Stage window;
    int defWidth = 800;
    int defHight = 300;
    String[] translation = new String[2];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Morse Code Translator by Arek Zajac");
        menu();
    }

    public void menu() {
        //Layout Main
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);

        //Components
        Label title = new Label("Morse Code Translator\nby Arek Zajac");
        title.setContentDisplay(ContentDisplay.CENTER);
        Button buttonTM = new Button("Plain Text → Morse Code");
        buttonTM.setOnAction(e -> TM());
        Button buttonMT = new Button("Morse Code → Plain Text");
        buttonMT.setOnAction(e -> MT());


        //Scene
        layout.getChildren().addAll(title, buttonTM, buttonMT);
        Scene scene = new Scene(layout, defWidth, defHight);
        window.setScene(scene);
        window.show();
    }

    public void TM(){


        //Layout

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setAlignment(Pos.CENTER);

        HBox doubleCell = new HBox();
        doubleCell.setSpacing(99);
        GridPane.setConstraints(doubleCell, 3, 2);


        //Components

        Button buttonBack = new Button("\uD83C\uDFE0");
        buttonBack.setTooltip(new Tooltip("Home"));
        buttonBack.setOnAction(e -> menu());
        GridPane.setConstraints(buttonBack, 0, 0);
        buttonBack.setMaxWidth(30);
        buttonBack.setMinWidth(30);

        Label labelText = new Label("Plain Text");
        GridPane.setConstraints(labelText, 1, 0);

        Label labelMorse = new Label("Morse Code");
        GridPane.setConstraints(labelMorse, 3, 0);

        TextArea fieldText = new TextArea();
        fieldText.setPromptText("Enter Plain Text Here...");
        GridPane.setConstraints(fieldText, 1, 1);
        fieldText.setMinSize(300, 100);
        fieldText.setMaxSize(300, 100);
        fieldText.setWrapText(true);

        TextArea fieldMorse = new TextArea();
        fieldMorse.setEditable(false);
        GridPane.setConstraints(fieldMorse, 3, 1);
        fieldMorse.setMinSize(300, 100);
        fieldMorse.setMaxSize(300, 100);
        fieldMorse.setWrapText(true);

        Button buttonImport = new Button("Open From File");
        buttonImport.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import Text File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(window);
            String fileVal = Sub.getText(selectedFile);
            fieldText.setText(fileVal);
        });
        GridPane.setConstraints(buttonImport, 1, 2);

        Button buttonExport = new Button("Export To File");
//        buttonExport.setOnAction(e ->);
        GridPane.setConstraints(buttonExport, 3, 2);

        TextArea output = new TextArea();
        output.setEditable(false);
        GridPane.setConstraints(output, 1, 3);
        output.setMaxWidth(300);
        output.setMinWidth(300);
        output.setPrefRowCount(1);

        Button buttonTranslate = new Button("→");
        buttonTranslate.setTooltip(new Tooltip("Translate"));
        buttonTranslate.setOnAction(e -> {
            String input = fieldText.getText();
            translation = Sub.translateTM(input);
            fieldMorse.setText(translation[0]);
            output.setText(translation[1]);
        });
        GridPane.setConstraints(buttonTranslate, 2, 1);
        buttonTranslate.setMaxWidth(30);
        buttonTranslate.setMinWidth(30);

        Button buttonHear = new Button("\uD83D\uDD0A");
        buttonHear.setTooltip(new Tooltip("Play Sound"));
        buttonHear.setOnAction(e -> {
            String tts = fieldMorse.getText();
            try {
                Sub.playMorse(tts);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        GridPane.setConstraints(buttonHear, 4, 1);
        buttonHear.setMaxWidth(30);
        buttonHear.setMinWidth(30);

        Button buttonCopy = new Button("Copy to Clipboard");
        buttonCopy.setOnAction(e -> {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable transferable = new StringSelection(fieldMorse.getText());
                clipboard.setContents(transferable, null);
                output.setText("Successfully copied to clipboard @ " + timestamp);
            }catch(Exception c){
                output.setText("Unsuccessfully copied to clipboard @ " + timestamp);
            }
        });


        //Scene

        doubleCell.getChildren().addAll(buttonExport, buttonCopy);

        layout.getChildren().addAll(buttonBack, labelText, labelMorse, fieldText, buttonTranslate, fieldMorse, buttonHear, buttonImport, output, doubleCell);
        Scene sceneTM = new Scene(layout, defWidth, defHight);
        window.setScene(sceneTM);


    }

    public void MT() {


        //Layout

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setAlignment(Pos.CENTER);


        //Components

        Button buttonBack = new Button("\uD83C\uDFE0");
        buttonBack.setTooltip(new Tooltip("Home"));
        buttonBack.setOnAction(e -> menu());
        GridPane.setConstraints(buttonBack, 0, 0);
        buttonBack.setMaxWidth(30);
        buttonBack.setMinWidth(30);

        Label labelText = new Label("Plain Text");
        GridPane.setConstraints(labelText, 3, 0);

        Label labelMorse = new Label("Morse Code");
        GridPane.setConstraints(labelMorse, 1, 0);

        TextArea fieldMorse = new TextArea();
        fieldMorse.setPromptText("Enter Morse Code Here...");
        GridPane.setConstraints(fieldMorse, 1, 1);
        fieldMorse.setMinSize(300, 100);
        fieldMorse.setMaxSize(300, 100);
        fieldMorse.setWrapText(true);

        TextArea fieldText = new TextArea();
        fieldText.setEditable(false);
        GridPane.setConstraints(fieldText, 3, 1);
        fieldText.setMinSize(300, 100);
        fieldText.setMaxSize(300, 100);
        fieldText.setWrapText(true);

        Button buttonImport = new Button("Open From File");
        buttonImport.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import Text File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(window);
            String fileVal = Sub.getText(selectedFile);
            fieldMorse.setText(fileVal);
        });
        GridPane.setConstraints(buttonImport, 1, 2);

        Button buttonExport = new Button("Export To File");
//        buttonExport.setOnAction(e ->);
        GridPane.setConstraints(buttonExport, 3, 2);

        TextArea output = new TextArea();
        output.setEditable(false);
        GridPane.setConstraints(output, 1, 3);
        output.setMaxWidth(300);
        output.setMinWidth(300);
        output.setPrefRowCount(1);

        Button buttonTranslate = new Button("→");
        buttonTranslate.setTooltip(new Tooltip("Translate"));
        buttonTranslate.setOnAction(e -> {
            String input = fieldMorse.getText();
            translation = Sub.translateMT(input);
            fieldText.setText(translation[0]);
            output.setText(translation[1]);
        });
        GridPane.setConstraints(buttonTranslate, 2, 1);
        buttonTranslate.setMaxWidth(30);
        buttonTranslate.setMinWidth(30);

        Button buttonHear = new Button("\uD83D\uDD0A");
        buttonHear.setTooltip(new Tooltip("Play Sound"));
        buttonHear.setOnAction(e -> {
            String tts = fieldText.getText();
            try {
                Sub.playText(tts);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        GridPane.setConstraints(buttonHear, 4, 1);
        buttonHear.setMaxWidth(30);
        buttonHear.setMinWidth(30);


        //Scene

        layout.getChildren().addAll(buttonBack, labelText, labelMorse, fieldMorse, buttonTranslate, fieldText, buttonHear, buttonImport, buttonExport, output);
        Scene sceneTM = new Scene(layout, defWidth, defHight);
        window.setScene(sceneTM);


    }
}
