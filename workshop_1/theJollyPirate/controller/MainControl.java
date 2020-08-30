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
    private ErrorHandling errorHandling = new ErrorHandling();
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
            removeMember();
        }
        else if (input.equals("3")){
        }
        else if (input.equals("4")){
        }
        else if (input.equals("5")){
        }
        else if (input.equals("6")){
            compactListMembers();
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
    public void registerMember() {
        view.promptFirstName();
        String firstName = view.getInput();
        view.promptSurName();
        String surName = view.getInput();
        // TODO: 2020-08-30 FIX REGEX 
          //if(errorHandling.nameFormat(firstName,surName) == false){
             //  view.nameFormat();
             //  registerMember(); }
        view.promptSocialNumber();
        String socialNumber = view.getInput();
            if(errorHandling.socialFormat(socialNumber)==false){
                view.socialFormat();
                registerMember();}
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

          public void removeMember(){
              view.findMember();
              jollyPirate.removeMember(view.getInput(), view);
              loginOptions();
          }

          public void compactListMembers(){
           try {
               Users[] membersList = jollyPirate.returnMembers();
               for (int i = 0; i < membersList.length; i++) {
                   view.compactList((Member) membersList[i]);
               }
               loginOptions();
           }
           catch (NullPointerException e) {
               view.noMemberRegistered();
               loginOptions();}
          }

}
