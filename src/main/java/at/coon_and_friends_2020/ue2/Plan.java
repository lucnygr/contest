package at.coon_and_friends_2020.ue2;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

import java.util.ArrayList;
import java.util.List;

public class Plan extends BaseObjectClass {

	public List<Flight> flights;

	public Plan(InputLinesHolder holder) {
		int number = holder.getNextLineAsInt();

		List<Flight> flights = new ArrayList<>();

		for(int i = 0;i<number;i++) {
			Flight flight = new Flight(holder);
			flights.add(flight);
		}

		this.flights = flights;
	}
}
