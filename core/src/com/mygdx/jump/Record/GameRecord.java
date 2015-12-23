package com.mygdx.jump.Record;

/**
 * Created by Yao on 15/12/7.
 */
public class GameRecord {
    //fields
    public int score;
    //public float height;
    public int coin;

    /**Constructor*/
    public GameRecord(int sc, int cn){
        this.score = sc;
        //this.height = ht;
        this.coin = cn;
    }

    /**default constructor*/
    public GameRecord(){
        this(0,0);
    }

    public void setRecord(int sc, int cn){
        this.score = sc;
        this.coin = cn;
    }
}
