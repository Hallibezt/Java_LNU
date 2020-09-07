package controller.search;

import model.roles.Users;

import java.util.ArrayList;

public class SearchANDProcess {
    Users[][] allResults;
     ArrayList<Users> finalResult;

    
    public SearchANDProcess(Users[]...searches){
        this.allResults = searches;
    }

    // TODO: 2020-09-07 Have found the smallest now add to finalResul array the ones in it found in all the others
    public void findCommonMembers(){
        Users[] forComparison = findTheSmallest();

    }

    public Users[] returnResults(){
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
