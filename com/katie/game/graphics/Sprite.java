/*     */ package com.katie.game.graphics;
/*     */ 
/*     */ import java.awt.Image;
/*     */ 
/*     */ public class Sprite
/*     */ {
/*     */   protected Animation anim;
/*     */   private float x;
/*     */   private float y;
/*     */   private float dx;
/*     */   private float dy;
/*     */ 
/*     */   public Sprite(Animation anim)
/*     */   {
/*  19 */     this.anim = anim;
/*     */   }
/*     */ 
/*     */   public void update(long elapsedTime)
/*     */   {
/*  27 */     this.x += this.dx * (float)elapsedTime;
/*  28 */     this.y += this.dy * (float)elapsedTime;
/*  29 */     this.anim.update(elapsedTime);
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/*  36 */     return this.x;
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/*  43 */     return this.y;
/*     */   }
/*     */ 
/*     */   public void setX(float x)
/*     */   {
/*  50 */     this.x = x;
/*     */   }
/*     */ 
/*     */   public void setY(float y)
/*     */   {
/*  57 */     this.y = y;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  65 */     return this.anim.getImage().getWidth(null);
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  73 */     return this.anim.getImage().getHeight(null);
/*     */   }
/*     */ 
/*     */   public float getVelocityX()
/*     */   {
/*  81 */     return this.dx;
/*     */   }
/*     */ 
/*     */   public float getVelocityY()
/*     */   {
/*  89 */     return this.dy;
/*     */   }
/*     */ 
/*     */   public void setVelocityX(float dx)
/*     */   {
/*  97 */     this.dx = dx;
/*     */   }
/*     */ 
/*     */   public void setVelocityY(float dy)
/*     */   {
/* 105 */     this.dy = dy;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/* 112 */     return this.anim.getImage();
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 120 */     return new Sprite(this.anim);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.graphics.Sprite
 * JD-Core Version:    0.6.0
 */