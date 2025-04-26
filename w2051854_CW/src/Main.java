import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        while (run) {
            System.out.println();
            System.out.println("=====Menu=====");
            System.out.println("Choose an option:");
            System.out.println("1. Test with custom testing files");
            System.out.println("2. Test with a benchmark file");
            System.out.println("3. Exit");
            System.out.println("==============");
            System.out.print("Enter your choice (1 or 2  or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println();
                System.out.println("Test with custom testing files");

                // Specify the test folder path (relative to the project root)
                File testFolder = new File("test");

                // Check if the test folder exists
                if (!testFolder.exists() || !testFolder.isDirectory()) {
                    System.out.println("Error: 'test' folder not found in the project directory.");
                    scanner.close();
                    return;
                }

                // Get all files in the test folder
                File[] testFiles = testFolder.listFiles();
                if (testFiles == null || testFiles.length == 0) {
                    System.out.println("Error: No files found in the test folder.");
                    scanner.close();
                    return;
                }

                // Process each file in the test folder
                for (File file : testFiles) {
                    if (file.isFile()) { // Ensure it's a file, not a subdirectory
                        String filename = file.getName();
                        String filepath = "test/" + filename; // Path relative to the project root
                        try {
                            GraphStructure graphStructure = FileLoader.file(filepath);
                            System.out.println("Successfully parsed " + filename + ": " + graphStructure.getNumNodes() + " nodes");
                            MaxFlowAlgo ff = new MaxFlowAlgo(graphStructure);
                            ff.computeMaxFlow(filename);
                        } catch (Exception e) {
                            System.out.println("Error in " + filename + ": " + e.getMessage());
                        }
                    }
                }


            } else if (choice == 2) {
                System.out.print("Enter the benchmark file number (1 to 20, for bridge_i.txt): ");
                int fileNumber = scanner.nextInt();


                if (fileNumber < 1 || fileNumber > 20) {
                    System.out.println("Invalid file number. Please enter a number between 1 and 20.");
                    scanner.close();
                    return;
                }

                // Construct the filename and filepath
                String filename = "bridge_" + fileNumber + ".txt";
                String filepath = "benchmarks/" + filename;

                System.out.println("\nTesting benchmark file: " + filename);
                try {
                    GraphStructure graphStructure = FileLoader.file(filepath);
                    MaxFlowAlgo maxFlowAlgo = new MaxFlowAlgo(graphStructure);
                    maxFlowAlgo.computeMaxFlow(filename);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (choice == 3) {
                run = false;

            }else {
                System.out.println("Invalid choice. Please run the program again and select 1 or 2.");
            }
        }



    }
}