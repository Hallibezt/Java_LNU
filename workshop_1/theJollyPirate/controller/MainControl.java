package controller;

import model.Berths;
import model.boats.Boat;
import model.Login;
import model.Registry;
import model.boats.BoatFactory;
import model.roles.Member;
import model.roles.Users;
import view.Mainview;

import java.util.InputMismatchException;
import java.util.Random;

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

        public boolean confirmMember(Login givenLogin){
            Users[] members = jollyPirate.returnMembers();

            for(int i = 0; i< members.length;i++){
                if(members[i].getLogin().compareTo(givenLogin) == true){
                    return true;
                }
            }
        return false;
        }

        public Users findMember() throws Exception{
            Users member = null;
            view.findMember();
            String userID = view.getInput();
            view.promptPassword();
            String password = view.getInput();
            Login givenLogin = new Login();
                givenLogin.addLoginUserID(userID);
                givenLogin.addPassword(password);
            if(confirmMember(givenLogin) == true){
                member = jollyPirate.returnOneMember(givenLogin);}
            if(member == null){throw new Exception();}
            return member;
        }

    public void loginOptions(){
        view.loginOptions();
        try {
            String input = view.inputConfirmation();
            if (input.equals("1")) {
                registerMember();
            } else if (input.equals("2")) {
                removeMember();
            } else if (input.equals("3")) {
                try{
                updateMember( findMember());}
                catch (Exception e) {
                    view.credFailure();
                    view.bar();
                    loginOptions();
                }
            } else if (input.equals("4")) {
                registerBoat();

            } else if (input.equals("5")) {
            } else if (input.equals("6")) {

            } else if (input.equals("7")) {
                compactListMembers();
            } else if (input.equals("8")) {
                verboseListMembers();
            } else if (input.equals("9")) {
                view.loggedOutMessage(loggedInUser.getFullName());
            } else if (input.equals("10")) {

            }
        }
        catch (InputMismatchException e){
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

    // TODO: 2020-09-02 call update member in registry to update all the changes there!!!! 
          public void updateMember(Users member)  {
                view.updateMember(member);
                try {
                    String input = view.inputConfirmation();
                    if (input.equalsIgnoreCase("1")) {//enter new name - member.update())
                        view.firstNameUpdate();
                        String name = view.getInput();
                        if (errorHandling.nameFormat(name) == false) {
                            view.nameFormat();
                            loginOptions();
                        }
                        member.addFirstName(name);
                        view.memberUpdated();
                        jollyPirate.updateMember(member);
                        updateMember(member);
                    } else if (input.equalsIgnoreCase("2")) {//enter new name - member.update())
                        view.secondNameUpdate();
                        String name = view.getInput();
                        if (errorHandling.nameFormat(name) == false) {
                            view.nameFormat();
                            loginOptions();
                        }
                        member.addSurName(name);
                        view.memberUpdated();
                        jollyPirate.updateMember(member);
                        updateMember(member);
                    } else if (input.equalsIgnoreCase("3")) {//enter new password - member.update()
                        view.passwordUpdate();
                        String name = view.getInput();
                        member.getLogin().addPassword(name);
                        view.memberUpdated();
                        jollyPirate.updateMember(member);
                        updateMember(member);

                    } else if (input.equalsIgnoreCase("4")) {//Enter boat registration number
                        String regNumber = "something";
                        //call the remove boat below???
                        //Jump to jolly pirate - iterate the berths find the boat and remove it there
                    } else if (input.equalsIgnoreCase("5")) { //call the registerboat below

                    } else if (input.equalsIgnoreCase("6")) {
                        loginOptions();
                    }
                    else{view.wrongInput();
                        view.bar();
                        updateMember(member);}

                }
                catch (InputMismatchException e){
                    view.wrongInput();
                    view.bar();
                    updateMember(member);}
                    loginOptions();

            }

            private void registerBoat() {
                try{
                    if(!jollyPirate.availableBerth()){
                        view.noBerths();
                        loginOptions();
                    }
                   Users member = findMember();
                   Boat boat = createBoat(member);
                   Berths berth = jollyPirate.findBert(member);
                   boat.addLocation(berth.getLocation());
                   member.addBoat(boat);
                   jollyPirate.updateMember(member);
                   jollyPirate.updateBerths(berth.getLocation(), boat);
                                   }
                catch(Exception e){
                    view.credFailure();
                    view.bar();
                }
            }
            

            private void removeBoat(){
                //Remove
            }

            private void updateBoat(){
                //List of option
                //update fee
            }

            //for members to look up in
            public void compactListMembers(){
              try {
                   Users[] membersList = jollyPirate.returnMembers();
                   for (Users users : membersList) {
                       view.compactList(users);
                   }
                   loginOptions();
               }
               catch (NullPointerException e) {
                  view.noMemberRegistered();
                  loginOptions();}
              }

              public void verboseListMembers(){
                  try {
                      Users[] membersList = jollyPirate.returnMembers();
                      for (int i = 0; i < membersList.length; i++) {
                          view.verboseList(membersList[i]);
                      }
                      loginOptions();
                  }
                  catch (NullPointerException e) {
                      view.noMemberRegistered();
                      loginOptions();}
              }

                public void search(){
                    //search combinations with full  or partly information depending on the user
                }
                
                private Boat createBoat(Users member){
                    BoatFactory boat = new BoatFactory();
                    String regNumber = null;
                    String boatType = null;
                    double length = 0;
                    view.listTypes();
                    try {
                        String input = view.inputConfirmation();
                        if (input.equals("1")) {
                            boatType = "sailboat";
                        } else if (input.equals("2")) {
                            boatType = "motorsailer";
                        } else if (input.equals("3")) {
                            boatType = "kayak_canoe";
                        } else if (input.equals("4")) {
                            boatType = "other";
                        }
                        else if (input.equals("5")) {
                            loginOptions();
                        }
                        else{view.wrongInput();
                            view.bar();
                            registerBoat();}
                    }
                    catch (InputMismatchException e){
                        view.wrongInput();
                        view.bar();
                        registerBoat();}

                    try{
                        length = view.enterLength();}
                    catch (InputMismatchException e){
                        view.wrongInput();
                        view.lengthError();
                        view.bar();
                        registerBoat();
                    }
                    view.hasRegNumber();
                    if(view.confirm()==true){
                        view.enterRegNumber();
                        regNumber = view.getInput();
                        // errorHandling.validRegNumber(regNumber); if I have time
                        if(jollyPirate.checkRegNumber(regNumber) == true){
                            view.boatAlreadyInRegistry();
                            loginOptions();}}
                    else
                        regNumber = createRegNumber();
                    
                    return boat.getBoat(boatType, length, regNumber, member);
                }

    // TODO: 2020-09-02 DOES this work? 
                private String createRegNumber() {
                    Random random = new Random();
                    String reg = "Pirate" + random.nextInt(1500);
                    if(jollyPirate.checkRegNumber(reg) == true){
                        createRegNumber();}
                        return reg;
                 }


}
