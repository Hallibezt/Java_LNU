package BlackJack.model;

public interface Subject {

    void notifyAllObservers();

    void attach(Observer observer);


}
