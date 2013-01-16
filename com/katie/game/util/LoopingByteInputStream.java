/*    */ package com.katie.game.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class LoopingByteInputStream extends ByteArrayInputStream
/*    */ {
/*    */   private boolean closed;
/*    */ 
/*    */   public LoopingByteInputStream(byte[] buffer)
/*    */   {
/* 23 */     super(buffer);
/* 24 */     this.closed = false;
/*    */   }
/*    */ 
/*    */   public int read(byte[] buffer, int offset, int length)
/*    */   {
/* 35 */     if (this.closed) {
/* 36 */       return -1;
/*    */     }
/* 38 */     int totalBytesRead = 0;
/*    */ 
/* 40 */     while (totalBytesRead < length) {
/* 41 */       int numBytesRead = super.read(buffer, 
/* 42 */         offset + totalBytesRead, 
/* 43 */         length - totalBytesRead);
/*    */ 
/* 45 */       if (numBytesRead > 0) {
/* 46 */         totalBytesRead += numBytesRead;
/*    */       }
/*    */       else {
/* 49 */         reset();
/*    */       }
/*    */     }
/* 52 */     return totalBytesRead;
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 61 */     super.close();
/* 62 */     this.closed = true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.util.LoopingByteInputStream
 * JD-Core Version:    0.6.0
 */