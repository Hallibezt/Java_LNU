package controller.search;

import model.Registry;
import model.roles.Users;

import java.util.ArrayList;

public class NameSearch extends Search{
    private Registry registry;
    private String searchWord;
    private ArrayList<Users> result;

    public NameSearch(String searchWord, Registry registry){
        this.searchWord = searchWord;
        this.registry = registry;    }

    @Override
    protected void search() {
        Users[] members = registry.returnMembers();
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
