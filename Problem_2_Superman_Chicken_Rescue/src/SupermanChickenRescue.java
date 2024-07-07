import java.util.logging.Logger;

public class SupermanChickenRescue {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(SupermanChickenRescue.class.getName());

        // Start time measurement
        long startTime = System.nanoTime();

        // Test cases
        SupermanChickenRescue superman =  new SupermanChickenRescue();
        System.out.println(superman.findMaxChickens(5,5, new int[]{2, 5, 10, 12, 15}));
        System.out.println(superman.findMaxChickens(6,10, new int[]{1, 11, 30, 34, 35, 37}));
        // End time measurement
        long endTime = System.nanoTime();

        // Monitor time usage
        logger.info(String.format("Execution time: %.6f ms", (endTime - startTime) / 1000000.0));

        // Monitor memory usage
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        logger.info(String.format("Memory used: %.6f MB", memoryUsed / 1024.0 / 1024.0));
    }

    public int findMaxChickens(int n, int k, int[] x) {

        // Validate input value n, k
        if (n < 1 || n > 1_000_000 || k < 1 || k > 1_000_000) {
            throw new IllegalArgumentException("Input integers n and k must be between 1 and 1,000,000.");
        }
        // Validate the number of chickens must equal chicken's positions
        if (x == null || x.length != n) {
            throw new IllegalArgumentException("Input integer array x must be of length n.");
        }
        // Validate input value x
        for (int i = 0; i < n; i++) {
            if (x[i] < 1 || x[i] > 1_000_000_000) {
                throw new IllegalArgumentException("Each element in array x must be between 1 and 1,000,000,000.");
            }
        }

        int maxChickens = 0;
        int left = 0;
        int right = 0;

        while (right < x.length) {
            // Expand the window to the right
            while (right < x.length && x[right] - x[left] < k) {
                right++;
            }
            // Update max chickens
            // The current window size (right - left) represents the number of chickens covered
            maxChickens = Math.max(maxChickens, right - left);

            // Move the left pointer to start a new window
            left++;
        }
        return maxChickens;
    }
}