package at.coon_and_friends_2018.ue1;

import java.util.Scanner;

import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseObjectClass;

public class Observation extends BaseObjectClass {

	int startTimestamp;

	int endTimestamp;

	int imagecount;

	public Observation(Scanner lineScanner) {
		startTimestamp = lineScanner.nextInt();
		endTimestamp = lineScanner.nextInt();
		imagecount = lineScanner.nextInt();

		Logger.info(this.getClass() + ": " + this.toString());
	}
}
