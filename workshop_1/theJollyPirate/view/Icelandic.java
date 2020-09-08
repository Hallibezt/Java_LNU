package view;

import model.Login;
import model.boats.Boat;
import model.roles.Users;
import view.inputs.Input;
import view.inputs.InputFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Icelandic extends Mainview{
    Scanner input = new Scanner(System.in);

    public void welcome(){
        System.out.print("Velkominn í Jolly Pirate skútuklúbbinn! \n" +
                "Vinsamlegast veljið : \n" +
                "A. Innskráning: Til þess að bóka eða breyta meðlimaupplýsingum \n" +
                "B. Án innskráningar: leit og uppflettingar eingöngu \n" +
                "C. Loka forritinu \n" +
                "D. Breyta tungumálinu");
    }

    //Common messages  ##############################
    public void bar() {System.out.println("\n =============================================================================== \n");}
    public void loggedInMessage(String fullName) {
        System.out.println("Þú ert innskráð/ur! Velkomin/n " + fullName);
    }
    public void loggedOutMessage(String fullName) {System.out.println("Þú ert útskráð/ur. Takk fyrir " + fullName + " að nota Jolly Pirate bókunnar kerfið."); }
    public void closingProgram( ) {System.out.println("Loka forriti.........");}
    public void programClosed( ) {System.out.println("Forrit lokað.");}
    public void memberRegistered( ) {System.out.print(" hefur verið skráð/ur í gagnabankann og notendanafnið er: ");}
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

    @Override
    public String getViewType() {
        return "Icelandic";
    }


    //Control messages #######################
    //Check inputs for different views(numbers vs letters)
    public double enterLength() {
        System.out.print("Vinsamlegast skráðu inn lengd bátsins: ");
        double length = input.nextDouble();
        if(length <1 || length>20)
            throw  new InputMismatchException();
        return length;
    }

    //Can arrange the input so it fits the list order that the user sees
    public String inputConfirmation()  {
        String uInput = getInput();
        Input input = new InputFactory().getInput(getViewType());
        String uOutput = input.inputConfirmation(uInput);
        return uOutput;
    }
    public String getInput(){
        String uInput = input.next();
        return uInput;
    }

    public boolean confirm(){
        String input = getInput();
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("já") )
            return true;
        else
            return false;
    }

    // TODO: 2020-08-29 Might have to split this up because login is domain class


    public Login getCredentials(){
        Login login = new Login();
        System.out.print("Vinsamlegast skráðu inn notendanafn þitt og leyniorð: \n" +
                "Notendanafn:  ");
        login.addLoginUserID(getInput());
        System.out.print("Leyniorð:  ");
        login.addPassword(getInput());
        return login;
    }

    public void compactList(Users users) {
        try {
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Fjöldi báta: " + users.returnBoats().length);
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID()  + " Fjöldi báta: Notandi er ekki með skráða báta.");
            bar();
        }
    }

    @Override
    public void changeView() {
        System.out.print("A. Enska \n" +
                "B. Íslenska \n");
    }


    public void verboseList(Users users) {
        try {
            Boat[] list = users.returnBoats();
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Kennitala: " + users.getSocialNumber() + " Fjöldi báta: " + users.returnBoats().length);
            for (int i = 0; i< list.length;i++ ){
                boatInfo(list[i]);
            }
        }
        catch (NullPointerException e){
            System.out.println(users.getFullName() + " Notendanafn: " + users.getLogin().getUserID() + " Kennitala: " + users.getSocialNumber() + " Fjöldi báta: Notandi er ekki með skráða báta.");
            bar();
        }
    }
    public void boatInfo(Boat boat){
        System.out.println("Tegund báts: " + boat.getType() + " Lengd báts: " + boat.getLength() + " Skráningarnúmer báts: " + boat.getRegNumber() + "Eigandi báts: " + boat.getOwner().getFullName() + " Bátur er staðsettur í stæði : " +  boat.getLoacation());
    }
    //Option messages ##########################
    // TODO: 2020-08-28 Create non-login options
    public void nonLoginOptions(){
        System.out.print("List of options if not logged in");
    }

    public void loginOptions() {
        System.out.print("A. Útskráning\n" +
                "B. Breyta um tungumál \n" +
                "C. Leita að meðlim eða bátum \n" +
                "D. Skoða ýtarlegan lista yfir meðlimi og báta \n" +
                "E. Skoða lista yfir meðlimi \n" +
                "F. Uppfæra bát\n" +
                "G. Eyða bát \n" +
                "H. Skrá bát\n" +
                "I  Uppfæra meðlim \n" +
                "J. Fjarlægja meðlim \n" +
                "K. Skrá meðlim");

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

    @Override
    public void searchMenu() {
        bar();
        System.out.println("Velkomin í meðlimaleit Jolly Pirate \n" +
                "sláðu inn leitarorð. Notaðu \"?\" til þess að sleppa leitarvalmöguleika \n");

    }

    public void nameCriteria(){System.out.println("Sláðu inn nafn eða hluta úr nafni: "); }
    public void ageCriteria(){System.out.println("Sláðu inn aldur - getur notað ójöfnu merki (t.d. <=18 leitar að undir eða jafnt og 18): ");}
    public void monthCriteria(){System.out.println("Sláðu inn fæðingarmánuð meðlims: ");}
    public void boatTypeCriteria(){System.out.println("Sláðu inn tegund báts: ");}


    //Error messages ################################
    public void loginFailure() { System.out.println("Notandi fannst ekki. Vinsamlegast reynið aftur eða notist við valmöguleika óskráðra"); }
    public void wrongInput() { System.out.println("Þetta er ekki valmöguleiki, vinsamlegast reynið aftur"); }
    public void userAlreadyInDB() {System.out.println("Notandinn er þegar meðlimur í Jolly Pirate") ;  }
    public void socialFormat(){System.out.println("Kennitalan er röng, notist við AAAADDMMXXXX. Sláið inn nýja kennitölu");}
    public void nameFormat(){System.out.println("Nöfn geta bara innihaldið bókstafi");}
    public void memberNotFound(){System.out.println("Þetta notendanafn finnst ekki í gagnagrunninum");}
    public void credFailure(){System.out.println("Meðlimur fannst ekki eða auðkenni stemma ekki");}
    public void noMemberRegistered(){System.out.println("Það er enginn meðlimur skráður í gagnagrunninum.");}
    public void noBerths() {System.out.println("Engin bátastæði laus.");   }
    public void noBoatsReg() {System.out.println("Engir bátar skráði hjá Jolly Pirate");   }
    public void boatNotFound() {System.out.println("Fannst enginn bátur með þetta skráningarnúmer");   }
    public void lengthError() { System.out.println("Við skráum EKKI báta undir 1 m. eða yfir 20 m."); }
    public void boatAlreadyInRegistry() {System.out.println("Bátur með þetta skráningarnúmer er þegar í gagnagrunninum ");  }
    public void noSearchResult() {System.out.println("Engar leitarniðurstöður samræmdust leitarviðmið "); }


}
