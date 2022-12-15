package com.yanou.snake;

/**
 * @author yanou
 * @date 2022/12/15 21:09
 */
public class Point {
    private int number;
    private int destination;

    public Point(int number, int destination) {
        this.number = number;
        this.destination = destination;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
