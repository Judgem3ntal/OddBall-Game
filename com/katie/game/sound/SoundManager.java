/*     */ package com.katie.game.sound;
/*     */ 
/*     */ import com.katie.game.util.LoopingByteInputStream;
/*     */ import com.katie.game.util.ThreadPool;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.DataLine.Info;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.Mixer;
/*     */ import javax.sound.sampled.SourceDataLine;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ 
/*     */ public class SoundManager extends ThreadPool
/*     */ {
/*     */   private AudioFormat playbackFormat;
/*     */   private ThreadLocal localLine;
/*     */   private ThreadLocal localBuffer;
/*     */   private Object pausedLock;
/*     */   private boolean paused;
/*     */ 
/*     */   public SoundManager(AudioFormat playbackFormat)
/*     */   {
/*  36 */     this(playbackFormat, 
/*  36 */       getMaxSimultaneousSounds(playbackFormat));
/*     */   }
/*     */ 
/*     */   public SoundManager(AudioFormat playbackFormat, int maxSimultaneousSounds)
/*     */   {
/*  48 */     super(Math.min(maxSimultaneousSounds, 
/*  48 */       getMaxSimultaneousSounds(playbackFormat)));
/*  49 */     this.playbackFormat = playbackFormat;
/*  50 */     this.localLine = new ThreadLocal();
/*  51 */     this.localBuffer = new ThreadLocal();
/*  52 */     this.pausedLock = new Object();
/*     */ 
/*  54 */     synchronized (this) {
/*  55 */       notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getMaxSimultaneousSounds(AudioFormat playbackFormat)
/*     */   {
/*  67 */     DataLine.Info lineInfo = new DataLine.Info(
/*  68 */       SourceDataLine.class, playbackFormat);
/*  69 */     Mixer mixer = AudioSystem.getMixer(null);
/*  70 */     return mixer.getMaxLines(lineInfo);
/*     */   }
/*     */ 
/*     */   protected void cleanUp()
/*     */   {
/*  79 */     setPaused(false);
/*     */ 
/*  82 */     Mixer mixer = AudioSystem.getMixer(null);
/*  83 */     if (mixer.isOpen())
/*  84 */       mixer.close();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*  90 */     cleanUp();
/*  91 */     super.close();
/*     */   }
/*     */ 
/*     */   public void join()
/*     */   {
/*  96 */     cleanUp();
/*  97 */     super.join();
/*     */   }
/*     */ 
/*     */   public void setPaused(boolean paused)
/*     */   {
/* 105 */     if (this.paused != paused)
/* 106 */       synchronized (this.pausedLock) {
/* 107 */         this.paused = paused;
/* 108 */         if (!paused)
/*     */         {
/* 110 */           this.pausedLock.notifyAll();
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean isPaused()
/*     */   {
/* 121 */     return this.paused;
/*     */   }
/*     */ 
/*     */   public Sound getSound(String filename)
/*     */   {
/* 130 */     return getSound(getAudioInputStream(filename));
/*     */   }
/*     */ 
/*     */   public Sound getSound(InputStream is)
/*     */   {
/* 139 */     return getSound(getAudioInputStream(is));
/*     */   }
/*     */ 
/*     */   public Sound getSound(AudioInputStream audioStream)
/*     */   {
/* 147 */     if (audioStream == null) {
/* 148 */       return null;
/*     */     }
/*     */ 
/* 152 */     int length = (int)(audioStream.getFrameLength() * 
/* 153 */       audioStream.getFormat().getFrameSize());
/*     */ 
/* 156 */     byte[] samples = new byte[length];
/* 157 */     DataInputStream is = new DataInputStream(audioStream);
/*     */     try {
/* 159 */       is.readFully(samples);
/* 160 */       is.close();
/*     */     }
/*     */     catch (IOException ex) {
/* 163 */       ex.printStackTrace();
/*     */     }
/*     */ 
/* 167 */     return new Sound(samples);
/*     */   }
/*     */ 
/*     */   public AudioInputStream getAudioInputStream(String filename)
/*     */   {
/*     */     try
/*     */     {
/* 177 */       return getAudioInputStream(
/* 178 */         new FileInputStream(filename));
/*     */     }
/*     */     catch (IOException ex) {
/* 181 */       ex.printStackTrace();
/* 182 */     }return null;
/*     */   }
/*     */ 
/*     */   public AudioInputStream getAudioInputStream(InputStream is)
/*     */   {
/*     */     try
/*     */     {
/* 194 */       if (!is.markSupported()) {
/* 195 */         is = new BufferedInputStream(is);
/*     */       }
/*     */ 
/* 198 */       AudioInputStream source = 
/* 199 */         AudioSystem.getAudioInputStream(is);
/*     */ 
/* 202 */       return AudioSystem.getAudioInputStream(
/* 203 */         this.playbackFormat, source);
/*     */     }
/*     */     catch (UnsupportedAudioFileException ex) {
/* 206 */       ex.printStackTrace();
/*     */     }
/*     */     catch (IOException ex) {
/* 209 */       ex.printStackTrace();
/*     */     }
/*     */     catch (IllegalArgumentException ex) {
/* 212 */       ex.printStackTrace();
/*     */     }
/*     */ 
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream play(Sound sound)
/*     */   {
/* 223 */     return play(sound, null, false);
/*     */   }
/*     */ 
/*     */   public InputStream play(Sound sound, SoundFilter filter, boolean loop)
/*     */   {
/* 235 */     if (sound != null)
/*     */     {
/*     */       InputStream is;
/*     */       InputStream is;
/* 236 */       if (loop) {
/* 237 */         is = new LoopingByteInputStream(
/* 238 */           sound.getSamples());
/*     */       }
/*     */       else {
/* 241 */         is = new ByteArrayInputStream(sound.getSamples());
/*     */       }
/*     */ 
/* 244 */       return play(is, filter);
/*     */     }
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream play(InputStream is)
/*     */   {
/* 255 */     return play(is, null);
/*     */   }
/*     */ 
/*     */   public InputStream play(InputStream is, SoundFilter filter)
/*     */   {
/* 264 */     if (is != null) {
/* 265 */       if (filter != null) {
/* 266 */         is = new FilteredSoundStream(is, filter);
/*     */       }
/* 268 */       runTask(new SoundPlayer(is));
/*     */     }
/* 270 */     return is;
/*     */   }
/*     */ 
/*     */   protected void threadStarted()
/*     */   {
/* 280 */     synchronized (this) {
/*     */       try {
/* 282 */         wait();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 289 */     int bufferSize = this.playbackFormat.getFrameSize() * 
/* 290 */       Math.round(this.playbackFormat.getSampleRate() / 10.0F);
/*     */ 
/* 294 */     DataLine.Info lineInfo = new DataLine.Info(
/* 295 */       SourceDataLine.class, this.playbackFormat);
/*     */     try {
/* 297 */       SourceDataLine line = (SourceDataLine)AudioSystem.getLine(lineInfo);
/* 298 */       line.open(this.playbackFormat, bufferSize);
/*     */     }
/*     */     catch (LineUnavailableException ex)
/*     */     {
/* 302 */       Thread.currentThread().interrupt();
/* 303 */       return;
/*     */     }
/*     */     SourceDataLine line;
/* 306 */     line.start();
/*     */ 
/* 309 */     byte[] buffer = new byte[bufferSize];
/*     */ 
/* 312 */     this.localLine.set(line);
/* 313 */     this.localBuffer.set(buffer);
/*     */   }
/*     */ 
/*     */   protected void threadStopped()
/*     */   {
/* 322 */     SourceDataLine line = (SourceDataLine)this.localLine.get();
/* 323 */     if (line != null) {
/* 324 */       line.drain();
/* 325 */       line.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class SoundPlayer
/*     */     implements Runnable
/*     */   {
/*     */     private InputStream source;
/*     */ 
/*     */     public SoundPlayer(InputStream source)
/*     */     {
/* 342 */       this.source = source;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 347 */       SourceDataLine line = (SourceDataLine)SoundManager.this.localLine.get();
/* 348 */       byte[] buffer = (byte[])SoundManager.this.localBuffer.get();
/* 349 */       if ((line == null) || (buffer == null))
/*     */       {
/* 351 */         return;
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 356 */         int numBytesRead = 0;
/* 357 */         while (numBytesRead != -1)
/*     */         {
/* 359 */           synchronized (SoundManager.this.pausedLock) {
/* 360 */             if (SoundManager.this.paused) {
/*     */               try {
/* 362 */                 SoundManager.this.pausedLock.wait();
/*     */               }
/*     */               catch (InterruptedException ex) {
/* 365 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 370 */           numBytesRead = 
/* 371 */             this.source.read(buffer, 0, buffer.length);
/* 372 */           if (numBytesRead != -1)
/* 373 */             line.write(buffer, 0, numBytesRead);
/*     */         }
/*     */       }
/*     */       catch (IOException ex)
/*     */       {
/* 378 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.SoundManager
 * JD-Core Version:    0.6.0
 */