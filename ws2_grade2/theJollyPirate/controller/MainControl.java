package controller;

import model.*;
import model.enums.BoatType;
import model.enums.UpdateBoatOption;
import controller.exceptions_errors.InputNotInListException;
import view.UserInterface;

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
        view.bar();
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
                case CreateMember:
                    registerMember();
                    break;
                case RemoveMember:
                    removeMember();
                    break;
                case editMember:
                    updateMember(null);
                    break;
                case FindMember:
                    findOneMember();
                    break;
                case RegisterBoat:
                    registerBoat(null);
                    break;
                case RemoveBoat:
                    view.needCredentialsWarning();
                    removeBoat(null);
                    break;
                case EditBoat:
                    view.needCredentialsWarning();
                    updateBoat();
                    break;
                case Compact:
                    compactListMembers();
                    break;
                case Verbose:
                    verboseListMembers();
                    break;
                case Exit:
                    view.closingProgram();
                    ExportImport export = new ExportImport();
                    export.exportRegistry(this.jollyPirate);
                    programRunning = false;
                    break;
                case WrongIn:
                    loginOptions();
            }
        }
        catch (InputNotInListException e){
            view.wrongInput();
        }
    }

    private void registerMember() {
                boolean memberAlreadyRegistered;
                Member member = view.getMemberInfo();
                if(member == null)//User enters x to cancel or wrong formats (name/social number) and return to main menu
                    loginOptions();
                else {//we add the member and then we get returned the registered username
                   memberAlreadyRegistered = jollyPirate.addMember(member);
                   if(!memberAlreadyRegistered){
                       view.userAlreadyInDB();
                       loginOptions();
                   }
                   else {
                       view.memberRegistered(member.getFullName(), member.getCredentials().getUserID());
                       loginOptions();
                   }
                }
    }

    private void removeMember() {
        try {
            String[] credentials = view.findMember();
            Member member = jollyPirate.findMember(credentials[0], credentials[1]);
            if (member == null) {
                view.credFailure();
                loginOptions();
            }
            else {
                view.confirmRemoveMember(member);
                if (view.confirm()) {
                    jollyPirate.removeMember(member);
                    view.memberRemoved();
                }
                loginOptions();
            }
        }
        catch (InputNotInListException e){//view.confirm was something else then yes/no
            view.memberNotRemoved();
            loginOptions();
        }
    }

    //Update member has I calls to many of the methods in the main menu - but now always with one member in mind, not general
    private void updateMember(Member member) {
        //If you are getting here from the main menu
        if(member == null){
        String[] credentials = view.findMember();
        member = jollyPirate.findMember(credentials[0], credentials[1]);}
        if ( member!=null) {
                switch (view.showUpdateMenu(member)) {
                    case FirstName:
                        String newName = view.firstNameUpdate();
                        if (newName != null) {//If there wasn't formatException
                            member.updateFirstName(newName);
                            jollyPirate.updateMember(member);
                            view.memberUpdated();
                        }
                        updateMember(member);
                        break;
                    case SecondName:
                        String newSurName = view.secondNameUpdate();
                        if (newSurName != null) {//If there wasn't formatException
                            member.updateSurName(newSurName);
                            jollyPirate.updateMember(member);
                            view.memberUpdated();
                        }
                        updateMember(member);
                        break;
                    case Password:
                        member.getCredentials().updatePassword(view.passwordUpdate());
                        jollyPirate.updateMember(member);
                        view.memberUpdated();
                        updateMember(member);
                        break;
                    case RegisterBoat:
                        registerBoat(member);
                        break;
                    case RemoveBoat:
                        removeBoat(member);
                        break;
                    case Exit:
                        break;
                    case WrongInput:
                        view.wrongInput();
                        break;
                }
        }
        else
            view.credFailure();
    }

    //Nr 4 in main menu, find information about one member with its userID
    private void findOneMember()  {
        Member member = null;
        String memberID = view.findMemberJustID();
        member = jollyPirate.findMember(memberID, null);
        if(member == null){
            view.memberNotFound();
            loginOptions();
        } //Username wasn't found
        else{
            view.compactList(member);
            view.likeToUpdate();
            try {
                if (view.confirm()) { //if yes, then prompt for the password
                    if (member.getPassword().equals(view.promptPassword()))
                        updateMember(member);
                    else {
                        view.wrongPassword();
                    }
                }
            }
            catch (InputNotInListException e){ //view.confirm was something else then yes/no
                view.wrongInput();
            }
        }
    }

    private void registerBoat(Member member) {
        try {
            //See first if there are any available berths
            if (!jollyPirate.availableBerth()) {
                view.noBerths();
                loginOptions();
            }
            //This method can be called either from main menu(general all members) or from updateMember().
            //Here I use boolean to decide where to return to and if it was called with special member in mind or general via main menu
            boolean backToMain = false;
            //If not called with certain member in mind (via updateMember) then prompt for one.
            if(member == null ){
                backToMain = true;//Now set to return to main menu
                String[] credentials = view.findMember();
                member = jollyPirate.findMember(credentials[0], credentials[1]);
            }
            //If the register found a member, else just return back to main
            if(member != null) {
                //create a boat that then gets registered, that is if its not already registered
                Boat boat = createBoat();
                //If all went good when creating the boat, not already in the registry and the length of it allowed
                if (boat != null) {
                    //Part 4 of the requirement in the problem description: return the price of the booking for the member to confirm
                    view.acceptPrice(boat.getPriceObject().getPrice());
                    //If do not accept the price
                    if (!view.confirm()) {
                        view.noBoatRegistered();
                        loginOptions();
                    }
                    //Now the other subtle part of requirement 4 in problem description: the member prefers a berth it has had before, if available
                    else {
                        //Check for previously used berths, else any berth available
                        Berth berth = jollyPirate.findPreviouslyUsedBerth(member);
                        //add the berth location to the boat
                        boat.addLocation(berth.getLocation());
                        member.addBoat(boat);
                        jollyPirate.updateMember(member);
                        jollyPirate.updateBerths(berth.getLocation(), boat, member);
                        //Message registration went through
                        view.boatRegistered(boat);
                    }
                }
            }
            //Now decide where to return to
            if (backToMain)
                loginOptions();
            else
                updateMember(member);
        }
        catch(InputNotInListException e){ //If wrong input when confirming the price - always back to main menu
            view.wrongInput();
        }
    }

    private void removeBoat(Member member) {
        try {
            //To find out if the method should return to updateMember or main menu
            boolean backToMain = false;
            String boatRegistrationNumber =  view.enterRegNumber();
            Boat boat = jollyPirate.findBoat(boatRegistrationNumber);
            //If this boat is registered continue
            if(boat != null) {
                view.confirmRemoveBoat();
                if (view.confirm()) {
                    //Ask for owner credential before removing - that is if not accessed via updateMember()
                    if (member == null) {
                        backToMain = true;//Now set to return to main menu
                        String[] credentials = view.findMember();
                        member = jollyPirate.findMember(credentials[0], credentials[1]);
                    }
                    if(member.ownsBoat(boat)){
                        //Removes boat both from berth list and member list in the boat club
                        jollyPirate.removeBoat(boat, member);
                        view.boatRemoved();}
                    else
                        view.canNotRemoveBoat();
                }
            }
            else{
                view.boatNotFound();
                if(member == null)
                    backToMain = true;
            }
            if (backToMain) //Either go to main menu or updateMember menu, depending on how the member got here.
                loginOptions();
            else
                updateMember(member);

            }
        catch (InputNotInListException e ){//not yes/no in confirmation of removing boat
            view.wrongInput();
            removeBoat(member);
        }
    }

    private void updateBoat() throws  InputNotInListException{
            String boatRegistrationNumber =  view.enterRegNumber();
            Boat boat = jollyPirate.findBoat(boatRegistrationNumber);
            //If this boat is registered continue
            if(boat != null) {
                String[] credentials = view.findMember();
                Member member = jollyPirate.findMember(credentials[0], credentials[1]);
                if(member !=null && member.ownsBoat(boat)){
                    double oldLength = boat.getLength();
                    double newLength = oldLength;
                    BoatType type;
                    //Get the options for updating the boat
                    UpdateBoatOption options = view.showUpdateBoatMenu();
                    if (options == UpdateBoatOption.Type) {
                        //Get the new boat type - i.e. changed sailboat to motorsailer
                        type = view.getBoatType();
                        if(type != BoatType.WrongInput) {
                            //Change length to?
                            view.updateLength();
                            if (view.confirm()) {
                                newLength = view.enterLength();
                                if (newLength < 1 || newLength > 20) {
                                    newLength = view.lengthError(newLength);
                                }
                            }
                            if (newLength != 0) {
                                //create the updated boat and update the price of it according to update boat not new one
                                Boat updatedBoat = new Boat(type, newLength, boat.getRegNumber());
                                updatedBoat.getMoreUpdateInfo(boat, updatedBoat, oldLength);
                                //Updates the boat in the boat club's berth list and in boat club's member list
                                jollyPirate.updateBoat(updatedBoat);
                                view.boatUpdated(updatedBoat.getPriceObject());
                                loginOptions();
                            }
                            //The boat length is not allowed in the boat club or canceled
                            else {
                                view.noBoatRegistered();
                                loginOptions();
                            }
                        }
                        else
                            view.wrongInput();
                    }
                    //Just update the length
                    else if (options == UpdateBoatOption.Length) {
                        newLength = view.enterLength();
                        if (newLength < 1 || newLength > 20) {
                           newLength = view.lengthError(newLength);
                        }
                        if(newLength != 0){
                            boat.changeLength(newLength);
                            boat.getPriceObject().setUpdatePrice(boat, boat.getType(), boat.getLength());
                            jollyPirate.updateBoat(boat);
                            view.boatUpdated(boat.getPriceObject());
                            loginOptions();
                        }
                        //The boat length is not allowed in the boat club or canceled
                        else {
                            view.noBoatRegistered();
                            loginOptions();
                        }
                    }
                    else if (options == UpdateBoatOption.Exit) {
                        loginOptions();
                    }
                    else if (options == UpdateBoatOption.WrongInput) {
                        loginOptions();
                    }
                }
                else{
                    view.memberNotOwnBoat();
                    loginOptions();
                }
            }
            else{
                view.boatNotFound();
                loginOptions();
            }
    }

    private void compactListMembers(){
        Member[] membersList = jollyPirate.returnMembers();
        for (Member user : membersList) {
            view.compactList(user);
        }
        if(membersList.length == 0)
            view.noMemberRegistered();;
        loginOptions();
    }

    private void verboseListMembers(){
        Member[] membersList = jollyPirate.returnMembers();
        for (Member user : membersList) {
            view.verboseList(user);
        }
        if(membersList.length == 0)
            view.noMemberRegistered();
        loginOptions();
    }

            //Assistant methods to loginOptions controllers ###############

    private Boat createBoat() {
        try {
            BoatType boatType = view.getBoatType();
            if(boatType != BoatType.WrongInput) {
                String regNumber;
                double length = view.enterLength();
                if (length < 1 || length > 20) {
                    length = view.lengthError(length);
                }
                //Not canceled (might cancel registration if you realize that we do not accept your boat (to big/small for our berths))
                if (length != 0) {
                    //enter registration number or get one from the club if the boat has none.
                    view.hasRegNumber();
                    if (view.confirm()) {
                        view.enterRegNumber();
                        regNumber = view.getInput();
                        //If it is already registered, then return to main menu.
                        if (jollyPirate.checkRegNumber(regNumber)) {
                            view.boatAlreadyInRegistry();
                            loginOptions();
                        }
                    } else //Let the boat club create unique pseudo registration number
                        regNumber = createRegNumber();
                    Boat boat = new Boat(boatType, length, regNumber);
                    //Create a price for this kind of boat
                    Price thePrice = new Price();
                    thePrice.setPrice(boat);
                    boat.setPrice(thePrice);
                    return boat;
                } else
                    view.noBoatRegistered();
            }
            else
                view.wrongInput();
        }
        catch (InputNotInListException e) {//The confirmation if the boat has registration number
            view.wrongInput();
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
