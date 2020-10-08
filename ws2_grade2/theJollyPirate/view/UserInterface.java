package view;

import model.enums.BoatType;
import model.enums.MainOption;
import model.enums.UpdateBoatOption;
import model.enums.UpdateOption;
import model.exceptions_errors.InputNotInListException;
import model.exceptions_errors.WrongFormatException;
import model.Member;
import java.util.Scanner;

public  class UserInterface extends MainView {
 public Scanner input = new Scanner(System.in);

 //Control messages #######################

 public BoatType getBoatType() throws InputNotInListException {
  listTypes();
  //Get the type and handle input error
  int input = this.input.nextInt();
  switch (input) {
   case 1:
    return  BoatType.Motorsailer;
   case 2:
    return  BoatType.Sailboat;
   case 3:
    return  BoatType.Kayak_Canoe;
   case 4:
    return  BoatType.Other;
   default:
    throw new InputNotInListException("");
  }
 }

 public UpdateOption showUpdateMenu(Member member) throws InputNotInListException {
  updateMember(member);
    int i = input.nextInt();
    switch (i) {
     case 1:
      return UpdateOption.firstName;
     case 2:
      return UpdateOption.secondName;
     case 3:
      return UpdateOption.password;
     case 4:
      return UpdateOption.registerBoat;
     case 5:
      return UpdateOption.removeBoat;
     case 6:
      return UpdateOption.exit;
     default:
      throw new InputNotInListException("");
    }
 }


 public UpdateBoatOption showUpdateBoatMenu() throws InputNotInListException {
   int i = input.nextInt();
   switch (i) {
    case 1:
     return UpdateBoatOption.type;
    case 2:
     return UpdateBoatOption.length;
    case 3:
     return UpdateBoatOption.exit;
    default:
     throw new InputNotInListException("");
   }
 }


 public MainOption showMainMenu() {
  loginOptions();
  int i = input.nextInt();
  switch (i) {
   case 1:
    return MainOption.createMember;
   case 2:
    return MainOption.removeMember;
   case 3:
    return MainOption.editMember;
   case 4:
    return MainOption.findMember;
   case 5:
    return MainOption.registerBoat;
   case 6:
    return MainOption.removeBoat;
   case 7:
    return MainOption.editBoat;
   case 8:
    return MainOption.compact;
   case 9:
    return MainOption.verbose;
   case 10:
    return MainOption.exit;
   default:
   wrongInput();
    input.next();
  }
  return null;
 }


 //Handling inputs
 public double enterLength()  {
  System.out.println("Please enter the boats length: ");
  return input.nextDouble();
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


 public String getName() throws WrongFormatException {
  String name = getInput();
  if(nameFormat(name))
   throw new WrongFormatException("");
  return name;
 }

}
