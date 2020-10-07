import controller.MemberController;
import model.IBoatClubFacade;
import model.BoatClubFacade;
import view.Console;

public class Main {
    public static void main(String[] args) {
        Console view = new Console();
        IBoatClubFacade bfc = new BoatClubFacade();
        MemberController controller = new MemberController(view, bfc);

        controller.startApplication();
    }
}
