package controller;

import controller.exceptions_errors.*;
import model.*;
import model.Boat;
import model.User;
import view.MainView;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Random;


public class MainControl implements Serializable {
    private final Registry jollyPirate;
    private final MainView view;
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
        try {
            switch (view.showMainMenu()) {
                case createMember:
                    registerMember();
                    break;
                case removeMember:
                    removeMember();
                    break;
                case editMember:
                    updateMember(findMember());
                        break;
                case findMember:
                    findMemberViaOptionFour();
                    break;
                case registerBoat:
                    registerBoat(null);
                    break;
                case removeBoat:
                    removeBoat(null);
                    break;
                case editBoat:
                    updateBoat();
                    break;
                case compact:
                    compactListMembers();
                    break;
                case verbose:
                    verboseListMembers();
                    break;
                case exit:
                    view.closingProgram();
                    ExportImport export = new ExportImport();
                    export.exportRegistry(this.jollyPirate);
                    programRunning = false;
                    break;
                default:
                    view.bar();
                    loginOptions();
            }
        }
        catch (CreditFailureException e) {
            view.credFailure();
            view.bar();
            loginOptions();
        }
        catch (InputNotInListException | InputMismatchException e){
            view.wrongInput();
            view.bar();
            view.input.next();
        }
        catch (BoatNotFoundException e) {
            view.boatNotFound();
            view.bar();
            loginOptions();
        }

    }



        //LoginOptions() controls ###############
        private void registerMember() {
            view.exitOption();
                view.promptFirstName();
                String firstName = view.getInput();
                 if(view.x(firstName)){ //If user enters x he can cancel member creation and return to main menu
                     return;}
                view.promptSurName();
                String surName = view.getInput();
                    if(view.x(surName))
                        return;
                view.promptSocialNumber();
                String socialNumber = view.getInput();
                    if(view.x(socialNumber))
                        return;
                view.promptPassword();
                String password = view.getInput();
                    if(view.x(password))
                        return;
        try{
            User member = new User(firstName, surName, socialNumber,password);
                //we add the member and then we get returned the registered username
                String memberUsername = jollyPirate.addMember(member);
                    System.out.print(member.getFullName());
                    view.memberRegistered();
                    System.out.println(memberUsername);
                    loginOptions();
        }
        catch(WrongFormatException e){
                view.nameOrSocialFormat();
                registerMember();
        }
        catch (IllegalArgumentException e){
               view.userAlreadyInDB();
               view.bar();
               loginOptions();
        }
    }

      private void removeMember() {
         try{
          User member = findMember();
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
             removeMember();
         }
         catch (CreditFailureException e ){
             view.credFailure();
             view.bar();
                loginOptions();
         }
      }

    //This is when I use this method via nr 4, find one member, in main menu and do not want to prompt for password unless you want to change the member searched for
    private void findMemberViaOptionFour() throws CreditFailureException, InputNotInListException {
        User member = null;
        view.findMember();
        String userID = view.getInput();
        User[] all = jollyPirate.returnMembers();
        for (User user : all) {
            if (user.getLogin().getUserID().equalsIgnoreCase(userID))
                member = user;
        }
        if(member == null){throw new CreditFailureException("");} //Username wasn't found
        view.compactList(member);
        view.likeToUpdate();
        if (view.confirm()){ //if yes, then prompt for the password
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


          private void updateMember(User member)  {
                  try {
                      switch (view.showUpdateMenu(member)) {
                          case firstName:
                              view.firstNameUpdate();
                              member.addFirstName(view.getInput());
                              jollyPirate.updateMember(member);
                              view.memberUpdated();
                              updateMember(member);
                              break;
                          case secondName:
                              view.secondNameUpdate();
                              member.addSurName(view.getInput());
                              jollyPirate.updateMember(member);
                              view.memberUpdated();
                              updateMember(member);
                              break;
                          case password:
                              view.passwordUpdate();
                              member.getLogin().addPassword(view.getInput());
                              jollyPirate.updateMember(member);
                              view.memberUpdated();
                              updateMember(member);
                              break;
                          case registerBoat:
                              registerBoat(member);
                              break;
                          case removeBoat:
                              removeBoat(member);
                              break;
                          case exit:
                              view.bar();
                              loginOptions();
                              break;
                          default:
                              view.wrongInput();
                              updateMember(member);

                      }
                  } catch (WrongFormatException e) {
                      view.nameFormat();
                      updateMember(member);
                  }
                  catch (InputNotInListException e) {
                      view.wrongInput();
                      view.bar();
                      updateMember(member);
                  }
                  catch (InputMismatchException e) {
                      view.wrongInput();
                      view.bar();
                      view.input.next();
                      updateMember(member);
                  }
            }

            private void registerBoat(User member) {
                try {
                    if (!jollyPirate.availableBerth()) { //See first if there are any available berths
                        view.noBerths();
                        view.bar();
                        loginOptions();
                    }
                    boolean backToMain = false; //To find out if the method should return to updateMember or main menu
                    if(member == null ){  //If not called with certain member in mind (via updateMember) then prompt for one.
                        backToMain = true;
                        member = findMember();
                    }
                    Boat boat = createBoat(member);
                    if (boat != null) {
                        view.acceptPrice(boat.getPrice().getPrice());
                        if (!view.confirm()) {
                            view.noBoatRegistered();
                            loginOptions();
                        } else {
                            Berth berth = jollyPirate.findBert(member); //Check for previously used berths
                            boat.addLocation(berth.getLocation()); //add the berth location given to the boat
                            member.addBoat(boat);
                            jollyPirate.updateMember(member);
                            jollyPirate.updateBerths(berth.getLocation(), boat);
                            view.boatRegistered(boat); //Message registration went through
                        }
                    }
                    if (backToMain) //Either go to main menu or updateMember menu, depending on how the member got here.
                        loginOptions();
                    else
                        updateMember(member);
                }
                catch( CreditFailureException e){
                    view.credFailure();
                    view.bar();
                    loginOptions();
                }
                catch(InputNotInListException e){
                    view.wrongInput();
                    view.bar();
                    updateMember(member);
                }
            }


            private void removeBoat(User member) {
            try {
                boolean backToMain = false; //To find out if the method should return to updateMember or main menu
                if(member == null ){  //If not called with certain member in mind (via updateMember) then prompt for one.
                    backToMain = true;
                    member = findMember();
                }
                view.enterRegNumberRemove();
                String boatRegistrationNumber = view.getInput();
                if (view.x(boatRegistrationNumber)){
                    return;}
                Boat boat = jollyPirate.findBoat(boatRegistrationNumber, member); //Find a boat, but it has to have same memberID, so you can not remove from others
                view.confirmRemoveBoat();
                if (view.confirm()) {
                    jollyPirate.removeBoat(boat, member);
                    view.boatRemoved();
                }
                if (backToMain) //Either go to main menu or updateMember menu, depending on how the member got here.
                    loginOptions();
                else
                    updateMember(member);

            }
            catch( CreditFailureException e){
                view.credFailure();
                view.bar();
                loginOptions();
            }
            catch (InputNotInListException | InputMismatchException e ){
                    view.wrongInput();
                    view.bar();
                    removeBoat(member);
            }
            catch (BoatNotFoundException e){
                    view.boatNotFound();
                    view.bar();
                     removeBoat(member);
            }

            }

            private void updateBoat() throws CreditFailureException, InputNotInListException, BoatNotFoundException {
                    Price newPrice = new Price();
                    view.enterRegNumber();
                    String boatRegistrationNumber = view.getInput();
                    User owner = findMember();
                    Boat boat = jollyPirate.findBoat(boatRegistrationNumber, owner);
                    double oldLength = boat.getLength();
                    EnumValues.boatType type;
                    view.boatOptions();
                    EnumValues.updateBoatOptions options = view.showUpdateBoatMenu();
                    if (options ==  EnumValues.updateBoatOptions.type) {
                        view.listTypes();
                        type = view.getBoatType();
                        view.hasLength();
                        if (view.confirm()) {
                            boat.changeLength(view.enterLength());
                        }
                        Boat updatedBoat = new Boat(type, boat.getLength(), boat.getRegNumber(), boat.getOwner());
                        newPrice.setUpdatePrice(updatedBoat, boat.getType(), oldLength);
                        jollyPirate.updateBoat(updatedBoat);
                        view.boatUpdated(newPrice);
                        loginOptions();
                    } else if (options ==  EnumValues.updateBoatOptions.length) {
                        boat.changeLength(view.enterLength());
                        newPrice.setUpdatePrice(boat, boat.getType(), boat.getLength());
                        jollyPirate.updateBoat(boat);
                        view.boatUpdated(newPrice);
                        loginOptions();
                    } else if (options ==  EnumValues.updateBoatOptions.exit) {
                        view.bar();
                        loginOptions();
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


            //Assistant methods to loginOptions controllers ###############

                private User findMember() throws CreditFailureException{
                    User member;
                    view.findMember();
                    String userID = view.getInput();
                    view.promptPassword();
                    String password = view.getInput();
                    Login givenLogin = new Login();
                        givenLogin.addLoginUserID(userID);
                        givenLogin.addPassword(password);
                    member = jollyPirate.returnOneMember(givenLogin);
                    return member;
                }



                private Boat createBoat(User member) {
                    try {
                        EnumValues.boatType boatType = view.getBoatType();
                        String regNumber;
                        double length = view.enterLength();
                        view.hasRegNumber(); //enter registration number or create one from the club if the boat has none
                        if (view.confirm()) {
                            view.enterRegNumber();
                            regNumber = view.getInput();
                            if (jollyPirate.checkRegNumber(regNumber)) {
                                view.boatAlreadyInRegistry();
                                loginOptions();
                            }
                        } else
                            regNumber = createRegNumber();
                        Boat boat = new Boat(boatType, length, regNumber, member);
                        Price thePrice = new Price();
                           thePrice.setPrice(boat);
                           boat.setPrice(thePrice);
                        return boat;
                    }  catch (InputNotInListException e) {
                        view.wrongInput();
                        view.bar();
                    }
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
