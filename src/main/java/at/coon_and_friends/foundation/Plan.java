package at.coon_and_friends.foundation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class Plan<T extends Drawable> extends BaseObjectClass {

	public int rows;

	public int columns;

	public T[][] grid;

	public Plan() {}

	public void draw(Path path, int pixelbreiteProFeld) throws IOException {
		BufferedImage image = new BufferedImage(columns * pixelbreiteProFeld + 1, rows * pixelbreiteProFeld + 1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics2D = image.createGraphics();
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.setRenderingHints(rh);

		double maxValue = Arrays.stream(this.grid).flatMap(a -> Arrays.stream(a)).map(T::getColorValue).max(Comparator.naturalOrder()).get();

		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.columns; x++) {
				T object = this.grid[x][y];
				object.draw(graphics2D, x, y, pixelbreiteProFeld, maxValue);
			}
		}

		ImageIO.write(image, "png", path.toFile());
	}
}
