/*     */ package com.katie.game.util;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class ThreadPool extends ThreadGroup
/*     */ {
/*     */   private boolean isAlive;
/*     */   private LinkedList taskQueue;
/*     */   private int threadID;
/*     */   private static int threadPoolID;
/*     */ 
/*     */   public ThreadPool(int numThreads)
/*     */   {
/*  21 */     super("ThreadPool-" + threadPoolID++);
/*  22 */     setDaemon(true);
/*     */ 
/*  24 */     this.isAlive = true;
/*     */ 
/*  26 */     this.taskQueue = new LinkedList();
/*  27 */     for (int i = 0; i < numThreads; i++)
/*  28 */       new PooledThread().start();
/*     */   }
/*     */ 
/*     */   public synchronized void runTask(Runnable task)
/*     */   {
/*  43 */     if (!this.isAlive) {
/*  44 */       throw new IllegalStateException();
/*     */     }
/*  46 */     if (task != null) {
/*  47 */       this.taskQueue.add(task);
/*  48 */       notify();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized Runnable getTask()
/*     */     throws InterruptedException
/*     */   {
/*  57 */     while (this.taskQueue.size() == 0) {
/*  58 */       if (!this.isAlive) {
/*  59 */         return null;
/*     */       }
/*  61 */       wait();
/*     */     }
/*  63 */     return (Runnable)this.taskQueue.removeFirst();
/*     */   }
/*     */ 
/*     */   public synchronized void close()
/*     */   {
/*  74 */     if (this.isAlive) {
/*  75 */       this.isAlive = false;
/*  76 */       this.taskQueue.clear();
/*  77 */       interrupt();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void join()
/*     */   {
/*  89 */     synchronized (this) {
/*  90 */       this.isAlive = false;
/*  91 */       notifyAll();
/*     */     }
/*     */ 
/*  95 */     Thread[] threads = new Thread[activeCount()];
/*  96 */     int count = enumerate(threads);
/*  97 */     for (int i = 0; i < count; i++)
/*     */       try {
/*  99 */         threads[i].join();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void threadStarted()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void threadStopped()
/*     */   {
/*     */   }
/*     */ 
/*     */   private class PooledThread extends Thread
/*     */   {
/*     */     public PooledThread()
/*     */     {
/* 136 */       super("PooledThread-" + ThreadPool.this.threadID++);
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 142 */       ThreadPool.this.threadStarted();
/*     */ 
/* 144 */       while (!isInterrupted())
/*     */       {
/* 147 */         Runnable task = null;
/*     */         try {
/* 149 */           task = ThreadPool.this.getTask();
/*     */         }
/*     */         catch (InterruptedException localInterruptedException)
/*     */         {
/*     */         }
/*     */ 
/* 155 */         if (task == null)
/*     */         {
/*     */           break;
/*     */         }
/*     */         try
/*     */         {
/* 161 */           task.run();
/*     */         }
/*     */         catch (Throwable t) {
/* 164 */           ThreadPool.this.uncaughtException(this, t);
/*     */         }
/*     */       }
/*     */ 
/* 168 */       ThreadPool.this.threadStopped();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.util.ThreadPool
 * JD-Core Version:    0.6.0
 */