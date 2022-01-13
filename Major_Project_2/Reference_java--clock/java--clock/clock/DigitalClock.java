//==============================================================================
// D i g i t a l C l o c k                                               Applet
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

// This applet animates a digital clock representing the current local time.
//
// The parameters of the applet:
//  - alignment    = alignment of the display ("left", "center" or "right").
//  - width        = width of the applet.
//  - height       = height of the applet.
//  - background   = color of the background of the applet (RGB value).
//  - light_color  = color of the lightened LED segments (RGB value).
//  - dark_color   = color of the darkened LED segments (RGB value).

import java.util.*;
import java.applet.*;
import java.awt.*;

// D i g i t a l C l o c k  Class //--------------------------------------------
public class DigitalClock extends Applet implements Runnable {
 //-------------------------------------------------------------------Attributes
 private int atHeight;        // Height of the applet.
 private int atWidth;         // Width of the applet.
 private int atDigitSize;     // Width of a digit (height is double of that).
 private int atSpaceSize;     // Width of a space between digits.
 private int atColonSize;     // Width of a colon.
 private int atStartPosition; // Position of the drawing.

 private Font        atFont;        // Font used to write information.
 private FontMetrics atFontMetrics; // Metrics of the font.

 private Color atBackground; // Color of the background of the applet.
 private Color atLightColor; // Color of the lightened segments.
 private Color atDarkColor;  // Color of the darkened segments.

 private Image atCopyrightImage; // Image of the copyright.
 private Image atOffScreen;      // Image to avoid the flickering.

 private Image atDigitS[];   // Images of the digits.
 private Image atLightColon; // Image of a lightened colon.
 private Image atDarkColon;  // Image of a darkened colon.

 private Thread atTimeKeeper = null; // Thread that maintains the applet
                                     // running.

 private int atClick = 0; // Indicates if a mouse click just happened.
 //-------------------------------------------------------------------------Init
 // Initializes the applet.
 public void init() {
  String   lcAlignment;
  LcdDigit lcDigit;
  Graphics lcGraphic;
  String   lcParameter;

  int lcCounter = 0;

  // Parameters of the applet.
  lcAlignment=getParameter("alignment");
  atWidth=Integer.parseInt(getParameter("width"));
  atHeight=Integer.parseInt(getParameter("height"));
  atBackground=new Color(Integer.parseInt(getParameter("background"),16));
  atLightColor=new Color(Integer.parseInt(getParameter("light_color"),16));
  atDarkColor=new Color(Integer.parseInt(getParameter("dark_color"),16));

  // Setting of the graphical context.
  atFont=new Font("Arial",Font.PLAIN,
                  (atWidth*0.35 > 12 ? 12 : (int)(atWidth*0.35)));

  atFontMetrics=getFontMetrics(atFont);
  setFont(atFont);

  // Building of the digits.
  atDigitS=new Image[10];
  atDigitSize=(int)(atHeight*0.4);
  atSpaceSize=(int)(atHeight*0.1);
  atColonSize=(int)(atHeight*0.2);

  atStartPosition=6*(atDigitSize+2*atSpaceSize)+2*(atColonSize+2*atSpaceSize);

  if (lcAlignment.equals("center"))
   atStartPosition=(atWidth-atStartPosition)/2;
  else if (lcAlignment.equals("right"))
   atStartPosition=atWidth-atStartPosition;
  else atStartPosition=0;

  while (lcCounter<10) {
   atDigitS[lcCounter]=createImage(atDigitSize,2*atDigitSize);
   lcGraphic=atDigitS[lcCounter].getGraphics();
   lcGraphic.clipRect(0,0,atDigitSize,2*atDigitSize);
   lcGraphic.setColor(atBackground);
   lcGraphic.fillRect(0,0,atDigitSize,2*atDigitSize);

   lcDigit=new LcdDigit(lcCounter,atDigitSize,2*atDigitSize,atLightColor,
                        atDarkColor);

   lcDigit.draw(lcGraphic,0,0);
   lcCounter++;
  }

  // Building of the colons.
  atDarkColon=createImage(atColonSize,2*atDigitSize);
  lcGraphic=atDarkColon.getGraphics();
  lcGraphic.clipRect(0,0,atColonSize,2*atDigitSize);
  lcGraphic.setColor(atBackground);
  lcGraphic.fillRect(0,0,atColonSize,2*atDigitSize);
  lcGraphic.setColor(atDarkColor);

  lcGraphic.fillRect((int)(atColonSize*0.3),
                     (int)(atDigitSize*0.5)-(int)(atColonSize*0.2),
                     (int)(atColonSize*0.4),(int)(atColonSize*0.4));

  lcGraphic.fillRect((int)(atColonSize*0.3),
                     (int)(atDigitSize*1.5)-(int)(atColonSize*0.2),
                     (int)(atColonSize*0.4),(int)(atColonSize*0.4));

  atLightColon=createImage(atColonSize,2*atDigitSize);
  lcGraphic=atLightColon.getGraphics();
  lcGraphic.clipRect(0,0,atColonSize,2*atDigitSize);
  lcGraphic.setColor(atBackground);
  lcGraphic.fillRect(0,0,atColonSize,2*atDigitSize);
  lcGraphic.setColor(atLightColor);

  lcGraphic.fillRect((int)(atColonSize*0.3),
                     (int)(atDigitSize*0.5)-(int)(atColonSize*0.2),
                     (int)(atColonSize*0.4),(int)(atColonSize*0.4));

  lcGraphic.fillRect((int)(atColonSize*0.3),
                     (int)(atDigitSize*1.5)-(int)(atColonSize*0.2),
                     (int)(atColonSize*0.4),(int)(atColonSize*0.4));

  // Building of the copyright image.
  atCopyrightImage=createImage(atWidth,atHeight);
  lcGraphic=atCopyrightImage.getGraphics();
  lcGraphic.clipRect(0,0,atWidth,atHeight);
  drawCopyright(lcGraphic);

  // Building of the off screen image.
  atOffScreen=createImage(atWidth,atHeight);
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
  repaint();

  while(true) {
   try { atTimeKeeper.sleep(250); }
   catch(Exception agException) { return; }
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
   lcGraphic.clipRect(0,0,atWidth,atHeight);
   drawClock(lcGraphic);
   agGraphic.drawImage(atOffScreen,0,0,this);
  }
 }
 //--------------------------------------------------------------------DrawClock
 // Draws the clock digits.
 private void drawClock(Graphics agGraphic) {
  Calendar lcCalendar        = new GregorianCalendar();
  int      lcHour            = lcCalendar.get(Calendar.HOUR_OF_DAY);
  int      lcMinute          = lcCalendar.get(Calendar.MINUTE);
  int      lcSecond          = lcCalendar.get(Calendar.SECOND);
  int      lcCurrentPosition = atStartPosition+atSpaceSize;

  // Coloring the background.
  agGraphic.setColor(atBackground);
  agGraphic.fillRect(0,0,atWidth,atHeight);

  // Drawing of the hour.
  agGraphic.drawImage(atDigitS[lcHour/10],lcCurrentPosition,atSpaceSize,this);
  lcCurrentPosition+=atDigitSize+2*atSpaceSize;
  agGraphic.drawImage(atDigitS[lcHour%10],lcCurrentPosition,atSpaceSize,this);
  lcCurrentPosition+=atDigitSize+2*atSpaceSize;

  // Drawing of the first colon.
  if (lcSecond%2==0)
   agGraphic.drawImage(atLightColon,lcCurrentPosition,atSpaceSize,this);
  else agGraphic.drawImage(atDarkColon,lcCurrentPosition,atSpaceSize,this);

  // Drawing of the minute.
  lcCurrentPosition+=atColonSize+2*atSpaceSize;
  agGraphic.drawImage(atDigitS[lcMinute/10],lcCurrentPosition,atSpaceSize,this);
  lcCurrentPosition+=atDigitSize+2*atSpaceSize;
  agGraphic.drawImage(atDigitS[lcMinute%10],lcCurrentPosition,atSpaceSize,this);
  lcCurrentPosition+=atDigitSize+2*atSpaceSize;

  // Drawing of the second colon.
  if (lcSecond%2==1)
   agGraphic.drawImage(atLightColon,lcCurrentPosition,atSpaceSize,this);
  else agGraphic.drawImage(atDarkColon,lcCurrentPosition,atSpaceSize,this);

  // Drawing of the second.
  lcCurrentPosition+=atColonSize+2*atSpaceSize;
  agGraphic.drawImage(atDigitS[lcSecond/10],lcCurrentPosition,atSpaceSize,this);
  lcCurrentPosition+=atDigitSize+2*atSpaceSize;
  agGraphic.drawImage(atDigitS[lcSecond%10],lcCurrentPosition,atSpaceSize,this);
 }
 //----------------------------------------------------------------DrawCopyright
 // Draws the copyright image.
 private void drawCopyright(Graphics agGraphic) {
  int    lcX;
  int    lcY;
  String lcString;

  ((Graphics2D)agGraphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                           RenderingHints.VALUE_ANTIALIAS_ON);

  agGraphic.setColor(atBackground);
  agGraphic.fillRect(0,0,atWidth,atHeight);
  agGraphic.setColor(atLightColor);
  agGraphic.setFont(atFont);
  lcX=(int)(atWidth*0.5);
  lcY=(int)(atHeight*0.5-atFontMetrics.getHeight()/2);
  lcString="Copyright (c) 1999-2016";

  agGraphic.drawString(lcString,lcX-atFontMetrics.stringWidth(lcString)/2,
                       lcY+atFontMetrics.getHeight()/4);

  lcY=(int)(atHeight*0.5+atFontMetrics.getHeight()/2);
  lcString="http://www.nawouak.net";

  agGraphic.drawString(lcString,lcX-atFontMetrics.stringWidth(lcString)/2,
                       lcY+atFontMetrics.getHeight()/4);
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

// End //-----------------------------------------------------------------------
