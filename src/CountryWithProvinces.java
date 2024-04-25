import java.util.Arrays;

public class CountryWithProvinces extends Country{

    private Country[] provinces;
    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);
        this.provinces=provinces;
    }

    @Override
    public String toString() {
        return "CountryWithProvinces{" + getName() +
                ", provinces=" + Arrays.toString(provinces) +
                '}';
    }

}
