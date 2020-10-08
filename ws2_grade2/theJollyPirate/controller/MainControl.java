package controller;

import model.*;
import model.enums.BoatType;
import model.enums.UpdateBoatOption;
import model.exceptions_errors.*;
import view.UserInterface;
import java.util.InputMismatchException;
import java.util.Random;


public class MainControl  {
    private final Registry jollyPirate;
    private final UserInterface view;
    private Boolean programRunning = true;



    public MainControl(Registry jollyPirate, UserInterface view){
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
                    view.needCredentialsWarning();
                    removeBoat(null);
                    break;
                case editBoat:
                    view.needCredentialsWarning();
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
        catch (InputNotInListException e){
            view.wrongInput();
            view.bar();
        }
        catch (InputMismatchException e){
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
            try{
                view.exitOption();
                view.promptFirstName();
                String firstName = view.getName();
                 if(view.x(firstName)){ //If user enters x he can cancel member creation and return to main menu
                     return;}
                view.promptSurName();
                String surName = view.getName();
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

            Member member = new Member(firstName, surName, socialNumber,password);
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
          Member member = findMember();
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
        Member member = null;
        view.findMember();
        String userID = view.getInput();
        Member[] all = jollyPirate.returnMembers();
        for (Member user : all) {
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


          private void updateMember(Member member)  {
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

            private void registerBoat(Member member) {
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
                            jollyPirate.updateBerths(berth.getLocation(), boat, member);
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


            private void removeBoat(Member member) {
            try {
                boolean backToMain = false; //To find out if the method should return to updateMember or main menu
                view.enterRegNumberRemove();
                String boatRegistrationNumber = view.getInput();
                if (view.x(boatRegistrationNumber)){
                    return;}
                if(member == null ){  //If not called with certain member in mind (via updateMember) then prompt for one.
                    backToMain = true;
                    member = findMember();
                }
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
                try {
                    view.enterRegNumber();
                    String boatRegistrationNumber = view.getInput();
                    Member owner = findMember();
                    Boat boat = jollyPirate.findBoat(boatRegistrationNumber, owner);
                    double oldLength = boat.getLength();
                    double newLength = oldLength;
                    BoatType type;
                    view.boatOptions();
                    UpdateBoatOption options = view.showUpdateBoatMenu();
                    if (options == UpdateBoatOption.type) {
                        type = view.getBoatType();
                        view.hasLength();
                        if (view.confirm()) {
                            newLength = view.enterLength();
                            if (newLength < 1 || newLength > 20) {
                                view.lengthError();
                                while (newLength < 1 || newLength > 20) {
                                    newLength = view.enterLength();
                                }
                            }
                        }

                        Boat updatedBoat = new Boat(type, newLength, boat.getRegNumber());
                        updatedBoat.getMoreUpdateInfo(boat, updatedBoat, oldLength);
                        jollyPirate.updateBoat(updatedBoat);
                        view.boatUpdated(updatedBoat.getPrice());
                        loginOptions();
                    } else if (options == UpdateBoatOption.length) {
                        newLength = view.enterLength();
                        if (newLength < 1 || newLength > 20) {
                            view.lengthError();
                            while (newLength < 1 || newLength > 20) {
                                newLength = view.enterLength();
                            }
                        }
                        boat.changeLength(newLength);
                        boat.getPrice().setUpdatePrice(boat, boat.getType(), boat.getLength());
                        jollyPirate.updateBoat(boat);
                        view.boatUpdated(boat.getPrice());
                        loginOptions();
                    } else if (options == UpdateBoatOption.exit) {
                        view.bar();
                        loginOptions();
                    }
                }
                catch (InputMismatchException e){
                    view.wrongInput();
                    loginOptions();
                }
            }


            private void compactListMembers(){
                   Member[] membersList = jollyPirate.returnMembers();
                   for (Member user : membersList) {
                       view.compactList(user);
                   }
                   if(membersList.length == 0)
                       view.noMemberRegistered();
                   view.bar();
                   loginOptions();

              }

              private void verboseListMembers(){
                      Member[] membersList = jollyPirate.returnMembers();
                      for (Member user : membersList) {
                          view.verboseList(user);
                      }
                          if(membersList.length == 0)
                              view.noMemberRegistered();
                      view.bar();
                      loginOptions();
              }


            //Assistant methods to loginOptions controllers ###############

                private Member findMember() throws CreditFailureException{
                    Member member;
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



                private Boat createBoat(Member member) {
                    try {
                        BoatType boatType = view.getBoatType();
                        String regNumber;
                        double length = view.enterLength();
                            if (length < 1 || length > 20) {
                                view.lengthError();
                                while (length < 1 || length > 20) {
                                    length = view.enterLength();
                                }
                            }
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
                        Boat boat = new Boat(boatType, length, regNumber);
                        Price thePrice = new Price();
                           thePrice.setPrice(boat);
                           boat.setPrice(thePrice);
                        return boat;
                    }  catch (InputNotInListException | InputMismatchException e) {
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
