import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

public class FunctionalFileProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String directoryPath = "./data_files"; 
        
        System.out.println("Loading files from: " + directoryPath);
        // 1. READ (Functional IO)
        List<String> loadedData = FileReaderAPI.readAllFiles(directoryPath);

        if (loadedData.isEmpty()) {
            System.out.println("Warning: No .txt files found in ./data_files. Please create them.");
        }

        while (true) {
            System.out.println("\n=== CS F301 FUNCTIONAL-OO DEMO ===");
            System.out.println("1. Search Keyword Frequency (Map & Reduce)");
            System.out.println("2. Calculate Average of Numbers (Aggregation)");
            System.out.println("3. Check Sorted-ness (Inversion Count)");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            // Simple robust input handling
            if (!scanner.hasNextInt()) { scanner.next(); continue; }
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter keywords (space separated): ");
                    String line = scanner.nextLine();
                    if (line.trim().isEmpty()) break;
                    String[] keywords = java.util.Arrays.stream(line.split("\\s+"))
                        .map(String::toLowerCase)
                        .toArray(String[]::new);
                    
                    // 2. TRANSFORM & 3. AGGREGATE
                    var words = FunctionalList.of(loadedData)
                        .map(s -> s.toLowerCase().replaceAll("[^a-z0-9 ]", " "))
                        .flatMap(s -> java.util.Arrays.stream(s.split("\\s+")))
                        .toList();
                    
                    var freqMap = words.stream()
                        .filter(w -> java.util.List.of(keywords).contains(w))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                    
                    System.out.println(">> Results: " + freqMap);
                    break;

                case 2:
                    double avg = AggregationUtils.calculateAverageFromText(loadedData);
                    System.out.println(">> Average of all numbers found in files: " + avg);
                    break;

                case 3:
                    // Convert first 50 words to length integers to check sortedness
                    List<Integer> wordLengths = loadedData.stream()
                        .flatMap(s -> java.util.Arrays.stream(s.split("\\s+")))
                        .limit(50) // Limit to avoid massive computation on huge files
                        .map(String::length)
                        .collect(Collectors.toList());
                    
                    long inv = AggregationUtils.countInversions(wordLengths);
                    System.out.println(">> Inversion count (on word lengths of first 50 words): " + inv);
                    break;

                case 4:
                    System.out.println("Exiting.");
                    return;
            }
        }
    }
}
