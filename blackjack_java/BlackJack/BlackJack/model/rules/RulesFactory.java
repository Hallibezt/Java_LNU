package BlackJack.model.rules;

import BlackJack.model.Dealer;

public class RulesFactory {

  public IHitStrategy GetHitRule(Dealer.Rule rule) {
    if(rule == Dealer.Rule.soft17)
     return new Soft17HitStrategy();
    else
      return new BasicHitStrategy();
  }

  public INewGameStrategy GetNewGameRule() {
    return new AmericanNewGameStrategy();
  }

  public IWinsStrategy GetWinsRule() {
    return new DealerWins();
  }
}