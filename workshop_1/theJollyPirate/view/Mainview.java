package view;

import model.Login;

import java.util.Scanner;

public class Mainview {
     Scanner input = new Scanner(System.in);

    public void welcome(){
        System.out.print("Welcome to Jolly Pirate Yacht Club! \n" +
                "Please choose from list: \n" +
                "1. Login: For booking a boat or change members information \n" +
                "2. Non-login: For view and search only \n");
    }

    public String getInput(){
        String uInput = input.next();
        return uInput;
    }

    public Login getCredentials(){
        Login login = new Login();
        System.out.print("Please enter your userId and Password.\n" +
                "UserID:  ");
                login.addLoginUserID(getInput());
        System.out.print("Password:  ");
        login.addPassword(getInput());
        return login;
    }

    public void nonLoginOptions(){
        System.out.print("List of options if not logged in");
    }

    public void loginFailure() {
        System.out.println("user not found, please try again or use non-logged in options.");
    }

    public void loggedInMessage(String fullName) {
        System.out.println("You Are Logged In! Welcome " + fullName);
    }
}
