package model;

import model.data.Member;

import java.util.ArrayList;

public interface IPersistenceLayer {
    // läsa/skriva xml fil
    public ArrayList<Member> readFromFile();
    public void writeToFile();
}
