/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.Polygon;

/**
 * This class returns a crosshair for the Radar Game. It is en extension of the 
 * polygon class, and uses x-points and y-points in order to have a square with 
 * small indentations towards the middle of the screen, where the pointer 
 * should point
 * @author probs
 */
public class Crosshair extends Polygon{
    private int x,y;

    /**
     * This constructor returns a Crosshair object that is based on the value of
     *  the cursor, which is (x,y)
     * @param x This parameter is the x value of the cursor
     * @param y This parameter is the y value of the cursor
     */
    public Crosshair(int x, int y){
        this.x = x;
        this.y = y;
        npoints = 25;
        xpoints = new int[npoints];
        ypoints = new int[npoints];
        xpoints[0] = x-25;
        ypoints[0] = y-25;
        xpoints[1] = x-1;
        ypoints[1] = ypoints[0];
        xpoints[2] = xpoints[1];
        ypoints[2] = ypoints[1]+15;
        xpoints[3] = xpoints[2]+1;
        ypoints[3] = ypoints[2]+5;
        xpoints[4] = xpoints[3]+1;
        ypoints[4]=ypoints[2];
        xpoints[5] = x+1;
        ypoints[5] = ypoints[1];
        xpoints[6] = x+25;
        ypoints[6] = ypoints[5];
        xpoints[7] = xpoints[6];
        ypoints[7] = ypoints[6]+24;
        xpoints[8] = xpoints[7]-15;
        ypoints[8] = ypoints[7];
        xpoints[9] = xpoints[8]-5;
        ypoints[9] = ypoints[8]+1;
        xpoints[10] = xpoints[8];
        ypoints[10] = ypoints[9]+1;
        xpoints[11] = xpoints[10]+15;
        ypoints[11] = ypoints[10];
        xpoints[12] = xpoints[11];
        ypoints[12] = y+25;
        xpoints[13] = xpoints[12]-24;
        ypoints[13] = ypoints[12];
        xpoints[14] = xpoints[13];
        ypoints[14] = ypoints[12]-15;
        xpoints[15] = xpoints[14]-1;
        ypoints[15] = ypoints[14]-5;
        xpoints[16] = xpoints[15]-1;
        ypoints[16] = ypoints[14];
        xpoints[17] = xpoints[16];
        ypoints[17] = ypoints[12];
        xpoints[18] = x-25;
        ypoints[18] = y+25;
        xpoints[19] = xpoints[18];
        ypoints[19] = ypoints[18]-24;
        xpoints[20] = xpoints[19]+15;
        ypoints[20] = ypoints[19];
        xpoints[21] = xpoints[20]+5;
        ypoints[21] = ypoints[20]-1;
        xpoints[22] = xpoints[20];
        ypoints[22] = ypoints[21]-1;
        xpoints[23] = xpoints[19];
        ypoints[23] = ypoints[22];
        xpoints[24] = xpoints[0];
        ypoints[24] = ypoints[0];
    }

    /**
     * This method tests to see if it intersects the Enemy. It tests the center point 
     * of the Enemy and the boundaries of the crosshair, and see if they are within each other.
     * @param enemy This is an Enemy that tests to see if it intersects the Crosshair
     * @return Returns true if the Enemy intersects, and false if it doesn't
     */
    public boolean intersects (Enemy enemy){
        return (enemy.getCenterX()>x-25 && enemy.getCenterX()<x+25 && enemy.getCenterY()>y-25 && enemy.getCenterY()<y+25);
    }

    /**
     * This method tests to see if it intersects the Powerup. It tests the center point 
     * of the Powerup and the boundaries of the crosshair, and see if they are within each other.
     * @param powerup This is a Powerup that tests to see if it intersects the Crosshair
     * @return Returns true if it intersects, and false if it doesn't
     */
    public boolean intersects(Powerup powerup) {
        return (powerup.getCenterX()>x-25 && powerup.getCenterX()<x+25 && powerup.getCenterY()>y-25 && powerup.getCenterY()<y+25);
    }
}
