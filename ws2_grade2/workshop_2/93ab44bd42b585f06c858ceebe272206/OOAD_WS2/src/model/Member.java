package model;
import java.util.ArrayList;

public class Member {
	private String name;
	private String personalNumber;
	private String memberID;
	private String password;
	private ArrayList<Boat> boats = new ArrayList<Boat>();
	
	// Added so ObjectMapper has default creator for creating Members from JSON string
	public Member() {
		
	}
	public Member(String name, String pn, String mID, String pass) {
		this.name = name;
		this.personalNumber = pn;
		this.memberID = mID;
		this.password = pass;
	}
	
	
	//vvvvvvvvvvvvvvvvvvv GETTERS SETTERS vvvvvvvvvvvvvvvvvvvvvvvvvv
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getMemberID() {
		return memberID;
	}

	public ArrayList<Boat> getBoats(){
		return boats;
	}
	
	public void addBoatToMember(Boat b) {
		//Boat boat = new Boat(type, meters);
		boats.add(b);
	}
	
	public void removeBoat(Boat b) {
		this.boats.remove(b);
	}
	
	@Override
	public String toString() {
		return name + "\t" + memberID;
	}
	
	//^^^^^^^^^^^^^^^^^^ GETTERS SETTERS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public void setPassword(String passwordInput) {
		password = passwordInput;
	}
	public String getPassword() {
		return password;
	}
}
