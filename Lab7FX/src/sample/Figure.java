package sample;

import javafx.scene.paint.Color;

public class Figure {
    private double x;
    private double y;

    private double size;
    private Color color;
    private int sides;

    private String text;

    public Figure(double x, double y, double size, int sides, Color color, String text) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.sides = sides;
        this.text = text;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public int getSides() {
        return sides;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
