package com.jatrex.changeButton.classes;

import com.jatrex.changeButton.controllers.RowChangeController;
import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadButton implements NativeKeyListener, NativeMouseListener {

    private ButtonCode pressedButtonCode;
    private RowChangeController rowChangeController;

    public ReadButton(){
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex){
            Logger.getLogger(ReadButton.class.getName()).log(Level.SEVERE, null, ex);
        }

        GlobalScreen.getInstance().addNativeKeyListener(this);
        GlobalScreen.getInstance().addNativeMouseListener(this);
    }

    public ButtonCode getPressedButton(){
        return pressedButtonCode;
    }

    public void setRowChangeController(RowChangeController rowChangeController){
        this.rowChangeController = rowChangeController;
    }

    private void reportButtonPressed(){
        if(rowChangeController != null){
            Platform.runLater(() -> {
                rowChangeController.changeButton(pressedButtonCode);
                rowChangeController = null;
            });
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.getInstance().removeNativeKeyListener(this);
            GlobalScreen.getInstance().removeNativeMouseListener(this);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        int code = nativeKeyEvent.getKeyCode();
        //System.out.println("Key pressed: " + code);
        pressedButtonCode = new ButtonCode(ButtonCode.KEY, code);
        reportButtonPressed();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        int code = nativeMouseEvent.getButton();
        int codeTransform;
        switch (code){
            case 1: codeTransform = 16; break;
            case 2: codeTransform = 4; break;
            case 3: codeTransform = 8; break;
            default: codeTransform = 0;
        }
        pressedButtonCode = new ButtonCode(ButtonCode.MOUSE, codeTransform);
        reportButtonPressed();
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }
}