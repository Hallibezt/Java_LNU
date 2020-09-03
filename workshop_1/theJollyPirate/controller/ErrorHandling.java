package controller;

public class ErrorHandling {

    public boolean socialFormat(String socialNumber){
    boolean socialFormat = socialNumber.matches("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))0229)\\d{4}$"
            + "|^(((19|2[0-9])[0-9]{2})02(0[1-9]|1[0-9]|2[0-8]))\\d{4}$"
            + "|^(((19|2[0-9])[0-9]{2})(0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))\\d{4}$"
            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))\\d{4}$");
        return socialFormat;
    }

    public boolean nameFormat(String firstName) {
        boolean nameFormat = firstName.matches("^[A-Za-z]+$");
        return nameFormat;
    }
}
