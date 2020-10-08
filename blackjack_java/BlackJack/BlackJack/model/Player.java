package BlackJack.model;


import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


public class Player implements Subject {

  private List<Card> m_hand;
  private boolean softHandUsed = false;
  protected final int g_maxScore = 21;
  private List<Observer> observers = new ArrayList<Observer>();
  private boolean status = false;


  public Player()
  {
    m_hand = new LinkedList<Card>();
    System.out.println("Hello List World");
  }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.Update(status);}
        setStatus(false);
    }

    public void attach(Observer observer){
        observers.add(observer);
    }


    public void setStatus(boolean status){
        this.status = status;
    }
  
  public void addCard(Card c){
      m_hand.add(c);
      setStatus(true);
      notifyAllObservers();
      //Notify view == print hand + pause
  }
  
  public Iterable<Card> GetHand()
  {
    return m_hand;
  }
  
  public void ClearHand()
  {
    m_hand.clear();
  }
  
  public void ShowHand()
  {
    for(Card c : GetHand())
    {
      c.Show(true);
    }
  }

  public boolean SoftHandUsed(){
      return this.softHandUsed;
  }
  
  public int CalcScore()
  {
    // the number of scores is dependant on the number of scorable values
    // as it seems there is no way to do this check at compile time in java ?!
    // cardScores[13] = {...};
    int cardScores[] = {
        2, 3, 4, 5, 6, 7, 8, 9, 10, 10 ,10 ,10, 11
    };
    assert (cardScores.length == Card.Value.Count.ordinal()) : "Card Scores array size does not match number of card values";
  
    
    int score = 0;

    for(Card c : GetHand()) {
        if (c.GetValue() != Card.Value.Hidden)
        {
            score += cardScores[c.GetValue().ordinal()];
        }
    }

    if (score > g_maxScore)
    {
        for(Card c : GetHand())
        {
            if (c.GetValue() == Card.Value.Ace && score > g_maxScore)
            {
                score -= 10;
                this.softHandUsed = true;
            }
        }
    }

    return score;
  }



}
