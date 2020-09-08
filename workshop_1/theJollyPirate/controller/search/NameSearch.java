package controller.search;

import model.Registry;
import model.roles.Users;

import java.util.ArrayList;

public class NameSearch extends Search{
    Users[] members;
    private String searchWord;
    private ArrayList<Users> result;

    public NameSearch(String searchWord, Users[] members ){
        this.searchWord = searchWord;
        this.members = members;    }

    @Override
    protected void search() {

        for(int i = 0; i<members.length; i++){
            if(members[i].getFullName().contains(searchWord))
                this.result.add(members[i]);
        }
    }

    @Override
    public Users[] returnResult() {
        Users[] result = new Users[this.result.size()];
        for(int i = 0; i<this.result.size();i++){
            result[i] = this.result.get(i);
        }
        return result;
    }
}
