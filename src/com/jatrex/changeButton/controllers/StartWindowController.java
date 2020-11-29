package com.jatrex.changeButton.controllers;

import com.jatrex.changeButton.classes.ManagerChangesButtons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StartWindowController {

    @FXML
    private HBox header;
    @FXML
    private ScrollPane listChanges;
    @FXML
    private ToggleButton activateButton;
    @FXML
    private Label statusLabel;

    private double xOffset = 0;
    private double yOffset = 0;
    private ManagerChangesButtons managerChangesButtons;

    @FXML
    private void initialize() throws IOException {
        managerChangesButtons = new ManagerChangesButtons();

        // Generate first row
        RowChangeController controllerRow = new RowChangeController();
        FXMLLoader viewRowChange = new FXMLLoader(getClass().getResource("../views/rowChange.fxml"));
        viewRowChange.setController(controllerRow);
        HBox row = viewRowChange.load();

        VBox listContainer = new VBox(row);
        controllerRow.setListContainer(listContainer);
        controllerRow.setManagerChangesButtons(managerChangesButtons);
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

    @FXML
    private void activate(ActionEvent event){
        if(activateButton.isSelected()){
            managerChangesButtons.setListenButtons(true);
            activateButton.setAlignment(Pos.CENTER_RIGHT);
            activateButton.setStyle("-fx-padding: 1 3 1 3; -fx-background-radius: 30;-fx-background-color: #489742;");
            statusLabel.setText("Active");
        } else{
            managerChangesButtons.setListenButtons(false);
            activateButton.setAlignment(Pos.CENTER_LEFT);
            activateButton.setStyle("-fx-padding: 1 3 1 3; -fx-background-radius: 30;-fx-background-color: #4f4f4f;");
            statusLabel.setText("Disabled");
        }
    }

    @FXML
    private void openLinkCredits(){
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/JamesTovarR04"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

}
