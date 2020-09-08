package controller.search;

import controller.exceptions_errors.EmptyListException;
import model.roles.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchANDProcess {
    Users[][] allResults;
     ArrayList<Users> finalResult = null;


    public SearchANDProcess(Users[]...searches){
        this.allResults = searches;
    }

    // TODO: 2020-09-07 Have found the smallest now add to finalResul array the ones in it found in all the others
    public void findCommonMembers(){
        Users[] forComparison = findTheSmallest();
        Boolean isInAll = false;
        outerloop:
        for(int i = 0; i< forComparison.length; i++){
            for (int j = 0; j < allResults.length; j++) {
                for (int k = 0; k < allResults[j].length; j++) {
                    if (allResults[j][k].getLogin().getUserID().equals(forComparison[i].getLogin().getUserID())) {
                        isInAll = true;
                        break;
                    }
                    else{
                        isInAll = false;
                        break outerloop;}
                }
            }
        }
        if(isInAll == true)
            finalResult = (ArrayList<Users>) Arrays.asList(forComparison);

    }

    public Users[] returnResults() throws EmptyListException {
        if(finalResult == null)
            throw new EmptyListException("No search results for this criteria");
        Users[] finalResult = new Users[this.finalResult.size()];
        for(int i = 0; i<this.finalResult.size();i++){
            finalResult[i] = this.finalResult.get(i);
        }
        return finalResult;
    }

    //Find the smallest searchResult since this is a AND process so the ones not in the smallest do not matter
    private Users[] findTheSmallest(){
        int length = 10000;
        Users[] smallest = null;
        for( int i = 0; i< allResults.length; i++){
            if(allResults[i].length < length){
                smallest = allResults[i];
                length = allResults.length;
            }
        }
        return smallest;
    }
}
