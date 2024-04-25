import java.util.Locale;


public class CountryNotFoundException extends Exception{

    public CountryNotFoundException(String countryName){
        super(String.format(Locale.ENGLISH, "%s doesn't exist",countryName));
    }
}
