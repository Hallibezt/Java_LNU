package controller;

import controller.exceptions_errors.*;
import model.Berth;
import model.Price;
import model.boats.Boat;
import model.Login;
import model.Registry;
import model.boats.BoatFactory;
import model.roles.Member;
import model.roles.User;
import view.MainView;
import view.ViewFactory;

import java.util.InputMismatchException;
import java.util.Random;


public class MainControl {
    private final Registry jollyPirate;
    private MainView view;
    private final ErrorHandling errorHandling = new ErrorHandling();
    private Boolean programRunning = true;


    public MainControl(Registry jollyPirate, MainView view){
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

    //Main menu ###############
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
                    updateBoat();
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
                    ExportImport export = new ExportImport();
                    export.exportRegistry(this.jollyPirate);
                    programRunning = false;
                    break;
            }
        }

        catch (InputNotInListException | InputMismatchException e){
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
             if(errorHandling.nameFormat(firstName) || errorHandling.nameFormat(surName)){
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
            User member = new Member(firstName, surName, socialNumber,password);
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
              User member = findMember(false);
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


          private void updateMember(User member)  {
                view.updateMember(member);
                try {
                    String input = view.inputConfirmation();
                    if (input.equalsIgnoreCase("1")) {//enter new name - member.update())
                        view.firstNameUpdate();
                        String name = view.getInput();
                        if (errorHandling.nameFormat(name)) {
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
                        if (errorHandling.nameFormat(name)) {
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
                catch (InputNotInListException | InputMismatchException e){
                    view.wrongInput();
                    view.bar();
                    updateMember(member);}


            }

            private void registerBoat(User member) {
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
                        Boat boat = createBoat(member);
                        Price thePrice = new Price();
                        if(boat == null)
                            throw new NullPointerException();
                        thePrice.setPrice(boat);
                        view.acceptPrice(thePrice.getPrice());
                        if(!view.confirm()){
                            view.noBoatRegistered();
                            loginOptions();
                        }
                        else {
                            Berth berth = jollyPirate.findBert(member); //Check for previously used berths
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
                catch(NullPointerException ignored){}
                catch(CreditFailureException | InputNotInListException | InputMismatchException e){ //If findmember() given credits are wrong: username does not match password
                    view.credFailure();
                    view.bar();
                    loginOptions();
                }
            }


            private void removeBoat(User member) {
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
                catch (CreditFailureException e){
                    view.credFailure();
                    view.bar();}
                catch (InputNotInListException | InputMismatchException e ){
                    view.wrongInput();
                    view.bar();
                    removeBoat(member);}
                catch (BoatNotFoundException e){
                    view.boatNotFound();
                    view.bar();
                    loginOptions();}

            }

            private void updateBoat() throws CreditFailureException, InputNotInListException, BoatNotFoundException {
            try {
                view.enterRegNumber();
                Price newPrice = new Price();
                String boatRegistrationNumber = view.getInput();
                User owner = findMember(false);
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
                    if (view.confirm()) {
                        boat.changeLength(view.enterLength());
                    }
                    Boat updatedBoat = newBoat.getBoat(boatType, boat.getLength(), boat.getRegNumber(), boat.getOwner());
                    newPrice.setUpdatePrice(updatedBoat, boat.getType(), oldLength);
                    updatedBoat.addLocation(boat.getLocation());
                    jollyPirate.updateBoat(updatedBoat, newPrice);
                    view.boatUpdated(newPrice);
                    loginOptions();
                } else if (input.equalsIgnoreCase("2")) {
                    boat.changeLength(view.enterLength());
                    newPrice.setUpdatePrice(boat, boat.getType(), boat.getLength());
                    jollyPirate.updateBoat(boat, newPrice);
                    view.boatUpdated(newPrice);
                    loginOptions();
                } else if (input.equalsIgnoreCase("3")) {
                    loginOptions();
                } else {
                    view.wrongInput();
                    view.bar();
                    updateBoat();
                }
            }
            catch(BoatLengthError e){
                view.lengthError();
                updateBoat();
            }

            }


            private void compactListMembers(){
                   User[] membersList = jollyPirate.returnMembers();
                   for (User user : membersList) {
                       view.compactList(user);
                   }
                   if(membersList.length == 0)
                       view.noMemberRegistered();
                   view.bar();
                   loginOptions();

              }

              private void verboseListMembers(){
                      User[] membersList = jollyPirate.returnMembers();
                      for (User user : membersList) {
                          view.verboseList(user);
                      }
                          if(membersList.length == 0)
                              view.noMemberRegistered();
                      view.bar();
                      loginOptions();
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
                User[] members = jollyPirate.returnMembers();

                for (User member : members) {
                    if (member.getLogin().compareTo(givenLogin)) {
                        return true;
                    }
                }
                return false;
            }

            private User findMember(boolean viaNr4) throws CreditFailureException, InputNotInListException {
                User member = null;
                view.findMember();
                String userID = view.getInput();
                            if(viaNr4){ //This is when I use this method via nr 4 in main menu and do not want to promt for password unless you want to change the member searched for
                                User[] all = jollyPirate.returnMembers();
                                for (User user : all) {
                                    if (user.getLogin().getUserID().equals(userID))
                                        member = user;
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

            private Boat createBoat(User member) {
                          try{
                            BoatFactory boatFactory = new BoatFactory();
                            String regNumber;
                            String boatType = null;
                            double length;
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
                                          registerBoat(null);
                                          break;
                                  }
                                  //Get the length - checking allowed length and handle input error
                                  length = view.enterLength();
                            view.hasRegNumber(); //enter registration number or create one from the club if the boat has none
                                  if(view.confirm()){
                                    view.enterRegNumber();
                                    regNumber = view.getInput();
                                    if(jollyPirate.checkRegNumber(regNumber)){
                                        view.boatAlreadyInRegistry();
                                        loginOptions();}}
                                else
                                    regNumber = createRegNumber();
                              return boatFactory.getBoat(boatType, length, regNumber, member);
                          }
                          catch (BoatLengthError x){
                              view.lengthError();
                              registerBoat(null);
                          }
                          catch (InputNotInListException | InputMismatchException e){
                              view.wrongInput();
                              view.bar();
                              registerBoat(null);}
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
