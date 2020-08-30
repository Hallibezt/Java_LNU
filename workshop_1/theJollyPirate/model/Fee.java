package model;

public class Fee {
    private double totalFee;
    private final int memberFee = 100;
    private final int sailboat = 500;
    private final int motorsailer = 600; //Risk of oil-leakage - environment price
    private final int kayak_canoe = 150;
    private final int other = 350;
    private final int underFive = 0;
    private final int fiveToTen = 100;
    private final int overTen = 200;

    public Fee(){addMemberFee();}

    public double  getTotalFee(){return totalFee;}
    public void addMemberFee(){this.totalFee = this.totalFee+memberFee;}

    // TODO: 2020-08-29 Ask about if this is needed
    // If I need this for the assignment public void addRegistrationFee(Season season){Three versions}
    //public void removeBoatFee(Boat fee){}

    // TODO: 2020-08-29 create for different boats
    public void addBoatFee(Boat boat){
        String type = boat.getType();
        double length = boat.getLength();
        if(type.equals(sailboat)){
            if (boat.getLength()<=5){this.totalFee = totalFee + this.sailboat + this.underFive;}
            else if (boat.getLength()>5 & boat.getLength() <= 10){this.totalFee = totalFee + this.sailboat + this.fiveToTen;}
            else
                this.totalFee = totalFee + this.sailboat + this.overTen;}
        else if(type.equals(motorsailer)){
            if (boat.getLength()<=5){this.totalFee = totalFee + this.motorsailer + this.underFive;}
            else if (boat.getLength()>5 & boat.getLength() <= 10){this.totalFee = totalFee + this.motorsailer + this.fiveToTen;}
            else
                this.totalFee = totalFee + this.motorsailer + this.overTen;}
       else if(type.equals(kayak_canoe)) {
            this.totalFee = totalFee + this.kayak_canoe;
        }

       else if(type.equals(other)){
            if (boat.getLength()<=5){this.totalFee = totalFee + this.other + this.underFive;}
            else if (boat.getLength()>5 & boat.getLength() <= 10){this.totalFee = totalFee + this.other + this.fiveToTen;}
            else
                this.totalFee = totalFee + this.other + this.overTen;}

    }

}
