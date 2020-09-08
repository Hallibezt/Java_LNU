package controller.search;

import model.Registry;
import model.roles.Member;
import model.roles.Users;

import java.util.ArrayList;

public class AgeSearch extends Search{
    private Registry registry;
    private int ageToSearch;
    private ArrayList<Users> result;
    boolean under = false;
    boolean over = false;
    boolean under_even = false;
    boolean over_even = false;

    public AgeSearch(String searchWord, Registry registry){
        if(searchWord.matches("[=<>].*"))
            setBoolean(searchWord);
        else
            this.ageToSearch = Integer.valueOf(searchWord);
        this.registry = registry;    }

    @Override
    protected void search() {
        Users[] members = registry.returnMembers();
        for(int i = 0; i<members.length; i++){
                if(this.over_even == true){
                    if(members[i].getAge() >= this.ageToSearch)
                        this.result.add(members[i]);
                }
                else if (this.over == true){
                    if(members[i].getAge() > this.ageToSearch)
                        this.result.add(members[i]);
                }
                else if (this.under_even == true){
                    if(members[i].getAge() <= this.ageToSearch)
                        this.result.add(members[i]);
                }
                else if (this.under == true){
                    if(members[i].getAge() < this.ageToSearch)
                        this.result.add(members[i]);
                }
                else  {
                  if(members[i].getAge() >= this.ageToSearch)
                    this.result.add(members[i]);
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

    private void setBoolean(String searchWord){
        extractAge(searchWord);
        StringBuilder inequality = new StringBuilder();
        for (char dig : searchWord.toCharArray())
        {
            if (!Character.isLetterOrDigit(dig))
            {
                inequality.append(dig);
            }
        }
        switch (inequality.toString()) {
            case "<=":
                this.under_even = true;
                break;
            case ">=":
                this.over_even = true;
                break;
            case "<":
                under = true;
                break;
            case ">":
                over = true;
                break;
        }

    }

    private void extractAge(String searchWord){
        StringBuilder age = new StringBuilder();
        for (char dig : searchWord.toCharArray())
        {
            if (Character.isDigit(dig))
            {
                age.append(dig);
            }
        }
        this.ageToSearch = Integer.valueOf(age.toString());
    }
}
