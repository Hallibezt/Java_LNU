package view.inputs;

public class InputFactory {

    public Input getInput(String type){
        if("English".equalsIgnoreCase(type)) return new EnglishInput();
        else if ("Icelandic".equalsIgnoreCase(type)) return new IcelandicInput();

        return null;
    }
}
