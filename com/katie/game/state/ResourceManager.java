/*     */ package com.katie.game.state;
/*     */ 
/*     */ import com.katie.game.sound.MidiPlayer;
/*     */ import com.katie.game.sound.Sound;
/*     */ import com.katie.game.sound.SoundManager;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class ResourceManager
/*     */ {
/*     */   private GraphicsConfiguration gc;
/*     */   private SoundManager soundManager;
/*     */   private MidiPlayer midiPlayer;
/*     */ 
/*     */   public ResourceManager(GraphicsConfiguration gc, SoundManager soundManager, MidiPlayer midiPlayer)
/*     */   {
/*  29 */     this.gc = gc;
/*  30 */     this.soundManager = soundManager;
/*  31 */     this.midiPlayer = midiPlayer;
/*     */     try
/*     */     {
/*  34 */       Enumeration e = getClass().getClassLoader().getResources("com.katie.game.state.ResourceManager");
/*  35 */       while (e.hasMoreElements())
/*  36 */         System.out.println(e.nextElement());
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public Image loadImage(String name)
/*     */   {
/*  51 */     String filename = "images/" + name;
/*  52 */     return new ImageIcon(getResource(filename)).getImage();
/*     */   }
/*     */ 
/*     */   public Image getMirrorImage(Image image)
/*     */   {
/*  57 */     return getScaledImage(image, -1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   public Image getFlippedImage(Image image)
/*     */   {
/*  62 */     return getScaledImage(image, 1.0F, -1.0F);
/*     */   }
/*     */ 
/*     */   private Image getScaledImage(Image image, float x, float y)
/*     */   {
/*  69 */     AffineTransform transform = new AffineTransform();
/*  70 */     transform.scale(x, y);
/*  71 */     transform.translate(
/*  72 */       (x - 1.0F) * image.getWidth(null) / 2.0F, 
/*  73 */       (y - 1.0F) * image.getHeight(null) / 2.0F);
/*     */ 
/*  76 */     Image newImage = this.gc.createCompatibleImage(
/*  77 */       image.getWidth(null), 
/*  78 */       image.getHeight(null), 
/*  79 */       2);
/*     */ 
/*  82 */     Graphics2D g = (Graphics2D)newImage.getGraphics();
/*  83 */     g.drawImage(image, transform, null);
/*  84 */     g.dispose();
/*     */ 
/*  86 */     return newImage;
/*     */   }
/*     */ 
/*     */   public URL getResource(String filename)
/*     */   {
/*  91 */     return getClass().getClassLoader().getResource(filename);
/*     */   }
/*     */ 
/*     */   public InputStream getResourceAsStream(String filename) {
/*  95 */     return getClass().getClassLoader()
/*  96 */       .getResourceAsStream(filename);
/*     */   }
/*     */ 
/*     */   public Sound loadSound(String name)
/*     */   {
/* 101 */     return this.soundManager.getSound(getResourceAsStream(name));
/*     */   }
/*     */ 
/*     */   public Sequence loadSequence(String name)
/*     */   {
/* 106 */     return this.midiPlayer.getSequence(getResourceAsStream(name));
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.state.ResourceManager
 * JD-Core Version:    0.6.0
 */
