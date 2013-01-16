/*     */ package com.katie.game.tilegame;
/*     */ 
/*     */ import com.katie.game.graphics.Sprite;
/*     */ import com.katie.game.tilegame.sprites.Creature;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class TileMapRenderer
/*     */ {
/*     */   private static final int TILE_SIZE = 64;
/*     */   private static final int TILE_SIZE_BITS = 6;
/*     */   private Image background;
/*     */ 
/*     */   public static int pixelsToTiles(float pixels)
/*     */   {
/*  36 */     return pixelsToTiles(Math.round(pixels));
/*     */   }
/*     */ 
/*     */   public static int pixelsToTiles(int pixels)
/*     */   {
/*  45 */     return pixels >> 6;
/*     */   }
/*     */ 
/*     */   public static int tilesToPixels(int numTiles)
/*     */   {
/*  60 */     return numTiles << 6;
/*     */   }
/*     */ 
/*     */   public void setBackground(Image background)
/*     */   {
/*  71 */     this.background = background;
/*     */   }
/*     */ 
/*     */   public void draw(Graphics2D g, TileMap map, int screenWidth, int screenHeight)
/*     */   {
/*  81 */     Sprite player = map.getPlayer();
/*  82 */     int mapWidth = tilesToPixels(map.getWidth());
/*     */ 
/*  86 */     int offsetX = screenWidth / 2 - 
/*  87 */       Math.round(player.getX()) - 64;
/*  88 */     offsetX = Math.min(offsetX, 0);
/*  89 */     offsetX = Math.max(offsetX, screenWidth - mapWidth);
/*     */ 
/*  92 */     int offsetY = screenHeight - 
/*  93 */       tilesToPixels(map.getHeight());
/*     */ 
/*  96 */     if ((this.background == null) || 
/*  97 */       (screenHeight > this.background.getHeight(null)))
/*     */     {
/*  99 */       g.setColor(Color.black);
/* 100 */       g.fillRect(0, 0, screenWidth, screenHeight);
/*     */     }
/*     */ 
/* 104 */     if (this.background != null) {
/* 105 */       int x = offsetX * (
/* 106 */         screenWidth - this.background.getWidth(null)) / (
/* 107 */         screenWidth - mapWidth);
/* 108 */       int y = screenHeight - this.background.getHeight(null);
/*     */ 
/* 110 */       g.drawImage(this.background, x, y, null);
/*     */     }
/*     */ 
/* 114 */     int firstTileX = pixelsToTiles(-offsetX);
/* 115 */     int lastTileX = firstTileX + 
/* 116 */       pixelsToTiles(screenWidth) + 1;
/* 117 */     for (int y = 0; y < map.getHeight(); y++) {
/* 118 */       for (int x = firstTileX; x <= lastTileX; x++) {
/* 119 */         Image image = map.getTile(x, y);
/* 120 */         if (image != null) {
/* 121 */           g.drawImage(image, 
/* 122 */             tilesToPixels(x) + offsetX, 
/* 123 */             tilesToPixels(y) + offsetY, 
/* 124 */             null);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 130 */     g.drawImage(player.getImage(), 
/* 131 */       Math.round(player.getX()) + offsetX, 
/* 132 */       Math.round(player.getY()) + offsetY, 
/* 133 */       null);
/*     */ 
/* 136 */     Iterator i = map.getSprites();
/* 137 */     while (i.hasNext()) {
/* 138 */       Sprite sprite = (Sprite)i.next();
/* 139 */       int x = Math.round(sprite.getX()) + offsetX;
/* 140 */       int y = Math.round(sprite.getY()) + offsetY;
/* 141 */       g.drawImage(sprite.getImage(), x, y, null);
/*     */ 
/* 144 */       if ((!(sprite instanceof Creature)) || 
/* 145 */         (x < 0) || (x >= screenWidth))
/*     */         continue;
/* 147 */       ((Creature)sprite).wakeUp();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.TileMapRenderer
 * JD-Core Version:    0.6.0
 */