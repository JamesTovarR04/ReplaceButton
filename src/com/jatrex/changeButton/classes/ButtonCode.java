package com.jatrex.changeButton.classes;

import org.jnativehook.keyboard.NativeKeyEvent;

public class ButtonCode {

    public static final int KEY = 0;
    public static final int MOUSE = 1;

    private int origin;
    private int code;

    public ButtonCode(int origin, int code){
        this.origin = origin;
        this.code = code;
    }

    public int getOrigin(){ return origin; }

    public int getCode(){ return code; }

    public String getStringKey(){ return NativeKeyEvent.getKeyText(code);}

}
