/*    */ package com.katie.game.sound;
/*    */ 
/*    */ public class FilterSequence extends SoundFilter
/*    */ {
/*    */   private SoundFilter[] filters;
/*    */ 
/*    */   public FilterSequence(SoundFilter[] filters)
/*    */   {
/* 13 */     this.filters = filters;
/*    */   }
/*    */ 
/*    */   public int getRemainingSize()
/*    */   {
/* 22 */     int max = 0;
/* 23 */     for (int i = 0; i < this.filters.length; i++) {
/* 24 */       max = Math.max(max, this.filters[i].getRemainingSize());
/*    */     }
/* 26 */     return max;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/* 34 */     for (int i = 0; i < this.filters.length; i++)
/* 35 */       this.filters[i].reset();
/*    */   }
/*    */ 
/*    */   public void filter(byte[] samples, int offset, int length)
/*    */   {
/* 45 */     for (int i = 0; i < this.filters.length; i++)
/* 46 */       this.filters[i].filter(samples, offset, length);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.FilterSequence
 * JD-Core Version:    0.6.0
 */