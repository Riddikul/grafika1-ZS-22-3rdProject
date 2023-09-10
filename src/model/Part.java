package model;

public class Part {
    private int color;
    private int count;
    private int start;



    public Part(int start, int count,int color) {
        this.start = start;
        this.count = count;
        this.color = color;
    }
    public int getColor() {
        return color;
    }

    public int getCount() {
        return count;
    }
    public int getStart() {
        return start;
    }

}