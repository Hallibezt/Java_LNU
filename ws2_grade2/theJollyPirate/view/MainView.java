package view;

import controller.exceptions_errors.BoatLengthError;
import controller.exceptions_errors.InputNotInListException;
import model.Price;
import model.boats.Boat;
import model.roles.User;

public abstract class MainView {

 public abstract void welcome();

 //Common messages  ##############################
 public abstract void bar();

 public abstract void closingProgram();

 public abstract void programClosed();

 public abstract void memberRegistered();

 public abstract void promptFirstName();

 public abstract void promptSurName();

 public abstract void promptSocialNumber();

 public abstract void promptPassword();

 public abstract void findMember();

 public abstract void confirmRemoveMember(User member);

 public abstract void memberRemoved();

 public abstract void firstNameUpdate();

 public abstract void secondNameUpdate();

 public abstract void passwordUpdate();

 public abstract void memberUpdated();

 public abstract void enterRegNumber();

 public abstract void hasRegNumber();

 public abstract void confirmRemoveBoat();

 public abstract void likeToUpdate();

 public abstract void hasLength();

 public abstract void acceptPrice(double price);

 public abstract void noBoatRegistered();
 public abstract void boatUpdated(Price newPrice);


 //Those two are for grade 4
 //public abstract void loggedInMessage(String fullName);
 //public abstract void loggedOutMessage(String fullName);


 //Control messages #######################
 //Those two are for switching the language/view
 public abstract String getViewType();

 public abstract String inputConfirmation();

 //Handling inputs
 public abstract double enterLength() throws InputNotInListException, BoatLengthError;

 public abstract String getInput();

 public abstract boolean confirm() throws InputNotInListException; //Yes or no question

 //Printing information about users and boats
 public abstract void compactList(User user);

 public abstract void verboseList(User user);

 public abstract void boatInfo(Boat boat);

 //Option messages ##########################

 public abstract void loginOptions();
 // public abstract void nonLoginOptions(); //for grade 4

 public abstract void updateMember(User member);

 public abstract void changeView();

 public abstract void listTypes();

 public abstract void boatOptions();

 //Error messages ################################

 public abstract void wrongInput();

 public abstract void userAlreadyInDB();

 public abstract void socialFormat();

 public abstract void nameFormat();

 public abstract void memberNotFound();

 public abstract void credFailure();

 public abstract void noMemberRegistered();

 public abstract void noBerths();

 public abstract void noBoatsAreReg();

 public abstract void boatNotFound();

 public abstract void lengthError();

 public abstract void boatAlreadyInRegistry();

 public abstract void boatRegistered(Boat boat);


 //Below is part of higher grade implementation
 // TODO: 2020-08-28 Create non-login options
 //public abstract void nonLoginOptions();
 // public abstract void loginFailure();
 //public abstract void noSearchResult();
 //public abstract void searchMenu();
 // public abstract void nameCriteria();
 // public abstract void ageCriteria();
 //public abstract void monthCriteria();
 //public abstract void boatTypeCriteria();

 public abstract void exitOption();


}
