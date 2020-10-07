Workshop 2 in 1dv607

Points that we know are not finished
1. The assignment is not completely finished. There is a working menu structure, although there is not much error handling.
	The program crashes on String input in menu choices.
2. Right now there is no persistence. There is a hardcoded placeholder method, in the persistence class, that is creating members with a random number of boats.
3. The incapsulation is not complete. There are places in the code where incapsulation are broken.
4. The code is missing JavaDoc and general comments. Sorry! :(

These points are things that we intend to fix before final hand-in.

Structure of the program
The controller package is dependant on the model and view packages.
MenuController handles the flow of the menu and MemberController is the main controller of the program.
Every Enum in MenuController has a set of methods that handles the actions of each menu option and returns next sub-menu to be displayed.
Persistence is planned to handle an XML document where the members are stored. The boats of each member is supposed to be stored within the member.

Run instructions
1. Install Java 14
2. Open terminal window
3. cd into workshop_2.jar
4. Run with command java -jar workshop_2.jar
5. Follow instructions in the program

For Windows
Update Runtime Environment variables for Java 14 or higher
Then go to step 2
