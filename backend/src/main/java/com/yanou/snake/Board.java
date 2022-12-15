package com.yanou.snake;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yanou
 * @date 2022/12/15 20:52
 */
public class Board {
    private final Map<Integer, Point> points;
    int flag;

    public Board() {
        points = new HashMap<>();
        reStart(5);
    }

    public void reStart(int n) {
        flag = 0;
        if (n > 100 || n < 1) {
//todo 棋盘大小限制
        }
        points.clear();
        Random random = new Random();
        for (int i = 1, floor = 0; i <= n * n; i++) {
            if (i == n * n) {
                points.put(i, new Point(i, -1));
                continue;
            }
            if (i % n == 1) {
                floor++;
            }
            int randomNum = random.nextInt(10);
            if (floor == 1) {
                randomNum = random.nextInt(9);
            } else if (floor == n) {
                while (randomNum == 8) {
                    randomNum = random.nextInt(10);
                }
            }
            if (randomNum < 8) {
                points.put(i, new Point(i, -1));
            } else if (randomNum == 8) {
                int dFloor = random.nextInt(n - floor) + floor;
                points.put(i, new Point(i, dFloor * n + random.nextInt(n) + 1));
            } else {
                int dFloor = random.nextInt(floor);
                points.put(i, new Point(i, dFloor * n + random.nextInt(n) + 1));
            }
        }
    }

    public Map<Integer, Point> getPoints() {
        return points;
    }

    public int getFlag() {
        return flag;
    }

    public void move(int step) {
        int newflag = flag + step;
        if (newflag > points.size()) {
            flag = points.size();
            move(points.size() - newflag);
            return;
        }
        if (newflag < 1) {
            flag = 1;
            move(1 - newflag);
            return;
        }
        Point point = points.get(flag + step);
        if (point.getDestination() == -1) {
            flag = newflag;
        } else {
            flag = point.getDestination();
        }
    }

    public boolean isEnd() {
        return flag == points.size();
    }
}
