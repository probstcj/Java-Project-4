/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.Polygon;

/**
 * This class is an extension of the Polygon class, and creates a "Bullet" to 
 * allow for shooting in the game.
 * @author probs
 */
public class Bullet extends Polygon{
    private double angle, dr, drad;
    private int[] dx, dy;
    private int damage;

    /**
     * 
     * @param x The x component in which the Bullet is going to
     * @param y The y component in which the Bullet is going to
     * @param damage The amount of damage that the individual Bullet has
     */
    public Bullet(int x, int y, int damage){
        this.damage = damage;
        npoints = 20;
        xpoints = new int[npoints];
        ypoints = new int[npoints];
        // Use ngon class, sides = 20 (basically a circle, so we dont have to worry about rotation :)
        int sides = 20;
        int length = 2;
        double thetaRad = ((double)2/sides)*Math.PI;
        double radius = length/(2*Math.sin(thetaRad/2));
        npoints = sides;
        xpoints = new int[sides];
        ypoints = new int[sides];
        for (int i = 0; i < sides; i++) {
            xpoints[i] = (int)(radius*(double)Math.cos(thetaRad*i-(Math.PI/(double)2))+350);
            ypoints[i] = (int)(radius*(double)Math.sin(thetaRad*i-(Math.PI/(double)2))+350);
        }
        // Calculate angle from x and y from the point (350,350)
        if(x>350 && y<350){
            angle = -Math.atan((double)(350-y)/(double)(350-x));
        }
        else if(x==350 && y<350){
            angle = Math.PI/2;
        }
        else if (x==350 && y<350){
            angle = 3*Math.PI/2;
        }
        else if(x>350 && y==350){
            angle = 0;
        }
        else if(x>350 && y>350){
            angle = -Math.atan((double)(350-y)/(double)(350-x));
            angle += 2*Math.PI;
        }
        else{
            angle = -Math.atan((double)(350-y)/(double)(350-x));
            angle += Math.PI;
        }
        dr = 5;
        drad = dr;
        dx = new int[sides];
        dy = new int[sides];
        for (int i = 0; i < sides-1; i++) {
            if (i==2){
                dx[i] = xpoints[0] - xpoints[i];
                dy[i] = ypoints[0] - ypoints[i];
            }
            else{
                dx[i] = xpoints[i+1] - xpoints[i];
                dy[i] = ypoints[i+1] - ypoints[i];
            }
        }
    }

    /**
     * This method moves the Bullet towards the destination from the constructor.
     */
    public void move(){
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] = (int)(350-1*Math.cos(-angle-Math.PI)-dr*Math.cos(-angle-Math.PI))-dx[i];
            ypoints[i] = (int)(350-1*Math.sin(-angle-Math.PI)-dr*Math.sin(-angle-Math.PI))-dy[i];
        }
        dr = dr+drad;
    }

    /**
     * This method gets the damage of the Bullet
     * @return This returns the damage of the Bullet
     */
    public int getDamage(){
        return damage;
    }

    /**
     * This method sets the damage of the individual Bullet
     * @param damage This is the damage that the user wishes to give to the Bullet
     * 
     */ 
    public void setDamage(int damage){
        if(damage<0){
            throw new IllegalArgumentException("Damage value must be greater than 0");
        }
        else{
            this.damage = damage;
        }
    }

    /**
     * This method tests for intersections between the Bullet and a Polygon
     * @param poly This is the Polygon that the Bullet is testing for intersection
     * @return Returns True if the Bullet intersects, or False if it doesn't
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
    
    /**
     * This method is used for finding the center Y value of the Bullet
     * @return This returns the center Y value of the Bullet
     */
    public int getCenterY(){
        int sum =0;
        int i = 0;
        for (int y : ypoints) {
            sum += y;
            i++;
        }
        return sum/i;
    }

    /**
     * This method is used for finding the center X value of the Bullet
     * @return This returns the center X value of the Bullet
     */
    public int getCenterX() {
        int sum =0;
        int i = 0;
        for (int x : xpoints) {
            sum += x;
            i++;
        }
        return sum/i;
    }
}
