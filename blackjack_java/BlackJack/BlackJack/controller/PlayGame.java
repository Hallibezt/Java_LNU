package BlackJack.controller;
import BlackJack.model.Observer;
import BlackJack.model.Subject;
import BlackJack.view.IView;
import BlackJack.model.Game;
import BlackJack.view.Input;

import java.util.concurrent.TimeUnit;

public class PlayGame implements Observer {
    private IView a_view;
    private Game a_game;

    public PlayGame(Game game, IView view){
        this.a_view = view;
        this.a_game = game;
        SetObserver(a_game.GetDealer());
    }

 private void SetObserver(Subject dealer){
     dealer.Attach(this);
 }

  public boolean Play() throws InterruptedException {
    a_view.DisplayWelcomeMessage();
      a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
      a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());

    if (a_game.IsGameOver())
    {
        a_view.DisplayGameOver(a_game.IsDealerWinner());
    }

    Input input = a_view.GetInput();

    if (input == Input.Play)
    {
        a_game.NewGame();
    }
    else if (input == Input.Hit)
    {
        a_game.Hit();
    }
    else if (input == Input.Stand)
    {
        a_game.Stand();
    }

    return input != Input.Quit;
  }


  @Override
  public void Update() throws InterruptedException {
    a_view.DisplayDealerHand(a_game.GetDealerHand(), a_game.GetDealerScore());
      TimeUnit.SECONDS.sleep(3);
    a_view.DisplayPlayerHand(a_game.GetPlayerHand(), a_game.GetPlayerScore());
      TimeUnit.SECONDS.sleep(3);
  }
}