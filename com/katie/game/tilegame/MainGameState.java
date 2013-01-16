/*     */ package com.katie.game.tilegame;
/*     */ 
/*     */ import com.katie.game.graphics.Sprite;
/*     */ import com.katie.game.input.GameAction;
/*     */ import com.katie.game.input.InputManager;
/*     */ import com.katie.game.sound.EchoFilter;
/*     */ import com.katie.game.sound.MidiPlayer;
/*     */ import com.katie.game.sound.Sound;
/*     */ import com.katie.game.sound.SoundManager;
/*     */ import com.katie.game.state.GameState;
/*     */ import com.katie.game.state.ResourceManager;
/*     */ import com.katie.game.tilegame.sprites.Creature;
/*     */ import com.katie.game.tilegame.sprites.Player;
/*     */ import com.katie.game.tilegame.sprites.PowerUp;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Goal;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Music;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Star;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.util.Iterator;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.Sequencer;
/*     */ 
/*     */ public class MainGameState
/*     */   implements GameState
/*     */ {
/*     */   private static final int DRUM_TRACK = 1;
/*     */   public static final float GRAVITY = 0.002F;
/*     */   private SoundManager soundManager;
/*     */   private MidiPlayer midiPlayer;
/*     */   private TileGameResourceManager resourceManager;
/*     */   private int width;
/*     */   private int height;
/*  29 */   private Point pointCache = new Point();
/*     */   private Sound prizeSound;
/*     */   private Sound boopSound;
/*     */   private Sequence music;
/*     */   private TileMap map;
/*     */   private TileMapRenderer renderer;
/*     */   private String stateChange;
/*     */   private GameAction moveLeft;
/*     */   private GameAction moveRight;
/*     */   private GameAction jump;
/*     */   private GameAction exit;
/*     */ 
/*     */   public MainGameState(SoundManager soundManager, MidiPlayer midiPlayer, int width, int height)
/*     */   {
/*  46 */     this.soundManager = soundManager;
/*  47 */     this.midiPlayer = midiPlayer;
/*  48 */     this.width = width;
/*  49 */     this.height = height;
/*  50 */     this.moveLeft = new GameAction("moveLeft");
/*  51 */     this.moveRight = new GameAction("moveRight");
/*  52 */     this.jump = 
/*  53 */       new GameAction("jump", 
/*  53 */       1);
/*  54 */     this.exit = 
/*  55 */       new GameAction("exit", 
/*  55 */       1);
/*     */ 
/*  57 */     this.renderer = new TileMapRenderer();
/*  58 */     toggleDrumPlayback();
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  62 */     return "Main";
/*     */   }
/*     */ 
/*     */   public String checkForStateChange()
/*     */   {
/*  67 */     return this.stateChange;
/*     */   }
/*     */ 
/*     */   public void loadResources(ResourceManager resManager)
/*     */   {
/*  72 */     this.resourceManager = ((TileGameResourceManager)resManager);
/*     */ 
/*  74 */     this.resourceManager.loadResources();
/*     */ 
/*  76 */     this.renderer.setBackground(
/*  77 */       this.resourceManager.loadImage("background.png"));
/*     */ 
/*  80 */     this.map = this.resourceManager.loadNextMap();
/*     */ 
/*  83 */     this.prizeSound = this.resourceManager.loadSound("sounds/prize.wav");
/*  84 */     this.boopSound = this.resourceManager.loadSound("sounds/boop2.wav");
/*  85 */     this.music = this.resourceManager.loadSequence("sounds/music.wav");
/*     */   }
/*     */ 
/*     */   public void start(InputManager inputManager) {
/*  89 */     inputManager.mapToKey(this.moveLeft, 37);
/*  90 */     inputManager.mapToKey(this.moveRight, 39);
/*  91 */     inputManager.mapToKey(this.jump, 32);
/*  92 */     inputManager.mapToKey(this.jump, 38);
/*  93 */     inputManager.mapToKey(this.exit, 27);
/*     */ 
/*  95 */     this.soundManager.setPaused(false);
/*  96 */     this.midiPlayer.setPaused(false);
/*  97 */     this.midiPlayer.play(this.music, true);
/*  98 */     toggleDrumPlayback();
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 102 */     this.soundManager.setPaused(true);
/* 103 */     this.midiPlayer.setPaused(true);
/*     */   }
/*     */ 
/*     */   public void draw(Graphics2D g)
/*     */   {
/* 108 */     this.renderer.draw(g, this.map, this.width, this.height);
/*     */   }
/*     */ 
/*     */   public void toggleDrumPlayback()
/*     */   {
/* 116 */     Sequencer sequencer = this.midiPlayer.getSequencer();
/* 117 */     if (sequencer != null)
/* 118 */       sequencer.setTrackMute(1, 
/* 119 */         !sequencer.getTrackMute(1));
/*     */   }
/*     */ 
/*     */   private void checkInput(long elapsedTime)
/*     */   {
/* 125 */     if (this.exit.isPressed()) {
/* 126 */       this.stateChange = "_ExitGame";
/* 127 */       return;
/*     */     }
/*     */ 
/* 130 */     Player player = (Player)this.map.getPlayer();
/* 131 */     if (player.isAlive()) {
/* 132 */       float velocityX = 0.0F;
/* 133 */       if (this.moveLeft.isPressed()) {
/* 134 */         velocityX -= player.getMaxSpeed();
/*     */       }
/* 136 */       if (this.moveRight.isPressed()) {
/* 137 */         velocityX += player.getMaxSpeed();
/*     */       }
/* 139 */       if (this.jump.isPressed()) {
/* 140 */         player.jump(false);
/*     */       }
/* 142 */       player.setVelocityX(velocityX);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point getTileCollision(Sprite sprite, float newX, float newY)
/*     */   {
/* 156 */     float fromX = Math.min(sprite.getX(), newX);
/* 157 */     float fromY = Math.min(sprite.getY(), newY);
/* 158 */     float toX = Math.max(sprite.getX(), newX);
/* 159 */     float toY = Math.max(sprite.getY(), newY);
/*     */ 
/* 162 */     int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
/* 163 */     int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
/* 164 */     int toTileX = TileMapRenderer.pixelsToTiles(
/* 165 */       toX + sprite.getWidth() - 1.0F);
/* 166 */     int toTileY = TileMapRenderer.pixelsToTiles(
/* 167 */       toY + sprite.getHeight() - 1.0F);
/*     */ 
/* 170 */     for (int x = fromTileX; x <= toTileX; x++) {
/* 171 */       for (int y = fromTileY; y <= toTileY; y++) {
/* 172 */         if ((x >= 0) && (x < this.map.getWidth()) && 
/* 173 */           (this.map.getTile(x, y) == null)) {
/*     */           continue;
/*     */         }
/* 176 */         this.pointCache.setLocation(x, y);
/* 177 */         return this.pointCache;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isCollision(Sprite s1, Sprite s2)
/*     */   {
/* 194 */     if (s1 == s2) {
/* 195 */       return false;
/*     */     }
/*     */ 
/* 199 */     if (((s1 instanceof Creature)) && (!((Creature)s1).isAlive())) {
/* 200 */       return false;
/*     */     }
/* 202 */     if (((s2 instanceof Creature)) && (!((Creature)s2).isAlive())) {
/* 203 */       return false;
/*     */     }
/*     */ 
/* 207 */     int s1x = Math.round(s1.getX());
/* 208 */     int s1y = Math.round(s1.getY());
/* 209 */     int s2x = Math.round(s2.getX());
/* 210 */     int s2y = Math.round(s2.getY());
/*     */ 
/* 216 */     return (s1x < s2x + s2.getWidth()) && 
/* 214 */       (s2x < s1x + s1.getWidth()) && 
/* 215 */       (s1y < s2y + s2.getHeight()) && 
/* 216 */       (s2y < s1y + s1.getHeight());
/*     */   }
/*     */ 
/*     */   public Sprite getSpriteCollision(Sprite sprite)
/*     */   {
/* 227 */     Iterator i = this.map.getSprites();
/* 228 */     while (i.hasNext()) {
/* 229 */       Sprite otherSprite = (Sprite)i.next();
/* 230 */       if (isCollision(sprite, otherSprite))
/*     */       {
/* 232 */         return otherSprite;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   public void update(long elapsedTime)
/*     */   {
/* 246 */     Creature player = (Creature)this.map.getPlayer();
/*     */ 
/* 250 */     if (player.getState() == 2) {
/* 251 */       this.map = this.resourceManager.reloadMap();
/* 252 */       return;
/*     */     }
/*     */ 
/* 256 */     checkInput(elapsedTime);
/*     */ 
/* 259 */     updateCreature(player, elapsedTime);
/* 260 */     player.update(elapsedTime);
/*     */ 
/* 263 */     Iterator i = this.map.getSprites();
/* 264 */     while (i.hasNext()) {
/* 265 */       Sprite sprite = (Sprite)i.next();
/* 266 */       if ((sprite instanceof Creature)) {
/* 267 */         Creature creature = (Creature)sprite;
/* 268 */         if (creature.getState() == 2) {
/* 269 */           i.remove();
/*     */         }
/*     */         else {
/* 272 */           updateCreature(creature, elapsedTime);
/*     */         }
/*     */       }
/*     */ 
/* 276 */       sprite.update(elapsedTime);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateCreature(Creature creature, long elapsedTime)
/*     */   {
/* 290 */     if (!creature.isFlying()) {
/* 291 */       creature.setVelocityY(creature.getVelocityY() + 
/* 292 */         0.002F * (float)elapsedTime);
/*     */     }
/*     */ 
/* 296 */     float dx = creature.getVelocityX();
/* 297 */     float oldX = creature.getX();
/* 298 */     float newX = oldX + dx * (float)elapsedTime;
/* 299 */     Point tile = 
/* 300 */       getTileCollision(creature, newX, creature.getY());
/* 301 */     if (tile == null) {
/* 302 */       creature.setX(newX);
/*     */     }
/*     */     else
/*     */     {
/* 306 */       if (dx > 0.0F) {
/* 307 */         creature.setX(
/* 308 */           TileMapRenderer.tilesToPixels(tile.x) - 
/* 309 */           creature.getWidth());
/*     */       }
/* 311 */       else if (dx < 0.0F) {
/* 312 */         creature.setX(
/* 313 */           TileMapRenderer.tilesToPixels(tile.x + 1));
/*     */       }
/* 315 */       creature.collideHorizontal();
/*     */     }
/* 317 */     if ((creature instanceof Player)) {
/* 318 */       checkPlayerCollision((Player)creature, false);
/*     */     }
/*     */ 
/* 322 */     float dy = creature.getVelocityY();
/* 323 */     float oldY = creature.getY();
/* 324 */     float newY = oldY + dy * (float)elapsedTime;
/* 325 */     tile = getTileCollision(creature, creature.getX(), newY);
/* 326 */     if (tile == null) {
/* 327 */       creature.setY(newY);
/*     */     }
/*     */     else
/*     */     {
/* 331 */       if (dy > 0.0F) {
/* 332 */         creature.setY(
/* 333 */           TileMapRenderer.tilesToPixels(tile.y) - 
/* 334 */           creature.getHeight());
/*     */       }
/* 336 */       else if (dy < 0.0F) {
/* 337 */         creature.setY(
/* 338 */           TileMapRenderer.tilesToPixels(tile.y + 1));
/*     */       }
/* 340 */       creature.collideVertical();
/*     */     }
/* 342 */     if ((creature instanceof Player)) {
/* 343 */       boolean canKill = oldY < creature.getY();
/* 344 */       checkPlayerCollision((Player)creature, canKill);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void checkPlayerCollision(Player player, boolean canKill)
/*     */   {
/* 358 */     if (!player.isAlive()) {
/* 359 */       return;
/*     */     }
/*     */ 
/* 363 */     Sprite collisionSprite = getSpriteCollision(player);
/* 364 */     if ((collisionSprite instanceof PowerUp)) {
/* 365 */       acquirePowerUp((PowerUp)collisionSprite);
/*     */     }
/* 367 */     else if ((collisionSprite instanceof Creature)) {
/* 368 */       Creature badguy = (Creature)collisionSprite;
/* 369 */       if (canKill)
/*     */       {
/* 371 */         this.soundManager.play(this.boopSound);
/* 372 */         badguy.setState(1);
/* 373 */         player.setY(badguy.getY() - player.getHeight());
/* 374 */         player.jump(true);
/*     */       }
/*     */       else
/*     */       {
/* 378 */         player.setState(1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void acquirePowerUp(PowerUp powerUp)
/*     */   {
/* 390 */     this.map.removeSprite(powerUp);
/*     */ 
/* 392 */     if ((powerUp instanceof PowerUp.Star))
/*     */     {
/* 394 */       this.soundManager.play(this.prizeSound);
/*     */     }
/* 396 */     else if ((powerUp instanceof PowerUp.Music))
/*     */     {
/* 398 */       this.soundManager.play(this.prizeSound);
/* 399 */       toggleDrumPlayback();
/*     */     }
/* 401 */     else if ((powerUp instanceof PowerUp.Goal))
/*     */     {
/* 403 */       this.soundManager.play(this.prizeSound, 
/* 404 */         new EchoFilter(2000, 0.7F), false);
/* 405 */       this.map = this.resourceManager.loadNextMap();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.MainGameState
 * JD-Core Version:    0.6.0
 */