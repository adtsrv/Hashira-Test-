import java.math.BigInteger;
import java.util.*;

public class mainT2 {
    
    static class Point {
        BigInteger x, y;
        
        Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static BigInteger decodeValue(String value, int base) {
        return new BigInteger(value, base);
    }
    
    public static BigInteger lagrangeInterpolation(List<Point> points, BigInteger x) {
        BigInteger result = BigInteger.ZERO;
        int n = points.size();
        
        for (int i = 0; i < n; i++) {
            Point pi = points.get(i);
            BigInteger xi = pi.x;
            BigInteger yi = pi.y;
            
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    Point pj = points.get(j);
                    BigInteger xj = pj.x;
                    
                    numerator = numerator.multiply(x.subtract(xj));
                    denominator = denominator.multiply(xi.subtract(xj));
                }
            }
            
            BigInteger term = yi.multiply(numerator).divide(denominator);
            result = result.add(term);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int n = 10;
        int k = 7;
        
        List<Point> points = new ArrayList<>();
        
        points.add(new Point(BigInteger.valueOf(1), decodeValue("13444211440455345511", 6)));
        points.add(new Point(BigInteger.valueOf(2), decodeValue("aed7015a346d63", 15)));
        points.add(new Point(BigInteger.valueOf(3), decodeValue("6aeeb69631c227c", 15)));
        points.add(new Point(BigInteger.valueOf(4), decodeValue("1ace2b78fa272de", 16)));
        points.add(new Point(BigInteger.valueOf(5), decodeValue("742186715", 8)));
        points.add(new Point(BigInteger.valueOf(6), decodeValue("a37e6aca74", 13)));
        points.add(new Point(BigInteger.valueOf(7), decodeValue("d9730da3c", 14)));
        points.add(new Point(BigInteger.valueOf(8), decodeValue("1a228867a0b", 12)));
        points.add(new Point(BigInteger.valueOf(9), decodeValue("16fa45b00ff", 16)));
        points.add(new Point(BigInteger.valueOf(10), decodeValue("413212101203301", 6)));
        
        points.sort(Comparator.comparing(p -> p.x));
        
        List<Point> selectedPoints = points.subList(0, k);
        
        BigInteger secret = lagrangeInterpolation(selectedPoints, BigInteger.ZERO);
        
        System.out.println("Test Case 2 Secret: " + secret);
    }
}