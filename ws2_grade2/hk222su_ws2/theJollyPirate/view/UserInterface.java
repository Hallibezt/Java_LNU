package view;

import model.enums.BoatType;
import model.enums.MainOption;
import model.enums.UpdateBoatOption;
import model.enums.UpdateOption;
import controller.exceptions.InputNotInListException;
import controller.exceptions.WrongFormatException;
import model.Member;
import java.util.Scanner;

public  class UserInterface extends MainView {
   public Scanner input = new Scanner(System.in);

   //Control messages #######################

   public BoatType getBoatType() {
    listTypes();
    BoatType boatType;
    //Get the type and handle input error
    String input = getInput();
    if(!input.matches("\\d+"))
     input = "0";
    int i = Integer.parseInt(input);
    switch (i) {
     case 1: boatType = BoatType.Motorsailer;
      break;
     case 2: boatType =  BoatType.Sailboat;
      break;
     case 3: boatType =  BoatType.Kayak_Canoe;
      break;
     case 4: boatType =  BoatType.Other;
      break;
     default: boatType = BoatType.WrongInput;
      break;
    }
    return boatType;
   }

   public UpdateOption showUpdateMenu(Member member) {
    updateMember(member);
    UpdateOption updateOption;
    String input = getInput();
    if(!input.matches("\\d+"))
     input = "0";
    int i = Integer.parseInt(input);
      switch (i) {
       case 1: updateOption = UpdateOption.FirstName;
        break;
       case 2: updateOption = UpdateOption.SecondName;
        break;
       case 3: updateOption = UpdateOption.Password;
        break;
       case 4: updateOption = UpdateOption.RegisterBoat;
        break;
       case 5: updateOption = UpdateOption.RemoveBoat;
        break;
       case 6: updateOption = UpdateOption.Exit;
        break;
       default: updateOption = UpdateOption.WrongInput;
        break;
      }
       return updateOption;
   }


   public UpdateBoatOption showUpdateBoatMenu()  {
    UpdateBoatOption boatOption;
    boatOptions();
    String input = getInput();
    if(!input.matches("\\d+"))
     input = "0";
    int i = Integer.parseInt(input);
    switch (i) {
     case 1:boatOption = UpdateBoatOption.Type;
      break;
     case 2:boatOption = UpdateBoatOption.Length;
      break;
     case 3: boatOption = UpdateBoatOption.Exit;
      break;
     default: boatOption = UpdateBoatOption.WrongInput;
      wrongInput();
      break;
    }
    return boatOption;
   }


   public MainOption showMainMenu() {
    MainOption option;
    loginOptions();
    String input = getInput();
    if(!input.matches("\\d+"))
     input = "0";
    int i = Integer.parseInt(input);
    switch (i) {
     case 1: option = MainOption.CreateMember;
      break;
     case 2: option = MainOption.RemoveMember;
      break;
     case 3: option = MainOption.editMember;
      break;
     case 4: option = MainOption.FindMember;
      break;
     case 5: option = MainOption.RegisterBoat;
      break;
     case 6: option = MainOption.RemoveBoat;
      break;
     case 7: option = MainOption.EditBoat;
      break;
     case 8: option = MainOption.Compact;
      break;
     case 9: option =  MainOption.Verbose;
      break;
     case 10: option = MainOption.Exit;
      break;
     default: option = MainOption.WrongIn;
      wrongInput();
      break;
    }
    return option;
   }


   //Handling inputs
   public String getInput(){
    return input.next();
   }

   public double enterLength()  {
    System.out.println("Please enter the boats length (or 0 to cancel): ");
    return input.nextDouble();
  }

   public double lengthError(double newLength) {
    System.out.println("We do NOT register boats under 1 meter or over 20 meters");
    while (newLength < 1 || newLength > 20) {
     newLength = enterLength();
     if(newLength == 0)
      break;
    }
    return newLength;
   }

   public String enterRegNumber() {
    System.out.print("Please enter the boat's registration number (case sensitive): ");
    return getInput();
   }

   public boolean cancelAndReturnToMainMenu(String firstName) {
    return firstName.equalsIgnoreCase("x");
   }


   public boolean confirm() throws InputNotInListException {
    String input = getInput();
    if(input.equalsIgnoreCase("yes"))
     return true;
    if(input.equalsIgnoreCase("no"))
     return false;
    else
     throw new InputNotInListException("");
   }


   public String getName() throws WrongFormatException {
     String name = getInput();
     if (notCorrectFormatName(name))
      throw new WrongFormatException("");
     return name;
   }

   public String getSocialNumber() throws WrongFormatException {
    String socialNumber = getInput();
    if (!notCorrectFormatNameSocial(socialNumber))
     throw new WrongFormatException("");
    return socialNumber;
   }

   public String firstNameUpdate()  {
    try {
     System.out.println("Please, enter member's new first name: ");
     return getName();
    }
    catch (WrongFormatException e){
     nameOrSocialFormatError();
    }
    return null;
   }

   public String secondNameUpdate() {
    try{
     System.out.println("Please, enter member's new second name: ");
     return getName();
    }
    catch (WrongFormatException e){
     nameOrSocialFormatError();
    }
    return null;
   }

   public String passwordUpdate() {
    System.out.println("Please, enter member's new password: ");
    return getInput();
   }

   public Member getMemberInfo()  {
    Member member = null;
    String firstName;
    String surName;
    String socialNumber;
    String password;
    try{
     exitOption();
     promptFirstName();
     firstName = getName();
     if(cancelAndReturnToMainMenu(firstName))
      return null;
     else
      promptSurName();
     surName = getName();
     if(cancelAndReturnToMainMenu(firstName))
      return null;
     else
      promptSocialNumber();
     socialNumber = getSocialNumber();
     if(cancelAndReturnToMainMenu(socialNumber))
      return null;
     else
       password = promptPassword();
     if(cancelAndReturnToMainMenu(password))
      return null;
     else
      member = new Member(firstName, surName, socialNumber,password);}
    catch (WrongFormatException e){
     nameOrSocialFormatError();
    }
    return member;
   }

   public String[] findMember() {
    String[] credentials = new String[2];
    System.out.print("Please, enter memberID: ");
    credentials[0] = getInput();
    System.out.print("Please, enter password: ");
    credentials[1] = getInput();
    return credentials;
   }

   public String findMemberJustID() {
    System.out.print("Please, enter memberID: ");
    return getInput();
   }

   public String promptPassword() {
    System.out.println("Please, enter member's chosen password: ");
    return getInput();
   }

}
