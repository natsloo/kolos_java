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
            String countryNames = sCC.nextLine();
            CountryColumns cc = getCountryColumns(countryNames,countryName);
            String[] countries = countryNames.split(";");
            if(cc.columnCount>1){
                Country[] provinces_arr = new Country[cc.columnCount];
                String[] provinceNames = sCC.nextLine().split(";");
                for(int k=0;k<cc.columnCount;k++) {
                    provinces_arr[k] = new CountryWithoutProvinces(provinceNames[k + cc.firstColumnIndex]);
                }
                return new CountryWithProvinces(countryName,provinces_arr);
            } else if (cc.columnCount==1) {
                return new CountryWithoutProvinces(countryName);
            }


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

    private static CountryColumns getCountryColumns(String firstCsvLine, String country) throws CountryNotFoundException {

        String[] countryNames = firstCsvLine.split(";");
        for(int i=1;i<countryNames.length;i++) {
            if (countryNames[i].equals(country)) {
                int j = 0;
                for (; countryNames[i + j].equals(country); j++) {
                }
                return new CountryColumns(i,j);
            }
        }
        throw new CountryNotFoundException(country);
    }

    private static class CountryColumns{
        public final int firstColumnIndex, columnCount;

        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }
    }

}
