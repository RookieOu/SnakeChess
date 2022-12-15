package com.yanou.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


/**
 * @author yanou
 * @date ${DATE} ${TIME}
 */
public class Main {
    static RestTemplate restTemplate = new RestTemplate();

    static Player player = new Player();

    private static JFrame createGame(Map<Integer, Integer> board) {
        int n = (int) Math.pow(board.size(), 0.5);
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("蛇棋");
        frame.setTitle("🐍");
        frame.setSize(450, 450);
        frame.setVisible(true);
        //绘制网格
        if (n == 0) {
            return frame;
        }
        int gridSize = 400 / n;
        // 行
        for (int i = 0; i < n; i++) {
            // 每行的每一格
            for (int j = 0; j < n; j++) {
                JLabel jLabel = new JLabel();
                jLabel.setSize(gridSize, gridSize);
                //设置每个Label的位置
                jLabel.setLocation(i * gridSize, j * gridSize);
                //设置颜色
                if ((i + j) % 2 == 0) {
                    jLabel.setBackground(Color.black);
                } else {
                    jLabel.setBackground(Color.white);
                }
                jLabel.setOpaque(true);
                //设置每个Label边框的颜色
                jLabel.setBorder(BorderFactory.createLineBorder(Color.yellow));
                frame.add(jLabel);
            }
        }
        return frame;
    }

    private static void refresh(JFrame frame){
        frame.removeAll();
        Map<Integer,Integer> board = player.getBoard();
        int n = (int) Math.pow(board.size(), 0.5);
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        //绘制网格
        if (n == 0) {
            return;
        }
        int gridSize = 400 / n;
        // 行
        for (int i = 0; i < n; i++) {
            // 每行的每一格
            for (int j = 0; j < n; j++) {
                JLabel jLabel = new JLabel();
                jLabel.setSize(gridSize, gridSize);
                //设置每个Label的位置
                jLabel.setLocation(i * gridSize, j * gridSize);
                //设置颜色
                if ((i + j) % 2 == 0) {
                    jLabel.setBackground(Color.black);
                } else {
                    jLabel.setBackground(Color.white);
                }
                jLabel.setOpaque(true);
                //设置每个Label边框的颜色
                jLabel.setBorder(BorderFactory.createLineBorder(Color.yellow));
                frame.add(jLabel);
            }
        }
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        int playerId = 0;
        Map<Integer, Integer> board = new HashMap<>();
        JFrame frame = createGame(new HashMap<>());
        // 设置窗口的大小
        frame.setSize(800, 600);
        // 创建一个按钮
        JButton button = new JButton("login!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(1, "http://127.0.0.1:13000", frame);
            }
        });
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        // 将按钮添加到窗口中
        frame.add(button);
        // 显示窗口
        frame.setVisible(true);

    }

    public static void login(int playerId, String ip, JFrame frame) {
        String url = ip + "/play/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = new HashMap<>();
        params.put("playerId", String.valueOf(playerId));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        JSONObject json = JSON.parseObject(response.getBody());
        Map<Integer, Integer> mapType = (HashMap<Integer, Integer>) JSON.parseObject(json.getJSONObject("board").toJSONString(), Map.class);
        player.setBoard(mapType);
        player.setId(playerId);
        player.setFlag(json.getObject("flag", int.class));
        refresh(frame);
    }

    public static void move(String ip) {
        int playerId = player.getId();
        if(playerId == 0){
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
        Map<Integer, Integer> mapType = (HashMap<Integer, Integer>) JSON.parseObject(json.getJSONObject("board").toJSONString(), Map.class);
        player.setBoard(mapType);
        player.setFlag(json.getObject("flag", int.class));
    }
}