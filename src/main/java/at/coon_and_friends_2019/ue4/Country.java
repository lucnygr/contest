package at.coon_and_friends_2019.ue4;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Country extends BaseObjectClass implements Comparable<Country>{

	List<Cell> cells;

	long countryId;
	
	public int avgX;
	
	public int avgY;
	
	public Cell capital;

	public Country(long countryId, List<Cell> cells) {
		this.countryId= countryId;
		this.cells = cells;
		
		this.avgX = (int) Math.floor(cells.stream().mapToInt(c -> c.x).average().getAsDouble());
		this.avgY = (int) Math.floor(cells.stream().mapToInt(c -> c.y).average().getAsDouble());
		
		List<Cell> inlandCells = cells.stream().filter(c -> !c.isBorderCell()).collect(Collectors.toList());
		inlandCells.forEach(c -> c.calcDinstanceToCenterOfMass(this.avgX, this.avgY));
		Map<Double, List<Cell>> inlandCellsGroupedByDistance = inlandCells.stream().collect(Collectors.groupingBy(Cell::getDistanceToCenterOfMass));
		TreeMap<Double, List<Cell>> inlandCellsGroupedByDistanceSorted = new TreeMap<>(inlandCellsGroupedByDistance);
		
		List<Cell> cellsWithLowestDistance = inlandCellsGroupedByDistanceSorted.firstEntry().getValue();		
		this.capital = cellsWithLowestDistance.stream().sorted().findFirst().get();
	}
	
	public Long getCountryId() {
		return this.countryId;
	}

	@Override
	public int compareTo(Country o) {
		return (int) (this.countryId - o.countryId);
	}

}
