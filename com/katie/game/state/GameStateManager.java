/*    */ package com.katie.game.state;
/*    */ 
/*    */ import com.katie.game.input.InputManager;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GameStateManager
/*    */ {
/*    */   public static final String EXIT_GAME = "_ExitGame";
/*    */   private Map gameStates;
/*    */   private Image defaultImage;
/*    */   private GameState currentState;
/*    */   private InputManager inputManager;
/*    */   private boolean done;
/*    */ 
/*    */   public GameStateManager(InputManager inputManager, Image defaultImage)
/*    */   {
/* 21 */     this.inputManager = inputManager;
/* 22 */     this.defaultImage = defaultImage;
/* 23 */     this.gameStates = new HashMap();
/*    */   }
/*    */ 
/*    */   public void addState(GameState state) {
/* 27 */     this.gameStates.put(state.getName(), state);
/*    */   }
/*    */ 
/*    */   public Iterator getStates() {
/* 31 */     return this.gameStates.values().iterator();
/*    */   }
/*    */ 
/*    */   public void loadAllResources(ResourceManager resourceManager) {
/* 35 */     Iterator i = getStates();
/* 36 */     while (i.hasNext()) {
/* 37 */       GameState gameState = (GameState)i.next();
/* 38 */       gameState.loadResources(resourceManager);
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean isDone()
/*    */   {
/* 44 */     return this.done;
/*    */   }
/*    */ 
/*    */   public void setState(String name)
/*    */   {
/* 53 */     if (this.currentState != null) {
/* 54 */       this.currentState.stop();
/*    */     }
/* 56 */     this.inputManager.clearAllMaps();
/*    */ 
/* 58 */     if (name == "_ExitGame") {
/* 59 */       this.done = true;
/*    */     }
/*    */     else
/*    */     {
/* 64 */       this.currentState = ((GameState)this.gameStates.get(name));
/* 65 */       if (this.currentState != null)
/* 66 */         this.currentState.start(this.inputManager);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void update(long elapsedTime)
/*    */   {
/* 77 */     if (this.currentState == null) {
/*    */       try {
/* 79 */         Thread.sleep(100L);
/*    */       } catch (InterruptedException localInterruptedException) {
/*    */       }
/*    */     }
/*    */     else {
/* 84 */       String nextState = this.currentState.checkForStateChange();
/* 85 */       if (nextState != null) {
/* 86 */         setState(nextState);
/*    */       }
/*    */       else
/* 89 */         this.currentState.update(elapsedTime);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void draw(Graphics2D g)
/*    */   {
/* 99 */     if (this.currentState != null) {
/* 100 */       this.currentState.draw(g);
/*    */     }
/*    */     else
/*    */     {
/* 104 */       g.drawImage(this.defaultImage, 0, 0, null);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.state.GameStateManager
 * JD-Core Version:    0.6.0
 */