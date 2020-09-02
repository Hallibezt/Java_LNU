package controller.search;

import model.Registry;

public class SearchFactory {

    public Search getSearch(String typeOfSearch, String searchWord, Registry registry){

        if("Name".equalsIgnoreCase(typeOfSearch)) return new NameSearch(searchWord, registry);
        else if("Age".equalsIgnoreCase(typeOfSearch)) return new AgeSearch(searchWord, registry);
        else if("Boat".equalsIgnoreCase(typeOfSearch)) return new BoatSearch(searchWord, registry);

        return null;
    }



}
