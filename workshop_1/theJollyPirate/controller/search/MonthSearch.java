package controller.search;

import model.Registry;
import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class MonthSearch extends Search{
    private Registry registry;
    private String searchWord;
    private ArrayList<Users> result;

    public MonthSearch(String searchWord, Registry registry){
        this.searchWord = searchWord;
        this.registry = registry;    }

    @Override
    protected void search() {
        Users[] members = registry.returnMembers();
        for(int i = 0; i<members.length; i++){

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
