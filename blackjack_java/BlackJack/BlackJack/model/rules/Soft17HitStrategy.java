package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Player;

public class Soft17HitStrategy implements IHitStrategy{
    private final int g_hitLimit = 17;

    public boolean DoHit(Player a_dealer) {
        boolean ace = false;
        for(Card c : a_dealer.GetHand()){
            c.Show(true);
            if(c.GetValue().equals(Card.Value.Ace))
                ace = true;
        }
        if(a_dealer.CalcScore() == 17 & ace){
            return true;

        }
        else
         return a_dealer.CalcScore() < g_hitLimit;
    }
}
