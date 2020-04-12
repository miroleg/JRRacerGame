package com.javarush.games.racer;

import com.javarush.games.racer.ShapeMatrix;
import com.javarush.games.racer.GameObject;

public class FinishLine extends GameObject{
    private  boolean isVisible = false; 
    public FinishLine() {
       super(RacerGame.ROADSIDE_WIDTH, -1 * ShapeMatrix.FINISH_LINE.length, ShapeMatrix.FINISH_LINE);
    }
    public void show() {
       isVisible = true;
    }
    
    public void move(int boost) {
       if (isVisible) {
           this.y += boost;
       }
    }
       
    
}