/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject2.pkg0;

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 *
 * @author probs
 */
public class Crosshair extends Polygon{
    private int x,y;
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
    public boolean intersects (Enemy enemy){
        return (enemy.getCenterX()>x-25 && enemy.getCenterX()<x+25 && enemy.getCenterY()>y-25 && enemy.getCenterY()<y+25);
    }

    boolean intersects(Powerup powerup) {
        return (powerup.getCenterX()>x-25 && powerup.getCenterX()<x+25 && powerup.getCenterY()>y-25 && powerup.getCenterY()<y+25);
    }
}
