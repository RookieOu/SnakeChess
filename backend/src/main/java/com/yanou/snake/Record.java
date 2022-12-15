package com.yanou.snake;

import java.util.List;
import java.util.Map;

/**
 * @author yanou
 * @date 2022/12/16 01:09
 */
public class Record {
    private final Map<Integer, Integer> points;

    List<Integer> track;

    public Record(Map<Integer, Integer> points, List<Integer> track) {
        this.points = points;
        this.track = track;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public List<Integer> getTrack() {
        return track;
    }

    public void setTrack(List<Integer> track) {
        this.track = track;
    }
}
