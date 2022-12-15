# SnakeChess 蛇梯游戏
### 需求分析
蛇梯游戏，将需求简化后可以理解为，棋盘上有很多点，玩家按顺序往前走，有些点会有特殊的机制，玩家落到这个点会飞跃到指定的地点，可以是向前（爬梯），也可是向后（蛇吻），因此每一个点包含的信息除了它自身的位置，还有其所对应的飞跃点编号，因此可以做一个约定，当飞跃点编号为-1时代表不飞跃，不为-1时，玩家落到此处会飞跃到指定编号点。

### 数据结构
棋盘落点
```
Point{
    int number; //落点编号，从1开始
    int destination; //导向地，棋子会飞到此编号落点处，-1为不进行飞跃
}
```
棋盘信息
```
Board{
    Map<Integer, Point> points; //存储棋盘的所有落点信息
    int flag; //玩家在此棋盘中目前处于的落点编号
}
```
游戏记录
```
Record{
    Map<Integer, Point> points; //一次游戏记录棋盘落点信息
    List<Integer> track; //此次记录的轨迹记录
}
```
玩家数据
```
playerData{
    int id; //玩家id
    Board board; //玩家的棋盘信息
    List<Integer> track; //玩家当前游戏轮次游戏记录
    Map<Integer, Record> record;; //玩家历史已完成游戏轨迹记录
}
```

### 接口实现
使用简单的http协议，进行交互，服务器在ChessController类提供四个接口（login、start、move，getRecords）
* login：用来进行简单的玩家注册与登录，客户端通过post传输一个playerId给服务器，服务器进行校验，如果是已有的玩家，就将此前的玩家数据返回，如果是新玩家，则进行初始化后进行返回
* start：用来进行棋盘的刷新，客户端通过post传输playerId和size给服务器，服务器对次玩家的棋盘进行刷新，生成新的大小为size*size的棋盘
* move：表示掷骰子的操作，客户端通过post传输playerId给服务器，**服务器进行骰子点数的随机**（这样避免了客户端的数据作弊），并进行人物移动，之后将此次掷骰的点数，和人物最新的位置返回给客户端
* getRecords：用来获取玩家游戏的记录以播放，客户端通过post传输playerId给服务器，服务器获取数据后返回

### 其他实现
在Board类reStart方法中实现了棋盘的生成，并设置爬梯生成概率和蛇吻生成概率都为0.1,方法在接受一个size后，会根据概率生成一个size*size大小的蛇梯棋盘


