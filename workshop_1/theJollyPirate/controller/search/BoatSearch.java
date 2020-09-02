package controller.search;

import model.Boat;
import model.Registry;
import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class BoatSearch extends Search{
    private Registry registry;
    private String searchWord;
    private ArrayList<Boat> result;

    public BoatSearch(String searchWord, Registry registry){
        this.searchWord = searchWord;
        this.registry = registry;    }

    @Override
    public Object[] search() {
        Boat[] boat = null;
        return boat;
    }
}
