package at.coon_and_friends_2020.ue3;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plan extends BaseObjectClass {

	public List<Location> locations;

	public Plan(InputLinesHolder holder) {
		int number = holder.getNextLineAsInt();

		List<Location> locations = new ArrayList<>();

		for(int i = 0;i<number;i++) {
			Location location = new Location(holder);
			locations.add(location);
		}

		this.locations = locations;
	}
}
