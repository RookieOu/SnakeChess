package com.yanou.snake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanou
 * @date 2022/12/15 21:38
 */
public class PlayerData {

    private final int id;

    private Board board;

    private List<Integer> track;

    private Map<Integer, Record> record;


    public PlayerData(int id) {
        this.id = id;
        board = new Board();
        track = new ArrayList<>();
        record = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Integer> getTrack() {
        return track;
    }

    public void setTrack(List<Integer> track) {
        this.track = track;
    }

    public Map<Integer, Record> getRecord() {
        return record;
    }

    public void setRecord(Map<Integer, Record> record) {
        this.record = record;
    }

    public void saveTrack() {
        getRecord().put(record.size() + 1, new Record(new HashMap<>(board.getPoints()), new ArrayList<>(getTrack())));
        getTrack().clear();
    }
}
