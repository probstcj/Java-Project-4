/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.Color;
import java.awt.Polygon;

/**
 * This class extends the Polygon class and creates a Polygon at the center of 
 * the circle
 * @author probs
 */
public class You extends Polygon{
    private int health;
    private Color color = Color.BLUE;

    /**
     * This constructor creates a You that spawns in the middle of the circle
     */
    public You(){
        health = 400;
        int sides = 6;
        int length = 30;
        int x = 350;
        int y = 350;
        double thetaRad = ((double)2/sides)*Math.PI;
        double radius = length/(2*Math.sin(thetaRad/2));
        npoints = sides;
        xpoints = new int[sides];
        ypoints = new int[sides];
        for (int i = 0; i < sides; i++) {
            xpoints[i] = (int)(radius*(double)Math.cos(thetaRad*i-(Math.PI/(double)2))+x);
            ypoints[i] = (int)(radius*(double)Math.sin(thetaRad*i-(Math.PI/(double)2))+y);
        }
    }

    /**
     * This method returns the color of the You
     * @return Returns the color of the You
     */
    public Color getColor(){
        return color;
    }

    /**
     * This method returns the health of the You
     * @return Returns the health of the You
     */
    public int getHealth(){
        return health;
    }

    /**
     * This method sets the health of the You
     * @param health The user-given health of the You
     */
    public void setHealth(int health){
        if(health<0){
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health = health;
    }

    /**
     * This method tests for intersections between the You and a polygon.
     * @param poly The polygon that is being tested for intersections
     * @return Returns true if the You intersects, and false if it doesn't
     */
    public boolean intersects (Polygon poly){
        boolean isIntersect = false;
        int xMax=xpoints[0],yMax=ypoints[0],xMin=xMax,yMin=yMax,polyXMax=poly.xpoints[0],polyYMax=poly.ypoints[0],polyXMin=polyXMax,polyYMin=polyYMax;
        for (int x : xpoints) {
            if(x>xMax){
                xMax=x;
            }
            if (x<xMin) {
                xMin=x;
            }
        }
        for (int y : ypoints) {
            if(y>yMax){
                yMax=y;
            }
            if (y<yMin) {
                yMin=y;
            }
        }
        for (int polyX : poly.xpoints) {
            if(polyX>polyXMax){
                polyXMax=polyX;
            }
            if (polyX<polyXMin) {
                polyXMin=polyX;
            }
        }
        for (int polyY : poly.ypoints) {
            if(polyY>polyYMax){
                polyYMax=polyY;
            }
            if (polyY<polyYMin) {
                polyYMin=polyY;
            }
        }
        if(xMax>polyXMax && xMin<polyXMin && yMax>polyYMax && yMin<polyYMin){
            isIntersect=true;
        }
        return isIntersect;
    }
}
