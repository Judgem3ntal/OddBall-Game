/*     */ package com.katie.game.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaEventListener;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiSystem;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.Sequencer;
/*     */ 
/*     */ public class MidiPlayer
/*     */   implements MetaEventListener
/*     */ {
/*     */   public static final int END_OF_TRACK_MESSAGE = 47;
/*     */   private Sequencer sequencer;
/*     */   private boolean loop;
/*     */   private boolean paused;
/*     */ 
/*     */   public MidiPlayer()
/*     */   {
/*     */     try
/*     */     {
/*  20 */       this.sequencer = MidiSystem.getSequencer();
/*  21 */       this.sequencer.open();
/*  22 */       this.sequencer.addMetaEventListener(this);
/*     */     }
/*     */     catch (MidiUnavailableException ex) {
/*  25 */       this.sequencer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Sequence getSequence(String filename)
/*     */   {
/*     */     try
/*     */     {
/*  36 */       return getSequence(new FileInputStream(filename));
/*     */     }
/*     */     catch (IOException ex) {
/*  39 */       ex.printStackTrace();
/*  40 */     }return null;
/*     */   }
/*     */ 
/*     */   public Sequence getSequence(InputStream is)
/*     */   {
/*     */     try
/*     */     {
/*  51 */       if (!is.markSupported()) {
/*  52 */         is = new BufferedInputStream(is);
/*     */       }
/*  54 */       Sequence s = MidiSystem.getSequence(is);
/*  55 */       is.close();
/*  56 */       return s;
/*     */     }
/*     */     catch (InvalidMidiDataException ex) {
/*  59 */       ex.printStackTrace();
/*  60 */       return null;
/*     */     }
/*     */     catch (IOException ex) {
/*  63 */       ex.printStackTrace();
/*  64 */     }return null;
/*     */   }
/*     */ 
/*     */   public void play(Sequence sequence, boolean loop)
/*     */   {
/*  74 */     if ((this.sequencer != null) && (sequence != null) && (this.sequencer.isOpen()))
/*     */       try {
/*  76 */         this.sequencer.setSequence(sequence);
/*  77 */         this.sequencer.start();
/*  78 */         this.loop = loop;
/*     */       }
/*     */       catch (InvalidMidiDataException ex) {
/*  81 */         ex.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public void meta(MetaMessage event)
/*     */   {
/*  94 */     if ((event.getType() == 47) && 
/*  95 */       (this.sequencer != null) && (this.sequencer.isOpen()) && (this.loop)) {
/*  96 */       this.sequencer.setTickPosition(0L);
/*  97 */       this.sequencer.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 107 */     if ((this.sequencer != null) && (this.sequencer.isOpen())) {
/* 108 */       this.sequencer.stop();
/* 109 */       this.sequencer.setMicrosecondPosition(0L);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 118 */     if ((this.sequencer != null) && (this.sequencer.isOpen()))
/* 119 */       this.sequencer.close();
/*     */   }
/*     */ 
/*     */   public Sequencer getSequencer()
/*     */   {
/* 128 */     return this.sequencer;
/*     */   }
/*     */ 
/*     */   public void setPaused(boolean paused)
/*     */   {
/* 136 */     if ((this.paused != paused) && (this.sequencer != null) && (this.sequencer.isOpen())) {
/* 137 */       this.paused = paused;
/* 138 */       if (paused) {
/* 139 */         this.sequencer.stop();
/*     */       }
/*     */       else
/* 142 */         this.sequencer.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isPaused()
/*     */   {
/* 152 */     return this.paused;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.sound.MidiPlayer
 * JD-Core Version:    0.6.0
 */