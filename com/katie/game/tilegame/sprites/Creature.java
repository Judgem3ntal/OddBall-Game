/*     */ package com.katie.game.tilegame.sprites;
/*     */ 
/*     */ import com.katie.game.graphics.Animation;
/*     */ import com.katie.game.graphics.Sprite;
/*     */ import java.lang.reflect.Constructor;
/*     */ 
/*     */ public abstract class Creature extends Sprite
/*     */ {
/*     */   private static final int DIE_TIME = 1000;
/*     */   public static final int STATE_NORMAL = 0;
/*     */   public static final int STATE_DYING = 1;
/*     */   public static final int STATE_DEAD = 2;
/*     */   private Animation left;
/*     */   private Animation right;
/*     */   private Animation deadLeft;
/*     */   private Animation deadRight;
/*     */   private int state;
/*     */   private long stateTime;
/*     */ 
/*     */   public Creature(Animation left, Animation right, Animation deadLeft, Animation deadRight)
/*     */   {
/*  35 */     super(right);
/*  36 */     this.left = left;
/*  37 */     this.right = right;
/*  38 */     this.deadLeft = deadLeft;
/*  39 */     this.deadRight = deadRight;
/*  40 */     this.state = 0;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*  46 */     Constructor constructor = getClass().getConstructors()[0];
/*     */     try {
/*  48 */       return constructor.newInstance(new Object[] { 
/*  49 */         (Animation)this.left.clone(), 
/*  50 */         (Animation)this.right.clone(), 
/*  51 */         (Animation)this.deadLeft.clone(), 
/*  52 */         (Animation)this.deadRight.clone() });
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  57 */       ex.printStackTrace();
/*  58 */     }return null;
/*     */   }
/*     */ 
/*     */   public float getMaxSpeed()
/*     */   {
/*  67 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void wakeUp()
/*     */   {
/*  76 */     if ((getState() == 0) && (getVelocityX() == 0.0F))
/*  77 */       setVelocityX(-getMaxSpeed());
/*     */   }
/*     */ 
/*     */   public int getState()
/*     */   {
/*  87 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(int state)
/*     */   {
/*  96 */     if (this.state != state) {
/*  97 */       this.state = state;
/*  98 */       this.stateTime = 0L;
/*  99 */       if (state == 1) {
/* 100 */         setVelocityX(0.0F);
/* 101 */         setVelocityY(0.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isAlive()
/*     */   {
/* 111 */     return this.state == 0;
/*     */   }
/*     */ 
/*     */   public boolean isFlying()
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   public void collideHorizontal()
/*     */   {
/* 128 */     setVelocityX(-getVelocityX());
/*     */   }
/*     */ 
/*     */   public void collideVertical()
/*     */   {
/* 137 */     setVelocityY(0.0F);
/*     */   }
/*     */ 
/*     */   public void update(long elapsedTime)
/*     */   {
/* 146 */     Animation newAnim = this.anim;
/* 147 */     if (getVelocityX() < 0.0F) {
/* 148 */       newAnim = this.left;
/*     */     }
/* 150 */     else if (getVelocityX() > 0.0F) {
/* 151 */       newAnim = this.right;
/*     */     }
/* 153 */     if ((this.state == 1) && (newAnim == this.left)) {
/* 154 */       newAnim = this.deadLeft;
/*     */     }
/* 156 */     else if ((this.state == 1) && (newAnim == this.right)) {
/* 157 */       newAnim = this.deadRight;
/*     */     }
/*     */ 
/* 161 */     if (this.anim != newAnim) {
/* 162 */       this.anim = newAnim;
/* 163 */       this.anim.start();
/*     */     }
/*     */     else {
/* 166 */       this.anim.update(elapsedTime);
/*     */     }
/*     */ 
/* 170 */     this.stateTime += elapsedTime;
/* 171 */     if ((this.state == 1) && (this.stateTime >= 1000L))
/* 172 */       setState(2);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.sprites.Creature
 * JD-Core Version:    0.6.0
 */