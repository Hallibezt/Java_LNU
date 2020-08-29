package controller;

import model.Login;
import model.Registry;
import model.roles.Users;
import view.Mainview;

public class UserInterface {
    private Registry jollyPirate;
    private Mainview view;
    private Users loggedInUser;
    private Boolean programRunning = true;


    public UserInterface(Registry jollyPirate, Mainview view){
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
        if (input == "1"){
        }
        else if (input == "2"){
        }
        else if (input == "3"){
        }
        else if (input == "4"){
        }
        else if (input == "5"){
        }
        else if (input == "6"){
        }
        else if (input == "7"){
        }
        else if (input == "8" ){
            view.loggedOutMessage(loggedInUser.getFullName());
        }
        else
            view.wrongInput();
            view.bar();
    }




}
