package model;

import controller.EnumValues;

import java.io.Serializable;

public class Price implements Serializable {
    private static final long serialVersionUID = -4021840820602133716L;
    private double price = 0;
    private final int sailboat = 500;
    private final int motorsailer = 600; //Risk of oil-leakage - environment price
    private final int kayak_canoe = 150;
    private final int other = 350;
    private final int underFive = 0;
    private final int fiveToTen = 100;
    private final int overTen = 200;

    public Price() {
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(Boat boat){
        EnumValues.boatType type = boat.getType();
        double length = boat.getLength();
        if(type == EnumValues.boatType.sailboat){
            if (length<=5){this.price = this.sailboat + this.underFive;}
            else if (length>5 & boat.getLength() <= 10){this.price =  this.sailboat + this.fiveToTen;}
            else
                this.price = this.sailboat + this.overTen;}
        else if(type == EnumValues.boatType.motorsailer){
            if (length<=5){this.price =  this.motorsailer + this.underFive;}
            else if (length>5 & boat.getLength() <= 10){this.price = this.motorsailer + this.fiveToTen;}
            else
                this.price = this.motorsailer + this.overTen;}
        else if(type == EnumValues.boatType.kayakCanoe) {
            this.price = this.kayak_canoe;
        }
        else if(type == EnumValues.boatType.other){
            if (length<=5){this.price =  this.other + this.underFive;}
            else if (length>5 & boat.getLength() <= 10){this.price = this.other + this.fiveToTen;}
            else
                this.price =  this.other + this.overTen;}

    }

    public void setUpdatePrice(Boat boat, EnumValues.boatType prevType, double prevLength) {
        double lengthDifference = boat.getLength()-prevLength;
        double typeDifference = 0;
        if(!boat.getType().equals(prevType))
            typeDifference = findDifference(boat.getType(), prevType);
        this.price =  typeDifference;
        if(lengthDifference > 0){
            //if the length change is under 5 m - no fee is charged
            if(lengthDifference >= 5)
                this.price = price + fiveToTen;
            if(lengthDifference >= 10)
                this.price = price + overTen;
        }

    }

    private double findDifference(EnumValues.boatType newType, EnumValues.boatType prevType) {

        if (prevType == EnumValues.boatType.kayakCanoe) {
            if (newType == EnumValues.boatType.motorsailer)
                return 450;
            if (newType == EnumValues.boatType.sailboat)
                return 350;
            if (newType == EnumValues.boatType.other)
                return 200;
            else
                return 0;
        }
        if (prevType==EnumValues.boatType.other) {
            if (newType == EnumValues.boatType.motorsailer)
                return 250;
            if (newType == EnumValues.boatType.sailboat)
                return 150;
            else
                return 0;
        }
        if (prevType == EnumValues.boatType.sailboat) {
            if (newType == EnumValues.boatType.motorsailer)
                return 100;
            else
                return 0;
        } else
            return 0;
        }
    }
