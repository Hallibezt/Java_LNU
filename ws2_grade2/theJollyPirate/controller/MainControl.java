package controller;

import controller.exceptions_errors.ErrorHandling;
import model.Berths;
import model.boats.Boat;
import model.Login;
import model.Registry;
import model.boats.BoatFactory;
import model.roles.Member;
import model.roles.Users;
import view.Mainview;
import view.ViewFactory;

import java.util.InputMismatchException;
import java.util.Random;


public class MainControl {
    private Registry jollyPirate;
    private Mainview view;
    private ErrorHandling errorHandling = new ErrorHandling();
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
        loginOptions();
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
                registerBoat(null);

            } else if (input.equals("5")) {
                removeBoat();
            } else if (input.equals("6")) {
                updateBoat(); // TODO: 2020-09-06 left
            } else if (input.equals("7")) {
                compactListMembers();
                //Berths[] experiment= jollyPirate.returnBerths();
                //for(int i = 0; i<experiment.length;i++){
                  //  System.out.println(experiment[i].getLocation());
                    //if(experiment[i].getCurrentUser() != null)
                      //  System.out.println(experiment[i].getCurrentUser().getFullName() + " " + experiment[i].getLocation());

            } else if (input.equals("8")) {
                verboseListMembers();
            } else if (input.equals("9")) {
                changeView(view.inputConfirmation());
            } else if (input.equals("10")) {
                view.closingProgram();
                ExportInport export = new ExportInport();
                export.exportRegistry(this.jollyPirate);
                programRunning = false;
            }

        }
        catch (InputMismatchException e){
            view.wrongInput();
            view.bar();
            loginOptions();}
    }

    private void changeView(String input){
        try{
        ViewFactory newView = new ViewFactory();
            this.view = newView.getView(input); }

        catch (InputMismatchException e){
            view.wrongInput();}
    }



    //Login option controls ###############
    public void registerMember() {
        view.exitOption();
        view.promptFirstName();
        String firstName = view.getInput();
         if(firstName.equalsIgnoreCase("x"))
             loginOptions();
        view.promptSurName();
        String surName = view.getInput();
            if(surName.equalsIgnoreCase("x"))
                loginOptions();
       //   if(errorHandling.nameFormat(firstName) == false || errorHandling.nameFormat(surName) == false){
           //   view.nameFormat();
          //    registerMember(); }
        view.promptSocialNumber();
        String socialNumber = view.getInput();
            if(socialNumber.equalsIgnoreCase("x"))
                loginOptions();
            if(errorHandling.socialFormat(socialNumber)==false){
                view.socialFormat();
                registerMember();}
        view.promptPassword();
        String password = view.getInput();
            if(password.equalsIgnoreCase("x"))
                loginOptions();
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

          public void removeMember() {
             try{
              Users member = findMember();
              view.confirmRemoveMember(member);
                if(view.confirm()){
                    view.memberRemoved();
                     jollyPirate.removeMember(member);
                     }
                loginOptions();
             }
             catch (Exception e){
                 view.memberNotFound();
                    loginOptions();}
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

                    } else if (input.equalsIgnoreCase("4")) {
                        registerBoat(member);
                        view.memberUpdated();
                        updateMember(member);
                    } else if (input.equalsIgnoreCase("5")) { //call the registerboat below

                    } else if (input.equalsIgnoreCase("6")) {
                        view.bar();
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

            private void registerBoat(Users member) {
                try{
                    if(!jollyPirate.availableBerth()){
                        view.noBerths();
                        loginOptions();
                    }
                    Boolean backToMain = false;
                    if(member == null ){
                        System.out.println("KOmst hingar..1");
                    member = findMember();
                        System.out.println("KOmst hingar..2");
                    backToMain = true;}
                   Boat boat = createBoat(member);
                    System.out.println("KOmst hingar..3 .... Boat created and the regnumber is:"+ boat.getRegNumber());
                   Berths berth = jollyPirate.findBert(member);
                    System.out.println("KOmst hingar..4");
                   boat.addLocation(berth.getLocation());
                    System.out.println("KOmst hingar..5");
                   member.addBoat(boat);
                    System.out.println("KOmst hingar..6");
                   jollyPirate.updateMember(member);
                   jollyPirate.updateBerths(berth.getLocation(), boat);
                    view.boatRegistered();
                    System.out.println(boat.getRegNumber());
                    if(backToMain==true)
                        loginOptions();
                    else
                        updateMember(member);
                                   }
                catch(Exception e){
                    view.credFailure();
                    view.bar();
                }
            }
            

            private void removeBoat() {
                try {
                    view.enterRegNumber();
                    String boatRegistrationNumber = view.getInput();
                    Boat boat = jollyPirate.findBoat(boatRegistrationNumber);
                view.confirmRemoveBoat();
                if (view.confirm()) {
                    jollyPirate.removeBoat(boat);
                    loginOptions();
                } else
                    loginOptions();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                    loginOptions();
                  }
                catch (Exception e){
                    e.printStackTrace();
                    view.boatNotFound();
                    loginOptions();}

            }

        // TODO: 2020-09-05 make some easy updates
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




    private Boat createBoat(Users member){
                    BoatFactory boat = new BoatFactory();
                    String regNumber;
                    String boatType = null;
                    double length = 0;
                    view.listTypes();
                    //Get the type and handle input error
                        try {
                            String input = view.inputConfirmation();
                            if (input.equals("1")) {
                                boatType = "motorsailer";
                            } else if (input.equals("2")) {
                                boatType = "sailboat";
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
                                registerBoat(member);}
                        }
                        catch (InputMismatchException e){
                            view.wrongInput();
                            view.bar();
                            registerBoat(member);}
                        //Get the length - checking allowed length and handle input error
                        try{
                            length = view.enterLength();}
                        catch (InputMismatchException e){
                            view.wrongInput();
                            view.lengthError();
                            view.bar();
                            registerBoat(member);
                        }
                    view.hasRegNumber();
                        //enter registration number or create one from the club if the boat has none
                        if(view.confirm()==true){
                            view.enterRegNumber();
                            regNumber = view.getInput();
                            // errorHandling.validRegNumber(regNumber); if I have time
                            if(jollyPirate.checkRegNumber(regNumber) == true){
                                view.boatAlreadyInRegistry();
                                loginOptions();}}
                        else
                            regNumber = createRegNumber();
                        Boat theBoat = boat.getBoat(boatType, length, regNumber, member);
                    return theBoat;
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
