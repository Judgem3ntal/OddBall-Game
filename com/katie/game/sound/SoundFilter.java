/*    */ package com.katie.game.sound;
/*    */ 
/*    */ public abstract class SoundFilter
/*    */ {
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public int getRemainingSize()
/*    */   {
/* 20 */     return 0;
/*    */   }
/*    */ 
/*    */   public void filter(byte[] samples)
/*    */   {
/* 29 */     filter(samples, 0, samples.length);
/*    */   }
/*    */ 
/*    */   public abstract void filter(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*    */ 
/*    */   public static short getSample(byte[] buffer, int position)
/*    */   {
/* 48 */     return (short)(
/* 49 */       (buffer[(position + 1)] & 0xFF) << 8 | 
/* 50 */       buffer[position] & 0xFF);
/*    */   }
/*    */ 
/*    */   public static void setSample(byte[] buffer, int position, short sample)
/*    */   {
/* 62 */     buffer[position] = (byte)(sample & 0xFF);
/* 63 */     buffer[(position + 1)] = (byte)(sample >> 8 & 0xFF);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.SoundFilter
 * JD-Core Version:    0.6.0
 */