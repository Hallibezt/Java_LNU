package controller;

import controller.exceptions_errors.BoatNotFoundException;
import controller.exceptions_errors.CreditFailureException;
import controller.exceptions_errors.ErrorHandling;
import controller.exceptions_errors.InputNotInListException;
import model.Berths;
import model.Price;
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
    private final Registry jollyPirate;
    private Mainview view;
    private final ErrorHandling errorHandling = new ErrorHandling();
    private Boolean programRunning = true;


    public MainControl(Registry jollyPirate, Mainview view){
        this.jollyPirate = jollyPirate;
        this.view = view;
    }
    public void welcome(){
        view.welcome();
        if(programRunning)
             loginOptions();
    }
    //If this becomes false - while loop in TheProgram stops and program stops
    public Boolean getProgramRunning(){
        return programRunning;
    }

    //Main menu ############### todo ADD look up one member
    private void loginOptions(){
        view.loginOptions();
        try {
            String input = view.inputConfirmation();
            switch (input) {
                case "1":
                    registerMember();
                    break;
                case "2":
                    removeMember();
                    break;
                case "3":
                    try {
                        updateMember(findMember(false));
                        break;
                    }
                    catch (CreditFailureException e) {
                        view.credFailure();
                        view.bar();
                        loginOptions();
                    }

                case "4":
                    try{
                    findMember(true);
                    break;}
                    catch (CreditFailureException e){
                        view.memberNotFound();
                        view.bar();
                        loginOptions();}
                case "5":
                    registerBoat(null);
                    break;
                case "6":
                    removeBoat(null);
                    break;
                case "7":
                    updateBoat(); // TODO: 2020-09-06 left - get information, create boat and call jollypirate.updateboat
                    break;
                case "8":
                    compactListMembers();
                    break;
                case "9":
                    verboseListMembers();
                    break;
                case "10":
                    view.changeView();
                    changeView(view.inputConfirmation());
                    break;
                case "11":
                    view.closingProgram();
                    ExportInport export = new ExportInport();
                    export.exportRegistry(this.jollyPirate);
                    programRunning = false;
                    break;
            }
        }

        catch (InputMismatchException | InputNotInListException e){
            view.wrongInput();
            view.bar();
            loginOptions();}
        catch (BoatNotFoundException e) {
            view.boatNotFound();
            view.bar();
            view.loginOptions();
        } catch (CreditFailureException e) {
            view.credFailure();
            view.bar();
            loginOptions();
        }
    }



        //LoginOptions() controls ###############
        private void registerMember() {
            view.exitOption();
            view.promptFirstName();
            String firstName = view.getInput();
             if(firstName.equalsIgnoreCase("x"))
                 loginOptions();
            view.promptSurName();
            String surName = view.getInput();
                if(surName.equalsIgnoreCase("x"))
                    loginOptions();
             if(!errorHandling.nameFormat(firstName) || !errorHandling.nameFormat(surName)){
                view.nameFormat();
                registerMember(); }
            view.promptSocialNumber();
            String socialNumber = view.getInput();
                if(socialNumber.equalsIgnoreCase("x"))
                    loginOptions();
                if(!errorHandling.socialFormat(socialNumber)){
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

          private void removeMember() {
             try{
              Users member = findMember(false);
              view.confirmRemoveMember(member);
                if(view.confirm()) {
                    view.memberRemoved();
                    jollyPirate.removeMember(member);
                }
                loginOptions();
             }
             catch (InputNotInListException e){
                 view.wrongInput();
                 view.bar();
                 removeMember();}
             catch (CreditFailureException e ){
                 view.credFailure();
                 view.bar();
                    loginOptions();}
          }


          private void updateMember(Users member)  {
                view.updateMember(member);
                try {
                    String input = view.inputConfirmation();
                    if (input.equalsIgnoreCase("1")) {//enter new name - member.update())
                        view.firstNameUpdate();
                        String name = view.getInput();
                        if (!errorHandling.nameFormat(name)) {
                            view.nameFormat();
                            updateMember(member);
                        }
                        member.addFirstName(name);
                        view.memberUpdated();
                        jollyPirate.updateMember(member);
                        updateMember(member);
                    } else if (input.equalsIgnoreCase("2")) {//enter new name - member.update())
                        view.secondNameUpdate();
                        String name = view.getInput();
                        if (!errorHandling.nameFormat(name)) {
                            view.nameFormat();
                            updateMember(member);
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
                    } else if (input.equalsIgnoreCase("5")) {
                        removeBoat(member);
                        view.memberUpdated();
                        updateMember(member);
                    } else if (input.equalsIgnoreCase("6")) {
                        view.bar();
                        loginOptions();
                    }
                    else{view.wrongInput();
                       throw new InputNotInListException(""); }

                }
                catch (InputMismatchException | InputNotInListException e){
                    view.wrongInput();
                    view.bar();
                    updateMember(member);}


            }

            private void registerBoat(Users member) {
                try{
                    if(!jollyPirate.availableBerth()){ //See first if there are any available berths
                        view.noBerths();
                        view.bar();
                        loginOptions();
                    }
                        boolean backToMain = false; //To find out if the method should return to updateMember or main menu
                        if(member == null ){  //If not called with certain member in mind (via updateMember) then prompt for one.
                            backToMain = true;
                            member = findMember(false);
                        }
                    // TODO: 2020-09-13 should call boatfactory
                        Boat boat = createBoat(member);
                        Price thePrice = new Price();
                        thePrice.setPrice(boat);
                        view.acceptPrice(thePrice.getPrice());
                        if(!view.confirm()){
                            view.noBoatRegistered();
                            loginOptions();
                        }
                        else {
                            Berths berth = jollyPirate.findBert(member); //Check for previously used berths
                            assert boat != null;
                            boat.addLocation(berth.getLocation()); //add the berth location given to the boat
                            member.addBoat(boat, thePrice);
                            jollyPirate.updateMember(member);
                            jollyPirate.updateBerths(berth.getLocation(), boat);
                            view.boatRegistered(boat); //Message registration went through
                        }
                        if (backToMain) //Either go to main menu or updateMember menu, depending on how the member got here.
                            loginOptions();
                        else
                            updateMember(member);

                      }
                catch(CreditFailureException | InputNotInListException e){ //If findmember() given credits are wrong: username does not match password
                    view.credFailure();
                    view.bar();
                }
            }


            private void removeBoat(Users member) {
            try {
                boolean backToMain = false; //To find out if the method should return to updateMember or main menu
                if (member == null) {  //If not called with certain member in mind (via updateMember) then prompt for one.
                    member = findMember(false);
                    backToMain = true;
                }

                view.enterRegNumber();
                String boatRegistrationNumber = view.getInput();
                Boat boat = jollyPirate.findBoat(boatRegistrationNumber, member); //Find a boat, but it has to have same memberID, so you can not remove from others
                view.confirmRemoveBoat();
                if (view.confirm()) {
                    jollyPirate.removeBoat(boat, member);
                }
                else if (backToMain) //Either go to main menu or updateMember menu, depending on how the member got here.
                    loginOptions();
                else
                    updateMember(member);
            }
            // TODO: 2020-09-13 see what to do about this one
                catch (NullPointerException e){
                    e.printStackTrace();
                    loginOptions();
                  }
                catch (CreditFailureException e){
                    view.credFailure();
                    view.bar();}
                catch (InputNotInListException e ){
                    view.wrongInput();
                    view.bar();
                    removeBoat(member);}
                catch (BoatNotFoundException e){
                    view.boatNotFound();
                    view.bar();
                    loginOptions();}

            }

        // TODO: 2020-09-05 make some easy updates
            private void updateBoat() throws CreditFailureException, InputNotInListException, BoatNotFoundException {
                view.enterRegNumber();
                Price newPrice = new Price();
                String boatRegistrationNumber = view.getInput();
                Users owner = findMember(false);
                Boat boat = jollyPirate.findBoat(boatRegistrationNumber, owner);
                view.boatOptions();
                String boatType = null;
                double oldLength = boat.getLength();
                BoatFactory newBoat = new BoatFactory();
                String input = view.inputConfirmation();
                    if (input.equalsIgnoreCase("1")) {
                        view.listTypes();
                        String type = view.inputConfirmation();
                        switch (type) {
                            case "1":
                                boatType = "motorsailer";
                                break;
                            case "2":
                                boatType = "sailboat";
                                break;
                            case "3":
                                boatType = "kayak_canoe";
                                break;
                            case "4":
                                boatType = "other";
                                break;
                            default:
                                view.wrongInput();
                                view.bar();
                               updateBoat();
                                break;
                        }
                        view.hasLength();
                        if(view.confirm()){
                            boat.changeLength(view.enterLength());
                        }
                            Boat updatedBoat = newBoat.getBoat(boatType, boat.getLength(), boat.getRegNumber(), boat.getOwner());
                            newPrice.setUpdatePrice(updatedBoat, boat.getType(), oldLength);
                            updatedBoat.addLocation(boat.getLoacation());
                            jollyPirate.updateBoat(updatedBoat, newPrice);
                            view.boatUpdated(newPrice);
                        loginOptions();
                    }
                    else if (input.equalsIgnoreCase("2")){
                        boat.changeLength(view.enterLength());
                        newPrice.setUpdatePrice(boat, boat.getType(), boat.getLength());
                        jollyPirate.updateBoat(boat, newPrice);
                        loginOptions();
                    }
                    else if (input.equalsIgnoreCase("3")){
                        loginOptions();
                    }
                    else{view.wrongInput();
                        view.bar();
                        updateBoat();}


            }


            private void compactListMembers(){
              try {
                   Users[] membersList = jollyPirate.returnMembers();
                   for (Users users : membersList) {
                       view.compactList(users);
                   }
                   view.bar();
                   loginOptions();
               }
               catch (NullPointerException e) {
                  view.noMemberRegistered();
                   view.bar();
                  loginOptions();}
              }

              private void verboseListMembers(){
                  try {
                      Users[] membersList = jollyPirate.returnMembers();
                      for (Users users : membersList) {
                          view.verboseList(users);
                      }
                      view.bar();
                      loginOptions();
                  }
                  catch (NullPointerException e) {
                      view.noMemberRegistered();
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


            //Assistant methods to loginOptions controllers ###############
            private boolean confirmMember(Login givenLogin){
                Users[] members = jollyPirate.returnMembers();

                for (Users member : members) {
                    if (member.getLogin().compareTo(givenLogin)) {
                        return true;
                    }
                }
                return false;
            }

            private Users findMember(boolean viaNr4) throws CreditFailureException, InputNotInListException {
                Users member = null;
                view.findMember();
                String userID = view.getInput();
                            if(viaNr4){ //This is when I use this method via nr 4 in main menu and do not want to promt for password unless you want to change the member searched for
                                Users [] all = jollyPirate.returnMembers();
                                for(int i = 0; i< all.length;i++){
                                    if(all[i].getLogin().getUserID().equals(userID))
                                        member = all[i];
                                }
                                if(member == null){throw new CreditFailureException("");}
                                view.compactList(member);
                                view.likeToUpdate();
                                    if (view.confirm()){
                                        view.promptPassword();
                                        String password = view.getInput();
                                        if(member.getLogin().getPassword().equals(password))
                                            updateMember(member);
                                        else
                                            throw new CreditFailureException("");
                                    }
                                  else
                                    loginOptions();
                            }
                view.promptPassword();
                String password = view.getInput();
                Login givenLogin = new Login();
                givenLogin.addLoginUserID(userID);
                givenLogin.addPassword(password);
                if(confirmMember(givenLogin)){
                    member = jollyPirate.returnOneMember(givenLogin);}
                if(member == null){throw new CreditFailureException("");}
                return member;
            }

            private Boat createBoat(Users member) {
                          try{
                            BoatFactory boatFactory = new BoatFactory();
                            String regNumber;
                            String boatType = null;
                            double length = 0;
                            view.listTypes();
                            //Get the type and handle input error
                                  String input = view.inputConfirmation();
                                  switch (input) {
                                      case "1":
                                          boatType = "motorsailer";
                                          break;
                                      case "2":
                                          boatType = "sailboat";
                                          break;
                                      case "3":
                                          boatType = "kayak_canoe";
                                          break;
                                      case "4":
                                          boatType = "other";
                                          break;
                                      case "5":
                                          loginOptions();
                                          break;
                                      default:
                                          view.wrongInput();
                                          view.bar();
                                          registerBoat(member);
                                          break;
                                  }
                                  //Get the length - checking allowed length and handle input error
                                  length = view.enterLength();
                            view.hasRegNumber(); //enter registration number or create one from the club if the boat has none
                                  if(view.confirm()){
                                    view.enterRegNumber();
                                    regNumber = view.getInput();
                                    // errorHandling.validRegNumber(regNumber); if I have time
                                    if(jollyPirate.checkRegNumber(regNumber)){
                                        view.boatAlreadyInRegistry();
                                        loginOptions();}}
                                else
                                    regNumber = createRegNumber();
                              return boatFactory.getBoat(boatType, length, regNumber, member);
                          }
                          catch (InputNotInListException | InputMismatchException e){
                              view.wrongInput();
                              view.bar();
                              registerBoat(member);}
                          return null;
                        }


                private String createRegNumber() {
                    Random random = new Random();
                    String reg = "Pirate" + random.nextInt(1500);
                    while(jollyPirate.checkRegNumber(reg)){
                         reg = "Pirate" + random.nextInt(1500);}
                    return reg;
         }


}
