package transform;

public class Transform { 
    private static final long DEFAULT_N = 5;
    private static final long DEFAULT_M = 13;
    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = (long)Math.pow(10, 15);
    
    private static int log2(double value) {
        return (int)(Math.log(value)/Math.log(2));
    }

    private static long pow2(int value) {
        return (long)Math.pow(2, value);
    }

    private static long additiveFuncCount(long value) {
        return (value + 1) / 2;
    }

    private static long getArg(String[] args, int i) {
        long arg = 0;

        try {
            arg = Long.parseLong(args[i]);
        } catch (NumberFormatException e) {
            System.err.println("Argument: " + args[i] + " must be a long.");
            System.exit(1);
        }
        
        if(arg < MIN_VALUE || arg > MAX_VALUE) {
            System.err.println("Argument: " + args[i] + " must be between " + 
                    MIN_VALUE + " and " + MAX_VALUE + ".");
            System.exit(1);
        }

        return arg;
    }
    
    private static long transform(long n, long m) {
        int maxPower = log2((double)m / n);
        long minResult = 0;

        for(int iPower = maxPower; iPower >= 0; iPower--) {
            long rest = m - pow2(iPower) * n;
            long result = iPower;

            for(int i = iPower; i>=0; i--) {
                long pow = pow2(i);

                result += additiveFuncCount(rest / pow);
                rest %= pow;
            }

            minResult = iPower == maxPower ? result : Math.min(minResult, result);
        }
        
        return minResult;
    }

    public static void main(String[] args) {
        long n;
        long m;

        if(args.length == 2) {
            n = getArg(args, 0);
            m = getArg(args, 1);
        } else {
            n = DEFAULT_N;
            m = DEFAULT_M;
        }

        if(n > m) {
            System.err.println("Argument " + n + " must be less then " + m + ".");
            System.exit(1);            
        }
        
        System.out.println("n: " + n);
        System.out.println("m: " + m);
        System.out.println("Total functions: " + transform(n, m));
    }
}