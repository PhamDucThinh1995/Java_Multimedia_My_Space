//==============================================================================
// L c d D i g i t                                                        Class
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

// This class provides a font to display digits as in an LCD screen.

import java.util.*;
import java.awt.*;

// L c d D i g i t  Class //----------------------------------------------------
class LcdDigit {
 //--------------------------------------------------------------------Constants
 private static int atMapX[][] = {{1,1,1,1,1,1,0},  // Indicates which segments
                                  {0,0,0,0,1,1,0},  // are lighted to draw each
                                  {1,0,1,1,0,1,1},  // digit.
                                  {1,0,0,1,1,1,1},
                                  {0,1,0,0,1,1,1},
                                  {1,1,0,1,1,0,1},
                                  {1,1,1,1,1,0,1},
                                  {1,0,0,0,1,1,0},
                                  {1,1,1,1,1,1,1},
                                  {1,1,0,1,1,1,1}};
 //-------------------------------------------------------------------Attributes
 private int   atDigit;      // Number of the digit.
 private int   atWidth;      // Width of the digit.
 private int   atHeight;     // Height of the digit.
 private Color atLightColor; // Color of the lightened segments.
 private Color atDarkColor;  // Color of the darkened segments.
 //------------------------------------------------------------------Constructor
 // Creates a digit.
 public LcdDigit(int agDigit,int agWidth,int agHeight,Color agLightColor,
                 Color agDarkColor) {
  atDigit=agDigit;
  atWidth=agWidth;
  atHeight=agHeight;
  atLightColor=agLightColor;
  atDarkColor=agDarkColor;
 }
 //-------------------------------------------------------------------------Draw
 // Draws the digit at a given position.
 public void draw(Graphics agGraphic,int agPositionX,int agPositionY) {
  int lcSegmentXS[] = new int [7];
  int lcSegmentYS[] = new int [7];

  // Numbering of the segments:
  //
  //  ##0##
  // #     #
  // 1     5
  // #     #
  //  ##6##
  // #     #
  // 2     4
  // #     #
  //  ##3##
  //

  ((Graphics2D)agGraphic).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                           RenderingHints.VALUE_ANTIALIAS_ON);

  // Segment No 0.
  if (atMapX[atDigit][0]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX+(int)(atWidth*0.1);
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.9);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.7);
  lcSegmentXS[3]=agPositionX+(int)(atWidth*0.3);
  lcSegmentXS[4]=agPositionX+(int)(atWidth*0.1);

  lcSegmentYS[0]=agPositionY;
  lcSegmentYS[1]=agPositionY;
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.1);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.1);
  lcSegmentYS[4]=agPositionY;

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 1.
  if (atMapX[atDigit][1]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX;
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[3]=agPositionX;
  lcSegmentXS[4]=agPositionX;

  lcSegmentYS[0]=agPositionY+(int)(atHeight*0.05);
  lcSegmentYS[1]=agPositionY+(int)(atHeight*0.15);
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.35);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.45);
  lcSegmentYS[4]=agPositionY+(int)(atHeight*0.05);

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 2.
  if (atMapX[atDigit][2]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX;
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[3]=agPositionX;
  lcSegmentXS[4]=agPositionX;

  lcSegmentYS[0]=agPositionY+(int)(atHeight*0.55);
  lcSegmentYS[1]=agPositionY+(int)(atHeight*0.65);
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.85);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.95);
  lcSegmentYS[4]=agPositionY+(int)(atHeight*0.55);

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 3.
  if (atMapX[atDigit][3]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX+(int)(atWidth*0.1);
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.9);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.7);
  lcSegmentXS[3]=agPositionX+(int)(atWidth*0.3);
  lcSegmentXS[4]=agPositionX+(int)(atWidth*0.1);

  lcSegmentYS[0]=agPositionY+atHeight;
  lcSegmentYS[1]=agPositionY+atHeight;
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.9);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.9);
  lcSegmentYS[4]=agPositionY+atHeight;

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 4.
  if (atMapX[atDigit][4]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX+atWidth;
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[3]=agPositionX+atWidth;
  lcSegmentXS[4]=agPositionX+atWidth;

  lcSegmentYS[0]=agPositionY+(int)(atHeight*0.55);
  lcSegmentYS[1]=agPositionY+(int)(atHeight*0.65);
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.85);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.95);
  lcSegmentYS[4]=agPositionY+(int)(atHeight*0.55);

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 5.
  if (atMapX[atDigit][5]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX+atWidth;
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[3]=agPositionX+atWidth;
  lcSegmentXS[4]=agPositionX+atWidth;

  lcSegmentYS[0]=agPositionY+(int)(atHeight*0.05);
  lcSegmentYS[1]=agPositionY+(int)(atHeight*0.15);
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.35);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.45);
  lcSegmentYS[4]=agPositionY+(int)(atHeight*0.05);

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,5);

  // Segment No 6.
  if (atMapX[atDigit][6]==1) agGraphic.setColor(atLightColor);
  else agGraphic.setColor(atDarkColor);

  lcSegmentXS[0]=agPositionX+(int)(atWidth*0.1);
  lcSegmentXS[1]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[2]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[3]=agPositionX+(int)(atWidth*0.9);
  lcSegmentXS[4]=agPositionX+(int)(atWidth*0.8);
  lcSegmentXS[5]=agPositionX+(int)(atWidth*0.2);
  lcSegmentXS[6]=agPositionX+(int)(atWidth*0.1);

  lcSegmentYS[0]=agPositionY+(int)(atHeight*0.5);
  lcSegmentYS[1]=agPositionY+(int)(atHeight*0.45);
  lcSegmentYS[2]=agPositionY+(int)(atHeight*0.45);
  lcSegmentYS[3]=agPositionY+(int)(atHeight*0.5);
  lcSegmentYS[4]=agPositionY+(int)(atHeight*0.55);
  lcSegmentYS[5]=agPositionY+(int)(atHeight*0.55);
  lcSegmentYS[6]=agPositionY+(int)(atHeight*0.5);

  agGraphic.fillPolygon(lcSegmentXS,lcSegmentYS,7);
 }
}

// End //-----------------------------------------------------------------------
