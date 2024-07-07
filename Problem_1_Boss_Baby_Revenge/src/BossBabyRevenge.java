import java.util.logging.Logger;

public class BossBabyRevenge {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(BossBabyRevenge.class.getName());

        // Start time measurement
        long startTime = System.nanoTime();

        // Test cases
        BossBabyRevenge revenge =  new BossBabyRevenge();
        System.out.println(revenge.checkRevenge("SRSSRRR"));
        System.out.println(revenge.checkRevenge("RSSRR"));
        System.out.println(revenge.checkRevenge("SSSRRRRS"));
        System.out.println(revenge.checkRevenge("SRRSSR"));
        System.out.println(revenge.checkRevenge("SSRSRRR"));

        // End time measurement
        long endTime = System.nanoTime();

        // Monitor time usage
        logger.info(String.format("Execution time: %.6f ms", (endTime - startTime) / 1000000.0));

        // Monitor memory usage
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        logger.info(String.format("Memory used: %.6f MB", memoryUsed / 1024.0 / 1024.0));
    }

    public String checkRevenge(String S) {

        // This ensures Boss Baby doesn't shoot first
        if (S.charAt(0) == 'R') {
            return "Bad boy";
        }

        int shotCount = 0;    // Counter for shots ('S')
        int revengeCount = 0; // Counter for revenge actions ('R')

        // Iterate through each character in the string
        for (char action : S.toCharArray()) {
            if (action == 'S') {
                shotCount++;
            } else if (action == 'R') {
                revengeCount++;
            }
        }

        // Check if all shots have been avenged at least once
        return (revengeCount > shotCount) ? "Good boy" : "Bad boy";
    }
}