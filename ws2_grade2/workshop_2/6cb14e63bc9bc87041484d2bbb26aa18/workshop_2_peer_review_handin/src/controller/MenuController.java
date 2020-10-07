package controller;

import model.data.Boat;
import model.data.Member;
import view.Console;

import java.util.ArrayList;

public enum MenuController {
    MAIN_MENU {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
                    MEMBER_MENU,
                    BOAT_LIST,
                    QUIT
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            return getMenuChoice(view, this);
        }

        @Override
        public String getMenuDescription() {
            return "Main Menu";
        }
    },

    MEMBER_MENU {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
                    CREATE_MEMBER,
                    LIST_FORMAT_MENU,
                    MAIN_MENU
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            return getMenuChoice(view, this);
        }

        @Override
        public String getMenuDescription() {
            return "Member Menu";
        }
    },

    CREATE_MEMBER {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());

            view.displayText("First name: ");
            String firstName = view.getTextInput();
            view.displayText("Last name: ");
            String lastName = view.getTextInput();
            view.displayText("Personal ID (YYYYMMDD): ");
            int pID = view.getIntInput();

            mc.getBoatClubFacade().addMember(firstName, lastName, pID);

            return MAIN_MENU;
        }

        @Override
        public String getMenuDescription() {
            return "Create Member";
        }
    },

    LIST_FORMAT_MENU {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
                    MEMBER_LIST_COMPACT,
                    MEMBER_LIST_VERBOSE,
                    MAIN_MENU,
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            return getMenuChoice(view, this);
        }

        @Override
        public String getMenuDescription() {
            return "List Members";
        }
    },

    MEMBER_LIST_COMPACT {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            //            Return to main menu if there are no members.
            if (mc.getBoatClubFacade().getStoredMembers().isEmpty()) {
                view.displayText("No members registered");
                return MAIN_MENU;
            }

            view.displayText(this.getMenuDescription());
            ArrayList<Integer> idList = new ArrayList<>();

//            Display all members in list
            for (Member m : mc.getBoatClubFacade().getStoredMembers()) {
                view.displayCompact(m.getMemberID(), m.getName(), m.getBoats().size());
                idList.add(m.getMemberID());
            }

            view.displayListInstructions("Please enter the ID of the member you wish to select");

            int choice = view.getListChoice(idList);
            mc.setSelectedMember(mc.getBoatClubFacade().getMember(choice));

            return MEMBER_CHOICES;
        }

        @Override
        public String getMenuDescription() {
            return "Compact List";
        }
    },

    MEMBER_LIST_VERBOSE {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            //            Return to main menu if there are no members.
            if (mc.getBoatClubFacade().getStoredMembers().isEmpty()) {
                view.displayText("No members registered");
                return MAIN_MENU;
            }

            view.displayText(this.getMenuDescription());
            ArrayList<Integer> idList = new ArrayList<>();

//            Display all members in list
            for (Member m : mc.getBoatClubFacade().getStoredMembers()) {
                view.displayVerbose(m.getMemberID(), m.getPersonalID(), m.getName(), m.getBoats().size(), m.getAllBoatsInfo());
                idList.add(m.getMemberID());
            }

            view.displayListInstructions("Please enter the ID of the member you wish to select");

            int choice = view.getListChoice(idList);
            mc.setSelectedMember(mc.getBoatClubFacade().getMember(choice));

            return MEMBER_CHOICES;
        }

        @Override
        public String getMenuDescription() {
            return "Verbose List";
        }
    },

    MEMBER_CHOICES {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
                    MEMBER_BOAT_LIST,
                    DELETE_MEMBER,
                    CHANGE_MEMBER_INFO,
                    REGISTER_NEW_BOAT,
                    MAIN_MENU
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            view.displayMemberChoices(mc.getSelectedMember().getMemberID(), mc.getSelectedMember().getName(), mc.getSelectedMember().getPersonalID());

            return getMenuChoice(view, this);
        }

        @Override
        public String getMenuDescription() {
            return "Member Choice";
        }
    },

    BOAT_CHOICES {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
                    DELETE_BOAT,
                    CHANGE_BOAT_TYPE,
                    CHANGE_BOAT_LENGTH,
                    MAIN_MENU
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            view.displayText(mc.getSelectedBoat().getBoatInfo());
            return getMenuChoice(view, this);
        }

        @Override
        public String getMenuDescription() {
            return "Boat Choice";
        }
    },

    MEMBER_BOAT_LIST {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            if (mc.getSelectedMember().getBoats().isEmpty()){
                view.displayText("No boats registered for this member");
                return MAIN_MENU;
            }

            view.displayText(this.getMenuDescription());
            ArrayList<Integer> idList = new ArrayList<>();

//            Display all boats for a specific member
            for (Boat b: mc.getSelectedMember().getBoats()) {
                view.displayText(b.getBoatInfo());
                idList.add(b.getId());
            }

            int choice = view.getListChoice(idList);

            for (Boat b : mc.getSelectedMember().getBoats())
                if (b.getId() == choice)
                    mc.setSelectedBoat(b);

            return BOAT_CHOICES;
        }

        @Override
        public String getMenuDescription() {
            return "Boat List";
        }
    },

    BOAT_LIST {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            ArrayList<Integer> idList = new ArrayList<>();
            ArrayList<Boat> allBoats = new ArrayList<>();
            for (Member m: mc.getBoatClubFacade().getStoredMembers()) {
                for (Boat b : m.getBoats()) {
                    allBoats.add(b);
                    idList.add(b.getId());
                }
            }

            if (allBoats.isEmpty()) {
                view.displayText("No boats registered");
                return MAIN_MENU;
            }

            view.displayText(this.getMenuDescription());
            allBoats.sort(null);

//            Display all boats
            for (Boat b : allBoats)
                view.displayText(b.getBoatInfo());

            view.displayListInstructions("Please enter the ID of the boat you wish to select");

            int choice = view.getListChoice(idList);

            for (Member m: mc.getBoatClubFacade().getStoredMembers()) {
                for (Boat b : m.getBoats()) {
                    if (b.getId() == choice)
                        mc.setSelectedBoat(b);
                }
            }

            return BOAT_CHOICES;
        }

        @Override
        public String getMenuDescription() {
            return "Boat List";
        }
    },

    QUIT {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[]{
            };
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            view.exitMe();
            System.exit(1);
            return null;
        }

        @Override
        public String getMenuDescription() {
            return "Quit";
        }
    },

    DELETE_MEMBER {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[] {
            };
        }

        @Override
        public String getMenuDescription() {
            return "Delete Member";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            mc.getBoatClubFacade().deleteMember(mc.getSelectedMember().getMemberID());
            return MAIN_MENU;
        }
    },

    CHANGE_MEMBER_INFO {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[] {
                    CHANGE_NAME,
                    CHANGE_PID,
                    MAIN_MENU
            };
        }

        @Override
        public String getMenuDescription() {
            return "Change Member Info";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());
            return getMenuChoice(view, this);
        }
    },

    CHANGE_NAME {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Change Name";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription() + " " +  mc.getSelectedMember().getName());

            view.displayText("First name: ");
            String newFirstName = view.getTextInput();
            view.displayText("Last name: ");
            String newLastName = view.getTextInput();
            mc.getBoatClubFacade().changeMember(mc.getSelectedMember().getMemberID(), newFirstName, newLastName, mc.getSelectedMember().getPersonalID());

            return CHANGE_MEMBER_INFO;
        }
    },

    CHANGE_PID {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Change personal number";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription() + ": " + mc.getSelectedMember().getPersonalID());
            view.displayText("YYYYMMDD");

            int newPID = view.getIntInput();
            mc.getBoatClubFacade().changeMember(
                    mc.getSelectedMember().getMemberID(),
                    mc.getSelectedMember().getFirstName(),
                    mc.getSelectedMember().getLastName(),
                    newPID
            );

            return CHANGE_MEMBER_INFO;
        }
    },

    REGISTER_NEW_BOAT {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Register New Boat";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription());

            ArrayList<String> types = new ArrayList<>();
            for (Boat.BoatType boatType : Boat.BoatType.values())
                types.add(boatType.name());

            Boat boat = new Boat();

//            Get boat-type
            view.displayText("Choose a type");
            view.displayList(types, false);
            int typeChoice = view.getIntInput(Boat.BoatType.values().length + 1, 1); // not zero-based indexing, hence +1
            boat.setType(Boat.BoatType.values()[typeChoice - 1]);

//            Get length
            view.displayText("Enter the length (feet)");
            int size = view.getIntInput();
            boat.setLength(size);

//            Generate unique id
            ArrayList<Integer> allBoatID = new ArrayList<>();
            for (Member m: mc.getBoatClubFacade().getStoredMembers())
                for (Boat b : m.getBoats())
                    allBoatID.add(b.getId());

            for (int i = 0; i < allBoatID.size()+1; i++) {
                if (!allBoatID.contains(i))
                    boat.setId(i);
            }

//            mc.selectedMember.addBoat(boat);
            mc.getBoatClubFacade().registerBoat(mc.getSelectedMember(), boat);
            view.displayText("Boat successfully registered" + boat.getId());
            return MEMBER_CHOICES;
        }
    },

    CHANGE_BOAT_TYPE {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Change type";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription() + "");

            // KOPIERAD KOD
            ArrayList<String> types = new ArrayList<>();
            for (Boat.BoatType boatType : Boat.BoatType.values())
                types.add(boatType.name());

//            Get boat-type
            view.displayText("Choose a type:");
            view.displayList(types, false);
            int typeChoice = view.getIntInput(Boat.BoatType.values().length + 1, 1); // not zero-based indexing, hence +1

            mc.getBoatClubFacade().changeBoat(mc.getSelectedBoat().getId(), Boat.BoatType.values()[typeChoice - 1], mc.getSelectedBoat().getLength());

            return BOAT_CHOICES;
        }
    },

    CHANGE_BOAT_LENGTH {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Change boat length";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            view.displayText(this.getMenuDescription() + "");
            view.displayText("Input new length:");

            int newLength = view.getIntInput();

            mc.getBoatClubFacade().changeBoat(mc.getSelectedBoat().getId(), mc.getSelectedBoat().getType(), newLength);

            return BOAT_CHOICES;
        }
    },

    DELETE_BOAT {
        @Override
        public MenuController[] getOptions() {
            return new MenuController[0];
        }

        @Override
        public String getMenuDescription() {
            return "Delete Boat";
        }

        @Override
        public MenuController action(Console view, MemberController mc) {
            mc.getBoatClubFacade().deleteBoat(mc.getSelectedBoat().getId());

            return MAIN_MENU;
        }
    };

    protected MenuController getMenuChoice(Console view, MenuController menuItem) {
        ArrayList<String> menuItems = new ArrayList<>();

        for (MenuController mi : menuItem.getOptions())
            menuItems.add(mi.getMenuDescription());

        view.displayList(menuItems,false); // List is shown with a starting-index of 1

        int menuChoice = view.getIntInput(menuItems.size()+1, 1); // adjust for the starting index
        return this.getOptions()[menuChoice-1]; //
    }

    public abstract MenuController[] getOptions ();
    public abstract String getMenuDescription();
    public abstract MenuController action(Console view, MemberController mc);
}
