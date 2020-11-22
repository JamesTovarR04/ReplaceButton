package com.jatrex.changeButton.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowController {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private HBox header;
    @FXML
    private ScrollPane listChanges;

    public StartWindowController(){

    }

    @FXML
    private void initialize() throws IOException {
        HBox row = FXMLLoader.load(getClass().getResource("../views/rowChanges.fxml"));
        VBox listContainer = new VBox(row);
        listChanges.setContent(listContainer);
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
