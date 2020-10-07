package model;

import model.data.Boat;
import model.data.Member;

import java.util.ArrayList;

public class BoatClubFacade implements IBoatClubFacade {
    private int memberIDCounter;
    private ArrayList<Member> storedMembers;
    private XMLHandler xmlHandler = new XMLHandler();

    public BoatClubFacade() {
        storedMembers = xmlHandler.readFromFile();
        memberIDCounter = storedMembers.size();
    }

    @Override
    public ArrayList<Member> getStoredMembers() {

        return storedMembers;
    }

    @Override
    public void addMember(String firstName, String lastName, int pID) {
        // TODO:
        memberIDCounter++;
        Member m = new Member(firstName, lastName, pID, memberIDCounter);
        storedMembers.add(m);
    }

    @Override
    public Member getMember(int memberID) {
        for (Member m: storedMembers) {
            if (m.getMemberID() == memberID)
                return m;
        }
        return null;
    }

    @Override
    public void changeMember(int memberID, String newFirstName, String newLastName, int newPID) {
        Member m = getMember(memberID);
        m.setFirstName(newFirstName);
        m.setLastName(newLastName);
        m.setPersonalID(newPID);
    }

    public ArrayList<Boat> getStoredBoats() {
        ArrayList<Boat> storedBoats = new ArrayList<>();
        for(Member m: storedMembers) {
            storedBoats.addAll(m.getBoats());
        }

        return storedBoats;
    }

    @Override
    public void deleteMember(int memberID) {
        storedMembers.removeIf(m -> m.getMemberID() == memberID);
    }

    @Override
    public void registerBoat(Member m, Boat b) {
        getMember(m.getMemberID()).addBoat(b);
    }

    @Override
    public void changeBoat(int boatID, Boat.BoatType newType, int newLength) {
        for (Boat b: getStoredBoats()) {
            if (b.getId() == boatID) {
                b.setType(newType);
                b.setLength(newLength);
                break;
            }
        }
    }

    @Override
    public void deleteBoat(int boatID) {
        for (Member m : storedMembers)
            m.getBoats().removeIf(b -> b.getId() == boatID);
    }
}
