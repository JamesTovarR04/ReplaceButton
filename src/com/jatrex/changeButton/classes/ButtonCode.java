package com.jatrex.changeButton.classes;

import org.jnativehook.keyboard.NativeKeyEvent;

public class ButtonCode {

    public static final int KEY = 0;
    public static final int MOUSE = 1;

    private final int origin;
    private final int code;

    public ButtonCode(int origin, int code){
        this.origin = origin;
        this.code = code;
    }

    public int getOrigin(){ return origin; }

    public int getCode(){ return code; }

    public String getStringKey(){ return NativeKeyEvent.getKeyText(code);}

    public String getCodeMouse(){
        String codeTransform;
        switch (code){
            case 4: codeTransform = "2"; break;
            case 8: codeTransform = "3"; break;
            case 16: codeTransform = "1"; break;
            default: codeTransform = "0";
        }
        return codeTransform;
    }

}
