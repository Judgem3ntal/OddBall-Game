/*    */ package com.katie.game.sound;
/*    */ 
/*    */ public class EchoFilter extends SoundFilter
/*    */ {
/*    */   private short[] delayBuffer;
/*    */   private int delayBufferPos;
/*    */   private float decay;
/*    */ 
/*    */   public EchoFilter(int numDelaySamples, float decay)
/*    */   {
/* 20 */     this.delayBuffer = new short[numDelaySamples];
/* 21 */     this.decay = decay;
/*    */   }
/*    */ 
/*    */   public int getRemainingSize()
/*    */   {
/* 32 */     float finalDecay = 0.01F;
/*    */ 
/* 34 */     int numRemainingBuffers = (int)Math.ceil(
/* 35 */       Math.log(finalDecay) / Math.log(this.decay));
/* 36 */     int bufferSize = this.delayBuffer.length * 2;
/*    */ 
/* 38 */     return bufferSize * numRemainingBuffers;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/* 46 */     for (int i = 0; i < this.delayBuffer.length; i++) {
/* 47 */       this.delayBuffer[i] = 0;
/*    */     }
/* 49 */     this.delayBufferPos = 0;
/*    */   }
/*    */ 
/*    */   public void filter(byte[] samples, int offset, int length)
/*    */   {
/* 61 */     for (int i = offset; i < offset + length; i += 2)
/*    */     {
/* 63 */       short oldSample = getSample(samples, i);
/* 64 */       short newSample = (short)(int)(oldSample + this.decay * 
/* 65 */         this.delayBuffer[this.delayBufferPos]);
/* 66 */       setSample(samples, i, newSample);
/*    */ 
/* 69 */       this.delayBuffer[this.delayBufferPos] = newSample;
/* 70 */       this.delayBufferPos += 1;
/* 71 */       if (this.delayBufferPos == this.delayBuffer.length)
/* 72 */         this.delayBufferPos = 0;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.EchoFilter
 * JD-Core Version:    0.6.0
 */