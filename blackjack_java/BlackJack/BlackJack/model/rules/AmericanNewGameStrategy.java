package BlackJack.model.rules;

import BlackJack.model.Deck;
import BlackJack.model.Dealer;
import BlackJack.model.Player;
import BlackJack.model.Card;  

class AmericanNewGameStrategy implements INewGameStrategy {

  public boolean NewGame(Deck a_deck, Dealer a_dealer, Player a_player) throws InterruptedException {

    a_dealer.DealCard(a_player,true);

    a_dealer.DealCard(a_dealer,true);

    a_dealer.DealCard(a_player,true);

    a_dealer.DealCard(a_dealer,false);

    return true;
  }
}