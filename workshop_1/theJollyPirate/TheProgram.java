import controller.UserInterface;
import model.Registry;
import model.roles.Member;
import model.roles.Users;
import view.Mainview;

public class TheProgram {

    public static void main(String[] args){
      Mainview view = new Mainview();
      Registry jollyPirate = new Registry();
      UserInterface user = new UserInterface(jollyPirate, view);


      user.welcome();

    }

}


