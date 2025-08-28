import java.math.BigInteger;
import java.util.*;

public class mainT1 {
    
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
        int n = 4;
        int k = 3;
        
        List<Point> points = new ArrayList<>();
        
        points.add(new Point(BigInteger.valueOf(1), decodeValue("4", 10)));
        points.add(new Point(BigInteger.valueOf(2), decodeValue("111", 2)));
        points.add(new Point(BigInteger.valueOf(3), decodeValue("12", 10)));
        points.add(new Point(BigInteger.valueOf(6), decodeValue("213", 4)));
        
        points.sort(Comparator.comparing(p -> p.x));
        
        List<Point> selectedPoints = points.subList(0, k);
        
        BigInteger secret = lagrangeInterpolation(selectedPoints, BigInteger.ZERO);
        
        System.out.println("Test Case 1 Secret: " + secret);
    }
}