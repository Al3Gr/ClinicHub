package org.example.ui;

import java.util.Objects;

public class Utility {
    public static boolean validateData(String ...inputString) {
        for(String stringa : inputString) {
            if(stringa.equals(""))
                return false;
        }
        return true;
    }


}
