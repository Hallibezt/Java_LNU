package view.inputs;

import java.util.InputMismatchException;

public abstract class Input {
    public abstract String inputConfirmation(String uInput) throws InputMismatchException;
}
