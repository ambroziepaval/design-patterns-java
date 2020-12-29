package creational.factories;

enum CoordinateSystem {
    CARTESIAN,
    POLAR
}

class Point {

    private double x, y;

    protected Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double a,
                 double b,
                 CoordinateSystem cs) {
        
        switch (cs) {
            case CARTESIAN:
                this.x = a;
                this.y = b;
                break;
            case POLAR:
                this.x = a * Math.cos(b);
                this.y = a * Math.sin(b);
                break;
        }
    }

    // factory method
    public static Point newCartesianPoint(double x, double y) {
        return new Point(x, y);
    }

    public static Point newPolarPoint(double rho, double theta) {
        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static class Factory {
        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }
}

class PointFactory {
    public static Point newCartesianPoint(double x, double y) {
        return new Point(x, y);
    }

    public static Point newPolarPoint(double rho, double theta) {
        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
    }
}

class FactoryDemo {
    public static void main(String[] args) {
        Point point = Point.newCartesianPoint(2, 3);
        System.out.println(point);

        Point point1 = Point.Factory.newCartesianPoint(3, 4);
        System.out.println(point1);

        Point point2 = PointFactory.newCartesianPoint(4, 5);
        System.out.println(point2);
    }
}