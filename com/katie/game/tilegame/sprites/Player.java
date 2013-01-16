/*    */ package com.katie.game.tilegame.sprites;
/*    */ 
/*    */ import com.katie.game.graphics.Animation;
/*    */ 
/*    */ public class Player extends Creature
/*    */ {
/*    */   private static final float JUMP_SPEED = -0.95F;
/*    */   private boolean onGround;
/*    */ 
/*    */   public Player(Animation left, Animation right, Animation deadLeft, Animation deadRight)
/*    */   {
/* 17 */     super(left, right, deadLeft, deadRight);
/*    */   }
/*    */ 
/*    */   public void collideHorizontal()
/*    */   {
/* 22 */     setVelocityX(0.0F);
/*    */   }
/*    */ 
/*    */   public void collideVertical()
/*    */   {
/* 28 */     if (getVelocityY() > 0.0F) {
/* 29 */       this.onGround = true;
/*    */     }
/* 31 */     setVelocityY(0.0F);
/*    */   }
/*    */ 
/*    */   public void setY(float y)
/*    */   {
/* 37 */     if (Math.round(y) > Math.round(getY())) {
/* 38 */       this.onGround = false;
/*    */     }
/* 40 */     super.setY(y);
/*    */   }
/*    */ 
/*    */   public void wakeUp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void jump(boolean forceJump)
/*    */   {
/* 54 */     if ((this.onGround) || (forceJump)) {
/* 55 */       this.onGround = false;
/* 56 */       setVelocityY(-0.95F);
/*    */     }
/*    */   }
/*    */ 
/*    */   public float getMaxSpeed()
/*    */   {
/* 62 */     return 0.5F;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.sprites.Player
 * JD-Core Version:    0.6.0
 */