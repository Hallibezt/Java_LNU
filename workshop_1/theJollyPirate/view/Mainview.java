package view;

import model.Login;
import model.boats.Boat;
import model.roles.Member;
import model.roles.Users;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Mainview {
   protected Scanner input = new Scanner(System.in);

    public abstract void welcome();

    //Common messages  ##############################
    public abstract void bar();
    public abstract void loggedInMessage(String fullName);
    public abstract void loggedOutMessage(String fullName);
    public abstract void closingProgram( );
    public abstract void programClosed( );
    public abstract void memberRegistered( );
    public abstract void promptFirstName();
    public abstract void promptSurName();
    public abstract void promptSocialNumber();
    public abstract void promptPassword();
    public abstract void findMember();
    public abstract void confirmRemoveMember(Users member);
    public abstract void memberRemoved();
    public abstract void firstNameUpdate();
    public abstract void secondNameUpdate();
    public abstract  void passwordUpdate();
    public abstract void memberUpdated();
    public abstract void enterRegNumber();
    public abstract void hasRegNumber();
    public abstract void confirmRemoveBoat();


    //Control messages #######################
    //Check inputs for different views(numbers vs letters)
    public abstract String getViewType();
    public abstract double enterLength();


    public abstract String inputConfirmation() ;

    public abstract String getInput();

    public abstract boolean confirm();

    // TODO: 2020-08-29 Might have to split this up because login is domain class


    public abstract Login getCredentials();

    public abstract void compactList(Users users);
    public abstract void changeView();


    public abstract void verboseList(Users users);

    public abstract void boatInfo(Boat boat);

    //Option messages ##########################
    // TODO: 2020-08-28 Create non-login options
    public abstract void nonLoginOptions();

    public abstract void loginOptions();

    public abstract void updateMember(Users member);

    public abstract void listTypes();

    //Error messages ################################
    public abstract void loginFailure();

    public abstract void wrongInput();

    public abstract void userAlreadyInDB();

    public abstract void socialFormat();

    public abstract void nameFormat();

    public abstract void memberNotFound();

    public abstract void credFailure();

    public abstract void noMemberRegistered();

    public abstract void noBerths();

    public abstract void noBoatsReg();

    public abstract void boatNotFound();

    public abstract void lengthError();

    public abstract void boatAlreadyInRegistry();

    public abstract void noSearchResult();



}
