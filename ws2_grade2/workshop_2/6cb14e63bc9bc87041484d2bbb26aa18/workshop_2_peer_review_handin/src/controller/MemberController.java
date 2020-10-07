package controller;

import model.*;
import model.data.Boat;
import model.data.Member;
import view.Console;

public class MemberController {
    private final Console view;
    private final IBoatClubFacade boatClubFacade;
    private Member selectedMember;
    private Boat selectedBoat;

    IBoatClubFacade getBoatClubFacade() {
        return boatClubFacade;
    }



    void setSelectedBoat(Boat selectedBoat) {
        this.selectedBoat = selectedBoat;
    }

    Boat getSelectedBoat() {
        return selectedBoat;
    }

    void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    Member getSelectedMember() {
        return selectedMember;
    }

    public MemberController(Console view, IBoatClubFacade bcf) {
        this.view = view;
        this.boatClubFacade = bcf;
    }

    public void startApplication(){
        view.displayWelcomeMessage();

        MenuController nextMenu = MenuController.MAIN_MENU;

        while (nextMenu != null) {
            nextMenu = nextMenu.action(view, this);
        }
    }
}
