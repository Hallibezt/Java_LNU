package view;
import model.Boat;
import model.Member;
import model.Price;

public class MainView extends  ErrorHandling{

    public void welcome() {System.out.print("Welcome to Jolly Pirate Yacht Club! \n" + "Please choose from list: \n"); }

    //Common messages  ##############################
    public void bar() { System.out.print("\n =============================================================================== \n"); }

    public void closingProgram() {
        System.out.println("Closing program.........");
    }

    public void programClosed() {
        System.out.println("Program closed.");
    }

    public void memberRegistered(String name, String userName) { System.out.println(name + " has been registered to database and the username is: " + userName); }

    public void memberNotFound(){System.out.println("No member found in the registry");}

    public void boatRegistered(Boat boat) { System.out.print(" The boat has been registered to the database and the registration number is: " + boat.getRegNumber() + ", and it is located at berth number: " + boat.getLocation() + "."); bar(); }

    public void boatUpdated(Price newPrice) {  System.out.println(" The boat has been updated and the price added to the fee was: " + newPrice.getPrice() + " kr."); }

    public void promptFirstName() {
        System.out.println("Please, enter member's firstname: ");
    }

    public void promptSurName() {   System.out.println("Please, enter member's surname: ");   }

    public void promptSocialNumber() { System.out.println("Please, enter member's social security number (12 digits): ");}

    public void confirmRemoveMember(Member member) { System.out.print("Are you sure you want to remove " + member.getFullName() + ", social security number: " + member.getSocialNumber() + "?(yes/no)"); }

    public void memberRemoved() { System.out.println("Member has been removed from the database and associated boats"); }

    public void memberUpdated() {
        System.out.println("Member has been updated");
    }

    public void hasRegNumber() {
        System.out.print("Does the boat have registration number yes/no: ");
    }

    public void confirmRemoveBoat() {  System.out.println("Are you sure you want to remove this boat from the registry? (yes/no))"); }

    public void exitOption() { System.out.println("You can  enter \"x\" when prompted for input to return to main menu"); }

    public void likeToUpdate() {
        System.out.println("Would you like to update the member? (yes/no) ");
    }

    public void updateLength() {
        System.out.println("Would you like to update the length too? (yes/no) ");
    }

    public void acceptPrice(double price) {System.out.println("The price for this booking is: " + price + " kr and you fee will be updated accordingly.\n " + "Do you accept? (yes/no) "); }

    public void noBoatRegistered() {
        System.out.println("No boat was registered.");
    }

    public void memberNotOwnBoat() {System.out.println("This member does not own the boat - editing NOT allowed");    }

    public void boatRemoved() { System.out.println("The boat has been removed"); }

    public void needCredentialsWarning() { System.out.println("Attention - You will need the boat's owner credentials to remove or edit it.");}

    //Part of grade 4
    //public void loggedInMessage(String fullName) {System.out.println("You Are Logged In! Welcome " + fullName);}
    //public void loggedOutMessage(String fullName) {System.out.println("You are logged out. Thank you " + fullName + " for using Jolly Pirate booking system."); }

    //
    //Printing information about users and boats
    public void compactList(Member user) {
        System.out.println(user.getFullName() + ", UserID: " + user.getCredentials().getUserID() + ", Number of boats: " + user.getMemberBoats().length +".");
    }

    public void verboseList(Member user) {
        Boat[] list = user.getMemberBoats();
        System.out.print(user.getFullName() + " UserID: " + user.getCredentials().getUserID() + " Social Security Number: " + user.getSocialNumber() + " Number of boats: " + user.getMemberBoats().length + " Members total fee: " + user.getFee().getTotalFee() + " kr. \n" + "  Boat info: ")  ;
        if(user.getMemberBoats().length == 0)
            noBoatsAreReg();
        for (Boat boat : list) {
            boatInfo(boat);
        }
    }

    public void boatInfo(Boat boat){ System.out.print("\n   - Boat type: " + boat.getType() + ", Boat length: " + boat.getLength() + ", Boat registration number: " + boat.getRegNumber() + ", Boat located at berth number : " +  boat.getLocation() + "."); }

    //Option messages ##########################

    //public void nonLoginOptions(){ Part of grade 4
    //System.out.print("List of options if not logged in");
    //}

    public void loginOptions() {
        System.out.print("\n1. Create a member \n" +
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

    public void updateMember(Member member){
        bar();
        System.out.println("Member to update: ");
        compactList(member);
        System.out.print("\n1. Update member's first name \n" +
                        "2. Update member's second name\n" +
                        "3. Change member's password\n" +
                        "4. Register a boat\n" +
                        "5. Remove a boat\n" +
                        "6. Return to main menu\n");
    }

    public void listTypes() {
        System.out.print("What is the type of the boat?\n" +
            "\n1. Motorsailor \n" +
            "2. Sailboat \n" +
            "3. Kayak/Canoe\n" +
            "4. Other \n" );
    }

    public  void boatOptions(){
        System.out.print("What would you like to update: \n" +
                "\n1. Type \n" +
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
}
