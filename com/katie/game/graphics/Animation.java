/*     */ package com.katie.game.graphics;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Animation
/*     */ {
/*     */   private ArrayList frames;
/*     */   private int currFrameIndex;
/*     */   private long animTime;
/*     */   private long totalDuration;
/*     */ 
/*     */   public Animation()
/*     */   {
/*  22 */     this(new ArrayList(), 0L);
/*     */   }
/*     */ 
/*     */   private Animation(ArrayList frames, long totalDuration)
/*     */   {
/*  27 */     this.frames = frames;
/*  28 */     this.totalDuration = totalDuration;
/*  29 */     start();
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*  39 */     return new Animation(this.frames, this.totalDuration);
/*     */   }
/*     */ 
/*     */   public synchronized void addFrame(Image image, long duration)
/*     */   {
/*  50 */     this.totalDuration += duration;
/*  51 */     this.frames.add(new AnimFrame(image, this.totalDuration));
/*     */   }
/*     */ 
/*     */   public synchronized void start()
/*     */   {
/*  59 */     this.animTime = 0L;
/*  60 */     this.currFrameIndex = 0;
/*     */   }
/*     */ 
/*     */   public synchronized void update(long elapsedTime)
/*     */   {
/*  69 */     if (this.frames.size() > 1) {
/*  70 */       this.animTime += elapsedTime;
/*     */ 
/*  72 */       if (this.animTime >= this.totalDuration) {
/*  73 */         this.animTime %= this.totalDuration;
/*  74 */         this.currFrameIndex = 0;
/*     */       }
/*     */ 
/*  77 */       while (this.animTime > getFrame(this.currFrameIndex).endTime)
/*  78 */         this.currFrameIndex += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized Image getImage()
/*     */   {
/*  89 */     if (this.frames.size() == 0) {
/*  90 */       return null;
/*     */     }
/*     */ 
/*  93 */     return getFrame(this.currFrameIndex).image;
/*     */   }
/*     */ 
/*     */   private AnimFrame getFrame(int i)
/*     */   {
/*  99 */     return (AnimFrame)this.frames.get(i);
/*     */   }
/*     */ 
/*     */   private class AnimFrame {
/*     */     Image image;
/*     */     long endTime;
/*     */ 
/*     */     public AnimFrame(Image image, long endTime) {
/* 109 */       this.image = image;
/* 110 */       this.endTime = endTime;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.graphics.Animation
 * JD-Core Version:    0.6.0
 */