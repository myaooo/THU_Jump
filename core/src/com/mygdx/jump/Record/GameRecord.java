package com.mygdx.jump.Record;

/**
 * Created by Yao on 15/12/7.
 */
public class GameRecord {
    //fields
    public Player player;
    public int score;
    public float height;
    public int coin;

    /**Constructor*/
    public GameRecord(Player pl, int sc, float ht, int cn){
        this.player = pl;
        this.score = sc;
        this.height = ht;
        this.coin = cn;
    }

    /**default constructor*/
    public GameRecord(){
        this(new Player(),0,0,0);
    }
}
