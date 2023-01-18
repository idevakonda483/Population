import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *	Population - This program sorts cities in the database in 6 different
 *  ways based on the user input, comparing the time taken for different
 *  kinds of sorts (selection, insert, merge).
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Indu Devakonda
 *	@since	January 16, 2023
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	private int numCities; // number of cities in the database
	private List<City> temp; // temporary list for merge sort
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	
	public Population()
	{
		cities = new ArrayList<>();
		temp = new ArrayList<>();
	}
	
	public static void main(String [] args)
	{
		Population pop = new Population();
		pop.printIntroduction();
		pop.readFile();
		while(true)
		{
			pop.printMenu();
			System.out.println();
		}
	}
	
	/**	Prints the introduction to Population and the number of cities 
	 * in the database. */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
		numCities = readFile();
		System.out.println(numCities + " cities in database\n");
	}
	
	/**	Print out the choices for population sorting, reads in the user's 
	 * selection and calls the appropriate method depending on how they want
	 * the data sorted. It also records the amount of time taken for each
	 * sort 
	 * */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
		
		String selectionStr = Prompt.getString("Enter selection");
		while("1234569".indexOf(selectionStr) == -1 || selectionStr.equals(""))
		{
			System.out.println("ERROR: " + selectionStr + " is not valid");
			selectionStr = Prompt.getString("Enter selection");
		}
		
		int selection = Integer.parseInt(selectionStr);
		
		if (selection == 1)
		{
			long startMillisec = System.currentTimeMillis();
			sortLeastPopulousCities();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
	
		else if (selection == 2)
		{
			long startMillisec = System.currentTimeMillis();
			sortMostPopulousCities();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
		
		else if (selection == 3)
		{
			long startMillisec = System.currentTimeMillis();
			sortFirstCitiesByName();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
			
		else if (selection == 6)
		{
			long startMillisec = System.currentTimeMillis();
			sortSameNameCitiesByPopulation();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
	
		else if (selection == 4)
		{
			long startMillisec = System.currentTimeMillis();
			sortLastCitiesByName();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
		else if (selection == 5)
		{
			long startMillisec = System.currentTimeMillis();
			sortMostPopulousInState();
			long endMillisec = System.currentTimeMillis();
			System.out.println("\nElapsed Time " + Long.toString((endMillisec-startMillisec)) + " milliseconds");
		}
		else if (selection == 9)
		{
			System.out.println("\nThanks for using population!");
			System.exit(1);
		}
	}
	
	/**
	 * This method reads in the cities in the database and splits their
	 * information into the name of the city, state its located in, type 
	 * of city and population using the delimiter method. 
	 * 
	 * @return int		the number of cities in the database
	 * */
	public int readFile()
	{
		int numCities = 0;
		Scanner input = FileUtils.openToRead("usPopData2017.txt");
		input = input.useDelimiter("[\t\n]");
		while (input.hasNext())
		{
			String stateName = input.next();
			String cityName = input.next();
			String cityType = input.next();
			int population = Integer.parseInt(input.next());
			cities.add(new City(stateName, cityName, cityType, population));
			numCities++;
		}  
		return numCities;
	}
	
	/**
	 * This method uses selection sort to organize the least populous 
	 * cities in ascending order. Cities with the same population are 
	 * sorted alphabetically based on state name.
	 * */
	public void sortLeastPopulousCities()
	{
		for (int n = numCities; n > 1; n--)
		{
			int max = 0;
			for (int i = 1; i < n; i++)
			{
				City city = cities.get(i);
				City maxCity = cities.get(max);
				if (city.getPopulation() > maxCity.getPopulation())
				{
					max = i;
				}
				if (city.getPopulation() == maxCity.getPopulation())
				{
					if (city.getState().compareTo(maxCity.getState()) > 0)
					{
						max = i;
					}
					else if (city.getState().compareTo(maxCity.getState()) == 0 &&
						city.getCity().compareTo(maxCity.getCity()) > 0)
					{
						max = i;
					}	
				}
			}
			
			City temp = cities.get(max);
			cities.set(max, cities.get(n-1));
			cities.set(n-1, temp);
		}
		
		System.out.println("\nFifty least populous cities");
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		int numToPrint = 50;
		for (int i = 0; i < numToPrint; i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i+1), cities.get(i).toString());
		}
	}
	
	/**
	 * This method uses a merge sort to organize the fifty most populous
	 * cities in the database in descending order
	 * */
	public void sortMostPopulousCities()
	{
		int n = numCities;
		for (int i = 0; i < n; i++)
		{
			temp.add(null);
		}
		recursiveSort(0, n-1);
		System.out.println("\nFifty most populous cities");
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		int numToPrint = 50;
		for (int i = 0; i < numToPrint; i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i+1), cities.get(i).toString());
		}
	}
	
	/**
	 * This method is a recursive function for the merge sort that splits the
	 * cities into groups until the number of elements in each group is 1 or
	 * 2, and then corrects the order in each of the seperate groups.
	 * 
	 * @param from		index of start element of list being sorted
	 * @param to		index of end element of list being sorted
	 * */
	public void recursiveSort(int from, int to)
	{
		if (to - from < 2)
		{
			if (to > from && cities.get(to).getPopulation() > cities.get(from).getPopulation())
			{
				City city = cities.get(to);
				cities.set(to, cities.get(from));
				cities.set(from, city);
			}
		}
		else
		{
			int middle = (from+to)/2;
			recursiveSort(from, middle);
			recursiveSort(middle + 1, to);
			merge(from, middle, to);
		}
	}
	
	/**
	 * This method merges all the seperate, sorted groups into a fully
	 * sorted list by comparing the edge values.
	 * 
	 * @param from		index of first element of list to be sorted
	 * @param middle	index of middle element of list
	 * @param to		index of last element of list to be sorted
	 * 
	 * */
	public void merge(int from, int middle, int to)
	{
		int i = from;
		int j = middle + 1;
		int k = from;
		while (i <= middle && j <= to)
		{
			if (cities.get(i).getPopulation() > cities.get(j).getPopulation())
			{
				temp.set(k, cities.get(i));
				i++;
			}
			else
			{
				temp.set(k, cities.get(j));
				j++;
			}
			k++;
		}
		
		while (i <= middle)
		{
			temp.set(k, cities.get(i));
			i++;
			k++;
		}
		
		while (j <= to)
		{
			temp.set(k, cities.get(j));
			j++;
			k++;
		}
		
		for (k = from; k <= to; k++)
			cities.set(k, temp.get(k));
	}
	
	/**
	 * This method asks the user for a state and then looks through the file
	 * to find all the cities in that state. Then, using selection sort,
	 * I organized that list into the fifty most populous cities in 
	 * descending order
	 * */
	public void sortMostPopulousInState()
	{
		List<City> sameStateCities = new ArrayList<>();
		String userState = Prompt.getString("Enter state name (ie. Alabama)");
		for (int i = 0; i < numCities; i++)
		{
			if (cities.get(i).getState().equals(userState))
				sameStateCities.add(cities.get(i));
		}
		
		while (sameStateCities.size() == 0)
		{
			System.out.println("ERROR: " + userState + " is not valid.");
			userState = Prompt.getString("Enter state name (ie. Alabama)");
			for (int i = 0; i < numCities; i++)
			{
				if (cities.get(i).getState().equals(userState))
					sameStateCities.add(cities.get(i));
			}
		}
		
		
		for (int n = sameStateCities.size(); n > 1; n--)
		{
			int max = 0;
			for (int m = 1; m < n; m++)
			{
				City city = sameStateCities.get(m);
				City maxCity = sameStateCities.get(max);
				if (city.getPopulation() < maxCity.getPopulation())
				{
					max = m;
				}
				if (city.getPopulation() == maxCity.getPopulation())
				{
					if (city.getState().compareTo(maxCity.getState()) > 0)
					{
						max = m;
					}
					else if (city.getState().compareTo(maxCity.getState()) == 0 &&
						city.getCity().compareTo(maxCity.getCity()) > 0)
					{
						max = m;
					}	
				}
			}
			
			City temp = sameStateCities.get(max);
			sameStateCities.set(max, sameStateCities.get(n-1));
			sameStateCities.set(n-1, temp);
		}
		
		System.out.println("\nFifty most populous cities in " + userState);
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		int numToPrint = 50;
		for (int i = 0; i < numToPrint && i < sameStateCities.size(); i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i+1), sameStateCities.get(i).toString());
		}
	}
	
	/**
	 * This method uses a merge sort to organize the cities in the database
	 * in alphabetical order based on city name, and then prints the 
	 * last 50 cities in backwards order
	 * */
	public void sortLastCitiesByName()
	{
		int n = numCities;
		for (int i = 0; i < n; i++)
		{
			temp.add(null);
		}
		recursiveSortName(0, n-1);
		int count = 0;
		
		System.out.println("\nFifty cities sorted by name descending");
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		int numToPrint = 50;
		for (int i = 1; i <= numToPrint; i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i), cities.get(numCities-i).toString());
		}
	}
	
	/**
	 * This method is a recursive function for the merge sort that splits the
	 * cities into groups until the number of elements in each group is 1 or
	 * 2, and then corrects the order in each of the seperate groups.
	 * 
	 * @param from		index of start element of list being sorted
	 * @param to		index of end element of list being sorted
	 * */
	public void recursiveSortName(int from, int to)
	{
		if (to - from < 2)
		{
			if (to > from && cities.get(to).getCity().compareTo(cities.get(from).getCity()) < 0)
			{
				City city = cities.get(to);
				cities.set(to, cities.get(from));
				cities.set(from, city);
			}
		}
		else
		{
			int middle = (from+to)/2;
			recursiveSortName(from, middle);
			recursiveSortName(middle + 1, to);
			mergeName(from, middle, to);
		}
	}
	
	/**
	 * This method merges all the seperate, sorted groups into a fully
	 * sorted list by comparing the edge values.
	 * 
	 * @param from		index of first element of list to be sorted
	 * @param middle	index of middle element of list
	 * @param to		index of last element of list to be sorted
	 * 
	 * */
	public void mergeName(int from, int middle, int to)
	{
		int i = from;
		int j = middle + 1;
		int k = from;
		while (i <= middle && j <= to)
		{
			if (cities.get(i).getCity().compareTo(cities.get(j).getCity()) < 0)
			{
				temp.set(k, cities.get(i));
				i++;
			}
			else
			{
				temp.set(k, cities.get(j));
				j++;
			}
			k++;
		}
		
		while (i <= middle)
		{
			temp.set(k, cities.get(i));
			i++;
			k++;
		}
		
		while (j <= to)
		{
			temp.set(k, cities.get(j));
			j++;
			k++;
		}
		
		for (k = from; k <= to; k++)
			cities.set(k, temp.get(k));
	}
	
	/** 
	 * This method takes a user's input of a city and then finds
	 * all the cities with that name. Then, using selection sort
	 * I sorted these cities into descending population order.
	 * */
	public void sortSameNameCitiesByPopulation()
	{
		List<City> sameNameCities = new ArrayList<>();
		String userCity = Prompt.getString("Enter city name");
		for (int i = 0; i < numCities; i++)
		{
			if (cities.get(i).getCity().equals(userCity))
				sameNameCities.add(cities.get(i));
		}
		
		while(sameNameCities.size() == 0)
		{
			System.out.println("ERROR: " + userCity + " is not valid.");
			userCity = Prompt.getString("Enter city name");
			for (int i = 0; i < numCities; i++)
			{
				if (cities.get(i).getCity().equals(userCity))
					sameNameCities.add(cities.get(i));
			}
		}
		
		for (int n = sameNameCities.size(); n > 1; n--)
		{
			int max = 0;
			for (int m = 1; m < n; m++)
			{
				City city = sameNameCities.get(m);
				City maxCity = sameNameCities.get(max);
				if (city.getPopulation() < maxCity.getPopulation())
				{
					max = m;
				}
				if (city.getPopulation() == maxCity.getPopulation())
				{
					if (city.getState().compareTo(maxCity.getState()) > 0)
					{
						max = m;
					}
					else if (city.getState().compareTo(maxCity.getState()) == 0 &&
						city.getCity().compareTo(maxCity.getCity()) > 0)
					{
						max = m;
					}	
				}
			}
			
			City temp = sameNameCities.get(max);
			sameNameCities.set(max, sameNameCities.get(n-1));
			sameNameCities.set(n-1, temp);
		}
		
		System.out.println("\nCity " + userCity + " by population");
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		for (int i = 0; i < sameNameCities.size(); i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i+1), sameNameCities.get(i).toString());
		}
	}
	
	/**
	 * This method uses insertion sort to organize the cities in the databse
	 * alphabetically by city name and printing the first 50.
	 * */
	public void sortFirstCitiesByName()
	{
		for (int n = 1; n < numCities; n++)
		{
			City temp = cities.get(n);
			int i = n;
			while (i > 0 && (temp.getCity().compareTo(cities.get(i-1).getCity()) < 0 || 
			(temp.getCity().compareTo(cities.get(i-1).getCity()) == 0 && temp.getPopulation() > cities.get(i-1).getPopulation())))
			{
				cities.set(i, cities.get(i-1));
				i--;
			}
			cities.set(i, temp);
		}
		
		System.out.println("\nFifty cities sorted by name");
		System.out.printf("%9s %21s %22s %20s\n", "State", "City", "Type", "Population");
		int numToPrint = 50;
		for (int i = 0; i < numToPrint; i++)
		{
			System.out.printf("%2s: %s\n", Integer.toString(i+1), cities.get(i).toString());
		}
	}
}
