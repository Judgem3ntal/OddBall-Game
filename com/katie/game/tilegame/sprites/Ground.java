/*    */ package com.katie.game.tilegame.sprites;
/*    */ 
/*    */ import com.katie.game.graphics.Animation;
/*    */ 
/*    */ public class Ground extends Creature
/*    */ {
/*    */   public Ground(Animation left, Animation right, Animation deadLeft, Animation deadRight)
/*    */   {
/* 13 */     super(left, right, deadLeft, deadRight);
/*    */   }
/*    */ 
/*    */   public float getMaxSpeed()
/*    */   {
/* 18 */     return 0.05F;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.sprites.Ground
 * JD-Core Version:    0.6.0
 */