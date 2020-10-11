package model;
import model.enums.BoatType;

import java.io.Serializable;

public class Price implements Serializable {
    private static final long serialVersionUID = -4021840820602133716L; //For the serializer, if it is not set then i can happen that ID do not match
    private double price;
    private final int sailboat = 500;
    private final int motorsailer = 600;
    private final int kayak_canoe = 150;
    private final int other = 350;
    private final int underFive = 0;
    private final int fiveToTen = 100;
    private final int overTen = 200;

    private enum LengthEnum {
        fiveMeterLong(5), tenMeterLong(10);
        private final int value;
        LengthEnum(int value) {
            this.value = value;
        }
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(Boat boat){
        BoatType type = boat.getType();
        double length = boat.getLength();
        int five = LengthEnum.fiveMeterLong.value;
        int ten = LengthEnum.tenMeterLong.value;
        if(type == BoatType.Sailboat){
            if (length<=five)
                this.price = this.sailboat + this.underFive;
            else if (length>ten & boat.getLength() <= ten)
                this.price =  this.sailboat + this.fiveToTen;
            else
                this.price = this.sailboat + this.overTen;
        }
        else if(type == BoatType.Motorsailer){
            if (length<=five)
                this.price =  this.motorsailer + this.underFive;
            else if (length>five & boat.getLength() <= ten)
                this.price = this.motorsailer + this.fiveToTen;
            else
                this.price = this.motorsailer + this.overTen;
        }
        else if(type == BoatType.Kayak_Canoe)
            this.price = this.kayak_canoe;
        else if(type == BoatType.Other){
            if (length<=five)
                this.price =  this.other + this.underFive;
            else if (length>five & boat.getLength() <= ten)
                this.price = this.other + this.fiveToTen;
            else
                this.price =  this.other + this.overTen;
        }
    }

    public void setUpdatePrice(Boat boat, BoatType prevType, double prevLength) {
        double lengthDifference = boat.getLength()-prevLength;
        this.price = findDifference(boat.getType(), prevType);
            if(lengthDifference < LengthEnum.fiveMeterLong.value)
                this.price = price + underFive;
            if(lengthDifference >= LengthEnum.fiveMeterLong.value)
                this.price = price + fiveToTen;
            if(lengthDifference >= LengthEnum.tenMeterLong.value)
                this.price = price + overTen;
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private double findDifference(BoatType newType, BoatType prevType) {
        if (prevType == BoatType.Kayak_Canoe) {
            if (newType == BoatType.Motorsailer)
                return this.motorsailer-this.kayak_canoe;
            if (newType == BoatType.Sailboat)
                return this.sailboat-this.kayak_canoe;
            if (newType == BoatType.Other)
                return this.other-this.kayak_canoe;
            else
                return this.kayak_canoe-this.kayak_canoe;
        }
        if (prevType== BoatType.Other) {
            if (newType == BoatType.Motorsailer)
                return this.motorsailer-this.other;
            if (newType == BoatType.Sailboat)
                return this.sailboat-this.other;
            else
                return this.other-this.other;
        }
        if (prevType == BoatType.Sailboat) {
            if (newType == BoatType.Motorsailer)
                return this.motorsailer-this.sailboat;
            else
                return this.sailboat-this.sailboat;
        } else
            return this.motorsailer-this.motorsailer;
        }
    }
