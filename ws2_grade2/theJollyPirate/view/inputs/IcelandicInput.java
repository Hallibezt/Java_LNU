package view.inputs;

import java.util.InputMismatchException;

public class IcelandicInput extends  Input{
    @Override
    public String inputConfirmation(String uInput) throws InputMismatchException {
        String output = null;
        if(uInput.matches("[a,A]"))
            output = "10";
        else if(uInput.matches("[b,B]"))
            output = "9";
        else if(uInput.matches("[c,C]"))
            output = "8";
        else if(uInput.matches("[d,D]"))
            output = "7";
        else if(uInput.matches("[e,E]"))
            output = "6";
        else if(uInput.matches("[f,F]"))
            output = "5";
        else if(uInput.matches("[g,G]"))
            output = "4";
        else if(uInput.matches("[h,H]"))
            output = "3";
        else if(uInput.matches("[i,I]"))
            output = "2";
        else if(uInput.matches("[j,J]"))
            output = "1";


        if (output == null){throw new InputMismatchException();}
        return output;
    }
}
