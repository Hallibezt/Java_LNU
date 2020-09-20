package controller.exceptions_errors;

public class ErrorHandling {

    public boolean socialFormat(String socialNumber){
        return socialNumber.matches("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))0229)\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})02(0[1-9]|1[0-9]|2[0-8]))\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})(0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})(0[469]|11)(0[1-9]|[12][0-9]|30))\\d{4}$");
    }

    public boolean nameFormat(String firstName) {
        char[] c = firstName.toCharArray();
        for (char value : c) {
            if (!Character.isLetter(value))
                return true;
        }

        return false;
    }
}
