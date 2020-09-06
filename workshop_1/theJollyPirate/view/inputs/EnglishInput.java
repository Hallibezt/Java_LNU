package view.inputs;

import java.util.InputMismatchException;

public class EnglishInput extends Input {
    @Override
    public String inputConfirmation(String uInput) throws InputMismatchException {
        String output = null;
        if(uInput.matches("[1,a,A]"))
            output = "1";
        else if(uInput.matches("[2,b,B]"))
            output = "2";
        else if(uInput.matches("[3,c,C]"))
            output = "3";
        else if(uInput.matches("[4,d,D]"))
            output = "4";
        else if(uInput.matches("[5,e,E]"))
            output = "5";
        else if(uInput.matches("[6,f,F]"))
            output = "6";
        else if(uInput.matches("[7,g,G]"))
            output = "7";
        else if(uInput.matches("[8,h,H]"))
            output = "8";
        else if(uInput.matches("[9,i,I]"))
            output = "9";
        else if(uInput.matches("[10,j,J]"))
            output = "10";
        else if(uInput.matches("[11,k,K]"))
            output = "11";

        if (output == null){throw new InputMismatchException();}
        return output;
    }
}
