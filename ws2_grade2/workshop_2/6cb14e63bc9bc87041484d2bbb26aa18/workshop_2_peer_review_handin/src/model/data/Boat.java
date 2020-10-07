package model.data;

public class Boat implements Comparable<Boat>{
    private int length;
    private BoatType type;
    private int id;

    public Boat() {
    }

    public Boat(int aLength, BoatType type, int id) {
        setLength(aLength);
        setType(type);
        setId(id);
    }

    @Override
    public int compareTo(Boat b) {

        if (getId() < b.getId())
            return -1;
        else if (getId() > b.getId()){
            return 1;
        }
        return 0;
    }

    public enum BoatType {
        SAILBOAT,
        MOTORSAILOR,
        MOTORBOAT,
        KAYAK,
        CANOE,
        OTHER
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setType(BoatType aType) {
        this.type = aType;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public BoatType getType() {
        return this.type;
    }

    public String getBoatInfo() {
        return "ID: " + id + " Type: " + type + ", Length: " + length;
    }
}
