import java.io.File;
import java.util.Scanner;
public class Testing {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Please choose action");
        System.out.print("1. Log in \n 2. Log out");
        int userInput = input.nextInt();
        if (userInput == 2){
            toJason();
        }

    }
    public static void toJason(){
        File outFile = new File("src/workshop_2");


    }
}


