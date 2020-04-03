package at.coon_and_friends_2019.ue4;

import java.util.Scanner;

import at.coon_and_friends.foundation.BaseObjectClass;
import at.coon_and_friends.foundation.InputLinesHolder;

public class Ray  extends BaseObjectClass {

	int x;
	
	int y;
	
	int directionX;
	
	int directionY;
	
	int endX;
	
	int endY;
	
	public Ray(InputLinesHolder holder) {
		Scanner scanner = holder.getNextLineAsScanner();
		x = scanner.nextInt();
		y = scanner.nextInt();
		directionX = scanner.nextInt();
		directionY = scanner.nextInt();
	}
	
	public void calcEndpoint(int rows, int cols) {
		int multiplier = Math.max(rows, cols);
		this.endX = this.x + this.directionX * multiplier;
		this.endY = this.y + this.directionY * multiplier;
	}
	
	public boolean isDominantHorizontal() {
		if(Math.abs(directionX) < Math.abs(directionY)) {
			return false;
		}
		return true;
	}
	
	public int getDirectionMultiplierX() {
		if(this.directionX >= 0) {
			return 1;
		}
		return -1;
	}
	
	public int getDirectionMultiplierY() {
		if(this.directionY >= 0) {
			return 1;
		}
		return -1;
	}
	
}
