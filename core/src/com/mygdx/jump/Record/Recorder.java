package com.mygdx.jump.Record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.jump.MenuScreen.ScoreScreen;
import com.mygdx.jump.Settings;
import com.badlogic.gdx.utils.Array;
import java.util.Collections;
import java.util.Comparator;


/**
 * @author Ming Yao
 */
public class Recorder {
    private static final String recordfile = ".record";

    public static int coinSum;

    public static GameRecord currentRecord;

    public static Array<GameRecord> records = new Array<>();

    public static void load(){

        for(int i=0;i<5;i++)
        {
            records.add(new GameRecord(0,0));
        }



        records.get(0).score = Settings.preferences.getInteger("score0",0);
        records.get(1).score = Settings.preferences.getInteger("score1",0);
        records.get(2).score = Settings.preferences.getInteger("score2",0);
        records.get(3).score = Settings.preferences.getInteger("score3",0);
        records.get(4).score = Settings.preferences.getInteger("score4",0);

        records.get(0).coin = Settings.preferences.getInteger("coin0",0);
        records.get(1).coin = Settings.preferences.getInteger("coin1",0);
        records.get(2).coin = Settings.preferences.getInteger("coin2",0);
        records.get(3).coin = Settings.preferences.getInteger("coin3",0);
        records.get(4).coin = Settings.preferences.getInteger("coin4",0);

        ScoreSort();

    }

    public static void save(){


        ScoreSort();

        Settings.preferences.putInteger("score0",records.get(0).score);
        Settings.preferences.putInteger("score1",records.get(1).score);
        Settings.preferences.putInteger("score2",records.get(2).score);
        Settings.preferences.putInteger("score3",records.get(3).score);
        Settings.preferences.putInteger("score4",records.get(4).score);

        Settings.preferences.putInteger("coin0",records.get(0).coin);
        Settings.preferences.putInteger("coin1",records.get(1).coin);
        Settings.preferences.putInteger("coin2",records.get(2).coin);
        Settings.preferences.putInteger("coin3",records.get(3).coin);
        Settings.preferences.putInteger("coin4",records.get(4).coin);

        Settings.preferences.flush();

    }


    public static void ScoreSort(){
        for(int i=0; i<records.size-1; i++)
            for (int j = 0; j<records.size - 1 - i; j++)
            {
                if(records.get(j).score<records.get(j+1).score)
                {
                    records.swap(j,j+1);
                }
            }

    }

    public static void addRecord(){
        System.out.println(records.size);
        records.add(new GameRecord());
        currentRecord = records.get(records.size-1);
    }

}
