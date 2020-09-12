package view.inputs;

import java.util.InputMismatchException;

public class EnglishInput extends Input {
    @Override
    public String inputConfirmation(String uInput) throws InputMismatchException {
        String output = null;
        if(uInput.matches("\\b([1-9]|10)\\b"))
            output = uInput;

        if (output == null){throw new InputMismatchException();}
        return output;
    }
}
