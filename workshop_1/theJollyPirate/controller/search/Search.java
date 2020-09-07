package controller.search;

import model.Registry;
import model.roles.Users;

import java.util.ArrayList;

public abstract class Search {
    protected abstract void search();
    public abstract Users[]  returnResult();
   // filteredTimelines.setPredicate(timeline -> timeline.getName().toLowerCase().contains(searchText.toLowerCase())
      //      || timeline.getKeywords().stream().anyMatch(k -> k.toLowerCase().contains(searchText.toLowerCase())));

}
