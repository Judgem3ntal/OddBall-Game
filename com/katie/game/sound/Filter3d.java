/*    */ package com.katie.game.sound;
/*    */ 
/*    */ import com.katie.game.graphics.Sprite;
/*    */ 
/*    */ public class Filter3d extends SoundFilter
/*    */ {
/*    */   private static final int NUM_SHIFTING_SAMPLES = 500;
/*    */   private Sprite source;
/*    */   private Sprite listener;
/*    */   private int maxDistance;
/*    */   private float lastVolume;
/*    */ 
/*    */   public Filter3d(Sprite source, Sprite listener, int maxDistance)
/*    */   {
/* 25 */     this.source = source;
/* 26 */     this.listener = listener;
/* 27 */     this.maxDistance = maxDistance;
/* 28 */     this.lastVolume = 0.0F;
/*    */   }
/*    */ 
/*    */   public void filter(byte[] samples, int offset, int length)
/*    */   {
/* 38 */     if ((this.source == null) || (this.listener == null))
/*    */     {
/* 40 */       return;
/*    */     }
/*    */ 
/* 44 */     float dx = this.source.getX() - this.listener.getX();
/* 45 */     float dy = this.source.getY() - this.listener.getY();
/* 46 */     float distance = (float)Math.sqrt(dx * dx + dy * dy);
/*    */ 
/* 49 */     float newVolume = (this.maxDistance - distance) / this.maxDistance;
/* 50 */     if (newVolume <= 0.0F) {
/* 51 */       newVolume = 0.0F;
/*    */     }
/*    */ 
/* 55 */     int shift = 0;
/* 56 */     for (int i = offset; i < offset + length; i += 2)
/*    */     {
/* 58 */       float volume = newVolume;
/*    */ 
/* 61 */       if (shift < 500) {
/* 62 */         volume = this.lastVolume + (newVolume - this.lastVolume) * 
/* 63 */           shift / 500.0F;
/* 64 */         shift++;
/*    */       }
/*    */ 
/* 68 */       short oldSample = getSample(samples, i);
/* 69 */       short newSample = (short)(int)(oldSample * volume);
/* 70 */       setSample(samples, i, newSample);
/*    */     }
/*    */ 
/* 73 */     this.lastVolume = newVolume;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.Filter3d
 * JD-Core Version:    0.6.0
 */