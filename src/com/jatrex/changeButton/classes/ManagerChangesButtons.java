package com.jatrex.changeButton.classes;

import com.jatrex.changeButton.controllers.RowChangeController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ManagerChangesButtons {

    private final Hashtable<Integer, List<RowChangeController>> tableButtonsMouse;
    private final Hashtable<Integer, List<RowChangeController>> tableKeys;

    public ManagerChangesButtons(){
        tableButtonsMouse = new Hashtable<>();
        tableKeys = new Hashtable<>();
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
        System.out.println(tableKeys.get(buttonIndexCode));
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
    }

}
