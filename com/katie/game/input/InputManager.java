/*     */ package com.katie.game.input;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Point;
/*     */ import java.awt.Robot;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class InputManager
/*     */   implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
/*     */ {
/*  20 */   public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
/*  21 */     Toolkit.getDefaultToolkit().getImage(""), 
/*  22 */     new Point(0, 0), 
/*  23 */     "invisible");
/*     */   public static final int MOUSE_MOVE_LEFT = 0;
/*     */   public static final int MOUSE_MOVE_RIGHT = 1;
/*     */   public static final int MOUSE_MOVE_UP = 2;
/*     */   public static final int MOUSE_MOVE_DOWN = 3;
/*     */   public static final int MOUSE_WHEEL_UP = 4;
/*     */   public static final int MOUSE_WHEEL_DOWN = 5;
/*     */   public static final int MOUSE_BUTTON_1 = 6;
/*     */   public static final int MOUSE_BUTTON_2 = 7;
/*     */   public static final int MOUSE_BUTTON_3 = 8;
/*     */   private static final int NUM_MOUSE_CODES = 9;
/*     */   private static final int NUM_KEY_CODES = 600;
/*  44 */   private GameAction[] keyActions = new GameAction[600];
/*     */ 
/*  46 */   private GameAction[] mouseActions = new GameAction[9];
/*     */   private Point mouseLocation;
/*     */   private Point centerLocation;
/*     */   private Component comp;
/*     */   private Robot robot;
/*     */   private boolean isRecentering;
/*     */ 
/*     */   public InputManager(Component comp)
/*     */   {
/*  59 */     this.comp = comp;
/*  60 */     this.mouseLocation = new Point();
/*  61 */     this.centerLocation = new Point();
/*     */ 
/*  64 */     comp.addKeyListener(this);
/*  65 */     comp.addMouseListener(this);
/*  66 */     comp.addMouseMotionListener(this);
/*  67 */     comp.addMouseWheelListener(this);
/*     */ 
/*  71 */     comp.setFocusTraversalKeysEnabled(false);
/*     */   }
/*     */ 
/*     */   public void setCursor(Cursor cursor)
/*     */   {
/*  79 */     this.comp.setCursor(cursor);
/*     */   }
/*     */ 
/*     */   public void setRelativeMouseMode(boolean mode)
/*     */   {
/*  91 */     if (mode == isRelativeMouseMode()) {
/*  92 */       return;
/*     */     }
/*     */ 
/*  95 */     if (mode) {
/*     */       try {
/*  97 */         this.robot = new Robot();
/*  98 */         recenterMouse();
/*     */       }
/*     */       catch (AWTException ex)
/*     */       {
/* 102 */         this.robot = null;
/*     */       }
/*     */     }
/*     */     else
/* 106 */       this.robot = null;
/*     */   }
/*     */ 
/*     */   public boolean isRelativeMouseMode()
/*     */   {
/* 115 */     return this.robot != null;
/*     */   }
/*     */ 
/*     */   public void mapToKey(GameAction gameAction, int keyCode)
/*     */   {
/* 126 */     this.keyActions[keyCode] = gameAction;
/*     */   }
/*     */ 
/*     */   public void mapToMouse(GameAction gameAction, int mouseCode)
/*     */   {
/* 140 */     this.mouseActions[mouseCode] = gameAction;
/*     */   }
/*     */ 
/*     */   public void clearMap(GameAction gameAction)
/*     */   {
/* 149 */     for (int i = 0; i < this.keyActions.length; i++) {
/* 150 */       if (this.keyActions[i] == gameAction) {
/* 151 */         this.keyActions[i] = null;
/*     */       }
/*     */     }
/*     */ 
/* 155 */     for (int i = 0; i < this.mouseActions.length; i++) {
/* 156 */       if (this.mouseActions[i] == gameAction) {
/* 157 */         this.mouseActions[i] = null;
/*     */       }
/*     */     }
/*     */ 
/* 161 */     gameAction.reset();
/*     */   }
/*     */ 
/*     */   public void clearAllMaps()
/*     */   {
/* 169 */     for (int i = 0; i < this.keyActions.length; i++) {
/* 170 */       this.keyActions[i] = null;
/*     */     }
/*     */ 
/* 173 */     for (int i = 0; i < this.mouseActions.length; i++)
/* 174 */       this.mouseActions[i] = null;
/*     */   }
/*     */ 
/*     */   public List getMaps(GameAction gameCode)
/*     */   {
/* 184 */     ArrayList list = new ArrayList();
/*     */ 
/* 186 */     for (int i = 0; i < this.keyActions.length; i++) {
/* 187 */       if (this.keyActions[i] == gameCode) {
/* 188 */         list.add(getKeyName(i));
/*     */       }
/*     */     }
/*     */ 
/* 192 */     for (int i = 0; i < this.mouseActions.length; i++) {
/* 193 */       if (this.mouseActions[i] == gameCode) {
/* 194 */         list.add(getMouseName(i));
/*     */       }
/*     */     }
/* 197 */     return list;
/*     */   }
/*     */ 
/*     */   public void resetAllGameActions()
/*     */   {
/* 206 */     for (int i = 0; i < this.keyActions.length; i++) {
/* 207 */       if (this.keyActions[i] != null) {
/* 208 */         this.keyActions[i].reset();
/*     */       }
/*     */     }
/*     */ 
/* 212 */     for (int i = 0; i < this.mouseActions.length; i++)
/* 213 */       if (this.mouseActions[i] != null)
/* 214 */         this.mouseActions[i].reset();
/*     */   }
/*     */ 
/*     */   public static String getKeyName(int keyCode)
/*     */   {
/* 224 */     return KeyEvent.getKeyText(keyCode);
/*     */   }
/*     */ 
/*     */   public static String getMouseName(int mouseCode)
/*     */   {
/* 232 */     switch (mouseCode) { case 0:
/* 233 */       return "Mouse Left";
/*     */     case 1:
/* 234 */       return "Mouse Right";
/*     */     case 2:
/* 235 */       return "Mouse Up";
/*     */     case 3:
/* 236 */       return "Mouse Down";
/*     */     case 4:
/* 237 */       return "Mouse Wheel Up";
/*     */     case 5:
/* 238 */       return "Mouse Wheel Down";
/*     */     case 6:
/* 239 */       return "Mouse Button 1";
/*     */     case 7:
/* 240 */       return "Mouse Button 2";
/*     */     case 8:
/* 241 */       return "Mouse Button 3"; }
/* 242 */     return "Unknown mouse code " + mouseCode;
/*     */   }
/*     */ 
/*     */   public int getMouseX()
/*     */   {
/* 251 */     return this.mouseLocation.x;
/*     */   }
/*     */ 
/*     */   public int getMouseY()
/*     */   {
/* 259 */     return this.mouseLocation.y;
/*     */   }
/*     */ 
/*     */   private synchronized void recenterMouse()
/*     */   {
/* 270 */     if ((this.robot != null) && (this.comp.isShowing())) {
/* 271 */       this.centerLocation.x = (this.comp.getWidth() / 2);
/* 272 */       this.centerLocation.y = (this.comp.getHeight() / 2);
/* 273 */       SwingUtilities.convertPointToScreen(this.centerLocation, 
/* 274 */         this.comp);
/* 275 */       this.isRecentering = true;
/* 276 */       this.robot.mouseMove(this.centerLocation.x, this.centerLocation.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   private GameAction getKeyAction(KeyEvent e)
/*     */   {
/* 282 */     int keyCode = e.getKeyCode();
/* 283 */     if (keyCode < this.keyActions.length) {
/* 284 */       return this.keyActions[keyCode];
/*     */     }
/*     */ 
/* 287 */     return null;
/*     */   }
/*     */ 
/*     */   public static int getMouseButtonCode(MouseEvent e)
/*     */   {
/* 296 */     switch (e.getButton()) {
/*     */     case 1:
/* 298 */       return 6;
/*     */     case 2:
/* 300 */       return 7;
/*     */     case 3:
/* 302 */       return 8;
/*     */     }
/* 304 */     return -1;
/*     */   }
/*     */ 
/*     */   private GameAction getMouseButtonAction(MouseEvent e)
/*     */   {
/* 310 */     int mouseCode = getMouseButtonCode(e);
/* 311 */     if (mouseCode != -1) {
/* 312 */       return this.mouseActions[mouseCode];
/*     */     }
/*     */ 
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   public void keyPressed(KeyEvent e)
/*     */   {
/* 322 */     GameAction gameAction = getKeyAction(e);
/* 323 */     if (gameAction != null) {
/* 324 */       gameAction.press();
/*     */     }
/*     */ 
/* 327 */     e.consume();
/*     */   }
/*     */ 
/*     */   public void keyReleased(KeyEvent e)
/*     */   {
/* 333 */     GameAction gameAction = getKeyAction(e);
/* 334 */     if (gameAction != null) {
/* 335 */       gameAction.release();
/*     */     }
/*     */ 
/* 338 */     e.consume();
/*     */   }
/*     */ 
/*     */   public void keyTyped(KeyEvent e)
/*     */   {
/* 345 */     e.consume();
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/* 351 */     GameAction gameAction = getMouseButtonAction(e);
/* 352 */     if (gameAction != null)
/* 353 */       gameAction.press();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/* 360 */     GameAction gameAction = getMouseButtonAction(e);
/* 361 */     if (gameAction != null)
/* 362 */       gameAction.release();
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent e)
/*     */   {
/* 375 */     mouseMoved(e);
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent e)
/*     */   {
/* 381 */     mouseMoved(e);
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/* 387 */     mouseMoved(e);
/*     */   }
/*     */ 
/*     */   public synchronized void mouseMoved(MouseEvent e)
/*     */   {
/* 394 */     if ((this.isRecentering) && 
/* 395 */       (this.centerLocation.x == e.getX()) && 
/* 396 */       (this.centerLocation.y == e.getY()))
/*     */     {
/* 398 */       this.isRecentering = false;
/*     */     }
/*     */     else {
/* 401 */       int dx = e.getX() - this.mouseLocation.x;
/* 402 */       int dy = e.getY() - this.mouseLocation.y;
/* 403 */       mouseHelper(0, 1, dx);
/* 404 */       mouseHelper(2, 3, dy);
/*     */ 
/* 406 */       if (isRelativeMouseMode()) {
/* 407 */         recenterMouse();
/*     */       }
/*     */     }
/*     */ 
/* 411 */     this.mouseLocation.x = e.getX();
/* 412 */     this.mouseLocation.y = e.getY();
/*     */   }
/*     */ 
/*     */   public void mouseWheelMoved(MouseWheelEvent e)
/*     */   {
/* 419 */     mouseHelper(4, 5, 
/* 420 */       e.getWheelRotation());
/*     */   }
/*     */ 
/*     */   private void mouseHelper(int codeNeg, int codePos, int amount)
/*     */   {
/*     */     GameAction gameAction;
/*     */     GameAction gameAction;
/* 427 */     if (amount < 0) {
/* 428 */       gameAction = this.mouseActions[codeNeg];
/*     */     }
/*     */     else {
/* 431 */       gameAction = this.mouseActions[codePos];
/*     */     }
/* 433 */     if (gameAction != null) {
/* 434 */       gameAction.press(Math.abs(amount));
/* 435 */       gameAction.release();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     com.katie.game.input.InputManager
 * JD-Core Version:    0.6.0
 */