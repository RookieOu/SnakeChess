package com.yanou.front;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanou
 * @date 2022/12/15 23:41
 */
public class Player {
    private int id;

    private int flag;
    private Map<Integer ,Integer> board;

    public Player() {
        board = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
