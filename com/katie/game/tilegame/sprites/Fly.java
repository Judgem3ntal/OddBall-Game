/*    */ package com.katie.game.tilegame.sprites;
/*    */ 
/*    */ import com.katie.game.graphics.Animation;
/*    */ 
/*    */ public class Fly extends Creature
/*    */ {
/*    */   public Fly(Animation left, Animation right, Animation deadLeft, Animation deadRight)
/*    */   {
/* 13 */     super(left, right, deadLeft, deadRight);
/*    */   }
/*    */ 
/*    */   public float getMaxSpeed()
/*    */   {
/* 18 */     return 0.2F;
/*    */   }
/*    */ 
/*    */   public boolean isFlying()
/*    */   {
/* 23 */     return isAlive();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.sprites.Fly
 * JD-Core Version:    0.6.0
 */