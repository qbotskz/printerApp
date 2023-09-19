package com.akimatBot.utils;

import lombok.Data;

@Data
public
class MapUtil {

    static int INF = 10000;
    public double lang;
    public double lat;

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    ;

    static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y)) {
            return true;
        }
        return false;
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }


    static boolean doIntersect(Point p1, Point q1,
                               Point p2, Point q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }


    static boolean isInside(Point polygon[], int n, Point p) {
        if (n < 3) {
            return false;
        }

        Point extreme = new Point(INF, p.y);

        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            if (doIntersect(polygon[i], polygon[next], p, extreme)) {

                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    return onSegment(polygon[i], p,
                            polygon[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);


        return (count % 2 == 1);
    }

    public boolean isInDeliveryZone(double longitude, double latitude) {
//        [76.95713797363251,43.27135439850919],
//        [76.92621617239705,43.26909988629434],
//        [76.91619405992589,43.267901435958976],
//        [76.90632353075111,43.26539252255894],
//        [76.88708685397621,43.259140068702436],
//        [76.89428143634031,43.19440676459286],
//        [76.9045811189575,43.200560631843345],
//        [76.91316418780514,43.203700119224955],
//        [76.91385083331296,43.20734172074585],
//        [76.9444065584106,43.2279314850937],
//        [76.96581719456361,43.22875547992668],
//        [76.95713797363251,43.27135439850919]

        Point polygon1[] = {new Point(76.95713797363251, 43.27135439850919),
                new Point(76.92621617239705, 43.26909988629434),
                new Point(76.91619405992589, 43.267901435958976),
                new Point(76.90632353075111, 43.26539252255894),
                new Point(76.88708685397621, 43.259140068702436),
                new Point(76.89428143634031, 43.19440676459286),
                new Point(76.9045811189575, 43.200560631843345),
                new Point(76.91316418780514, 43.203700119224955),
                new Point(76.91385083331296, 43.20734172074585),
                new Point(76.9444065584106, 43.2279314850937),
                new Point(76.96581719456361, 43.22875547992668),
                new Point(76.95713797363251, 43.27135439850919),};
//        Point polygon1[] = {new Point(76.88701430114708, 43.264360942481865),
//                new Point(76.89233580383262, 43.218805288672414),
//                new Point(76.9664935186764, 43.2302287986255),
//                new Point(76.95670882019004, 43.271260321486935),
//                new Point(76.88701430114708, 43.264360942481865)};
        int n = polygon1.length;
        Point p = new Point(longitude, latitude);
        if (isInside(polygon1, n, p)) {
            return true;
        } else {
            return false;
        }
    }

}


