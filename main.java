import java.math.BigInteger;
import java.util.*;

public class main {
    
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
    
    public static BigInteger solveShamirSecret(Map<String, Object> data) {
        Map<String, Object> keys = (Map<String, Object>) data.get("keys");
        int k = (Integer) keys.get("k");
        
        List<Point> points = new ArrayList<>();
        
        for (String key : data.keySet()) {
            if (!key.equals("keys")) {
                BigInteger x = new BigInteger(key);
                Map<String, Object> point = (Map<String, Object>) data.get(key);
                
                int base = Integer.parseInt((String) point.get("base"));
                String encodedValue = (String) point.get("value");
                BigInteger y = decodeValue(encodedValue, base);
                
                points.add(new Point(x, y));
            }
        }
        
        points.sort(Comparator.comparing(p -> p.x));
        
        List<Point> selectedPoints = points.subList(0, k);
        
        return lagrangeInterpolation(selectedPoints, BigInteger.ZERO);
    }
    
    public static void main(String[] args) {
        Map<String, Object> testCase1 = new HashMap<>();
        testCase1.put("keys", Map.of("n", 4, "k", 3));
        testCase1.put("1", Map.of("base", "10", "value", "4"));
        testCase1.put("2", Map.of("base", "2", "value", "111"));
        testCase1.put("3", Map.of("base", "10", "value", "12"));
        testCase1.put("6", Map.of("base", "4", "value", "213"));
        
        Map<String, Object> testCase2 = new HashMap<>();
        testCase2.put("keys", Map.of("n", 10, "k", 7));
        testCase2.put("1", Map.of("base", "6", "value", "13444211440455345511"));
        testCase2.put("2", Map.of("base", "15", "value", "aed7015a346d63"));
        testCase2.put("3", Map.of("base", "15", "value", "6aeeb69631c227c"));
        testCase2.put("4", Map.of("base", "16", "value", "1ace2b78fa272de"));
        testCase2.put("5", Map.of("base", "8", "value", "742186715"));
        testCase2.put("6", Map.of("base", "13", "value", "a37e6aca74"));
        testCase2.put("7", Map.of("base", "14", "value", "d9730da3c"));
        testCase2.put("8", Map.of("base", "12", "value", "1a228867a0b"));
        testCase2.put("9", Map.of("base", "16", "value", "16fa45b00ff"));
        testCase2.put("10", Map.of("base", "6", "value", "413212101203301"));
        
        BigInteger secret1 = solveShamirSecret(testCase1);
        BigInteger secret2 = solveShamirSecret(testCase2);
        
        System.out.println("Test Case 1 Secret: " + secret1);
        System.out.println("Test Case 2 Secret: " + secret2);
    }
}