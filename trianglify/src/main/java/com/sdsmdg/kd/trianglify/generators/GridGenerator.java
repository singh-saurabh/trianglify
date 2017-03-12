package com.sdsmdg.kd.trianglify.generators;

import android.graphics.Point;

import com.sdsmdg.kd.trianglify.models.Grid;

import java.util.Random;
import java.util.Vector;

public class GridGenerator {

    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;
    private int pointsPerCircle = 8;

    public Vector<Point> gridPoints = new Vector<>();
    public Grid grid;

    public enum GridType {CIRCULAR, RECTANGULAR}

    ;
    public GridType currentGridtype = GridType.RECTANGULAR;

    public GridGenerator() {
    }

    public GridGenerator(int bleedX, int bleedY) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;
    }

    public Grid generateGrid(int width, int height, int cellSize, int variance) {
        this.gridPoints.clear();
        
        if (this.currentGridtype == GridType.RECTANGULAR) {
            int x, y;
            for (int j = -bleedY; j < height + bleedY; j += cellSize) {
                for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                    x = i + random.nextInt(variance);
                    y = j + random.nextInt(variance);
                    this.gridPoints.add(new Point(x, y));
                }
            }
        } else if (this.currentGridtype == GridType.CIRCULAR) {
            Point center = new Point(width / 2, height / 2);
            int maxRadius = Math.max(width + bleedX, height + bleedY);
            this.gridPoints.add(center);

            double slice, angle;
            int x, y;

            for (int radius = cellSize; radius < maxRadius; radius += cellSize) {
                slice = 2 * Math.PI / pointsPerCircle;
                for (int i = 0; i < pointsPerCircle; i++) {
                    angle = slice * i;
                    x = (int) (center.x + radius * Math.cos(angle)) + random.nextInt(variance);
                    y = (int) (center.y + radius * Math.sin(angle)) + random.nextInt(variance);
                    this.gridPoints.add(new Point(x, y));
                }
            }
        }

        grid = new Grid(gridPoints);
        return grid;
    }
}