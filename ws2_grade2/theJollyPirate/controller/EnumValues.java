package controller;

import java.io.Serializable;

public class EnumValues implements Serializable {

    public enum updateOptions {firstName, secondName, password, registerBoat, removeBoat, exit}

    public enum updateBoatOptions {type, length, exit}

    public enum mainOptions {createMember, removeMember, editMember, findMember, registerBoat, removeBoat, editBoat, compact, verbose, exit}

    public enum boatType {Motorsailer, Sailboat, Kayak_Canoe, Other}
}
