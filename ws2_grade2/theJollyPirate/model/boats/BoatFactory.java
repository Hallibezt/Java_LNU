package model.boats;

import model.roles.Users;

public class BoatFactory {

    public Boat getBoat(String type,double length, String registrationNumber, Users owner){
        if("motorsailer".equalsIgnoreCase(type)) return new Motorsailer(length, registrationNumber, owner);
        else if("sailboat".equalsIgnoreCase(type)) return new Sailboat(length, registrationNumber, owner);
        else if("kayak_canoe".equalsIgnoreCase(type)) return new Kayak_canoe(length, registrationNumber, owner);
        else if("other".equalsIgnoreCase(type)) return new OtherBoat(length, registrationNumber, owner);

        return null;
    }
}
