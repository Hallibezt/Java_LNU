package view;

import model.Login;

import java.util.Scanner;

public class Mainview {
     Scanner input = new Scanner(System.in);

    public void welcome(){
        System.out.print("Welcome to Jolly Pirate Yacht Club! \n" +
                "Please choose from list: \n" +
                "1. Login: For booking a boat or change members information \n" +
                "2. Non-login: For view and search only \n" +
                "3. Close the program (you can also use \"q\") \n");
    }

    //Common messages  ##############################
    public void bar() {System.out.println("\n =============================================================================== \n");}
    public void loggedInMessage(String fullName) {
        System.out.println("You Are Logged In! Welcome " + fullName);
    }
    public void loggedOutMessage(String fullName) {System.out.println("You are logged out. Thank you " + fullName + " for using Jolly Pirate booking system."); }
    public void closingProgram( ) {System.out.println("Closing program.........");}
    public void programClosed( ) {System.out.println("Program closed.");}


    //Control messages #######################
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

    //Option messages ##########################
    // TODO: 2020-08-28 Create non-login options
    public void nonLoginOptions(){
        System.out.print("List of options if not logged in");
    }

    public void loginOptions() {
        System.out.print("1. Create a member \n" +
                "2. Remove a member \n" +
                "3. Edit a member \n" +
                "3. Register a boat \n" +
                "4. Remove a boat \n" +
                "5. Edit a boat \n" +
                "6. View a compact members list \n" +
                "7. View a verbose members list \n" +
                "8. Log-out \n");
    }

    //Error messages ################################
    public void loginFailure() {
        System.out.println("User not found, please try again or use non-logged in options.");
    }
    public void wrongInput() {
        System.out.println("Your input is not an option, please try again.");
    }

}
