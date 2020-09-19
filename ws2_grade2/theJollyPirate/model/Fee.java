package model;

import model.boats.Boat;

import java.io.Serializable;

public class Fee implements Serializable {
    private static final long serialVersionUID = -5117116163379721982L;
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



    public void addBoatFee(Price price){
        this.totalFee = this.totalFee + price.getPrice();
    }


}
