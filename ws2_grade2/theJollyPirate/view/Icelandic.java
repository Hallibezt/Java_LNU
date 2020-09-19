package view;

import controller.exceptions_errors.InputNotInListException;
import model.boats.Boat;
import model.roles.Users;
import view.inputs.Input;
import view.inputs.InputFactory;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Icelandic extends Mainview{
    private Scanner input = new Scanner(System.in);
    private String viewType = "Icelandic";


    public void welcome(){
        System.out.print("Velkominn í Jolly Pirate skútuklúbbinn! \n" +
                "Vinsamlegast veljið : \n" );
    }

    //Common messages  ##############################
    public void bar() {System.out.print("\n =============================================================================== \n");}
    public void closingProgram( ) {System.out.println("Loka forriti.........");}
    public void programClosed( ) {System.out.println("Forrit lokað.");}
    public void memberRegistered( ) {System.out.print(" hefur verið skráð/ur í gagnabankann og notendanafnið er: ");}
    public void boatRegistered( ) {System.out.print(" Báturinn hefur verið skráð/ur í gagnabankann og skráningarnúmerið er: ");}
    public void promptFirstName() { System.out.println("Vinsamlegast skráðu inn fornafn meðlimsins: ");   }
    public void promptSurName() { System.out.println("Vinsamlegast skráðu inn eftirnafn meðlimsins: ");   }
    public void promptSocialNumber() { System.out.println("Vinsamlegast skráðu inn kennitölu meðlimsins (12 stafir): ");   }
    public void promptPassword() {System.out.println("Vinsamlegast skráðu inn valið leyniorð meðlimsins: ");    }
    public void findMember() {System.out.println("Vinsamlegast skráðu inn notendanafn: ");    }
    public void confirmRemoveMember(Users member) { System.out.print("Ertu viss um að þú viljir fjarlæga " + member.getFullName() + ", kennitala: " + member.getSocialNumber() + "? (já/nei): "); }
    public void memberRemoved(){System.out.println("Meðlimur hefur verið fjarlægður úr gagnabankanum og bátar tengdir honum");}
    public void firstNameUpdate(){System.out.println("Vinsamlegast skráðu inn nýtt fornafn meðlimsins: ");}
    public void secondNameUpdate(){System.out.println("Vinsamlegast skráðu inn nýtt eftirnafn meðlimsins: ");}
    public void passwordUpdate(){System.out.println("Vinsamlegast skráðu inn nýtt leyniorð meðlimsins:: ");}
    public void memberUpdated(){System.out.println("Meðlimurinn hefur verið uppfærður");}
    public void enterRegNumber() { System.out.print("Vinsamlegast skráðu inn skráningarnúmer bátsins: ");    }
    public void hasRegNumber() {System.out.print("Hefur báturinn skráningarnúmer já/nei: ");    }
    public void confirmRemoveBoat() { System.out.println("Ertu viss um að þú viljir fjarlǽga þennan bát úr gagnagrunninum? (já/nei) "); }
    public void exitOption() { System.out.println("Þú getur slegið inn  \"x\" til þess að fara aftur á forsíðu");   }
    public void likeToUpdate() { System.out.println("Viltu uppfæra meðlim? (já/nei) ");}
    public  void hasLength(){System.out.println("Would you like to update the length too? (yes/no) ");}
    public void acceptPrice(double price) {System.out.println("The price for this booking is: " + price + " kr. Do you accept? (yes/no) "); }
    public void noBoatRegistered() {System.out.println("You did not accept the price and no boat is registered."); }
    //Part of grade 4
    //public void loggedInMessage(String fullName) {System.out.println("Þú ert innskráð/ur! Velkomin/n " + fullName); }
    //public void loggedOutMessage(String fullName) {System.out.println("Þú ert útskráð/ur. Takk fyrir " + fullName + " að nota Jolly Pirate bókunnar kerfið."); }





    //Control messages #######################
        //Those two are for switching the language/view
    public String getViewType() {
        return this.viewType;
    }
    public String inputConfirmation()  {
        String uInput = getInput();
        Input input = new InputFactory().getInput(getViewType());
        return input.inputConfirmation(uInput);
    }

    public double enterLength() {
        System.out.print("Vinsamlegast skráðu inn lengd bátsins: ");
        double length = input.nextDouble();
        if(length <1 || length>20)
            throw  new InputMismatchException();
        return length;
    }
        //Handling inputs
    public String getInput(){
        return input.next();
    }

    public boolean confirm() throws InputNotInListException {
        String input = getInput();
        if(!input.equalsIgnoreCase("yes") || !input.equalsIgnoreCase("já") || !input.equalsIgnoreCase("no") || !input.equalsIgnoreCase("yes"))
            throw new InputNotInListException("");
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("já") )
            return true;
        else
            return false;
    }

         //Printing informations about users and boats
    public void compactList(Users users) {
        try {
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Fjöldi báta: " + users.returnBoats().length);
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID()  + " Fjöldi báta: Notandi er ekki með skráða báta.");
            bar();
        }
    }




    public void verboseList(Users users) {
        try {
            Boat[] list = users.returnBoats();
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Kennitala: " + users.getSocialNumber() + " Fjöldi báta: " + users.returnBoats().length + "Heildargjöld meðlims: " + users.getFee().getTotalFee() + " kr.");
            for (Boat boat : list) {
                boatInfo(boat);
            }
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Kennitala: " + users.getSocialNumber() +  "Heildagjöld meðlims: " + users.getFee().getTotalFee() + " kr." + " Fjöldi báta: Notandi er ekki með skráða báta.");
            bar();
        }
    }
    public void boatInfo(Boat boat){
        System.out.println("Tegund báts: " + boat.getType() + " Lengd báts: " + boat.getLength() + " Skráningarnúmer báts: " + boat.getRegNumber() + "Eigandi báts: " + boat.getOwner().getFullName() + " Bátur er staðsettur í stæði : " +  boat.getLoacation());
    }

    //Option messages ##########################
    // TODO: 2020-08-28 Create non-login options
    //public void nonLoginOptions(){ Part of grade 4
        //System.out.print("List of options if not logged in");
    //}
    public void changeView() {
        System.out.print("I. Íslenska \n" +
                "J. Enska \n");
    }

    public void loginOptions() {
        System.out.print("A. Loka forriti\n" +
                "B. Breyta um tungumál \n" +
                "C. Leita að meðlim eða bátum \n" +
                "D. Skoða lista yfir meðlimi \n" +
                "E. Uppfæra bát\n" +
                "F. Eyða bát \n" +
                "G. Skrá bát\n" +
                "H. Finna einn meðlim \n" +
                "I.  Uppfæra meðlim \n" +
                "J. Fjarlægja meðlim \n" +
                "K. Skrá meðlim \n");

    }

    public void updateMember(Users member){
        System.out.println("Meðlimur til að uppfæra: ");
        compactList(member);
        bar();
        System.out.print("E. Aftur á forsíðu \n" +
                "F. Fjarlæga bát\n" +
                "G. Skrá bát\n" +
                "H. Breyta leyniorði \n" +
                "J. Uppfæra eftirnafn\n" +
                "K. Uppfæra fornafn\n");
    }

    public void listTypes() {
        System.out.print("Hvaða tegund er báturinn \n" +
                "F. Aftur á forsíðu \n" +
                "G. Annað \n" +
                "H. Kajak/Kanó \n" +
                "J. Seglskúta \n" +
                "K. Mótorbátur \n");
     }

    public  void boatOptions(){System.out.print("What would you like to update: \n" +
            "1. Type \n" +
            "2. Length \n" +
            "3. Back to main menu\n");}

    //Part of grade 4
    //public void searchMenu() {
      //  bar();
        //System.out.println("Velkomin í meðlimaleit Jolly Pirate \n" +
          //      "sláðu inn leitarorð. Notaðu \"?\" til þess að sleppa leitarvalmöguleika \n");
    //}
    //public void nameCriteria(){System.out.println("Sláðu inn nafn eða hluta úr nafni: "); }
    //public void ageCriteria(){System.out.println("Sláðu inn aldur - getur notað ójöfnu merki (t.d. <=18 leitar að undir eða jafnt og 18): ");}
    //public void monthCriteria(){System.out.println("Sláðu inn fæðingarmánuð meðlims: ");}
    //public void boatTypeCriteria(){System.out.println("Sláðu inn tegund báts: ");}
    //public void noSearchResult() {System.out.println("Engar leitarniðurstöður samræmdust leitarviðmið "); }
    //public void loginFailure() { System.out.println("Notandi fannst ekki. Vinsamlegast reynið aftur eða notist við valmöguleika óskráðra"); }


    //Error messages ################################
    public void wrongInput() { System.out.println("Þetta er ekki valmöguleiki, vinsamlegast reynið aftur"); }
    public void userAlreadyInDB() {System.out.println("Notandinn er þegar meðlimur í Jolly Pirate") ;  }
    public void socialFormat(){System.out.println("Kennitalan er röng, notist við AAAADDMMXXXX. Sláið inn nýja kennitölu");}
    public void nameFormat(){System.out.println("Nöfn geta bara innihaldið bókstafi");}
    public void memberNotFound(){System.out.println("Þetta notendanafn finnst ekki í gagnagrunninum");}
    public void credFailure(){System.out.println("Auðkenni stemma ekki eða meðlimur fannst ekki.");}
    public void noMemberRegistered(){System.out.println("Það er enginn meðlimur skráður í gagnagrunninum.");}
    public void noBerths() {System.out.println("Engin bátastæði laus.");   }
    public void noBoatsReg() {System.out.println("Engir bátar skráði hjá Jolly Pirate");   }
    public void boatNotFound() {System.out.println("Fannst enginn bátur með þetta skráningarnúmer");   }
    public void lengthError() { System.out.println("Við skráum EKKI báta undir 1 m. eða yfir 20 m."); }
    public void boatAlreadyInRegistry() {System.out.println("Bátur með þetta skráningarnúmer er þegar í gagnagrunninum ");  }


}
