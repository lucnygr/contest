package at.coon_and_friends.foundation;

import at.coon_and_friends.foundation.geom.DoublePoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public interface Drawable {
    default Point2D scale(Point2D point, int pixelBreitePoFeld) {
        return new DoublePoint(point.getX() * pixelBreitePoFeld, point.getY() * pixelBreitePoFeld);
    }

    default void draw(Graphics2D graphics, int column, int row, int pixelbreiteProFeld, double maxValue) {
        this.drawCell(graphics, column, row, pixelbreiteProFeld);
        this.fillCellBackground(graphics, this.getCellBackgroundColor(column, row, maxValue), column, row, pixelbreiteProFeld);
        this.drawString(graphics, this.getFontColor(column, row), this.getDrawString(), column, row, pixelbreiteProFeld);
    }

    default void drawCell(Graphics2D graphics, int column, int row, int pixelbreiteProFeld) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(column * pixelbreiteProFeld, row * pixelbreiteProFeld, pixelbreiteProFeld, pixelbreiteProFeld);
    }

    default Color getCellBackgroundColor(int column, int row, double maxValue) {
        return Color.getHSBColor((float) (this.getColorValue() / maxValue), 1, 1);
    }

    default void fillCellBackground(Graphics2D graphics, Color backgroundColor, int column, int row, int pixelbreiteProFeld) {
        graphics.setColor(backgroundColor);
        graphics.fillRect(column * pixelbreiteProFeld + 1, row * pixelbreiteProFeld + 1, pixelbreiteProFeld - 2, pixelbreiteProFeld - 2);
    }

    default String getDrawString() {
        return this.toString();
    }

    default Color getFontColor(int column, int row) {
        return Color.BLACK;
    }

    default void drawString(Graphics2D graphics, Color fontColor, String text, int column, int row, int pixelbreiteProFeld) {
        graphics.setColor(fontColor);
        int y = row * pixelbreiteProFeld + 1;
        for (String line : text.split("\n"))
            graphics.drawString(line, column * pixelbreiteProFeld + 1, y += graphics.getFontMetrics().getHeight());
    }

    default Color getColor(double value, double maxValue){
        return Color.getHSBColor((float) (value / maxValue), 1, 1);
    }

    default double getColorValue() {
        return 0;
    }
}
