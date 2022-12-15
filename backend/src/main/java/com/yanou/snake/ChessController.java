package com.yanou.snake;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yanou
 * @date 2022/12/15 21:33
 */
@RestController
@RequestMapping("/play")
public class ChessController {

    @Autowired
    PlayerService chessService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> params) {
        int playerId = Integer.parseInt(params.get("playerId"));
        PlayerData player = chessService.getPlayer(playerId);
        if (player == null) {
            chessService.createPlayer(playerId);
            player = chessService.getPlayer(playerId);
        }
        JSONObject json = new JSONObject();
        Map<Integer, Integer> board = new HashMap<>();
        player.getBoard().getPoints().forEach((k, v) -> board.put(k, v.getDestination()));
        json.put("board", board);
        json.put("flag", player.getBoard().getFlag());
        return json.toJSONString();
    }

    @PostMapping("/start")
    public String start(@RequestBody Map<String, String> params) {
        int playerId = Integer.parseInt(params.get("playerId"));
        int size = Integer.parseInt(params.get("size"));
        PlayerData player = chessService.getPlayer(playerId);
        if (player == null) {
            chessService.createPlayer(playerId);
            player = chessService.getPlayer(playerId);
        }
        player.getBoard().reStart(size);
        JSONObject json = new JSONObject();
        Map<Integer, Integer> board = new HashMap<>();
        player.getBoard().getPoints().forEach((k, v) -> board.put(k, v.getDestination()));
        json.put("board", board);
        json.put("flag", player.getBoard().getFlag());
        return json.toJSONString();
    }

    @PostMapping("/move")
    public String move(@RequestBody Map<String, String> params) {
        int playerId = Integer.parseInt(params.get("playerId"));
        int step = new Random().nextInt(6) + 1;
        PlayerData player = chessService.getPlayer(playerId);
        Board board = player.getBoard();
        if (board.isEnd()) {
            throw new RuntimeException("is end");
        }
        player.getBoard().move(step);
        player.getTrack().add(step);
        if (board.isEnd()) {
            player.saveTrack();
        }
        JSONObject json = new JSONObject();
        Map<Integer, Integer> boardMap = new HashMap<>();
        player.getBoard().getPoints().forEach((k, v) -> boardMap.put(k, v.getDestination()));
        json.put("board", boardMap);
        json.put("flag", player.getBoard().getFlag());
        json.put("step", step);
        return json.toJSONString();
    }

    @PostMapping("/getRecords")
    public String getRecords(@RequestBody Map<String, String> params) {
        int playerId = Integer.parseInt(params.get("playerId"));
        PlayerData player = chessService.getPlayer(playerId);
        JSONObject json = new JSONObject();
        json.put("record", player.getRecord());
        return json.toJSONString();
    }
}
