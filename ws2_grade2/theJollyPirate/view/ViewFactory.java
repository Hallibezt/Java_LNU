package view;


import java.util.InputMismatchException;

public class ViewFactory {

    public MainView getView(String type){
        if("1".equalsIgnoreCase(type)) return new English();
        else if("2".equalsIgnoreCase(type)) return new Icelandic();

        throw new InputMismatchException();
    }
}
