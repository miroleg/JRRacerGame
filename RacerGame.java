package com.javarush.games.racer;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.road.RoadManager;
import com.javarush.games.racer.road.RoadObject;
import java.util.List;

public class RacerGame extends Game {
    public static final int WIDTH  = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private RoadMarking roadMarking;
    private PlayerCar player;
    private int turnDelay;
    private RoadManager roadManager;
    private boolean isGameStopped;
    private FinishLine finishLine;
    
    
    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        turnDelay = 40;
        setTurnTimer(turnDelay);
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        drawScene();
        isGameStopped = false;
    }

    private void drawScene() {
         drawField();
         finishLine.draw(this);
         roadManager.draw(this);
         roadMarking.draw(this);
         player.draw(this);
         
    }
    
    private void drawField() {
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if (x == CENTER_X)
                    setCellColor(CENTER_X, y, Color.WHITE);
                else
                if (x >= ROADSIDE_WIDTH && x <  (WIDTH - ROADSIDE_WIDTH))
                    setCellColor(x, y, Color.DIMGREY);
                else
                    setCellColor(x, y, Color.GREEN);
            }
        }
    }
    
  
    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT)
            super.setCellColor(x, y, color);
    }
        
    private void moveAll() {
        roadMarking.move(player.speed);
        player.move();
        roadManager.move(player.speed);
        finishLine.move(player.speed);
    }
    
    
    public void onTurn(int onDelay ) { 
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        moveAll();
        roadManager.generateNewRoadObjects(this);
        drawScene();
    }
    
    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.NONE,"ПРИЕХАЛИ !", Color.RED, 70);
        stopTurnTimer();
        player.stop();
    }
    
    
    
    
    @Override
    public  void onKeyPress(Key key) {
        if (key.equals(Key.RIGHT)) player.setDirection(Direction.RIGHT);
        if (key.equals(Key.LEFT)) player.setDirection(Direction.LEFT);
        
        if (key.equals(Key.SPACE) && isGameStopped) createGame();
        if (key.equals(Key.UP)) player.speed = 2;
    }
    
    @Override
    public  void onKeyReleased(Key key) {
        if (key.equals(Key.RIGHT) && player.getDirection().equals(Direction.RIGHT))
            player.setDirection(Direction.NONE);
        if (key.equals(Key.LEFT) && player.getDirection().equals(Direction.LEFT)) 
            player.setDirection(Direction.NONE);    
        if (key.equals(Key.UP)) player.speed = 1;
        
    }    
    
    
    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
       // super.initialize();
    }
}