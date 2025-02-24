import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private final String fileDirectory = "src\\movies_data.csv";

    private final Scanner inputScanner = new Scanner(System.in);

    public MovieCollection() {
        movies = new ArrayList<>();
        importMovies();
        run();
    }

    private void run() {
        System.out.println("Welcome to the movie database 7956!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search by (t)itle");
            System.out.println("- search for (c)ast member");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = inputScanner.nextLine();

            switch (menuOption) {
                case "t" -> searchTitles();
                case "c" -> searchCast();
                case "q" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void searchTitles() {

    }


    private void searchCast() {

    }

    private void importMovies() {
        try {
            File myFile = new File(fileDirectory);
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine(); //Skip over the first (formatting) line
            while (fileScanner.hasNext()) {
                String[] splitData = fileScanner.nextLine().split(",");
                String title = splitData[0];
                String cast = splitData[1];
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);
                Movie movie = new Movie(title, cast, director, overview, runtime, userRating);
                movies.add(movie);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
