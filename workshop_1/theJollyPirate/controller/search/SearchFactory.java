package controller.search;

import model.Registry;
import model.roles.Users;

public class SearchFactory {

    public Search getSearch(String typeOfSearch, String searchWord, Users[] members){

        if("Name".equalsIgnoreCase(typeOfSearch)) return new NameSearch(searchWord, members);
        else if("Age".equalsIgnoreCase(typeOfSearch)) return new AgeSearch(searchWord, members);
        else if("Month".equalsIgnoreCase(typeOfSearch)) return new MonthSearch(searchWord, members);
        else if("Boat".equalsIgnoreCase(typeOfSearch)) return new BoatTypeSearch(searchWord, members);

        return null;
    }



}
