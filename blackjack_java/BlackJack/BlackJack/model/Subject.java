package BlackJack.model;

public interface Subject {
    void NotifyObservers() throws InterruptedException;
    void Attach(Observer observer);
}
