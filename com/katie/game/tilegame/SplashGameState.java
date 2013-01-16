/*    */ package com.katie.game.tilegame;
/*    */ 
/*    */ import com.katie.game.input.GameAction;
/*    */ import com.katie.game.input.InputManager;
/*    */ import com.katie.game.state.GameState;
/*    */ import com.katie.game.state.ResourceManager;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ 
/*    */ public class SplashGameState
/*    */   implements GameState
/*    */ {
/*    */   private String splashFilename;
/*    */   private Image splash;
/*    */   private GameAction exitSplash;
/*    */   private long totalElapsedTime;
/*    */   private boolean done;
/*    */ 
/*    */   public SplashGameState(String splashFilename)
/*    */   {
/* 19 */     this.exitSplash = 
/* 20 */       new GameAction("exitSplash", 
/* 20 */       1);
/* 21 */     this.splashFilename = splashFilename;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 25 */     return "Splash";
/*    */   }
/*    */ 
/*    */   public void loadResources(ResourceManager resourceManager) {
/* 29 */     this.splash = resourceManager.loadImage(this.splashFilename);
/*    */   }
/*    */ 
/*    */   public String checkForStateChange()
/*    */   {
/* 34 */     return this.done ? "Main" : null;
/*    */   }
/*    */ 
/*    */   public void start(InputManager inputManager)
/*    */   {
/* 39 */     inputManager.mapToKey(this.exitSplash, 32);
/* 40 */     inputManager.mapToMouse(this.exitSplash, 
/* 41 */       6);
/*    */ 
/* 43 */     this.totalElapsedTime = 0L;
/* 44 */     this.done = false;
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void update(long elapsedTime) {
/* 52 */     this.totalElapsedTime += elapsedTime;
/* 53 */     if ((this.totalElapsedTime > 3000L) || (this.exitSplash.isPressed()))
/* 54 */       this.done = true;
/*    */   }
/*    */ 
/*    */   public void draw(Graphics2D g)
/*    */   {
/* 59 */     g.drawImage(this.splash, 0, 0, null);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.SplashGameState
 * JD-Core Version:    0.6.0
 */