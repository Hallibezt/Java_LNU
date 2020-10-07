package model;

import model.data.Boat;
import model.data.Member;

import java.util.ArrayList;

public interface IBoatClubFacade {

    public ArrayList<Member> getStoredMembers();

    public void addMember(String firstName, String lastName, int pID);
    public Member getMember(int memberID);
    public void changeMember(int memberID, String newFirstName, String newLastName, int newPID);
    public void deleteMember(int memberID);

    public void registerBoat(Member m, Boat b);
    public void changeBoat(int boatID, Boat.BoatType newType, int newLength);
    public void deleteBoat(int boatID);
}
