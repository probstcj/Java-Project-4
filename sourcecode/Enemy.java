/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.Color;
import java.awt.Polygon;

/**
 * This class extends the Polygon class and creates a small triangle of various
 *  sizes.
 * @author probs
 * 
 */
public class Enemy extends Polygon {
    private int health, origHealth;
    private int[] dx,dy;
    private Color color;
    private double dr, theta, drad;

    /**
     * This constructor creates an Enemy with health, and the angle theta
     * @param theta The angle from the center to the Enemy on the outside of the circle
     * @param health The health of the Enemy
     */
    public Enemy(double theta, int health){
        color = Color.RED;
        this.health=health;
        this.origHealth = health;
        this.theta = theta;
        // Center is (350,350)
        // Create a triangle using the nGon class, centered at the center
        int sides =3;
        double thetaRad = ((double)2/sides)*Math.PI;
        double radius = (health/8)/(2*Math.sin(thetaRad/2));
        npoints = sides;
        xpoints = new int[sides];
        ypoints = new int[sides];
        for (int i = 0; i < sides; i++) {
            xpoints[i] = (int)(radius*(double)Math.cos(thetaRad*i-(Math.PI/(double)2))+350);
            ypoints[i] = (int)(radius*(double)Math.sin(thetaRad*i-(Math.PI/(double)2))+350);
        }
        // Take the points, and put them on the outer radius, at a rotation theta from x-axis
        
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] = xpoints[i]-(int)(300*Math.cos(theta-Math.PI));
            ypoints[i] = ypoints[i]-(int)(300*Math.sin(theta-Math.PI));
        }
        dr = (double)20/health;
        drad = dr;
        
        dx = new int[3];
        dy = new int[3];
        for (int i = 0; i < 3; i++) {
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
     * This method returns the color of the Enemy
     * @return Returns the color of the Enemy
     */
    public Color getColor(){
        return color;
    }

    /**
     * This method moves the Enemy towards the center of the circle
     */
    public void move(){
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] = (int)(350-300*Math.cos(theta-Math.PI)+dr*Math.cos(theta-Math.PI))-dx[i];
            ypoints[i] = (int)(350-300*Math.sin(theta-Math.PI)+dr*Math.sin(theta-Math.PI))-dy[i];
        }
        dr = dr+drad;
    }

    /**
     * This method gets the health of the Enemy
     * @return Returns the health of the Enemy
     */
    public int getHealth(){
        return health;
    }

    /**
     * This sets the health of the Enemy
     * @param health The health of the Enemy that the user wishes to set.
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * This gets the center X value of the Enemy.
     * @return Returns the center X value of the Enemy
     */
    public int getCenterX(){
        int sum =0;
        int i = 0;
        for (int x : xpoints) {
            sum += x;
            i++;
        }
        return sum/i;
    }

    /**
     * This gets the center Y value of the Enemy.
     * @return Returns the center Y value of the Enemy
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
     * This gets the maximum value of X in the X-points array
     * @return Returns the maximum value of X
     */
    public int getMaxX(){
        int max=xpoints[0];
        for (int x : xpoints) {
            if(x>max){
                max =x;
            }
        }
        return max;
    }

    /**
     * This gets the maximum value of Y in the Y-points array
     * @return Returns the maximum value of Y
     */
    public int getMaxY(){
        int max=ypoints[0];
        for (int y : ypoints) {
            if(y>max){
                max =y;
            }
        }
        return max;
    }

    /**
     * This gets the minimum value of X in the X-points array
     * @return Returns the minimum value of X
     */
    public int getMinX(){
        int min=xpoints[0];
        for (int x : xpoints) {
            if(x<min){
                min =x;
            }
        }
        return min;
    }

    /**
     * This gets the minimum value of Y in the Y-points array
     * @return Returns the minimum value of Y
     */
    public int getMinY(){
        int min=ypoints[0];
        for (int y : ypoints) {
            if(y<min){
                min =y;
            }
        }
        return min;
    }

    /**
     * This gets the health of the original Enemy, before deduction/damage
     * @return Returns the original health of Enemy
     */
    public int getOrigHealth(){
        return origHealth;
    }
}
