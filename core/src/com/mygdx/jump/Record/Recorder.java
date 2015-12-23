package com.mygdx.jump.Record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Ming Yao
 */
public class Recorder {
    private static final String recordfile = ".record";

    public static int coinSum;

    public static GameRecord currentRecord;

    public static ArrayList<GameRecord> records = new ArrayList<>();

    public static void load(){
        try {
            File file = new File(recordfile);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                }
                read.close();
                /*
                Load records here
                */
            }
            else{
                coinSum = 0;
            }
        }
        catch(Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public static void save(){

    }

    public static void addRecord(){
        records.add(new GameRecord());
        currentRecord = records.get(records.size()-1);
    }

}
