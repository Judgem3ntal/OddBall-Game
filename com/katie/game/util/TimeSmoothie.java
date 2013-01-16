/*     */ package com.katie.game.util;
/*     */ 
/*     */ public class TimeSmoothie
/*     */ {
/*     */   protected static final long FRAME_RATE_RECALC_PERIOD = 500L;
/*     */   protected static final long MAX_ELAPSED_TIME = 100L;
/*     */   protected static final long AVERAGE_PERIOD = 100L;
/*     */   protected static final int NUM_SAMPLES_BITS = 6;
/*     */   protected static final int NUM_SAMPLES = 64;
/*     */   protected static final int NUM_SAMPLES_MASK = 63;
/*     */   protected long[] samples;
/*  25 */   protected int numSamples = 0;
/*  26 */   protected int firstIndex = 0;
/*     */ 
/*  29 */   protected int numFrames = 0;
/*     */   protected long startTime;
/*     */   protected float frameRate;
/*     */ 
/*     */   public TimeSmoothie()
/*     */   {
/*  34 */     this.samples = new long[64];
/*     */   }
/*     */ 
/*     */   public long getTime(long elapsedTime)
/*     */   {
/*  43 */     addSample(elapsedTime);
/*  44 */     return getAverage();
/*     */   }
/*     */ 
/*     */   public void addSample(long elapsedTime)
/*     */   {
/*  52 */     this.numFrames += 1;
/*     */ 
/*  55 */     elapsedTime = Math.min(elapsedTime, 100L);
/*     */ 
/*  58 */     this.samples[(this.firstIndex + this.numSamples & 0x3F)] = 
/*  59 */       elapsedTime;
/*  60 */     if (this.numSamples == this.samples.length) {
/*  61 */       this.firstIndex = (this.firstIndex + 1 & 0x3F);
/*     */     }
/*     */     else
/*  64 */       this.numSamples += 1;
/*     */   }
/*     */ 
/*     */   public long getAverage()
/*     */   {
/*  73 */     long sum = 0L;
/*  74 */     for (int i = this.numSamples - 1; i >= 0; i--) {
/*  75 */       sum += this.samples[(this.firstIndex + i & 0x3F)];
/*     */ 
/*  79 */       if (sum >= 100L) {
/*  80 */         Math.round(sum / (this.numSamples - i));
/*     */       }
/*     */     }
/*  83 */     return Math.round(sum / this.numSamples);
/*     */   }
/*     */ 
/*     */   public float getFrameRate()
/*     */   {
/*  93 */     long currTime = System.currentTimeMillis();
/*     */ 
/*  96 */     if (currTime > this.startTime + 500L) {
/*  97 */       this.frameRate = 
/*  98 */         (this.numFrames * 1000.0F / 
/*  98 */         (float)(currTime - this.startTime));
/*  99 */       this.startTime = currTime;
/* 100 */       this.numFrames = 0;
/*     */     }
/*     */ 
/* 103 */     return this.frameRate;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.util.TimeSmoothie
 * JD-Core Version:    0.6.0
 */