package BlackJack.controller;

import BlackJack.view.IView;
import BlackJack.model.Game;
import BlackJack.view.SimpleView;

public class PlayGame {

  public boolean Play(Game a_game, IView a_view) throws InterruptedException {
    a_view.DisplayWelcomeMessage();
    
    a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
    a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());

    if (a_game.IsGameOver())
    {
        a_view.DisplayGameOver(a_game.IsDealerWinner());
    }

    SimpleView.Input input = a_view.GetInput();

    if (input == SimpleView.Input.Play)
    {
        a_game.NewGame();
    }
    else if (input == SimpleView.Input.Hit)
    {
        a_game.Hit();
    }
    else if (input == SimpleView.Input.Stand)
    {
        a_game.Stand();
    }

    return input != SimpleView.Input.Quit;
  }
}