package view;

import controller.exceptions_errors.BoatLengthError;
import controller.exceptions_errors.InputNotInListException;
import model.Price;
import model.Boat;
import model.User;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public  class MainView implements Serializable {
 public Scanner input = new Scanner(System.in);


 public void welcome() {
  System.out.print("Welcome to Jolly Pirate Yacht Club! \n" +
          "Please choose from list: \n");
 }

 //Common messages  ##############################
 public void bar() {
  System.out.print("\n =============================================================================== \n");
 }

 public void closingProgram() {
  System.out.println("Closing program.........");
 }

 public void programClosed() {
  System.out.println("Program closed.");
 }

 public void memberRegistered() {
  System.out.print(" has been registered to database and the username is: ");
 }

 public void boatRegistered(Boat boat) {
  System.out.println(" The boat has been registered to the database and the registration number is: " + boat.getRegNumber() + ", and it is located at berth number: " + boat.getLocation() + ".");
 }

 public void boatUpdated(Price newPrice) {
  System.out.println(" The boat has been updated and the price added to the fee was: " + newPrice.getPrice() + " kr.");
 }

 public void promptFirstName() {
  System.out.println("Please, enter member's firstname: ");
 }

 public void promptSurName() {
  System.out.println("Please, enter member's surname: ");
 }

 public void promptSocialNumber() {
  System.out.println("Please, enter member's social security number (12 digits): ");
 }

 public void promptPassword() {
  System.out.println("Please, enter member's chosen password: ");
 }

 public void findMember() {
  System.out.println("Please, enter memberID: ");
 }

 public void confirmRemoveMember(User member) {
  System.out.print("Are you sure you want to remove " + member.getFullName() + ", social security number: " + member.getSocialNumber() + "?(yes/no)");
 }

 public void memberRemoved() {
  System.out.println("Member has been removed from the database and associated boats");
 }

 public void firstNameUpdate() {
  System.out.println("Please, enter member's new first name: ");
 }

 public void secondNameUpdate() {
  System.out.println("Please, enter member's new second name: ");
 }

 public void passwordUpdate() {
  System.out.println("Please, enter member's new password: ");
 }

 public void memberUpdated() {
  System.out.println("Member has been updated");
 }

 public void enterRegNumber() {
  System.out.print("Please enter the boat's registration number: ");
 }

 public void hasRegNumber() {
  System.out.print("Does the boat have registration number yes/no: ");
 }

 public void confirmRemoveBoat() {
  System.out.println("Are you sure you want to remove this boat from the registry? (yes/no))");
 }

 public void exitOption() {
  System.out.println("You can  enter \"x\" when prompted for input to return to main menu");
 }

 public void likeToUpdate() {
  System.out.println("Would you like to update the member? (yes/no) ");
 }

 public void hasLength() {
  System.out.println("Would you like to update the length too? (yes/no) ");
 }

 public void acceptPrice(double price) {
  System.out.println("The price for this booking is: " + price + " kr and you fee will be updated accordingly.\n " +
          "Do you accept? (yes/no) ");
 }

 public void noBoatRegistered() {
  System.out.println("You did not accept the price and no boat is registered.");
 }


 //Part of grade 4
 //public void loggedInMessage(String fullName) {System.out.println("You Are Logged In! Welcome " + fullName);}
 //public void loggedOutMessage(String fullName) {System.out.println("You are logged out. Thank you " + fullName + " for using Jolly Pirate booking system."); }


 //Control messages #######################
 enum boatType {motorsailer, sailboat, kayakCanoe, other}

 public String getBoatType() throws InputNotInListException {
  String type = null;
  listTypes();
  //Get the type and handle input error
  int input = this.input.nextInt();
  switch (input) {
   case 1:
    type = boatType.motorsailer.name();
    break;
   case 2:
    type = boatType.sailboat.name();
    break;
   case 3:
    type = boatType.kayakCanoe.name();
    break;
   case 4:
    type = boatType.other.name();
    break;
   case 5:
    loginOptions();
    break;
   default:
    throw new InputNotInListException("");
  }
  return type;
 }

 public enum updateOptions {firstName, secondName, password, registerBoat, removeBoat, exit}

 public updateOptions showUpdateMenu(User member) {
  try {
   updateMember(member);

   int i = input.nextInt();
   switch (i) {
    case 1:
     return updateOptions.firstName;
    case 2:
     return updateOptions.secondName;
    case 3:
     return updateOptions.password;
    case 4:
     return updateOptions.registerBoat;
    case 5:
     return updateOptions.removeBoat;
    case 6:
     return updateOptions.exit;
    default:
     wrongInput();
   }
  } catch (InputMismatchException e) {
   wrongInput();
   input.next();
  }
  return null;
 }

 public enum updateBoatOptions {type, length, exit}

 public updateBoatOptions showUpdateBoatMenu() {
  try {
   int i = input.nextInt();
   switch (i) {
    case 1:
     return updateBoatOptions.type;
    case 2:
     return updateBoatOptions.length;
    case 3:
     return updateBoatOptions.exit;
    default:
     wrongInput();
     showUpdateBoatMenu();
   }
  } catch (InputMismatchException e) {
   wrongInput();
   input.next();
   showUpdateBoatMenu();
  }
  return null;
 }

 public enum mainOptions {createMember, removeMember, editMember, findMember, registerBoat, removeBoat, editBoat, compact, verbose, exit}

 public mainOptions showMainMenu() {
  loginOptions();
  int i = input.nextInt();
  switch (i) {
   case 1:
    return mainOptions.createMember;
   case 2:
    return mainOptions.removeMember;
   case 3:
    return mainOptions.editMember;
   case 4:
    return mainOptions.findMember;
   case 5:
    return mainOptions.registerBoat;
   case 6:
    return mainOptions.removeBoat;
   case 7:
    return mainOptions.editBoat;
   case 8:
    return mainOptions.compact;
   case 9:
    return mainOptions.verbose;
   case 10:
    return mainOptions.exit;
   default:
    wrongInput();
    input.next();
  }
  return null;
 }


 //Handling inputs
 public double enterLength() throws BoatLengthError {
  try {
   System.out.print("Please enter the boats length: ");
   double length = input.nextDouble();
   if (length < 1 || length > 20)
    throw new BoatLengthError("");
   return length;
  }
  catch (InputMismatchException e){
     wrongInput();
     input.next();
     enterLength();}
  return 0;
}


 public String getInput(){
  return input.next();
 }

 public boolean x(String firstName) {
  return firstName.equalsIgnoreCase("x");
 }


 public boolean confirm() throws InputNotInListException {
  String input = getInput();
  if(input.equalsIgnoreCase("yes")  )
   return true;
  if(input.equalsIgnoreCase("no"))
   return false;
  else
   throw new InputNotInListException("");
 }

 //Printing information about users and boats
 public void compactList(User user) {
  System.out.println(user.getFullName() + ", UserID: " + user.getLogin().getUserID() + ", Number of boats: " + user.returnBoats().length +".");


 }

 public void verboseList(User user) {
  Boat[] list = user.returnBoats();
  System.out.print(user.getFullName() + " UserID: " + user.getLogin().getUserID() + " Social Security Number: " + user.getSocialNumber() + " Number of boats: " + user.returnBoats().length + " Members total fee: " + user.getFee().getTotalFee() + " kr. \n" +
          "  Boat info: ")  ;
  if(user.returnBoats().length == 0)
   noBoatsAreReg();
  for (Boat boat : list) {
    boatInfo(boat);
  }

 }

 public void boatInfo(Boat boat){
  System.out.print("\n   - Boat type: " + boat.getType() + ", Boat length: " + boat.getLength() + ", Boat registration number: " + boat.getRegNumber() + ", Boat located at berth number : " +  boat.getLocation() + ".");
 }


 //Option messages ##########################

 //public void nonLoginOptions(){ Part of grade 4
 //System.out.print("List of options if not logged in");
 //}

 public void loginOptions() {
  System.out.print("1. Create a member \n" +
          "2. Remove a member \n" +
          "3. Edit a member \n" +
          "4. Find one member \n" +
          "5. Register a boat \n" +
          "6. Remove a boat \n" +
          "7. Edit a boat \n" +
          "8. View a compact members list \n" +
          "9. View a verbose members list \n" +
          "10. Exit program \n");

 }

 public void updateMember(User member){
  System.out.println("Member to update: ");
  compactList(member);
  System.out.print("1. Update member's first name \n" +
          "2. Update member's second name\n" +
          "3. Change member's password\n" +
          "4. Register a boat\n" +
          "5. Remove a boat\n" +
          "6. Return to main menu\n");
 }

 public void listTypes() { System.out.print("What is the type of the boat?\n" +
         "1. Motorsailor \n" +
         "2. Sailboat \n" +
         "3. Kayak/Canoe\n" +
         "4. Other \n" +
         "5. Back to main menu\n");
 }

 public  void boatOptions(){
  System.out.print("What would you like to update: \n" +
          "1. Type \n" +
          "2. Length \n" +
          "3. Back to main menu\n");

 }

 //Part of grade 4
 //public void searchMenu() {
 //  bar();
 //  System.out.println("Welcome to Jolly Pirate's members search \n" +
 //  "Enter search words for desired criteria if you want to skip a criteria enter \"?\" instead in that criteria's box \n");
 //}

 //public void nameCriteria(){System.out.print("Enter name or part of a name: "); }
 //public void ageCriteria(){System.out.print("Enter age - use inequality notation before with no space between if desired (i.e. <=18 searches for under or equal to 18 years): ");}
 //public void monthCriteria(){System.out.print("Enter member's birthmonth (digits or strings): ");}
 //public void boatTypeCriteria(){System.out.print("Enter boat type: ");}
 //public void noSearchResult() {System.out.println("No search results for this criteria"); }
 //public void loginFailure() { System.out.println("User not found, please try again or use non-logged in options."); }





 //Error messages ################################
 public void wrongInput() { System.out.println( "Your input is not an option, please try again."); }
 public void userAlreadyInDB() {System.out.println("The user is already registered member at Jolly Pirate") ;  }
 public void nameOrSocialFormat(){System.out.println("Wrong format - Names can not include non-characters and Social security number must be in the format:YYYYDDMMXXXX");}
 public void nameFormat(){System.out.println("Wrong format - Names can not include non-characters");}
 //public void memberNotFound(){System.out.println("This membersID is not in the database");}
 public void credFailure(){System.out.println("Credentials do not match or this membersID is not in the database. ");}
 public void noMemberRegistered(){System.out.println("There is no member registered in the database");}
 public void noBerths() {System.out.println("There are no berths available");   }
 public void noBoatsAreReg() {System.out.println("No boats registered at Jolly Pirate");   }
 public void boatNotFound() {System.out.println("A boat with this registration number was not found");   }
 public void lengthError() { System.out.println("We do NOT register boats under 1 meter or over 20 meters"); }
 public void boatAlreadyInRegistry() {System.out.println("The boat with this registration number is already in the database");}



}
