import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Country.setFiles("confirmed_cases.csv","deaths.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Country c = Country.fromCsv("gfrde");
        System.out.println(c.getName());
        System.out.println("\n" + c.toString());
    }

}