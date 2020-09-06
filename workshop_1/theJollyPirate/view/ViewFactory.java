package view;

import model.boats.*;
import model.roles.Users;

import java.util.InputMismatchException;

public class ViewFactory {

    public Mainview getView(String type){
        Mainview newView = null;
        if("1".equalsIgnoreCase(type)) return new English();
        else if("2".equalsIgnoreCase(type)) return new Icelandic();
        else if( newView == null){throw new InputMismatchException();
        }
        return null;
    }
}
