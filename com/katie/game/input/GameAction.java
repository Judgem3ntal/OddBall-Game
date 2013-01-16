/*     */ package com.katie.game.input;
/*     */ 
/*     */ public class GameAction
/*     */ {
/*     */   public static final int NORMAL = 0;
/*     */   public static final int DETECT_INITAL_PRESS_ONLY = 1;
/*     */   private static final int STATE_RELEASED = 0;
/*     */   private static final int STATE_PRESSED = 1;
/*     */   private static final int STATE_WAITING_FOR_RELEASE = 2;
/*     */   private String name;
/*     */   private int behavior;
/*     */   private int amount;
/*     */   private int state;
/*     */ 
/*     */   public GameAction(String name)
/*     */   {
/*  31 */     this(name, 0);
/*     */   }
/*     */ 
/*     */   public GameAction(String name, int behavior)
/*     */   {
/*  39 */     this.name = name;
/*  40 */     this.behavior = behavior;
/*  41 */     reset();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  49 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  58 */     this.state = 0;
/*  59 */     this.amount = 0;
/*     */   }
/*     */ 
/*     */   public synchronized void tap()
/*     */   {
/*  68 */     press();
/*  69 */     release();
/*     */   }
/*     */ 
/*     */   public synchronized void press()
/*     */   {
/*  77 */     press(1);
/*     */   }
/*     */ 
/*     */   public synchronized void press(int amount)
/*     */   {
/*  86 */     if (this.state != 2) {
/*  87 */       this.amount += amount;
/*  88 */       this.state = 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void release()
/*     */   {
/*  98 */     this.state = 0;
/*     */   }
/*     */ 
/*     */   public synchronized boolean isPressed()
/*     */   {
/* 107 */     return getAmount() != 0;
/*     */   }
/*     */ 
/*     */   public synchronized int getAmount()
/*     */   {
/* 117 */     int retVal = this.amount;
/* 118 */     if (retVal != 0) {
/* 119 */       if (this.state == 0) {
/* 120 */         this.amount = 0;
/*     */       }
/* 122 */       else if (this.behavior == 1) {
/* 123 */         this.state = 2;
/* 124 */         this.amount = 0;
/*     */       }
/*     */     }
/* 127 */     return retVal;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.input.GameAction
 * JD-Core Version:    0.6.0
 */