package controller.search;

import model.Registry;
import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class NameSearch extends Search{
    private Registry registry;
    private String searchWord;
    private ArrayList<Member> result;

    public NameSearch(String searchWord, Registry registry){
        this.searchWord = searchWord;
        this.registry = registry;    }



    @Override
    public Object[] search() {
        Users[] member = null;
        return member;
    }
}
