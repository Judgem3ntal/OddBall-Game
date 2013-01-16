/*     */ package com.katie.game.tilegame;
/*     */ 
/*     */ import com.katie.game.graphics.Sprite;
/*     */ import java.awt.Image;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class TileMap
/*     */ {
/*     */   private Image[][] tiles;
/*     */   private LinkedList sprites;
/*     */   private Sprite player;
/*     */ 
/*     */   public TileMap(int width, int height)
/*     */   {
/*  26 */     this.tiles = new Image[width][height];
/*  27 */     this.sprites = new LinkedList();
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  35 */     return this.tiles.length;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  43 */     return this.tiles[0].length;
/*     */   }
/*     */ 
/*     */   public Image getTile(int x, int y)
/*     */   {
/*  53 */     if ((x < 0) || (x >= getWidth()) || 
/*  54 */       (y < 0) || (y >= getHeight()))
/*     */     {
/*  56 */       return null;
/*     */     }
/*     */ 
/*  59 */     return this.tiles[x][y];
/*     */   }
/*     */ 
/*     */   public void setTile(int x, int y, Image tile)
/*     */   {
/*  68 */     this.tiles[x][y] = tile;
/*     */   }
/*     */ 
/*     */   public Sprite getPlayer()
/*     */   {
/*  76 */     return this.player;
/*     */   }
/*     */ 
/*     */   public void setPlayer(Sprite player)
/*     */   {
/*  84 */     this.player = player;
/*     */   }
/*     */ 
/*     */   public void addSprite(Sprite sprite)
/*     */   {
/*  92 */     this.sprites.add(sprite);
/*     */   }
/*     */ 
/*     */   public void removeSprite(Sprite sprite)
/*     */   {
/* 100 */     this.sprites.remove(sprite);
/*     */   }
/*     */ 
/*     */   public Iterator getSprites()
/*     */   {
/* 109 */     return this.sprites.iterator();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.tilegame.TileMap
 * JD-Core Version:    0.6.0
 */