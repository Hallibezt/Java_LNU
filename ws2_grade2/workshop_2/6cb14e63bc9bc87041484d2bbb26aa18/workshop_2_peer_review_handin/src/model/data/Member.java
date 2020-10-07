package model.data;

import java.util.ArrayList;

public class Member {

    private String firstName;
    private String lastName;
    private int personalID;
    private int memberID;
    private ArrayList<Boat> boatList = new ArrayList<>();

    public Member() {
    }

    public Member(String firstName, String lastName, int aPersonalID, int aMemberID) {
        setFirstName(firstName);
        setLastName(lastName);
        setPersonalID(aPersonalID);
        setMemberID(aMemberID);
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPersonalID() {
        return personalID;
    }

    public void setPersonalID(int personalID) {
        this.personalID = personalID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public void addBoat(Boat aBoat) {
        this.boatList.add(aBoat);
    }

    public ArrayList<Boat> getBoats() {
        return this.boatList;
    }

    public String[] getAllBoatsInfo() {
        ArrayList<String> boatInfo = new ArrayList<>();
        for (Boat b: boatList)
        boatInfo.add(b.getBoatInfo());
        return boatInfo.toArray(String[]::new);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Name: " + getName() + "\n");
        str.append("Personal ID: " + getPersonalID() + "\n");
        str.append("Member ID: " + getMemberID());

        return str.toString();
    }

}
