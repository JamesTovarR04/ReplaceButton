package com.jatrex.changeButton.classes;

import com.jatrex.changeButton.controllers.RowChangeController;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerChangesButtons implements NativeKeyListener, NativeMouseListener {

    private final Hashtable<Integer, List<RowChangeController>> tableButtonsMouse;
    private final Hashtable<Integer, List<RowChangeController>> tableKeys;
    private Robot robot;
    private boolean listening;

    public ManagerChangesButtons(){
        tableButtonsMouse = new Hashtable<>();
        tableKeys = new Hashtable<>();
        try{
            robot = new Robot();
        } catch(AWTException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void startGlobalListening(){
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex){
            Logger.getLogger(ReadButton.class.getName()).log(Level.SEVERE, null, ex);
        }

        GlobalScreen.getInstance().addNativeKeyListener(this);
        GlobalScreen.getInstance().addNativeMouseListener(this);
    }

    private void finalizeGlobalListening(){
        GlobalScreen.unregisterNativeHook();
        GlobalScreen.getInstance().removeNativeKeyListener(this);
        GlobalScreen.getInstance().removeNativeMouseListener(this);
    }

    public void restartGlobalListening(){
        if(listening) {
            finalizeGlobalListening();
            startGlobalListening();
        }
    }

    public void setListenButtons(boolean listen){
        listening = listen;
        if(listen)
            startGlobalListening();
        else
            finalizeGlobalListening();
    }

    public void addRowChange(RowChangeController rowChangeController){
        ButtonCode buttonIndex = rowChangeController.getButtonASave();
        int buttonIndexCode = buttonIndex.getCode();

        if(buttonIndex.getOrigin() == ButtonCode.KEY){
            tableKeys.computeIfAbsent(buttonIndexCode, k -> new ArrayList<>());
            tableKeys.get(buttonIndexCode).add(rowChangeController);
        } else {
            tableButtonsMouse.computeIfAbsent(buttonIndexCode, k -> new ArrayList<>());
            tableButtonsMouse.get(buttonIndexCode).add(rowChangeController);
        }
    }

    public void deleteRowChange(RowChangeController rowChangeController){
        ButtonCode buttonIndex = rowChangeController.getButtonASave();
        int buttonIndexCode = buttonIndex.getCode();

        if(buttonIndex.getOrigin() == ButtonCode.KEY){
            tableKeys.get(buttonIndexCode).remove(rowChangeController);
            if(tableKeys.get(buttonIndexCode).size() == 0)
                tableKeys.remove(buttonIndexCode);
        }
        else{
            tableButtonsMouse.get(buttonIndexCode).remove(rowChangeController);
            if(tableButtonsMouse.get(buttonIndexCode).size() == 0)
                tableButtonsMouse.remove(buttonIndexCode);
        }
        restartGlobalListening();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        int code = nativeKeyEvent.getKeyCode();
        pressButtons(tableKeys.get(code));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        int code = nativeKeyEvent.getKeyCode();
        releaseButtons(tableKeys.get(code));
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        int code = nativeMouseEvent.getButton();
        // In the Robot and JNativeHook library the codes 2 and 3 are changed
        if(code == 2) code = 3;
        else if(code == 3) code = 2;
        pressButtons(tableButtonsMouse.get(code));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        int code = nativeMouseEvent.getButton();
        // In the Robot and JNativeHook library the codes 2 and 3 are changed
        if(code == 2) code = 3;
        else if(code == 3) code = 2;
        releaseButtons(tableButtonsMouse.get(code));
    }

    private void pressButtons(List<RowChangeController> listButtons){
        if(listButtons != null){
            for (RowChangeController row:listButtons) {
                if(row.isActive()){
                    int codeButtonB = row.getButtonBSave().getCode();
                    if(row.getButtonBSave().getOrigin() == ButtonCode.KEY)
                        robot.keyPress(codeButtonB);
                    else
                        robot.mousePress(MouseEvent.getMaskForButton(codeButtonB));
                }
            }
        }
    }

    private void releaseButtons(List<RowChangeController> listButtons){
        if(listButtons != null){
            for (RowChangeController row:listButtons) {
                if(row.isActive()){
                    int codeButtonB = row.getButtonBSave().getCode();
                    if(row.getButtonBSave().getOrigin() == ButtonCode.KEY)
                        robot.keyRelease(codeButtonB);
                    else
                        robot.mouseRelease(MouseEvent.getMaskForButton(codeButtonB));
                }
            }
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) { }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) { }

}
