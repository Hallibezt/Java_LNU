package BlackJack.model;

import BlackJack.model.rules.*;

import java.util.List;

public class Dealer extends Player {
  public enum Rule{
    basic,
    soft17
  }

  private Deck m_deck;
  private INewGameStrategy m_newGameRule;
  private IHitStrategy m_hitRule;
  private IWinsStrategy m_winRule;

  public Dealer(RulesFactory a_rulesFactory) {
  
    m_newGameRule = a_rulesFactory.GetNewGameRule();
    m_hitRule = a_rulesFactory.GetHitRule(Rule.soft17);
    m_winRule = a_rulesFactory.GetWinsRule();
    
    /*for(Card c : m_deck.GetCards()) {
      c.Show(true);
      System.out.println("" + c.GetValue() + " of " + c.GetColor());
    }    */
  }
  
  
  public boolean NewGame(Player a_player) {
    if (m_deck == null || IsGameOver()) {
      m_deck = new Deck();
      ClearHand();
      a_player.ClearHand();
      return m_newGameRule.NewGame(m_deck, this, a_player);   
    }
    return false;
  }

  public boolean Hit(Player a_player) {
    if (m_deck != null && a_player.CalcScore() < g_maxScore && !IsGameOver()) {
      Card c;
      c = m_deck.GetCard();
      c.Show(true);
      a_player.DealCard(c);
      
      return true;
    }
    return false;
  }

  public boolean IsDealerWinner(Player a_player) {
    return m_winRule.whoWins(a_player,this);
  }

  public boolean IsGameOver() {
    if (m_deck != null && m_hitRule.DoHit(this) != true) {
        return true;
    }
    return false;
  }

  public boolean Stand() {
    if(m_deck != null){
      ShowHand();

    List<Card> theHand = (List<Card>) GetHand();
    for(int i = 0; i<theHand.size(); i++){
      theHand.get(i).Show(true);
    }

    while(m_hitRule.DoHit(this)){
      m_hitRule.DoHit(this);
      Card theCard = m_deck.GetCard();
      theCard.Show(true);
      DealCard(theCard);
    }
    }
    return true;
  }

}