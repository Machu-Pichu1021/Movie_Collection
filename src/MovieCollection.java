import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private final String fileDirectory = "src\\movies_data.csv";

    private final Scanner inputScanner = new Scanner(System.in);

    public static final String YELLOW = "\033[0;33m";
    public static final String RESET = "\033[0m";

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
        System.out.println("You chose to search by title.");
        System.out.print("Please enter a search term: ");
        String keyword = inputScanner.nextLine();
        System.out.println("Here are all the movies containing '" + keyword + "' in their title:");

        ArrayList<Movie> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                foundMovies.add(movie);
        }

        if (foundMovies.isEmpty()) {
            System.out.println("No movies were found with that search term!");
            System.out.println("Check your spelling or try again with a different search term.");
            return;
        }

        alphabetizeMovies(foundMovies);
        for (int i = 0; i < foundMovies.size(); i++) {
            String title = foundMovies.get(i).getTitle();
            int keyIndex = title.toLowerCase().indexOf(keyword.toLowerCase());
            String print = (i + 1) + ": " + title.substring(0, keyIndex) + YELLOW + title.substring(keyIndex, keyIndex + keyword.length()) + RESET + title.substring(keyIndex + keyword.length());
            System.out.println(print);
        }

        System.out.println("Which movie would you like to view the information of?");
        System.out.print("Enter the number next to the title as appeared in the list above: ");
        if (inputScanner.hasNextInt()) {
            int choice = inputScanner.nextInt();
            inputScanner.nextLine(); //Read the new line character
            if (0 >= choice || choice >= foundMovies.size() + 1) {
                System.out.println("Choice is out of bounds; returning to main menu...");
                return;
            }
            System.out.println(foundMovies.get(choice - 1));
            System.out.println("Press enter to return to the main menu.");
            inputScanner.nextLine();
        }
        else {
            System.out.println("Invalid choice; returning to main menu...");
            inputScanner.nextLine();
        }
    }


    private void searchCast() {
        System.out.println("You chose to search for an actor.");
        System.out.print("Please enter a search term: ");
        String keyword = inputScanner.nextLine();
        System.out.println("Here are all the actors containing '" + keyword + "' in their name:");

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Movie> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            String[] cast = movie.getCast().split("\\|");
            for (String actor : cast) {
                if (!names.contains(actor) && actor.toLowerCase().contains(keyword.toLowerCase())) {
                    names.add(actor);
                    foundMovies.add(movie);
                }
            }
        }

        if (names.isEmpty()) {
            System.out.println("No actors were found with that search term!");
            System.out.println("Check your spelling or try again with a different search term.");
            return;
        }

        alphabetizeNames(names);
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            int keyIndex = name.toLowerCase().indexOf(keyword.toLowerCase());
            String print = (i + 1) + ": " + name.substring(0, keyIndex) + YELLOW + name.substring(keyIndex, keyIndex + keyword.length()) + RESET + name.substring(keyIndex + keyword.length());
            System.out.println(print);
        }

        System.out.println("Which actor would you like to view the information of?");
        System.out.print("Enter the number next to their name as appeared in the list above: ");
        if (inputScanner.hasNextInt()) {
            int choice = inputScanner.nextInt();
            inputScanner.nextLine(); //Read the new line character
            if (0 >= choice || choice >= names.size() + 1) {
                System.out.println("Choice is out of bounds; returning to main menu...");
                return;
            }
            String actorChosen = names.get(choice - 1);
            System.out.println("Here are all the movies that " + actorChosen + " has acted in: ");

            ArrayList<Movie> actorMovies = new ArrayList<>();
            for (Movie movie : foundMovies) {
                String[] cast = movie.getCast().split("\\|");
                for (String actor : cast) {
                    if (actor.equalsIgnoreCase(actorChosen)) {
                        actorMovies.add(movie);
                    }
                }
            }

            for (int i = 0; i < actorMovies.size(); i++)
                System.out.println((i + 1) + ": " + actorMovies.get(i).getTitle());

            System.out.println("Which movie would you like to view the information of?");
            System.out.print("Enter the number next to the title as appeared in the list above: ");
            if (inputScanner.hasNextInt()) {
                int choice2 = inputScanner.nextInt();
                inputScanner.nextLine(); //Read the new line character
                if (0 >= choice2 || choice2 >= actorMovies.size() + 1) {
                    System.out.println("Choice is out of bounds; returning to main menu...");
                    return;
                }
                System.out.println(actorMovies.get(choice2 - 1));
                System.out.println("Press enter to return to the main menu.");
                inputScanner.nextLine();
            }
            else {
                System.out.println("Invalid choice; returning to main menu...");
                inputScanner.nextLine();
            }
        }
        else {
            System.out.println("Invalid choice; returning to main menu...");
            inputScanner.nextLine();
        }
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

    private void alphabetizeNames(ArrayList<String> names) {
        for (int i = 1; i < names.size(); i++) {
            String name = names.get(i);
            int j = i - 1;
            while (j >= 0 && name.compareTo(names.get(j)) < 0) {
                names.set(j + 1, names.get(j));
                names.set(j, name);
                j--;
            }
        }
    }

    private void alphabetizeMovies(ArrayList<Movie> movies) {
        for (int i = 1; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            int j = i - 1;
            while (j >= 0 && movie.getTitle().compareTo(movies.get(j).getTitle()) < 0) {
                movies.set(j + 1, movies.get(j));
                movies.set(j, movie);
                j--;
            }
        }
    }
}