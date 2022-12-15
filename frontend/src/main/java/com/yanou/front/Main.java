package com.yanou.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * @author yanou
 * @date ${DATE} ${TIME}
 */
public class Main {
    static RestTemplate restTemplate = new RestTemplate();

    static Player player = new Player();

    public static void main(String[] args) {
        String ip = "http://127.0.0.1:13000";
        Map<Integer, Integer> board = new HashMap<>();
        System.out.println("start game");
        printMenu();
        Scanner sc = new Scanner(System.in);
        while (true) {
            int op = sc.nextInt();
            if (op == 1) {
                System.out.println("请输入游戏id");
                int playerId = sc.nextInt();
                login(playerId, ip);
                player.setId(playerId);
                System.out.println("登录成功");
                printBoard(player.getBoard());
            } else if (op == 2) {
                System.out.println("请输入地图大小");
                int size = sc.nextInt();
                restart(ip, size);
                printBoard(player.getBoard());
            } else if (op == 3) {
                int step = move(ip);
                System.out.println("此次点数为: " + step);
                printBoard(player.getBoard());
            } else if (op == 4) {
                JSONObject jsonObject = getRecord(ip);
                for (String s : jsonObject.keySet()) {
                    System.out.println("地图" + s);
                    Map<Integer, Integer> newBord = new HashMap<>();
                    jsonObject.getJSONObject(s).getJSONObject("record").keySet().forEach(k -> newBord.put(Integer.valueOf(k), jsonObject.getJSONObject(s).getJSONObject("record").getInteger(k)));
                    printBoard(newBord);
                    System.out.println("掷骰记录" + jsonObject.getJSONObject(s).get("track"));
                }
            } else if (op == 5) {
                break;
            } else {
                System.out.println("操作码错误");
            }
        }
    }

    public static void printMenu() {
        System.out.println("1.登录 2.重新开始 3.掷骰子 4.获取历史记录 5.退出游戏");
    }

    public static void printBoard(Map<Integer, Integer> board) {
        int n = (int) Math.pow(board.size(), 0.5);
        for (int i = 1; i <= board.size(); i++) {
            if (i == player.getFlag()) {
                System.out.print("(玩家)");
            } else {
                System.out.print("(" + i + "," + board.get(i) + ") ");
            }
            if (i % n == 0) {
                System.out.println();
            }
        }
        System.out.println("1.登录 2.重新开始 3.掷骰子 4.获取历史记录 5.退出游戏");
    }

    public static void login(int playerId, String ip) {
        String url = ip + "/play/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<>();
        params.put("playerId", String.valueOf(playerId));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = JSON.parseObject(response.getBody());
        Map<Integer, Integer> newBord = new HashMap<>();
        json.getJSONObject("board").keySet().forEach(k -> newBord.put(Integer.valueOf(k), json.getJSONObject("board").getInteger(k)));
        player.setBoard(newBord);
        player.setId(playerId);
        player.setFlag(json.getObject("flag", int.class));
    }

    public static void restart(String ip, int size) {
        String url = ip + "/play/start";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<>();
        params.put("playerId", String.valueOf(player.getId()));
        params.put("size", String.valueOf(size));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = JSON.parseObject(response.getBody());
        Map<Integer, Integer> newBord = new HashMap<>();
        json.getJSONObject("board").keySet().forEach(k -> newBord.put(Integer.valueOf(k), json.getJSONObject("board").getInteger(k)));
        player.setBoard(newBord);
        player.setFlag(json.getObject("flag", int.class));
    }

    public static int move(String ip) {
        int playerId = player.getId();
        if (playerId == 0) {
            //todo 报错 need login
        }
        String url = ip + "/play/move";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<>();
        params.put("playerId", String.valueOf(playerId));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = JSON.parseObject(response.getBody());
        Map<Integer, Integer> newBord = new HashMap<>();
        json.getJSONObject("board").keySet().forEach(k -> newBord.put(Integer.valueOf(k), json.getJSONObject("board").getInteger(k)));
        player.setBoard(newBord);
        player.setFlag(json.getObject("flag", int.class));
        return json.getObject("step", int.class);
    }

    public static JSONObject getRecord(String ip) {
        int playerId = player.getId();
        if (playerId == 0) {
            //todo 报错 need login
        }
        String url = ip + "/play/getRecords";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new HashMap<>();
        params.put("playerId", String.valueOf(playerId));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = JSON.parseObject(response.getBody());
        return json;
    }
}