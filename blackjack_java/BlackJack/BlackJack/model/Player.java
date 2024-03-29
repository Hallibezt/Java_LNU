package BlackJack.model;



import java.util.List;
import java.util.LinkedList;


public class Player {

  private List<Card> m_hand;
  private boolean softHandUsed = false;
  protected final int g_maxScore = 21;




  public Player()
  {
    m_hand = new LinkedList<Card>();
    System.out.println("Hello List World");
  }

  
  public void AddCard(Card c){
      m_hand.add(c);
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
