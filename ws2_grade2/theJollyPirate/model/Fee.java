package model;



import java.io.Serializable;

public class Fee implements Serializable {
    private static final long serialVersionUID = -5117116163379721982L;
    private double totalFee;
    private final int memberFee = 100;


    public Fee(){addMemberFee();}

    public double  getTotalFee(){return totalFee;}
    public void addMemberFee(){this.totalFee = this.totalFee+memberFee;}



    public void addBoatFee(Price price){
        this.totalFee = this.totalFee + price.getPrice();
    }


}
