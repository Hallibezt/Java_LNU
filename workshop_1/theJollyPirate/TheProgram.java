import controller.MainControl;
import model.Registry;
import view.Mainview;

public class TheProgram {

    public static void main(String[] args){
      Mainview view = new Mainview();
      Registry jollyPirate = new Registry();
      MainControl user = new MainControl(jollyPirate, view);


      while(user.getProgramRunning() == true){user.welcome();}
      view.programClosed();

    }

}


