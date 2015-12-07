package com.mygdx.jump.Record;

/**
 * Created by Yao on 15/12/7.
 */
public class Player {
    //fields
    public String name;
    public int coins;
    /**default Constructor*/
    public Player(){
        this("NewPlayer",0);
    }

    /**Constructor*/
    public Player(String nm, int cns){
        this.name = nm;
        this.coins = cns;
    }
}
