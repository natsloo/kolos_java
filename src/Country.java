import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Country {
    private final String name;

    private static String confirmed_casesFilePath;
    private static String deathsFilePath;
    public String getName() {
        return name;
    }

    public Country(String name){
        this.name=name;
    }

    static void setFiles(String confirmed_casesCSV, String deathsCSV) throws FileNotFoundException {
        File confirmed_casesFile = new File(confirmed_casesCSV);
        File deathsFile = new File(deathsCSV);

        if(!confirmed_casesFile.exists() || !confirmed_casesFile.canRead()
                || !deathsFile.exists() || !deathsFile.canRead() ){
            throw new FileNotFoundException("Can't find or read files.");
        }
        Country.confirmed_casesFilePath = confirmed_casesCSV;
        Country.deathsFilePath = deathsCSV;
    }

    public static Country fromCsv(String countryName){
        try {
            Scanner sCC = new Scanner(new File(confirmed_casesFilePath));
            Scanner sD = new Scanner(new File(deathsFilePath));
            String[] countryNames = sCC.nextLine().split(";");
            for(int i=1;i<countryNames.length;i++){
                if(countryNames[i].equals(countryName)){
                    String[] provinceNames = sCC.nextLine().split(";");
                    if (provinceNames[i].equals("nan")){
                        return new CountryWithoutProvinces(countryName);
                    }
                    else {
                        List<Country> provinces = new ArrayList<>();
                        for (int j=0;countryNames[i+j].equals(countryName);j++){
                            provinces.add(new CountryWithoutProvinces(provinceNames[i+j]));
                        }
                        Country[] provinces_arr = new Country[provinces.size()];
                        for(int k=0;k< provinces.size();k++){
                            provinces_arr[k]= provinces.get(k);
                        }
                        return new CountryWithProvinces(countryName,provinces_arr);
                    }
                }
            }
            throw new CountryNotFoundException(countryName);

        } catch (CountryNotFoundException e)  {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }

}
