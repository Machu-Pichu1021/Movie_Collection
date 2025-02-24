import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private final String fileDirectory = "src\\movies_data.csv";

    public MovieCollection() {
        movies = new ArrayList<>();
        importMovies();
        for (Movie movie : movies)
            System.out.println(movie);
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
