package com.jatrex.changeButton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader startWindow = new FXMLLoader(getClass().getResource("views/startWindow.fxml"));
        Parent root = startWindow.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("views/styles/style.css").toExternalForm());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("ChangeButton");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("views/images/keyboard.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

}
