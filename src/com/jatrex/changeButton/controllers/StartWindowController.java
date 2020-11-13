package com.jatrex.changeButton.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartWindowController {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private HBox header;

    public StartWindowController(){

    }

    @FXML
    protected void closeWindow(ActionEvent event){
        System.exit(0);
    }

    @FXML
    protected void minimizeWindow(ActionEvent event){
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void movePreset(MouseEvent evento){
        xOffset = evento.getSceneX();
        yOffset = evento.getSceneY();
    }

    @FXML
    private void moveDragged(MouseEvent evt){
        Stage stage = (Stage)header.getScene().getWindow();
        stage.setX(evt.getScreenX() - xOffset);
        stage.setY(evt.getScreenY() - yOffset);
    }

}
