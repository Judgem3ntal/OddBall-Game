/*   */ import java.io.IOException;
/*   */ import java.io.PrintStream;
/*   */ import java.net.URL;
/*   */ import java.net.URLConnection;
/*   */ 
/*   */ public class GetContentType
/*   */ {
/*   */   public static void main(String[] args)
/*   */     throws IOException
/*   */   {
/* 8 */     if (args.length != 1) {
/* 9 */       System.out.println("Shows the value of the \"content-type\" header field of a remote file.");
/*   */ 
/* 11 */       System.out.println("Usage: GetContentType (url)");
/* 12 */       System.out.println(
/* 13 */         "Example: GetContentType http://www.yahoo.com");
/*   */     }
/*   */     else {
/* 16 */       URL url = new URL(args[0]);
/* 17 */       URLConnection connection = url.openConnection();
/* 18 */       System.out.println(connection.getContentType());
/*   */     }
/*   */   }
/*   */ }

/* Location:           C:\Users\Jake Thornton\Desktop\OddBall.jar
 * Qualified Name:     GetContentType
 * JD-Core Version:    0.6.0
 */