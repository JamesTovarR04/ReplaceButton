package com.jatrex.changeButton.controllers;

import com.jatrex.changeButton.classes.ButtonCode;
import com.jatrex.changeButton.classes.ManagerChangesButtons;
import com.jatrex.changeButton.classes.ReadButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class RowChangeController {

    @FXML
    HBox container;
    @FXML
    Circle activeCircle;
    @FXML
    Button deleteButton, buttonB;
    @FXML
    ImageView buttonAImage, buttonBImage;
    @FXML
    ToggleButton activateChangeButton;

    private Button standbyButton;
    private VBox listContainer;
    private ManagerChangesButtons managerChangesButtons;
    private ButtonCode buttonASave, buttonBSave;
    private boolean active;

    public RowChangeController(){
        active = false;
    }

    @FXML
    private void activateChange(ActionEvent event){
        active = activateChangeButton.isSelected();
        changeColorActiveCircle();
    }

    @FXML
    private void buttonClick(ActionEvent event){
        standbyButton = (Button) event.getSource();
        standbyButton.setText("Press a button");
        standbyButton.setDisable(true);
        changeIconButton("../views/images/add.png");
        deleteButton.setDisable(true);

        ReadButton reader = new ReadButton();
        reader.setRowChangeController(this);
    }

    public boolean isActive(){return active;}

    public ButtonCode getButtonASave(){return buttonASave;}

    public ButtonCode getButtonBSave(){return buttonBSave;}

    public void setListContainer(VBox listContainer){
        this.listContainer = listContainer;
    }

    public void setManagerChangesButtons(ManagerChangesButtons managerChangesButtons){
        this.managerChangesButtons = managerChangesButtons;
    }

    public void changeButton(ButtonCode buttonCode){
        String text;
        if( buttonCode.getOrigin() == ButtonCode.KEY ){
            text = "key " + buttonCode.getStringKey();
            changeIconButton("../views/images/keyboard.png");
        }else{
            text = "Button " + buttonCode.getCodeMouse();
            changeIconButton("../views/images/mouse.png");
        }
        standbyButton.setText(text);
        standbyButton.setDisable(false);

        if(standbyButton.getId().equals("buttonA")) {
            if (buttonASave != null)
                managerChangesButtons.deleteRowChange(this);
            buttonASave = buttonCode;
            managerChangesButtons.addRowChange(this);
        } else
            buttonBSave = buttonCode;

        if (buttonB.isDisable()){
            buttonB.setDisable(false);
        }else {
           activateChangeButton.setSelected(true);
           active = true;
           changeColorActiveCircle();
           deleteButton.setOpacity(1.0);
           deleteButton.setDisable(false);
           createRowChange();
        }

    }

    private void createRowChange(){
        // If it not is the last element don´t create a new element
        if((listContainer.getChildren().indexOf(container) + 1) == listContainer.getChildren().size()) {
            RowChangeController controllerRow = new RowChangeController();
            FXMLLoader viewRowChange = new FXMLLoader(getClass().getResource("../views/rowChange.fxml"));
            viewRowChange.setController(controllerRow);
            controllerRow.setManagerChangesButtons(managerChangesButtons);
            HBox row = new HBox();
            try {
                row = viewRowChange.load();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            listContainer.getChildren().add(row);
            controllerRow.setListContainer(listContainer);

        }
    }

    private void changeIconButton(String urlImage){
        if(standbyButton.getId().equals("buttonA"))
            buttonAImage.setImage(new Image(getClass().getResourceAsStream(urlImage)));
        else
            buttonBImage.setImage(new Image(getClass().getResourceAsStream(urlImage)));
    }

    private void changeColorActiveCircle(){
        if(activateChangeButton.isSelected())
            activeCircle.setFill(Color.web("#489742"));
        else
            activeCircle.setFill(Color.web("#a4a4a4"));
    }

    @FXML
    private void deleteRow(){
        managerChangesButtons.deleteRowChange(this);
        listContainer.getChildren().remove(container);
    }

}
