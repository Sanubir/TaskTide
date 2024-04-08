public class Functions {
    // Returns 2^n
    public static int pow2N(int n) {
        if (n == 0) {
            return 1; // 2^0 = 1
        }
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= 2;
        }
        return result;
    }

    // Returns sum of natural numbers in a given range
    public static int sumFromRange(int numberFrom, int numberTo) {
        int sum = 0;
        for (int i = numberFrom; i <= numberTo; i++) {
            sum += i;
        }
        return sum;
    }
}
