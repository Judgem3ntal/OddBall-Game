/*     */ package com.katie.game.graphics;
/*     */ 
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Window;
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import javax.swing.JFrame;
/*     */ 
/*     */ public class ScreenManager
/*     */ {
/*     */   private GraphicsDevice device;
/*     */ 
/*     */   public ScreenManager()
/*     */   {
/*  21 */     GraphicsEnvironment environment = 
/*  22 */       GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  23 */     this.device = environment.getDefaultScreenDevice();
/*     */   }
/*     */ 
/*     */   public DisplayMode[] getCompatibleDisplayModes()
/*     */   {
/*  32 */     return this.device.getDisplayModes();
/*     */   }
/*     */ 
/*     */   public DisplayMode findFirstCompatibleMode(DisplayMode[] modes)
/*     */   {
/*  43 */     DisplayMode[] goodModes = this.device.getDisplayModes();
/*  44 */     for (int i = 0; i < modes.length; i++) {
/*  45 */       for (int j = 0; j < goodModes.length; j++) {
/*  46 */         if (displayModesMatch(modes[i], goodModes[j])) {
/*  47 */           return modes[i];
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   public DisplayMode getCurrentDisplayMode()
/*     */   {
/*  61 */     return this.device.getDisplayMode();
/*     */   }
/*     */ 
/*     */   public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2)
/*     */   {
/*  78 */     if ((mode1.getWidth() != mode2.getWidth()) || 
/*  79 */       (mode1.getHeight() != mode2.getHeight()))
/*     */     {
/*  81 */       return false;
/*     */     }
/*     */ 
/*  84 */     if ((mode1.getBitDepth() != -1) && 
/*  85 */       (mode2.getBitDepth() != -1) && 
/*  86 */       (mode1.getBitDepth() != mode2.getBitDepth()))
/*     */     {
/*  88 */       return false;
/*     */     }
/*     */ 
/*  91 */     if (mode1.getRefreshRate() != 0)
/*     */     {
/*  93 */       if (mode2.getRefreshRate() != 0)
/*     */       {
/*  95 */         if (mode1.getRefreshRate() != mode2.getRefreshRate())
/*     */         {
/*  97 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public void setFullScreen(DisplayMode displayMode)
/*     */   {
/* 113 */     JFrame frame = new JFrame();
/* 114 */     frame.setDefaultCloseOperation(3);
/* 115 */     frame.setUndecorated(true);
/* 116 */     frame.setIgnoreRepaint(true);
/* 117 */     frame.setResizable(false);
/*     */ 
/* 119 */     this.device.setFullScreenWindow(frame);
/*     */ 
/* 121 */     if ((displayMode != null) && 
/* 122 */       (this.device.isDisplayChangeSupported()))
/*     */     {
/*     */       try {
/* 125 */         this.device.setDisplayMode(displayMode);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/*     */       }
/* 129 */       frame.setSize(displayMode.getWidth(), 
/* 130 */         displayMode.getHeight());
/*     */     }
/*     */     try
/*     */     {
/* 134 */       EventQueue.invokeAndWait(new Runnable(frame) {
/*     */         public void run() {
/* 136 */           this.val$frame.createBufferStrategy(2);
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public Graphics2D getGraphics()
/*     */   {
/* 159 */     Window window = this.device.getFullScreenWindow();
/* 160 */     if (window != null) {
/* 161 */       BufferStrategy strategy = window.getBufferStrategy();
/* 162 */       return (Graphics2D)strategy.getDrawGraphics();
/*     */     }
/*     */ 
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/* 174 */     Window window = this.device.getFullScreenWindow();
/* 175 */     if (window != null) {
/* 176 */       BufferStrategy strategy = window.getBufferStrategy();
/* 177 */       if (!strategy.contentsLost())
/* 178 */         strategy.show();
/*     */     }
/*     */   }
/*     */ 
/*     */   public JFrame getFullScreenWindow()
/*     */   {
/* 192 */     return (JFrame)this.device.getFullScreenWindow();
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 202 */     Window window = this.device.getFullScreenWindow();
/* 203 */     if (window != null) {
/* 204 */       return window.getWidth();
/*     */     }
/*     */ 
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 218 */     Window window = this.device.getFullScreenWindow();
/* 219 */     if (window != null) {
/* 220 */       return window.getHeight();
/*     */     }
/*     */ 
/* 223 */     return 0;
/*     */   }
/*     */ 
/*     */   public void restoreScreen()
/*     */   {
/* 232 */     Window window = this.device.getFullScreenWindow();
/* 233 */     if (window != null) {
/* 234 */       window.dispose();
/*     */     }
/* 236 */     this.device.setFullScreenWindow(null);
/*     */   }
/*     */ 
/*     */   public BufferedImage createCompatibleImage(int w, int h, int transparancy)
/*     */   {
/* 246 */     Window window = this.device.getFullScreenWindow();
/* 247 */     if (window != null) {
/* 248 */       GraphicsConfiguration gc = 
/* 249 */         window.getGraphicsConfiguration();
/* 250 */       return gc.createCompatibleImage(w, h, transparancy);
/*     */     }
/* 252 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.graphics.ScreenManager
 * JD-Core Version:    0.6.0
 */