package at.coon_and_friends.foundation;

public final class MathUtil {

	private MathUtil() {
	}

	/**
	 * This method rotates the matrix 90 degrees clockwise by using extra
	 * buffer.
	 */
	public static int[][] rotateMatrixClockwise(int[][] matrix) {
		int[][] rotated = new int[matrix[0].length][matrix.length];

		for (int i = 0; i < matrix[0].length; ++i) {
			for (int j = 0; j < matrix.length; ++j) {
				rotated[i][j] = matrix[matrix.length - j - 1][i];
			}
		}

		return rotated;
	}

}
