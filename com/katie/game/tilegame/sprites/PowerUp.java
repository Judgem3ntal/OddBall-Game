/*    */ package com.katie.game.tilegame.sprites;
/*    */ 
/*    */ import com.katie.game.graphics.Animation;
/*    */ import com.katie.game.graphics.Sprite;
/*    */ import java.lang.reflect.Constructor;
/*    */ 
/*    */ public abstract class PowerUp extends Sprite
/*    */ {
/*    */   public PowerUp(Animation anim)
/*    */   {
/* 12 */     super(anim);
/*    */   }
/*    */ 
/*    */   public Object clone()
/*    */   {
/* 18 */     Constructor constructor = getClass().getConstructors()[0];
/*    */     try {
/* 20 */       return constructor.newInstance(
/* 21 */         new Object[] { (Animation)this.anim.clone() });
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 25 */       ex.printStackTrace();
/* 26 */     }return null;
/*    */   }
/*    */ 
/*    */   public static class Goal extends PowerUp
/*    */   {
/*    */     public Goal(Animation anim)
/*    */     {
/* 56 */       super();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class Music extends PowerUp
/*    */   {
/*    */     public Music(Animation anim)
/*    */     {
/* 46 */       super();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class Star extends PowerUp
/*    */   {
/*    */     public Star(Animation anim)
/*    */     {
/* 36 */       super();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.sprites.PowerUp
 * JD-Core Version:    0.6.0
 */