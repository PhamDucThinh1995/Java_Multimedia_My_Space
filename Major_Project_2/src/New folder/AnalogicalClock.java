//==============================================================================
// A n a l o g i c a l C l o c k                                         Applet
//                                                            By Bruno Bachelet
//==============================================================================
// Copyright (c) 1999-2016
// Bruno Bachelet - bruno@nawouak.net - http://www.nawouak.net
//
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 2 of the license, or (at your option)
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// more details (http://www.gnu.org).

// This applet animates an analogical clock representing the current local
// time.
//
// The parameters of the applet:
//  - size         = size of the clock.
//  - image        = background image file name ("" means no background image).
//  - position_x   = X position of the background image.
//  - position_y   = Y position of the background image.
//  - background   = color of the background of the applet (RGB value).
//  - foreground   = color of the foreground of the clock (RGB value).
//  - clock_color  = color of the background of the clock (RGB value).
//  - needle_color = color of the needles of the clock (RGB value).

import java.io.*;
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.net.URL;

// A n a l o g i c a l C l o c k  Class //--------------------------------------
public class AnalogicalClock extends Applet implements Runnable {
 //-------------------------------------------------------------------Attributes
 private Needle atHourNeedle;   // Hour needle.
 private Needle atMinuteNeedle; // Minute needle.
 private Needle atSecondNeedle; // Second needle.

 private Font        atFont;        // Font used to draw the clock.
 private FontMetrics atFontMetrics; // Metrics of the font.

 private int    atSize;        // Size of the clock.
 private String atImage;       // Background image file name;
 private int    atPositionX;   // X position of the background image;
 private int    atPositionY;   // Y position of the background image;
 private Color  atBackground;  // Color of the background of the square.
 private Color  atForeground;  // Color of the foreground of the clock.
 private Color  atClockColor;  // Color of the background of the clock.
 private Color  atNeedleColor; // Color of the needles of the clock.

 private Image atCopyrightImage;  // Image of the copyright.
 private Image atBackgroundImage; // Image of the background.
 private Image atOffScreen;       // Image to avoid the flickering.

 private Thread atTimeKeeper = null; // Thread that maintains the applet
                                     // running.

 private int atClick = 0; // Indicates if a mouse click just happened.
 //-------------------------------------------------------------------------Init
 // Initializes the applet.
 public void init() {
  Graphics lcGraphic;
  String   lcParameter;
  int      lcWidth;

  // Parameters of the applet.
  atSize=Integer.parseInt(getParameter("size"));

  if (getParameter("width")!=null) {
   lcWidth=Integer.parseInt(getParameter("width"));
   if (lcWidth>atSize) atSize=lcWidth;
  }

  atImage=getParameter("image");

  if (atImage.equals("none")) {
   atBackground=new Color(Integer.parseInt(getParameter("background"),16));
   atClockColor=new Color(Integer.parseInt(getParameter("clock_color"),16));
  }
  else {
   atPositionX=Integer.parseInt(getParameter("position_x"));
   atPositionY=Integer.parseInt(getParameter("position_y"));
  }

  atForeground=new Color(Integer.parseInt(getParameter("foreground"),16));
  atNeedleColor=new Color(Integer.parseInt(getParameter("needle_color"),16));

  // Setting of the graphical context.
  atFont=new Font("Arial",Font.PLAIN,
                  (atSize*0.05 > 12 ? 12 : (int)(atSize*0.05)));

  atFontMetrics=getFontMetrics(atFont);
  setFont(atFont);

  // Building of the needles.
  atHourNeedle = new Needle((int)(atSize*0.5),(int)(atSize*0.5),
                            (int)(atSize*0.24),Needle.HOUR,atNeedleColor);

  atMinuteNeedle = new Needle((int)(atSize*0.5),(int)(atSize*0.5),
                              (int)(atSize*0.32),Needle.MINUTE,
                              atNeedleColor.brighter());

  atSecondNeedle = new Needle((int)(atSize*0.5),(int)(atSize*0.5),
                              (int)(atSize*0.37),Needle.SECOND,
                              atNeedleColor.darker());

  updateNeedles();

  // Building of the background image.
  atBackgroundImage=createImage(atSize,atSize);
  lcGraphic=atBackgroundImage.getGraphics();
  lcGraphic.clipRect(0,0,atSize,atSize);
  drawBackground(lcGraphic);

  // Building of the copyright image.
  atCopyrightImage=createImage(atSize,atSize);
  lcGraphic=atCopyrightImage.getGraphics();
  lcGraphic.clipRect(0,0,atSize,atSize);
  drawCopyright(lcGraphic);

  // Building of the off screen image.
  atOffScreen=createImage(atSize,atSize);
 }
 //------------------------------------------------------------------------Start
 // Starts the applet.
 public void start() {
  if(atTimeKeeper==null) {
   atTimeKeeper=new Thread(this);
   atTimeKeeper.start();
  }
 }
 //--------------------------------------------------------------------------Run
 // Runs the applet.
 public void run() {
  Calendar lcCalendar    = new GregorianCalendar();
  int      lcMillisecond = lcCalendar.get(Calendar.MILLISECOND);

  try { atTimeKeeper.sleep(1500-lcMillisecond); }
  catch(Exception agException) { return; }
  updateNeedles();
  repaint();

  while(true) {
   try { atTimeKeeper.sleep(250); }
   catch(Exception agException) { return; }
   updateNeedles();
   repaint();
  }
 }
 //-------------------------------------------------------------------------Stop
 // Stops the applet.
 public void stop() {
  if (atTimeKeeper!=null) {
   atTimeKeeper.stop();
   atTimeKeeper=null;
  }
 }
 //-----------------------------------------------------------------------Update
 // Updates the display of the applet.
 public void update(Graphics agGraphic) { paint(agGraphic); }
 //------------------------------------------------------------------------Paint
 // Draws the applet.
 public void paint(Graphics agGraphic) {
  Graphics lcGraphic = atOffScreen.getGraphics();

  if (atClick>0) {
   agGraphic.drawImage(atCopyrightImage,0,0,this);
   --atClick;
  }
  else {
   lcGraphic.clipRect(0,0,atSize,atSize);
   lcGraphic.drawImage(atBackgroundImage,0,0,this);
   drawNeedles(lcGraphic);
   agGraphic.drawImage(atOffScreen,0,0,this);
  }
 }
 //----------------------------------------------------------------UpdateNeedles
 // Updates the angle of the needles.
 private void updateNeedles() {
  Calendar lcCalendar = new GregorianCalendar();
  int      lcHour     = lcCalendar.get(Calendar.HOUR_OF_DAY);
  int      lcMinute   = lcCalendar.get(Calendar.MINUTE);
  int      lcSecond   = lcCalendar.get(Calendar.SECOND);

  atSecondNeedle.setAngle(2*Math.PI*(lcSecond-15.0)/60.0);
  atMinuteNeedle.setAngle(2*Math.PI*(lcMinute-15.0+lcSecond/60.0)/60.0);
  atHourNeedle.setAngle(2*Math.PI*(lcHour%12-3.0+lcMinute/60.0)/12.0);
 }
 //---------------------------------------------------------------DrawBackground
 // Draws the background of the clock.
 private void drawBackground(Graphics agGraphic) {
  int    lcRadius;
  int    lcX;
  int    lcY;
  String lcString;

  int lcHour   = 1;
  int lcMinute = 0;

  ((Graphics2D)agGraphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                           RenderingHints.VALUE_ANTIALIAS_ON);

  // There is no background image.
  if (atImage.equals("none")) {
   // Background.
   agGraphic.setColor(atBackground);
   agGraphic.fillRect(0,0,atSize,atSize);

   // Clock circle.
   agGraphic.setColor(atClockColor);

   agGraphic.fillOval((int)(atSize*0.05),(int)(atSize*0.05),
                      (int)(atSize*0.90),(int)(atSize*0.90));

   agGraphic.setColor(atForeground);

   agGraphic.drawOval((int)(atSize*0.05),(int)(atSize*0.05),
                      (int)(atSize*0.90),(int)(atSize*0.90));

   agGraphic.setColor(atForeground.darker());

   agGraphic.drawOval((int)(atSize*0.05)+1,(int)(atSize*0.05)+1,
                      (int)(atSize*0.90)-2,(int)(atSize*0.90)-2);

   agGraphic.setColor(atForeground.darker().darker());

   agGraphic.drawOval((int)(atSize*0.05)+2,(int)(atSize*0.05)+2,
                      (int)(atSize*0.90)-4,(int)(atSize*0.90)-4);
  }

  // There is a background image.
  else {
   // Background image.
   byte[]      lcBuffer;
   Image       lcImage;
   InputStream lcStream;
   URL         lcURL;

   MediaTracker lcTracker = new MediaTracker(this);

   try {
    lcURL=getClass().getClassLoader().getResource(atImage);
    lcImage=Toolkit.getDefaultToolkit().getImage(lcURL);
   }

   catch(Exception agException) {
    lcImage=createImage(atSize,atSize);
    lcImage.getGraphics().setColor(atBackground);
    lcImage.getGraphics().fillRect(0,0,atSize,atSize);
   }

   lcTracker.addImage(lcImage,0);
   try { lcTracker.waitForID(0); } catch(Exception agException) { return; }
   agGraphic.drawImage(lcImage,atPositionX,atPositionY,this);
  }

  agGraphic.setColor(atForeground);
  agGraphic.setFont(atFont);

  // Hour numbers.
  while (lcHour<13) {
   lcX=(int)(atSize*0.5)+(int)(atSize*0.41*Math.cos(2*Math.PI*(lcHour-3)/12.0));
   lcY=(int)(atSize*0.5)+(int)(atSize*0.41*Math.sin(2*Math.PI*(lcHour-3)/12.0));
   lcString=""+lcHour;

   agGraphic.drawString(lcString,lcX-atFontMetrics.stringWidth(lcString)/2,
                        lcY+(int)(atFontMetrics.getHeight()*0.4));

   lcHour++;
  }

  // Minute points.
  lcRadius=(int)(atSize*0.003);

  if (lcRadius<1) lcRadius=1;

  while (lcMinute<60) {
   if (lcMinute%5!=0) {
    lcX=(int)(atSize*0.5)+(int)(atSize*0.41*Math.cos(2*Math.PI*(lcMinute)/60.0));
    lcY=(int)(atSize*0.5)+(int)(atSize*0.41*Math.sin(2*Math.PI*(lcMinute)/60.0));
    agGraphic.fillOval(lcX-lcRadius,lcY-lcRadius,2*lcRadius,2*lcRadius);
   }

   lcMinute++;
  }
 }
 //----------------------------------------------------------------DrawCopyright
 // Draws the copyright image.
 private void drawCopyright(Graphics agGraphic) {
  int    lcX;
  int    lcY;
  String lcString;

  ((Graphics2D)agGraphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                           RenderingHints.VALUE_ANTIALIAS_ON);

  drawBackground(agGraphic);
  agGraphic.setColor(atForeground);
  agGraphic.setFont(atFont);
  lcX=(int)(atSize*0.5);
  lcY=(int)(atSize*0.5-atFontMetrics.getHeight()/2);
  lcString="Copyright (c) 1999-2016";

  agGraphic.drawString(lcString,lcX-atFontMetrics.stringWidth(lcString)/2,
                       lcY+atFontMetrics.getHeight()/4);

  lcY=(int)(atSize*0.5+atFontMetrics.getHeight()/2);
  lcString="http://www.nawouak.net";

  agGraphic.drawString(lcString,lcX-atFontMetrics.stringWidth(lcString)/2,
                       lcY+atFontMetrics.getHeight()/4);
 }
 //------------------------------------------------------------------DrawNeedles
 // Draws the needles.
 private void drawNeedles(Graphics agGraphic) {
  ((Graphics2D)agGraphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                           RenderingHints.VALUE_ANTIALIAS_ON);

  atHourNeedle.draw(agGraphic);
  atMinuteNeedle.draw(agGraphic);
  atSecondNeedle.draw(agGraphic);
  agGraphic.setColor(atNeedleColor.brighter().brighter());

  agGraphic.fillOval((int)(atSize*0.49),(int)(atSize*0.49),
                     (int)(atSize*0.02),(int)(atSize*0.02));
 }
 //----------------------------------------------------------------GetAppletInfo
 // Returns information about the applet.
 public String getAppletInfo() {
  return "Copyright (c) 1999-2016 - Bruno Bachelet - http://www.nawouak.net";
 }
 //--------------------------------------------------------------------MouseDown
 // When a mouse button is down...
 public boolean mouseDown(Event agEvent,int agX,int agY) {
  atClick=4;
  return (true);
 }
}

// N e e d l e  Class //--------------------------------------------------------
class Needle {
 //--------------------------------------------------------------------Constants
 public static int HOUR   = 0; // Indicates the needle is for hours.
 public static int MINUTE = 1; // Indicates the needle is for minutes.
 public static int SECOND = 2; // Indicates the needle is for seconds.
 //-------------------------------------------------------------------Attributes
 private int    atCenterX; // X coordinate of the center of the rotation.
 private int    atCenterY; // Y coordinate of the center of the rotation.
 private int    atRadius;  // Length of the needle.
 private int    atType;    // Type of the needle (for hours, minutes or
                           // seconds).
 private Color  atColor;   // Color of the needle.
 private double atAngle;   // Angle of the needle.
 //------------------------------------------------------------------Constructor
 // Creates a needle.
 public Needle(int agCenterX,int agCenterY,int agRadius,int agType,
               Color agColor) {
  atCenterX=agCenterX;
  atCenterY=agCenterY;
  atRadius=agRadius;
  atType=agType;
  atColor=agColor;
 }
 //---------------------------------------------------------------------SetAngle
 // Sets the angle of the needle.
 public void setAngle(double agAngle) { atAngle=agAngle; }
 //-------------------------------------------------------------------------Draw
 // Draws the needle.
 public void draw(Graphics agGraphic) {
  double lcAngle;

  double lcCos = atRadius*Math.cos(atAngle);
  double lcSin = atRadius*Math.sin(atAngle);

  int lcX = atCenterX+(int)(lcCos);
  int lcY = atCenterY+(int)(lcSin);

  if (atType==HOUR) lcAngle=0.35;
  else lcAngle=0.25;

  int lcXS[] = { atCenterX-(int)(0.2*atRadius*Math.cos(atAngle-lcAngle)),
                 atCenterX-(int)(0.2*atRadius*Math.cos(atAngle+lcAngle)),
                 lcX,
                 atCenterX-(int)(0.2*atRadius*Math.cos(atAngle-lcAngle)) };

  int lcYS[] = { atCenterY-(int)(0.2*atRadius*Math.sin(atAngle-lcAngle)),
                 atCenterY-(int)(0.2*atRadius*Math.sin(atAngle+lcAngle)),
                 lcY,
                 atCenterY-(int)(0.2*atRadius*Math.sin(atAngle-lcAngle)) };

  agGraphic.setColor(atColor);

  if (atType==SECOND)
   agGraphic.drawLine(atCenterX-(int)(0.2*lcCos),atCenterY-(int)(0.2*lcSin),
                      lcX,lcY);

  else agGraphic.fillPolygon(lcXS,lcYS,4);
 }
}

// End //-----------------------------------------------------------------------
