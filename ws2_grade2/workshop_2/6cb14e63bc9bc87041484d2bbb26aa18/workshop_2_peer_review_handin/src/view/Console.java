package view;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {
    Scanner sc;

    public Console() {
        sc = new Scanner(System.in);
    }

    public void exitMe() {
        sc.close();
    }

    public void displayWelcomeMessage() {
        System.out.println("                __/___            \n" +
                "          _____/______|           \n" +
                "  _______/_____\\_______\\_____     \n" +
                "  \\              < < <       |    \n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello Boat World!");
        System.out.println("Enter the number of your choice in the list");
    }

    public void displayCompact(int memID, String name, int noOfBoats) {
        System.out.println("Member ID: " + memID + " | Name: " + name + " | Number of boats: " + noOfBoats);
    }

    public void displayVerbose(int memID, int pID, String name, int noOfBoats, String[] boatInfo) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("|  Member ID: " + memID + " | Personal number: " + pID + " | Name: " + name + " | Number of boats: " + noOfBoats + "  ║");
        System.out.println("-----------------");
        System.out.println("|  Boat details |");
        for (String str: boatInfo) {
            System.out.println("|\t " + str);
        }
    }

    public void displayListInstructions(String str) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(str);
    }

    public int getListChoice(ArrayList<Integer> options) {
        int input = -1;
        while (!options.contains(input)) {
            input = getIntInput();
        }

        return input;
    }

    public void displayMemberChoices(int memID, String name, int pID) {
        System.out.println(memID + " " + name + " " + pID);
    }

    public void displayText(String str) {
        System.out.println(str);
    }

    public void displayList(ArrayList<String> list, boolean zeroBasedIndex) {
        for (int i = 0; i < list.size(); i++) {
            if (zeroBasedIndex)
                System.out.println(i + ". | " + list.get(i));
            else
                System.out.println(i+1 + ". | " + list.get(i));
        }
    }

    public int getIntInput(int maxVal, int minVal) {
        int input = Integer.MIN_VALUE;

        while (input > maxVal || input < minVal)
            input = sc.nextInt();

        return input;
    }

    public String getTextInput() {
        return sc.next();
    }

    public int getIntInput() {
      return sc.nextInt();
    }
}
