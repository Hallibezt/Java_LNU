package model;

import model.data.Boat;
import model.data.Member;

import java.util.ArrayList;

public class XMLHandler implements IPersistenceLayer{

    public XMLHandler() {}

    @Override
    public ArrayList<Member> readFromFile() {
        return generateTestMembers(); // Temporary
    }

    @Override
    public void writeToFile() {
        // TODO : Add persistence
    }

    //    To be removed
    private ArrayList<Member> generateTestMembers() {
        ArrayList<Member> members = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count++;
            Member member = new Member("Herr", "Testman" + i, 465484, i);
            for (int n = 0; n < Math.random() * 5; n++) {
                member.addBoat(new Boat((int) (Math.random() * 30), Boat.BoatType.values()[(int) (Math.random() * 5)], count));
                count++;
            }
            members.add(member);
        }

        return members;
    }
}
