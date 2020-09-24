package model;



import java.io.Serializable;

public class Fee implements Serializable {
    private double totalFee;
    private final int memberFee = 100;


    public Fee(){addMemberFee();}

    public double  getTotalFee(){return totalFee;}
    public void addMemberFee(){this.totalFee = this.totalFee+memberFee;}



    public void addBoatFee(Price price){
        this.totalFee = this.totalFee + price.getPrice();
    }


}
