package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public interface IWinsStrategy {
    boolean whoWins(Player a_player, Dealer a_dealer);
}
