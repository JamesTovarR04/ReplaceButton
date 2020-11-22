package com.jatrex.changeButton.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RowChangeController {

    @FXML
    Circle activeCircle;
    @FXML
    Button deleteButton, buttonB;
    @FXML
    ImageView buttonAImage, buttonBImage;

    @FXML
    private void activateChange(ActionEvent event){
        ToggleButton button = (ToggleButton) event.getSource();
        if(button.isSelected())
            activeCircle.setFill(Color.web("#489742"));
        else
            activeCircle.setFill(Color.web("#a4a4a4"));
    }

    @FXML
    private void buttonClick(ActionEvent event){
        Button button = (Button) event.getSource();
        button.setText("Press a button");
        button.setDisable(true);
        // TODO: Read button and save
    }




}
