/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength)
    {
        int i;
        for ( i = 0; i < 4; i++)
        {
            turtle.forward(sideLength);
            turtle.turn(90);
        }

    }

    /**
     * Determine inside angles of a regular polygon.
     *
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides)
    {
        double kiki = sides;
        return (180 * (kiki - 2)) / kiki;

    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     *
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle)
    {
        return (int) Math.round(360 / (180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     *
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        turtle.turn(90);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(calculateRegularPolygonAngle(sides) + 180);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     *
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY)
    {
        double anglepp;
        if(targetY-currentY == 0)
            if(targetX-currentX > 0)
                return 90-currentBearing >=0 ? 90-currentBearing : 90-currentBearing + 360;
            else
                return 270-currentBearing >=0 ? 270-currentBearing : 270-currentBearing + 360;
        double tan=(targetX-currentX) / (targetY-currentY);
        System.out.println(tan);
        if (tan > 0)
            anglepp = Math.toDegrees(Math.atan(tan));
        else
            anglepp = Math.toDegrees(Math.atan(tan)) + 180;
        System.out.println(anglepp);
        if(targetX-currentX<0)
        {
            return 180 + anglepp - currentBearing >=0 ? 180 + anglepp - currentBearing : 360 + anglepp - currentBearing;
        }
        else if(targetX-currentX>0)
        {
            return anglepp-currentBearing >=0 ? anglepp-currentBearing : anglepp-currentBearing +360;
        }
        if(targetY > currentY)
            return  -currentBearing >=0 ? -currentBearing : -currentBearing+360;
        else
            return  180-currentBearing >=0 ? 180-currentBearing :180-currentBearing+360;

    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     *
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords)
    {
        List<Double> angles = new ArrayList<Double>();
        int currentX = xCoords.get(0);
        int currentY = yCoords.get(0);
        int targetX, targetY;
        double angle = 0;
        for (int i = 1; i < xCoords.size(); i++) { // 执行n-1次
            targetX = xCoords.get(i);
            targetY = yCoords.get(i);
            angle = calculateBearingToPoint(angle, currentX, currentY, targetX, targetY);
            angles.add(angle);
            currentX = targetX;
            currentY = targetY;
        }
        return angles;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points)
    {
        if (points.size() <= 2) {
            return points;
        }
        Set<Point> convexHullPoints = new HashSet<Point>();
        Point a = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        for (Point i : points) {
            if (i.x() < a.x() || (i.x() == a.x() && i.y() < a.y()))
                a = i;
        }
        Point curPoint = a, minPoint = null, lastPoint = a;
        double x1 = 0.0, y1 = -1.0;
        do {
            convexHullPoints.add(curPoint);
            double minTheta = Double.MAX_VALUE, x2 = 0.0, y2 = 0.0;
            for (Point i : points) {
                if ((!convexHullPoints.contains(i) || i == a) && (i != lastPoint)) {
                    double x3 = i.x() - curPoint.x(), y3 = i.y() - curPoint.y();
                    double Theta = Math
                            .acos((x1 * x3 + y1 * y3) / Math.sqrt(x1 * x1 + y1 * y1) / Math.sqrt(x3 * x3 + y3 * y3));
                    // System.out.println(i.x() + " " + i.y() + " " + Theta);
                    if (Theta < minTheta || (Theta == minTheta && x3 * x3 + y3 * y3 > Math.pow(i.x() - minPoint.x(), 2)
                            + Math.pow(i.y() - minPoint.y(), 2))) {
                        minPoint = i;
                        minTheta = Theta;
                        x2 = x3;
                        y2 = y3;
                    }
                }
            }
            x1 = x2;
            y1 = y2;
            lastPoint = curPoint;
            curPoint = minPoint;
            // System.out.println(curPoint.x() + " " + curPoint.y());
        } while (curPoint != a);
        return convexHullPoints;
    }

    /**
     * Draw your personal, custom art.
     *
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle)
    {
        int i,j;
        for(j=1;j<=5;j++)
        {
            for (i = 1; i <= 5; i++)
            {
                turtle.forward(200);
                turtle.turn(144);
            }
            turtle.turn(360-144);
        }

    }

    /**
     * Main method.
     *
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        //System.out.print(calculateBearingToPoint(30,1,1,0,0));
        //drawSquare(turtle, 40);
        drawPersonalArt(turtle);
        // draw the window
        turtle.draw();
    }

}