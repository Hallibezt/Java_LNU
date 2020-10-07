package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Player;

public class Soft17HitStrategy implements IHitStrategy{
    private final int g_hitLimit = 17;

    public boolean DoHit(Player a_dealer) {
        boolean ace = false; //No ace do not worry about soft17

        for(Card c : a_dealer.GetHand()){
            if(c.GetValue().equals(Card.Value.Ace)){
                ace = true;
                }
        }


        if(a_dealer.CalcScore() == 17 & ace & !a_dealer.softhandUsed()){
            return true;
        }
        else
         return a_dealer.CalcScore() < g_hitLimit;
    }
}
