/*    */ package com.katie.game.sound;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class FilteredSoundStream extends FilterInputStream
/*    */ {
/*    */   private static final int REMAINING_SIZE_UNKNOWN = -1;
/*    */   private SoundFilter soundFilter;
/*    */   private int remainingSize;
/*    */ 
/*    */   public FilteredSoundStream(InputStream in, SoundFilter soundFilter)
/*    */   {
/* 26 */     super(in);
/* 27 */     this.soundFilter = soundFilter;
/* 28 */     this.remainingSize = -1;
/*    */   }
/*    */ 
/*    */   public int read(byte[] samples, int offset, int length)
/*    */     throws IOException
/*    */   {
/* 40 */     int bytesRead = super.read(samples, offset, length);
/* 41 */     if (bytesRead > 0) {
/* 42 */       this.soundFilter.filter(samples, offset, bytesRead);
/* 43 */       return bytesRead;
/*    */     }
/*    */ 
/* 48 */     if (this.remainingSize == -1) {
/* 49 */       this.remainingSize = this.soundFilter.getRemainingSize();
/*    */ 
/* 52 */       this.remainingSize = (this.remainingSize / 4 * 4);
/*    */     }
/* 54 */     if (this.remainingSize > 0) {
/* 55 */       length = Math.min(length, this.remainingSize);
/*    */ 
/* 58 */       for (int i = offset; i < offset + length; i++) {
/* 59 */         samples[i] = 0;
/*    */       }
/*    */ 
/* 63 */       this.soundFilter.filter(samples, offset, length);
/* 64 */       this.remainingSize -= length;
/*    */ 
/* 67 */       return length;
/*    */     }
/*    */ 
/* 71 */     return -1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.FilteredSoundStream
 * JD-Core Version:    0.6.0
 */