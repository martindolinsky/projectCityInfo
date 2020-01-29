package sample;

/**
 * @author Martin Dolinsky
 */
public class City {
	private String name;
	private int population;
	private String threeCode;
	private String twoCode;
	private String country;

	public City(String name, int population, String threeCode, String twoCode,String country) {
		this.name = name;
		this.population = population;
		this.threeCode = threeCode;
		this.twoCode = twoCode;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public int getPopulation() {
		return population;
	}

	public String getThreeCode() {
		return threeCode;
	}

	public String getTwoCode() {
		return twoCode;
	}

	public String getCountry() {
		return country;
	}

}

