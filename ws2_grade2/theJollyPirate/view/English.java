package view;

import controller.exceptions_errors.InputNotInListException;
import model.boats.Boat;
import model.roles.Users;
import view.inputs.Input;
import view.inputs.InputFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class English extends Mainview{
    private final Scanner input = new Scanner(System.in);
    private String viewType = "English";

    public void welcome(){
        System.out.print("Welcome to Jolly Pirate Yacht Club! \n" +
                "Please choose from list: \n");
    }

    //Common messages  ##############################
    public void bar() {System.out.print("\n =============================================================================== \n");}
    public void closingProgram( ) {System.out.println("Closing program.........");}
    public void programClosed( ) {System.out.println("Program closed.");}
    public void memberRegistered( ) {System.out.print(" has been registered to database and the username is: ");}
    public void boatRegistered( ) {System.out.print(" The boat been registered to database and the registration number is: ");}
    public void promptFirstName() { System.out.println("Please, enter member's firstname: ");   }
    public void promptSurName() { System.out.println("Please, enter member's surname: ");   }
    public void promptSocialNumber() { System.out.println("Please, enter member's social security number (12 digits): ");   }
    public void promptPassword() {System.out.println("Please, enter member's chosen password: ");    }
    public void findMember() {System.out.println("Please, enter memberID: ");    }
    public void confirmRemoveMember(Users member) { System.out.print("Are you sure you want to remove " + member.getFullName() + ", social security number: " + member.getSocialNumber() + "?(yes/no)"); }
    public void memberRemoved(){System.out.println("Member has been removed from the database and associated boats");}
    public void firstNameUpdate(){System.out.println("Please, enter member's new first name: ");}
    public void secondNameUpdate(){System.out.println("Please, enter member's new second name: ");}
    public void passwordUpdate(){System.out.println("Please, enter member's new password: ");}
    public void memberUpdated(){System.out.println("Member has been updated");}
    public void enterRegNumber() { System.out.print("Please enter the boat's registration number: ");    }
    public void hasRegNumber() {System.out.print("Does the boat have registration number yes/no: ");    }
    public void confirmRemoveBoat() { System.out.println("Are you sure you want to remove this boat from the registry? (yes/no))"); }
    public void exitOption() { System.out.println("You can  enter \"x\" when prompted for input to return to main menu");   }
    public void likeToUpdate() { System.out.println("Would you like to update the member? (yes/no) ");}
    public  void hasLength(){System.out.println("Would you like to update the length too? (yes/no) ");}
    //Part of grade 4
    //public void loggedInMessage(String fullName) {System.out.println("You Are Logged In! Welcome " + fullName);}
    //public void loggedOutMessage(String fullName) {System.out.println("You are logged out. Thank you " + fullName + " for using Jolly Pirate booking system."); }




    //Control messages #######################
     //Those two are for switching the language/view
    public String getViewType() {return this.viewType;}
    public String inputConfirmation()  {
        String uInput = getInput();
        Input input = new InputFactory().getInput(getViewType());
        return input.inputConfirmation(uInput);
    }
        //Handling inputs
    public double enterLength() {
        System.out.print("Please enter the boats length: ");
        double length = input.nextDouble();
        if(length <1 || length>20)
            throw  new InputMismatchException();
        return length; }

    public String getInput(){
        return input.next();
    }

    public boolean confirm() throws InputNotInListException {
        String input = getInput();
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("já") )
            return true;
        if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nei"))
           return false;
        else
            throw new InputNotInListException("");
    }

         //Printing informations about users and boats
    public void compactList(Users users) {
        try {
            System.out.println(users.getFullName() + " UserID: " + users.getLogin().getUserID() + " Number of boats: " + users.returnBoats().length);
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " UserID: " + users.getLogin().getUserID() +  " Number of boats: User has no registered boats.");
            bar();
        }
    }

    public void verboseList(Users users) {
        try {
            Boat[] list = users.returnBoats();
            System.out.println(users.getFullName() + " UserID: " + users.getLogin().getUserID() + " Social Security Number: " + users.getSocialNumber() + " Number of boats: " + users.returnBoats().length + "Members total fee: " + users.getFee().getTotalFee() + " kr.")  ;
            for (Boat boat : list) {
                boatInfo(boat);
            }
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " UserID: " + users.getLogin().getUserID() + " Social Security Number: " + users.getSocialNumber() +  "Members total fee: " + users.getFee().getTotalFee() + " kr." + " Number of boats: User has no registered boats.");
            bar();
        }
    }

    public void boatInfo(Boat boat){
        System.out.println("    - Boat type: " + boat.getType() + " Boat length: " + boat.getLength() + " Boat registration number: " + boat.getRegNumber() + " Boat owner: " + boat.getOwner().getFullName() + " Boat located at berth number : " +  boat.getLoacation());
    }


    //Option messages ##########################
    // TODO: 2020-08-28 Create non-login options
    //public void nonLoginOptions(){ Part of grade 4
        //System.out.print("List of options if not logged in");
    //}

    public void changeView() {
        System.out.print("1. English \n" +
                "2. Icelandic \n");
    }
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
                "10. Change the language/view \n" +
                "11. Exit program \n");

    }

    public void updateMember(Users member){
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
    public void wrongInput() { System.out.println("Your input is not an option, please try again."); }
    public void userAlreadyInDB() {System.out.println("The user is already registered member at Jolly Pirate") ;  }
    public void socialFormat(){System.out.println("Social security number(SSN) has a wrong format, use YYYYDDMMXXXX. Enter new SSN");}
    public void nameFormat(){System.out.println("Names can not include non-characters");}
    public void memberNotFound(){System.out.println("This membersID is not in the database");}
    public void credFailure(){System.out.println("Credentials do not match or this membersID is not in the database. ");}
    public void noMemberRegistered(){System.out.println("There is no member registered in the database");}
    public void noBerths() {System.out.println("There are no berths available");   }
    public void noBoatsReg() {System.out.println("There are no boats registered at Jolly Pirate");   }
    public void boatNotFound() {System.out.println("A boat with this registration number was not found");   }
    public void lengthError() { System.out.println("We do NOT register boats under 1 meter or over 20 meters"); }
    public void boatAlreadyInRegistry() {System.out.println("The boat with this registration number is already in the database ");  }


}
