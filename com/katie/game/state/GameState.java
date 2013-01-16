package com.katie.game.state;

import com.katie.game.input.InputManager;
import java.awt.Graphics2D;

public abstract interface GameState
{
  public abstract String getName();

  public abstract String checkForStateChange();

  public abstract void loadResources(ResourceManager paramResourceManager);

  public abstract void start(InputManager paramInputManager);

  public abstract void stop();

  public abstract void update(long paramLong);

  public abstract void draw(Graphics2D paramGraphics2D);
}

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.state.GameState
 * JD-Core Version:    0.6.0
 */