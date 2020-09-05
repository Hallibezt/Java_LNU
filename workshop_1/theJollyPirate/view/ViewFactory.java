package view;

import model.boats.*;
import model.roles.Users;

public class ViewFactory {
    public Mainview getView(String type){
        if("Icelandic".equalsIgnoreCase(type)) return new Icelandic();
        else if("English".equalsIgnoreCase(type)) return new English();

        return null;
    }
}
