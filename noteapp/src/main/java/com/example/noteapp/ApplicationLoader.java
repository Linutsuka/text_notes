package com.example.noteapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationLoader extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationLoader.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 381, 604);
       scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("NoteApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}