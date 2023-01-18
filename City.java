/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Indu Devakonda
 *	@since	January 16, 2023
 */
public class City implements Comparable<City> {
	
	// fields
	private String city; // name of city
	private String state; // state city is located in 
	private String cityType; // type of city (town, township, etc.)
	private int population; // number people in city
	
	// constructor
	public City(String stateIn, String cityIn, String cityTypeIn, int populationIn)
	{
		city = cityIn;
		state = stateIn;
		cityType = cityTypeIn;
		population = populationIn;
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	 public int compareTo(City other)
	 {
		 if (this.population != other.population)
			return this.population - other.population;
		else if (!this.state.equals(other.state))
			return this.state.compareTo(other.state);
		else
			return this.city.compareTo(other.city);
	 }
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	 public boolean equals(City other)
	 {
		 if (this.city.equals(other.city) && this.state.equals(other.state))
			return true;
		return false;
	 }
	
	/**	Accessor methods */
	public String getCity()
	{
		return city;
	}
	
	public String getState()
	{
		return state;
	}
	
	public String getCityType()
	{
		return cityType;
	}
	
	public int getPopulation()
	{
		return population;
	}
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, city, cityType,
						population);
	}
}
