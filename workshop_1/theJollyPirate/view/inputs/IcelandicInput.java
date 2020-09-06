package view.inputs;

import java.util.InputMismatchException;

public class IcelandicInput extends  Input{
    @Override
    public String inputConfirmation(String uInput) throws InputMismatchException {
        String output = null;
        if(uInput.matches("[a,A]"))
            output = "11";
        else if(uInput.matches("[b,B]"))
            output = "10";
        else if(uInput.matches("[c,C]"))
            output = "9";
        else if(uInput.matches("[d,D]"))
            output = "8";
        else if(uInput.matches("[e,E]"))
            output = "7";
        else if(uInput.matches("[f,F]"))
            output = "6";
        else if(uInput.matches("[g,G]"))
            output = "5";
        else if(uInput.matches("[h,H]"))
            output = "4";
        else if(uInput.matches("[i,I]"))
            output = "3";
        else if(uInput.matches("[j,J]"))
            output = "2";
        else if(uInput.matches("[k,K]"))
            output = "1";

        if (output == null){throw new InputMismatchException();}
        return output;
    }
}
