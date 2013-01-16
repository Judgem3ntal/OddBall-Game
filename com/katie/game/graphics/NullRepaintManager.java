/*    */ package com.katie.game.graphics;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.RepaintManager;
/*    */ 
/*    */ public class NullRepaintManager extends RepaintManager
/*    */ {
/*    */   public static void install()
/*    */   {
/* 17 */     RepaintManager repaintManager = new NullRepaintManager();
/* 18 */     repaintManager.setDoubleBufferingEnabled(false);
/* 19 */     RepaintManager.setCurrentManager(repaintManager);
/*    */   }
/*    */ 
/*    */   public void addInvalidComponent(JComponent c)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addDirtyRegion(JComponent c, int x, int y, int w, int h)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void markCompletelyDirty(JComponent c)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void paintDirtyRegions()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.graphics.NullRepaintManager
 * JD-Core Version:    0.6.0
 */