//==============================================================================
// D a t e                                                               Applet
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

// This applet displays the current local date.
//
// The parameters of the applet:
//  - alignment    = alignment of the display ("left", "center" or "right").
//  - height       = height of the applet.
//  - background   = color of the background of the applet (RGB value).
//  - foreground   = color of the drawings in the applet (RGB value).

import java.util.*;
import java.applet.*;
import java.awt.*;

// D a t e  Class //------------------------------------------------------------
public class Date extends Applet implements Runnable {
 //--------------------------------------------------------------------Constants
 private static String atMonthNameS[] = { "January",    // Names of the months.
                                          "February",
                                          "March",
                                          "April",
                                          "May",
                                          "June",
                                          "July",
                                          "August",
                                          "September",
                                          "October",
                                          "November",
                                          "December" };

 private static String atWeekDayNameS[] = { "Sunday",     // Names of the week
                                            "Monday",     // days.
                                            "Tuesday",
                                            "Wednesday",
                                            "Thursday",
                                            "Friday",
                                            "Saturday" };
 //-------------------------------------------------------------------Attributes
 private int   atHeight;     // Height of the applet.
 private Color atBackground; // Color of the background of the applet.
 private Color atForeground; // Color of the drawings in the applet.

 private Font        atFont;        // Font used to write information.
 private FontMetrics atFontMetrics; // Metrics of the font.

 private Label atLabel; // Where the date is written.

 private Thread atTimeKeeper = null; // Thread that maintains the applet
                                     // running.
 //-------------------------------------------------------------------------Init
 // Initializes the applet.
 public void init() {
  String lcAlignment;

  int lcCounter = 0;

  // Parameters of the applet.
  lcAlignment=getParameter("alignment");
  atHeight=Integer.parseInt(getParameter("height"));
  atBackground=new Color(Integer.parseInt(getParameter("background"),16));
  atForeground=new Color(Integer.parseInt(getParameter("foreground"),16));

  // Setting of the graphical context.
  setLayout(new BorderLayout());
  atFont=new Font("Arial",Font.PLAIN,(int)(atHeight*0.8));
  atFontMetrics=getFontMetrics(atFont);
  setFont(atFont);
  setBackground(atBackground);
  setForeground(atForeground);

  // Building of the date label.
  if (lcAlignment.equals("center")) atLabel=new Label("",Label.CENTER);
  else if (lcAlignment.equals("right")) atLabel=new Label("",Label.RIGHT);
  else atLabel=new Label("",Label.LEFT);
  add("Center",atLabel);
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
  while(true) {
   updateText();
   repaint();
   try { atTimeKeeper.sleep(1000); }
   catch(Exception agException) { return; }
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
 //-------------------------------------------------------------------UpdateText
 // Updates the text displayed by the applet.
 private void updateText() {
  Calendar lcCalendar = new GregorianCalendar();
  int      lcYear     = lcCalendar.get(Calendar.YEAR);
  int      lcMonth    = lcCalendar.get(Calendar.MONTH);
  int      lcDay      = lcCalendar.get(Calendar.DAY_OF_MONTH);
  int      lcWeekDay  = lcCalendar.get(Calendar.DAY_OF_WEEK)-1;

  atLabel.setText(atWeekDayNameS[lcWeekDay]+" "+lcDay+" "+atMonthNameS[lcMonth]
                  +" "+lcYear);
 }
 //-----------------------------------------------------------------------Update
 // Updates the display of the applet.
 public void update(Graphics agGraphic) { paint(agGraphic); }
 //------------------------------------------------------------------------Paint
 // Draws the applet.
 public void paint(Graphics agGraphic) {}
 //----------------------------------------------------------------GetAppletInfo
 // Returns information about the applet.
 public String getAppletInfo() {
  return "Copyright (c) 1999-2016 - Bruno Bachelet - http://www.nawouak.net";
 }
}

// End //-----------------------------------------------------------------------
