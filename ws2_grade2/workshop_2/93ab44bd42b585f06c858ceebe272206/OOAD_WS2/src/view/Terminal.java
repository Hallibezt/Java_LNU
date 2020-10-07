package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Boat;
import model.BoatType;
import model.Member;

public class Terminal {

	// INPUT
	public String getMemberName() {
		clear();
		System.out.println("Enter your name: ");
		String name = "";
		name = readLine();
		return name;
	}

	public String getPersonalNumber() {
		clear();
		System.out.println("Enter your personal number (YYMMDDXXXX): ");
		String name = "";
		name = readLine();
		return name;
	}

	public String getPassword() {
		clear();
		System.out.println("Enter your password: ");
		String name = "";
		name = readLine();
		return name;
	}

	public Character getValidCharacter(ArrayList<Character> validChars) {
		Character c;
		// Trap until you get a valid character
		do {
			System.out.println("Enter option: ");
			c = getInputCharacter();
		} while (!isValidCharacter(c, validChars));
		return c;
	}

	public BoatType getBoatType() {
		clear();
		System.out.println("Pick a type: ");
		for (BoatType value : BoatType.values()) {
			System.out.println(value.getText());
		}
		boolean correctInput = true;
		while (true) {
			if (!correctInput) {
				System.out.println("Enter a valid type: ");
			}
			String input = readLine();
			for (BoatType value : BoatType.values()) {
				if (value.name().toLowerCase().equals(input)) {
					return value;
				}
			}
			correctInput = false;
		}
	}
	public double getBoatSize() {
		clear();
		System.out.println("Enter the boat's size in meters [i.e 1.07]: ");
		boolean isValid = false;
		double size = 0.00;
		while (!isValid) {
			try {
				size = Double.parseDouble(readLine());
				isValid = true;
			} catch (Exception e) {
				System.out.println("Invalid size, enter a double: ");
				isValid = false;
			}
		}
		return size;
	}

	public int getSelectionIndex(ArrayList<?> items) {
		ArrayList<Integer> validIntegers = new ArrayList<Integer>();
		int index = 1;
		for (Object item : items) {
			validIntegers.add(index);
			System.out.println(index + ". " + item.toString());
			index++;
		}
		boolean isValid = false;
		int selection = 0;
		while (!isValid) {
			try {
				selection = Integer.parseInt(readLine());
				isValid = validIntegers.contains(selection);
			} catch (Exception e) {
				System.out.println("Invalid input, enter a valid index: ");
				isValid = false;
			}
		}

		return --selection;

	}

	// PRINT
	public void printValidCharacters(ArrayList<Character> validChars) {
		System.out.println("----------------------------------------");
		System.out.print("Options - [");
		for (int i = 0; i < validChars.size(); i++) {
			System.out.print(validChars.get(i).toString() + " ");
		}
		System.out.print("]\n");
		System.out.println("----------------------------------------");
	}

	public void printNavigationNode(NavigationNode nn) {
		System.out.println("========================================");
		System.out.println("Current selection: " + nn.getTitle());
		System.out.println("========================================");
		if (nn.getChildren() != null) {
			int i = 1;
			for (NavigationNode n : nn.getChildren()) {
				System.out.println(i + ". " + n.getTitle());
				i++;
			}
		}
	}

	public void printMemberCompact(Member mem) {
		System.out.println("----------------------------------------");
		System.out.println("Name:\t" + mem.getName() + "\nID:\t"
				+ mem.getMemberID() + "\nBoats:\t" + mem.getBoats().size());
	}

	public void printMemberVerbose(Member mem) {
		System.out.println("----------------------------------------");
		System.out.println("Name:\t" + mem.getName() + "\nPersonal-number:\t"
				+ mem.getPersonalNumber() + "\nID\t" + mem.getMemberID());
		printMemberBoats(mem);
	}

	private void printMemberBoats(Member mem) {
		ArrayList<Boat> boats = mem.getBoats();
		System.out.println("Boats:");
		if (boats.size() == 0) {
			System.out.println("\tnone");
		}
		for (Boat boat : boats) {
			System.out.println("\t" + boat.getType().toString() + " "
					+ boat.getSize() + "meters.");
		}
	}

	// HELPERFUNCTIONS
	private String readLine() {
		String str = "";
		Scanner sc = new Scanner(System.in);
		try {
			str = sc.nextLine();
		} catch (Exception e) {
			System.out.print("You need to write something... \n");
			str = readLine();
		}
		return str;
	}

	private Character getInputCharacter() {
		char character = ' ';
		Scanner sc = new Scanner(System.in);
		try {
			character = sc.nextLine().charAt(0);
		} catch (Exception e) {
			System.out.print("You need to enter a character... \n");
			character = getInputCharacter();
		}
		return character;
	}

	private boolean isValidCharacter(Character c,
			ArrayList<Character> validChars) {
		return validChars.contains(c);
	}

	private void clear() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

}
