package at.coon_and_friends_2019.ue5;

import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class SolarPanel extends BaseObjectClass implements Comparable<SolarPanel> {

	int index;
	
	public int price;

	long countryId;
	
	public SolarPanel(int index, InputLinesHolder holder) {
		this.index = index;
		Scanner scanner = holder.getNextLineAsScanner();
		this.countryId = scanner.nextLong();
		this.price = scanner.nextInt();
	}
	
	public Long getCountryId() {
		return this.countryId;
	}
	
	@Override
	public int compareTo(SolarPanel o) {
		return this.price - o.price;
	}
	
	public void printToStringBuilder(StringBuilder sb) {
		sb.append(this.price);
	}

}
