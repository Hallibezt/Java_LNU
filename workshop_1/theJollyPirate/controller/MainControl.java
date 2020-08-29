package controller;

import model.Login;
import model.Registry;
import model.roles.Member;
import model.roles.Users;
import view.Mainview;

import java.io.IOException;

public class MainControl {
    private Registry jollyPirate;
    private Mainview view;
    private Users loggedInUser;
    private Boolean programRunning = true;


    public MainControl(Registry jollyPirate, Mainview view){
        this.jollyPirate = jollyPirate;
        this.view = view;
    }

    public Boolean getProgramRunning(){
        return programRunning;
    }
    public void welcome(){
        view.welcome();
        login();
    }

    public void login(){
        String input = view.getInput();
        if(input.equalsIgnoreCase("1")){
            loggedInUser = jollyPirate.confirmLogin(view.getCredentials());
            if(loggedInUser == null){
                view.loginFailure();
                view.bar();
                }
            else{
            view.loggedInMessage(loggedInUser.getFullName());
            loginOptions();}
        }
        else if(input.equalsIgnoreCase("2"))
            view.nonLoginOptions();
        else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("3")){
            view.closingProgram();
            programRunning = false;
           }
        else
            view.wrongInput();
            view.bar();
        }

    public void loginOptions(){
        view.loginOptions();
        String input = view.getInput();
        if (input.equals("1")){
            registerMember();
        }
        else if (input.equals("2")){
        }
        else if (input.equals("3")){
        }
        else if (input.equals("4")){
        }
        else if (input.equals("5")){
        }
        else if (input.equals("6")){
        }
        else if (input.equals("7")){
        }
        else if (input.equals("8") ){
            view.loggedOutMessage(loggedInUser.getFullName());
        }
        else{
            view.wrongInput();
            view.bar();
            loginOptions();}
    }

    //Login option controls ###############
    // TODO: 2020-08-29 move error message to view and check if there are numbers in names 
    public void registerMember() {
        view.promptFirstName();
        String firstName = view.getInput();
        view.promptSurName();
        String surName = view.getInput();
        view.promptSocialNumber();
        String socialNumber = view.getInput();
        boolean result = socialNumber.matches("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))0229)\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})02(0[1-9]|1[0-9]|2[0-8]))\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})(0[13578]|10|12)(0[1-9]|[12][0-9]|3[01]))\\d{4}$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))\\d{4}$");
        if(result == false){
            System.out.println("Social security number(SSN) has a wrong format, use YYYYDDMMXXXX. Enter new SSN");
            registerMember();
        }
        view.promptPassword();
        String password = view.getInput();
        Users member = new Member(firstName, surName, socialNumber,password);
        try{
        String memberUsername = jollyPirate.addMember(member);
        System.out.print(member.getFullName()); view.memberRegistered(); System.out.println(memberUsername);
        loginOptions();}
        catch (IllegalArgumentException e){
           view.userAlreadyInDB();
           view.bar();
           loginOptions();
        }
    }


}
