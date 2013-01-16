/*     */ package com.katie.game.test;
/*     */ 
/*     */ import com.katie.game.graphics.ScreenManager;
/*     */ import java.awt.Color;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Window;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public abstract class GameCore
/*     */ {
/*     */   protected static final int FONT_SIZE = 24;
/*  16 */   private static final DisplayMode[] POSSIBLE_MODES = { 
/*  17 */     new DisplayMode(800, 600, 16, 0), 
/*  18 */     new DisplayMode(800, 600, 32, 0), 
/*  19 */     new DisplayMode(800, 600, 24, 0), 
/*  20 */     new DisplayMode(640, 480, 16, 0), 
/*  21 */     new DisplayMode(640, 480, 32, 0), 
/*  22 */     new DisplayMode(640, 480, 24, 0), 
/*  23 */     new DisplayMode(1024, 768, 16, 0), 
/*  24 */     new DisplayMode(1024, 768, 32, 0), 
/*  25 */     new DisplayMode(1024, 768, 24, 0) };
/*     */   private boolean isRunning;
/*     */   protected ScreenManager screen;
/*     */ 
/*     */   public void stop()
/*     */   {
/*  36 */     this.isRunning = false;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  45 */       init();
/*  46 */       gameLoop();
/*     */     }
/*     */     finally {
/*  49 */       this.screen.restoreScreen();
/*  50 */       lazilyExit();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void lazilyExit()
/*     */   {
/*  63 */     Thread thread = new Thread()
/*     */     {
/*     */       public void run() {
/*     */         try {
/*  67 */           Thread.sleep(2000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {
/*     */         }
/*  71 */         System.exit(0);
/*     */       }
/*     */     };
/*  74 */     thread.setDaemon(true);
/*  75 */     thread.start();
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  83 */     this.screen = new ScreenManager();
/*  84 */     DisplayMode displayMode = 
/*  85 */       this.screen.findFirstCompatibleMode(POSSIBLE_MODES);
/*  86 */     this.screen.setFullScreen(displayMode);
/*     */ 
/*  88 */     Window window = this.screen.getFullScreenWindow();
/*  89 */     window.setFont(new Font("Dialog", 0, 24));
/*  90 */     window.setBackground(Color.blue);
/*  91 */     window.setForeground(Color.white);
/*     */ 
/*  93 */     this.isRunning = true;
/*     */   }
/*     */ 
/*     */   public Image loadImage(String fileName)
/*     */   {
/*  98 */     return new ImageIcon(fileName).getImage();
/*     */   }
/*     */ 
/*     */   public void gameLoop()
/*     */   {
/* 106 */     long startTime = System.currentTimeMillis();
/* 107 */     long currTime = startTime;
/*     */ 
/* 109 */     while (this.isRunning) {
/* 110 */       long elapsedTime = 
/* 111 */         System.currentTimeMillis() - currTime;
/* 112 */       currTime += elapsedTime;
/*     */ 
/* 115 */       update(elapsedTime);
/*     */ 
/* 118 */       Graphics2D g = this.screen.getGraphics();
/* 119 */       draw(g);
/* 120 */       g.dispose();
/* 121 */       this.screen.update();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(long elapsedTime)
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract void draw(Graphics2D paramGraphics2D);
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.test.GameCore
 * JD-Core Version:    0.6.0
 */