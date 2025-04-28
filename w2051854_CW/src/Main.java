import java.io.File;
import java.util.InputMismatchException;
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
            System.out.println("2. Test with a bridge benchmark file");
            System.out.println("3. Test with a ladder benchmark file");
            System.out.println("4. Exit");
            System.out.println("==============");
            System.out.print("Enter your choice (1, 2, 3, or 4): ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            if (choice == 1) {
                System.out.println();
                System.out.println("Test with custom testing files");

                // Specify the test folder path
                File testFolder = new File("test");


                if (!testFolder.exists() || !testFolder.isDirectory()) {
                    System.out.println("Error: 'test' folder not found in the project directory.");
                    continue;
                }

                // Get all files in the test folder
                File[] testFiles = testFolder.listFiles();
                if (testFiles == null || testFiles.length == 0) {
                    System.out.println("Error: No files found in the test folder.");
                    continue;
                }


                for (File file : testFiles) {
                    if (file.isFile()) {
                        String filename = file.getName();
                        String filepath = "test/" + filename;
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
                System.out.print("Enter the benchmark file number (1 to 19, for bridge_i.txt): ");

                int fileNumber;
                try {
                    fileNumber = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }

                if (fileNumber < 1 || fileNumber > 19) {
                    System.out.println("Invalid file number. Please enter a number between 1 and 19.");
                    continue;
                }


                String filename = "bridge_" + fileNumber + ".txt";
                String filepath = "benchmarks/" + filename;

                System.out.println("\nTesting benchmark file: " + filename);
                try {
                    GraphStructure graphStructure = FileLoader.file(filepath);
                    MaxFlowAlgo maxFlowAlgo = new MaxFlowAlgo(graphStructure);
                    maxFlowAlgo.computeMaxFlow(filename);

                }catch (StackOverflowError e) {
                    System.out.println("Error: StackOverflowError occurred while processing " + filename + ".");
                    System.out.println("The graph is too large for the recursive DFS implementation.");
                    System.out.println("Consider using a smaller graph or an iterative DFS approach.");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (choice == 3) {
                System.out.print("Enter the ladder benchmark file number (1 to 20, for ladder_i.txt): ");
                int fileNumber;
                try {
                    fileNumber = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }

                if (fileNumber < 1 || fileNumber > 20) {
                    System.out.println("Invalid file number. Please enter a number between 1 and 20.");
                    continue;
                }

                String filename = "ladder_" + fileNumber + ".txt";
                String filepath = "benchmarks/" + filename;

                System.out.println("\nTesting benchmark file: " + filename);
                try {
                    GraphStructure graphStructure = FileLoader.file(filepath);
                    MaxFlowAlgo maxFlowAlgo = new MaxFlowAlgo(graphStructure);
                    maxFlowAlgo.computeMaxFlow(filename);
                }catch (StackOverflowError e) {
                    System.out.println("Error: StackOverflowError occurred while processing " + filename + ".");
                    System.out.println("The graph is too large for the recursive DFS implementation.");
                    System.out.println("Consider using a smaller graph or an iterative DFS approach.");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (choice == 4) {
                run = false;

            }else {
                System.out.println("Invalid choice. Please run the program again and select 1 , 2 or 3.");
            }
        }



    }
}