import controller.ExportImport;
import controller.MainControl;
import controller.exceptions_errors.WrongFormatException;
import model.Registry;
import view.MainView;

import java.io.EOFException;
import java.io.IOException;

public class TheProgram {

    public static void main(String[] args) throws IOException, WrongFormatException {
      MainView view = new MainView();
      ExportImport importDatabase = new ExportImport();
      Registry jollyPirate = new Registry();
     try {
        jollyPirate = importDatabase.importRegistry();
      }
        catch (EOFException ignored){
        }

      MainControl user = new MainControl(jollyPirate, view);


      while(user.getProgramRunning()){user.welcome();}
      view.programClosed();

    }

}


