package controller.search;

import model.Registry;
import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class MonthSearch extends Search{
    private Users[] members;
    private int monthToSearch;
    private ArrayList<Users> result = null;

    public MonthSearch(String searchWord, Users[] members){
        if(searchWord.matches("\\d+"))
            this.monthToSearch = Integer.valueOf(searchWord);
        else
            this.monthToSearch = matchMonths(searchWord);
        this.members = members;    }

    private int matchMonths(String searchWord) {
        int month = 0;
        String monthString = searchWord.toLowerCase();
        switch (monthString) {
            case "january":  month = 1;
                break;
            case "february":  month = 2;
                break;
            case "march":  month = 3;
                break;
            case"april":  month = 4;
                break;
            case "may":  month = 5;
                break;
            case "june":  month = 6;
                break;
            case"july":  month = 7;
                break;
            case "august":  month = 8;
                break;
            case "september":  month = 9;
                break;
            case "october": month = 10;
                break;
            case "november":month = 11;
                break;
            case "december": month = 12;
                break;
        }
        return month;
    }

    @Override
    protected void search() {

        for(int i = 0; i<members.length; i++){
            if(members[i].getMonth() == monthToSearch)
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
