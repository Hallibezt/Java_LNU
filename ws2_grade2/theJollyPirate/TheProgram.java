import controller.ExportInport;
import controller.MainControl;
import model.Registry;
import view.English;
import view.Mainview;

import java.io.EOFException;
import java.io.IOException;
import java.time.LocalDate;

public class TheProgram {

    public static void main(String[] args) throws IOException {
      Mainview view = new English();
      ExportInport importDatabase = new ExportInport();
      Registry jollyPirate = new Registry();
      try {
          jollyPirate = importDatabase.importRegistry();
      }
      catch (EOFException e){

      }

      MainControl user = new MainControl(jollyPirate, view);


      while(user.getProgramRunning() == true){user.welcome();}
      view.programClosed();

    }

}


