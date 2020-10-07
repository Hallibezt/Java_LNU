package controller;

import java.util.ArrayList;

import model.Boat;
import model.BoatType;
import model.Member;
import model.Registry;
import view.NavigationNode;
import view.Terminal;

public class Controller {

	private Registry reg;
	private Terminal term;
	private NavigationNode selectedLeaf = null;
	private NavigationNode current = null;
	private NavigationNode root = null;

	public Controller(Registry reg, Terminal term) {
		this.reg = reg;
		this.term = term;
	}

	// ACTIONHANDLER
	public void performAction(String leaf) {
		Boat selectedBoat = null;
		Member selectedMember = null;
		switch (leaf) {
			case "register_member" :
				createMember();
				break;
			case "register_boat" :
				selectedMember = selectMember();
				createBoat(selectedMember);
				break;
			case "list_compact" :
				listCompact();
				break;
			case "list_verbose" :
				listVerbose();
				break;
			case "show_memberinfo":
				selectedMember = selectMember();
				term.printMemberVerbose(selectedMember);
				break;
			case "update_member_name" :
				selectMember().setName(term.getMemberName());
				break;
			case "update_member_personalnumber" :
				selectMember().setPersonalNumber(term.getPersonalNumber());
				break;
			case "update_member_password" :
				selectMember().setPassword(term.getPassword());
				break;
			case "update_boat_type" :
				selectedMember = selectMember();
				selectedBoat = selectBoat(selectedMember);
				selectedBoat.setType(term.getBoatType());
				break;
			case "update_boat_size" :
				selectedMember = selectMember();
				selectedBoat = selectBoat(selectedMember);
				selectedBoat.setSize(term.getBoatSize());
				break;
			case "delete_member" :
				// Test if change would work. selectedMember() instead ;
				selectedMember = selectMember();
				reg.deleteMember(selectedMember);
				break;
			case "delete_boat" :
				selectedMember = selectMember();
				selectedBoat = selectBoat(selectedMember);
				selectedMember.removeBoat(selectedBoat);
				break;
			default :
				// Exit enter default
				break;
		}
		selectedLeaf = null;
		reg.save();
	}
	private void createMember() {
		String name = term.getMemberName();
		String pn = term.getPersonalNumber();
		String pass = term.getPassword();
		reg.createMember(name, pn, pass);
	}
	private void createBoat(Member mem) {
		BoatType type = term.getBoatType();
		double size = term.getBoatSize();
		mem.addBoatToMember(new Boat(type, size));
	}
	private Member selectMember() {
		ArrayList<Member> mems = (ArrayList<Member>) reg.getMembers();
		int index = term.getSelectionIndex(mems);
		return mems.get(index);
	}
	private Boat selectBoat(Member mem) {
		ArrayList<Boat> boats = (ArrayList<Boat>) mem.getBoats();
		int index = term.getSelectionIndex(boats);
		return boats.get(index);
	}


	private void listCompact() {
		for ( Member mem : reg.getMembers() ) {
			term.printMemberCompact(mem);
		}
	}
	private void listVerbose() {
		for ( Member mem : reg.getMembers() ) {
			term.printMemberVerbose(mem);
		}
	}

	public void boot() {
		createMenu();
	}

	// NAVIGATIONHANDLER
	public void navigate() {
		ArrayList<Character> validChoices = new ArrayList<Character>();
		Character c;
		// Check if "Go back" is existent
		if (current.getParent() != null) {
			validChoices.add('b');
		}
		// Create navigational options
		for (int i = 0; i < current.getChildren().size(); i++) {
			int j = i + 1;
			c = Character.forDigit(j, 10);
			validChoices.add(c);
		}
		// Print menu with options
		term.printNavigationNode(current);
		term.printValidCharacters(validChoices);

		// Get input
		c = term.getValidCharacter(validChoices);

		// Go back
		if (c == 'b') {
			current = current.getParent();
		} else {
			int index = Character.getNumericValue(c);
			current = current.getChildren().get(index - 1);
		}
		// Check if user reached a leaf (action)
		if (current.getChildren() == null) {
			selectedLeaf = current;
			current = root;
		}
	}
	public NavigationNode getCurrentNavigationNode() {
		return current;
	}
	public NavigationNode getLeafNavigationNode() {
		return selectedLeaf;
	}
	private void createMenu() {
		NavigationNode main = new NavigationNode("root", null,
				new ArrayList<NavigationNode>());

		NavigationNode register = new NavigationNode("register", main,
				new ArrayList<NavigationNode>());
		NavigationNode list = new NavigationNode("list", main,
				new ArrayList<NavigationNode>());
		NavigationNode update = new NavigationNode("update", main,
				new ArrayList<NavigationNode>());
		NavigationNode delete = new NavigationNode("delete", main,
				new ArrayList<NavigationNode>());
		NavigationNode exit = new NavigationNode("exit", main, null);
		main.addChild(register);
		main.addChild(list);
		main.addChild(update);
		main.addChild(delete);
		main.addChild(exit);

		NavigationNode registerMember = new NavigationNode("register_member",
				register, null);
		NavigationNode registerBoat = new NavigationNode("register_boat",
				register, null);
		register.addChild(registerMember);
		register.addChild(registerBoat);

		NavigationNode listCompact = new NavigationNode("list_compact", list,
				null);
		NavigationNode listVerbose = new NavigationNode("list_verbose", list,
				null);
		NavigationNode showMemberInfo = new NavigationNode("show_memberinfo", list,
				null);
		list.addChild(listCompact);
		list.addChild(listVerbose);
		list.addChild(showMemberInfo);

		NavigationNode updateMember = new NavigationNode("update_member",
				update, new ArrayList<NavigationNode>());
		update.addChild(updateMember);

		NavigationNode updateMemberName = new NavigationNode(
				"update_member_name", updateMember, null);
		NavigationNode updateMemberPersonalnumber = new NavigationNode(
				"update_member_personalnumber", updateMember, null);
		NavigationNode updateMemberPassword = new NavigationNode(
				"update_member_password", updateMember, null);
		updateMember.addChild(updateMemberName);
		updateMember.addChild(updateMemberPersonalnumber);
		updateMember.addChild(updateMemberPassword);

		NavigationNode updateBoat = new NavigationNode("update_boat", update,
				new ArrayList<NavigationNode>());
		update.addChild(updateBoat);

		NavigationNode updateBoatType = new NavigationNode("update_boat_type",
				updateBoat, null);
		NavigationNode updateBoatSize = new NavigationNode("update_boat_size",
				updateBoat, null);
		updateBoat.addChild(updateBoatType);
		updateBoat.addChild(updateBoatSize);

		NavigationNode deleteMember = new NavigationNode("delete_member",
				delete, null);
		NavigationNode deleteBoat = new NavigationNode("delete_boat", delete,
				null);
		delete.addChild(deleteMember);
		delete.addChild(deleteBoat);

		root = main;
		current = main;
	}


}
