/*     */ package com.katie.game.tilegame;
/*     */ 
/*     */ import com.katie.game.graphics.Animation;
/*     */ import com.katie.game.graphics.Sprite;
/*     */ import com.katie.game.sound.MidiPlayer;
/*     */ import com.katie.game.sound.SoundManager;
/*     */ import com.katie.game.state.ResourceManager;
/*     */ import com.katie.game.tilegame.sprites.Fly;
/*     */ import com.katie.game.tilegame.sprites.Ground;
/*     */ import com.katie.game.tilegame.sprites.Player;
/*     */ import com.katie.game.tilegame.sprites.Plant;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Goal;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Music;
/*     */ import com.katie.game.tilegame.sprites.PowerUp.Star;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class TileGameResourceManager extends ResourceManager
/*     */ {
/*     */   private ArrayList tiles;
/*     */   private int currentMap;
/*     */   private Sprite playerSprite;
/*     */   private Sprite musicSprite;
/*     */   private Sprite coinSprite;
/*     */   private Sprite goalSprite;
/*     */   private Sprite grubSprite;
/*     */   private Sprite flySprite;
/*     */   private Sprite plantSprite;
/*     */ 
/*     */   public TileGameResourceManager(GraphicsConfiguration gc, SoundManager soundManager, MidiPlayer midiPlayer)
/*     */   {
/*  40 */     super(gc, soundManager, midiPlayer);
/*     */   }
/*     */ 
/*     */   public void loadResources()
/*     */   {
/*  45 */     loadTileImages();
/*  46 */     loadCreatureSprites();
/*  47 */     loadPowerUpSprites();
/*     */   }
/*     */ 
/*     */   public TileMap loadNextMap()
/*     */   {
/*  52 */     TileMap map = null;
/*  53 */     while (map == null) {
/*  54 */       this.currentMap += 1;
/*     */       try {
/*  56 */         map = loadMap(
/*  57 */           "maps/map" + this.currentMap + ".txt");
/*     */       }
/*     */       catch (IOException ex) {
/*  60 */         if (this.currentMap == 1)
/*     */         {
/*  62 */           return null;
/*     */         }
/*  64 */         this.currentMap = 0;
/*  65 */         map = null;
/*     */       }
/*     */     }
/*     */ 
/*  69 */     return map;
/*     */   }
/*     */ 
/*     */   public TileMap reloadMap()
/*     */   {
/*     */     try {
/*  75 */       return loadMap(
/*  76 */         "maps/map" + this.currentMap + ".txt");
/*     */     }
/*     */     catch (IOException ex) {
/*  79 */       ex.printStackTrace();
/*  80 */     }return null;
/*     */   }
/*     */ 
/*     */   private TileMap loadMap(String filename)
/*     */     throws IOException
/*     */   {
/*  88 */     ArrayList lines = new ArrayList();
/*  89 */     int width = 0;
/*  90 */     int height = 0;
/*     */ 
/*  92 */     ClassLoader classLoader = getClass().getClassLoader();
/*  93 */     URL url = classLoader.getResource(filename);
/*  94 */     if (url == null) {
/*  95 */       throw new IOException("No such map: " + filename);
/*     */     }
/*     */ 
/*  99 */     BufferedReader reader = new BufferedReader(
/* 100 */       new InputStreamReader(url.openStream()));
/*     */     while (true) {
/* 102 */       String line = reader.readLine();
/*     */ 
/* 104 */       if (line == null) {
/* 105 */         reader.close();
/* 106 */         break;
/*     */       }
/*     */ 
/* 110 */       if (!line.startsWith("#")) {
/* 111 */         lines.add(line);
/* 112 */         width = Math.max(width, line.length());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 117 */     height = lines.size();
/* 118 */     TileMap newMap = new TileMap(width, height);
/* 119 */     for (int y = 0; y < height; y++) {
/* 120 */       String line = (String)lines.get(y);
/* 121 */       for (int x = 0; x < line.length(); x++) {
/* 122 */         char ch = line.charAt(x);
/*     */ 
/* 125 */         int tile = ch - 'A';
/* 126 */         if ((tile >= 0) && (tile < this.tiles.size())) {
/* 127 */           newMap.setTile(x, y, (Image)this.tiles.get(tile));
/*     */         }
/* 131 */         else if (ch == 'o') {
/* 132 */           addSprite(newMap, this.coinSprite, x, y);
/*     */         }
/* 134 */         else if (ch == '!') {
/* 135 */           addSprite(newMap, this.musicSprite, x, y);
/*     */         }
/* 137 */         else if (ch == '*') {
/* 138 */           addSprite(newMap, this.goalSprite, x, y);
/*     */         }
/* 140 */         else if (ch == '1') {
/* 141 */           addSprite(newMap, this.grubSprite, x, y);
/*     */         }
/* 143 */         else if (ch == '2') {
/* 144 */           addSprite(newMap, this.flySprite, x, y);
/*     */         }
/*     */         else if (ch == '3') {
/*     */           addSprite(newMap, this.plantSprite, x, y);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 150 */     Sprite player = (Sprite)this.playerSprite.clone();
/* 151 */     player.setX(TileMapRenderer.tilesToPixels(3));
/* 152 */     player.setY(0.0F);
/* 153 */     newMap.setPlayer(player);
/*     */ 
/* 155 */     return newMap;
/*     */   }
/*     */ 
/*     */   private void addSprite(TileMap map, Sprite hostSprite, int tileX, int tileY)
/*     */   {
/* 162 */     if (hostSprite != null)
/*     */     {
/* 164 */       Sprite sprite = (Sprite)hostSprite.clone();
/*     */ 
/* 167 */       sprite.setX(
/* 168 */         TileMapRenderer.tilesToPixels(tileX) + 
/* 169 */         (TileMapRenderer.tilesToPixels(1) - 
/* 170 */         sprite.getWidth()) / 2);
/*     */ 
/* 173 */       sprite.setY(
/* 174 */         TileMapRenderer.tilesToPixels(tileY + 1) - 
/* 175 */         sprite.getHeight());
/*     */ 
/* 178 */       map.addSprite(sprite);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadTileImages()
/*     */   {
/* 191 */     this.tiles = new ArrayList();
/* 192 */     char ch = 'A';
/*     */     while (true) {
/* 194 */       String name = "tile_" + ch + ".png";
/* 195 */       ClassLoader classLoader = getClass().getClassLoader();
/* 196 */       URL url = classLoader.getResource("images/" + name);
/* 197 */       if (url == null) {
/*     */         break;
/*     */       }
/* 200 */       this.tiles.add(loadImage(name));
/* 201 */       ch = (char)(ch + '\001');
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadCreatureSprites()
/*     */   {
/* 208 */     Image[][] images = new Image[4][];
/*     */ 
/* 211 */     images[0] = { 
/*     */       loadImage("plant1.png"),
/*     */       loadImage("plant2.png"),
/*     */       loadImage("plant3.png"),
/* 212 */       loadImage("player1.png"), 
/* 213 */       loadImage("player2.png"), 
/* 214 */       loadImage("player3.png"), 
/* 215 */       loadImage("triangle1.png"), 
/* 216 */       loadImage("triangle2.png"), 
/* 217 */       loadImage("triangle3.png"), 
/* 218 */       loadImage("grub1.png"), 
/* 219 */       loadImage("grub2.png") };
/*     */ 
/* 222 */     images[1] = new Image[images[0].length];
/* 223 */     images[2] = new Image[images[0].length];
/* 224 */     images[3] = new Image[images[0].length];
/* 225 */     for (int i = 0; i < images[0].length; i++)
/*     */     {
/* 227 */       images[1][i] = getMirrorImage(images[0][i]);
/*     */ 
/* 229 */       images[2][i] = getFlippedImage(images[0][i]);
/*     */ 
/* 231 */       images[3][i] = getFlippedImage(images[1][i]);
/*     */     }
/*     */ 
/* 235 */     Animation[] playerAnim = new Animation[4];
/* 236 */     Animation[] flyAnim = new Animation[4];
/* 237 */     Animation[] grubAnim = new Animation[4];
/*     */     Animation[] plantAnim = new Animation[4];
/* 238 */     for (int i = 0; i < 4; i++) {
/* 239 */       playerAnim[i] = createPlayerAnim(
/* 240 */         images[i][0], images[i][1], images[i][2]);
/* 241 */       flyAnim[i] = createFlyAnim(
/* 242 */         images[i][3], images[i][4], images[i][5]);
/* 243 */       grubAnim[i] = createGrubAnim(
/* 244 */         images[i][6], images[i][7]);
/*     */       plantAnim = createPlantAnim(images[i][8], images[i][9], images[i][10]);
/*     */     }
/*     */ 
/* 248 */     this.playerSprite = 
/* 249 */       new Player(playerAnim[0], playerAnim[1], 
/* 249 */       playerAnim[2], playerAnim[3]);
/* 250 */     this.flySprite = 
/* 251 */       new Fly(flyAnim[0], flyAnim[1], 
/* 251 */       flyAnim[2], flyAnim[3]);
/* 252 */     this.grubSprite = 
/* 253 */       new Ground(grubAnim[0], grubAnim[1], 
/* 253 */       grubAnim[2], grubAnim[3]);
/*     */     this.plantSpirte =
/*     */      new Plant(plantAnim[0], plantAnim[1],
/*     */      plantAnim[2], plantAnim[3]);
/*     */
/*     */   }
/*     */ 
/*     */   private Animation createPlantAnim(Image plant1, Image plant2, Image plant3) {
/*     */    Animation anim = new Animation();
              anim.addFrame(plant1, )
}
/*     */   private Animation createPlayerAnim(Image player1, Image player2, Image player3)
/*     */   {
/* 260 */     Animation anim = new Animation();
/* 261 */     anim.addFrame(player1, 150L);
/* 262 */     anim.addFrame(player2, 150L);
/* 263 */     anim.addFrame(player3, 200L);
/* 264 */     anim.addFrame(player3, 200L);
/* 265 */     anim.addFrame(player2, 150L);
/* 266 */     anim.addFrame(player1, 150L);
/* 267 */     return anim;
/*     */   }
/*     */ 
/*     */   private Animation createFlyAnim(Image img1, Image img2, Image img3)
/*     */   {
/* 274 */     Animation anim = new Animation();
/* 275 */     anim.addFrame(img1, 50L);
/* 276 */     anim.addFrame(img2, 50L);
/* 277 */     anim.addFrame(img3, 50L);
/* 278 */     anim.addFrame(img2, 50L);
/* 279 */     return anim;
/*     */   }
/*     */ 
/*     */   private Animation createGrubAnim(Image img1, Image img2)
/*     */   {
/* 284 */     Animation anim = new Animation();
/* 285 */     anim.addFrame(img1, 250L);
/* 286 */     anim.addFrame(img2, 250L);
/* 287 */     return anim;
/*     */   }
/*     */ 
/*     */   private void loadPowerUpSprites()
/*     */   {
/* 293 */     Animation anim = new Animation();
/* 294 */     anim.addFrame(loadImage("thing1.png"), 150L);
/* 295 */     anim.addFrame(loadImage("thing2.png"), 150L);
/* 296 */     anim.addFrame(loadImage("thing3.png"), 150L);
/* 297 */     anim.addFrame(loadImage("thing2.png"), 150L);
/* 298 */     this.goalSprite = new PowerUp.Goal(anim);
/*     */ 
/* 301 */     anim = new Animation();
/* 302 */     anim.addFrame(loadImage("star1.png"), 100L);
/* 303 */     anim.addFrame(loadImage("star2.png"), 100L);
/* 304 */     anim.addFrame(loadImage("star3.png"), 100L);
/* 305 */     anim.addFrame(loadImage("star4.png"), 100L);
/* 306 */     this.coinSprite = new PowerUp.Star(anim);
/*     */ 
/* 309 */     anim = new Animation();
/* 310 */     anim.addFrame(loadImage("music1.png"), 150L);
/* 311 */     anim.addFrame(loadImage("music2.png"), 150L);
/* 312 */     anim.addFrame(loadImage("music3.png"), 150L);
/* 313 */     anim.addFrame(loadImage("music2.png"), 150L);
/* 314 */     this.musicSprite = new PowerUp.Music(anim);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.TileGameResourceManager
 * JD-Core Version:    0.6.0
 */
