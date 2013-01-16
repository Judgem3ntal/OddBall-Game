/*     */ package com.katie.game.tilegame;
/*     */ 
/*     */ import com.katie.game.graphics.ScreenManager;
/*     */ import com.katie.game.input.InputManager;
/*     */ import com.katie.game.sound.MidiPlayer;
/*     */ import com.katie.game.sound.SoundManager;
/*     */ import com.katie.game.state.GameStateManager;
/*     */ import com.katie.game.state.ResourceManager;
/*     */ import com.katie.game.test.GameCore;
/*     */ import com.katie.game.util.TimeSmoothie;
/*     */ import java.awt.Graphics2D;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.swing.JFrame;
/*     */ 
/*     */ public class GameManager extends GameCore
/*     */ {
/*  19 */   static final Logger log = Logger.getLogger("com.brackeen.javagamebook.tilegame");
/*     */ 
/*  27 */   private static final AudioFormat PLAYBACK_FORMAT = new AudioFormat(44100.0F, 16, 1, true, false);
/*     */   private MidiPlayer midiPlayer;
/*     */   private SoundManager soundManager;
/*     */   private ResourceManager resourceManager;
/*     */   private InputManager inputManager;
/*     */   private GameStateManager gameStateManager;
/*  35 */   private TimeSmoothie timeSmoothie = new TimeSmoothie();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  22 */     new GameManager().run();
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  38 */     log.setLevel(Level.INFO);
/*     */ 
/*  40 */     log.info("init sound manager");
/*  41 */     this.soundManager = new SoundManager(PLAYBACK_FORMAT, 8);
/*     */ 
/*  43 */     log.info("init midi player");
/*  44 */     this.midiPlayer = new MidiPlayer();
/*     */ 
/*  46 */     log.info("init gamecore");
/*  47 */     super.init();
/*     */ 
/*  49 */     log.info("init input manager");
/*  50 */     this.inputManager = 
/*  51 */       new InputManager(this.screen.getFullScreenWindow());
/*  52 */     this.inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
/*     */ 
/*  55 */     log.info("init resource manager");
/*  56 */     this.resourceManager = 
/*  58 */       new TileGameResourceManager(this.screen.getFullScreenWindow().getGraphicsConfiguration(), 
/*  58 */       this.soundManager, this.midiPlayer);
/*     */ 
/*  61 */     log.info("init game states");
/*  62 */     this.gameStateManager = 
/*  63 */       new GameStateManager(this.inputManager, 
/*  63 */       this.resourceManager.loadImage("loadingsplash.jpg"));
/*  64 */     this.gameStateManager.addState(
/*  66 */       new MainGameState(this.soundManager, this.midiPlayer, 
/*  66 */       this.screen.getWidth(), this.screen.getHeight()));
/*  67 */     this.gameStateManager.addState(
/*  68 */       new SplashGameState("gamesplash.jpg"));
/*     */ 
/*  71 */     new Thread() {
/*     */       public void run() {
/*  73 */         GameManager.log.info("loading resources");
/*  74 */         GameManager.this.gameStateManager.loadAllResources(GameManager.this.resourceManager);
/*  75 */         GameManager.log.info("setting to Splash state");
/*  76 */         GameManager.this.gameStateManager.setState("Splash");
/*     */       }
/*     */     }
/*  78 */     .start();
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*  86 */     log.info("stopping game");
/*  87 */     super.stop();
/*  88 */     log.info("closing midi player");
/*  89 */     this.midiPlayer.close();
/*  90 */     log.info("closing sound manager");
/*  91 */     this.soundManager.close();
/*     */   }
/*     */ 
/*     */   public void update(long elapsedTime) {
/*  95 */     if (this.gameStateManager.isDone()) {
/*  96 */       stop();
/*     */     }
/*     */     else {
/*  99 */       elapsedTime = this.timeSmoothie.getTime(elapsedTime);
/* 100 */       this.gameStateManager.update(elapsedTime);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void draw(Graphics2D g)
/*     */   {
/* 106 */     this.gameStateManager.draw(g);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.GameManager
 * JD-Core Version:    0.6.0
 */