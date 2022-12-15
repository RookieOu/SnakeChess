package com.yanou.snake;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yanou
 * @date 2022/12/15 20:40
 */
@Service
public class PlayerService {
    private final Map<Integer, PlayerData> players = new HashMap<>();

    public PlayerData getPlayer(int id){
        return players.get(id);
    }

    public void createPlayer(int id){
        if(!players.containsKey(id)){
            players.put(id, new PlayerData(id));
        }
    }
}
