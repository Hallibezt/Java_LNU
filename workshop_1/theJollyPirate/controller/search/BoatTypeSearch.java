package controller.search;

import model.boats.Boat;
import model.Registry;
import model.roles.Users;

import java.util.ArrayList;

public class BoatTypeSearch extends Search{
    private Users[] members;
    private String searchWord;
    private ArrayList<Users> result;

    public BoatTypeSearch(String searchWord, Users[] members){
        this.searchWord = searchWord;
        this.members = members;
        search();}

    @Override
    protected void search() {
        for(int i = 0; i<members.length; i++){
            if(members[i].returnBoats() != null){
                Boat[] boats = members[i].returnBoats();
                for(int j = 0; j<boats.length; j++){
                    if(boats[j].getType().equalsIgnoreCase(searchWord)){
                        result.add(members[i]);
                        break;
                    }
                }
            }
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
