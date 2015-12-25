package com.mygdx.jump.Resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ordly on 15/12/21.
 */
public class Resource {
    //font

    public static Font font = new Font("data/3.ttf",18, Color.BLACK);

    public static Font fontX = new Font("data/3.ttf",22, Color.BLACK);

    public static Font Bfont = new Font("data/4.otf",18,Color.BLACK);

   // public static float[]

    //image
    //doctor


    public static float lineMove(Actor textButton, float time, float positionX, float positionY, int range)
    {
        float Dtime = (float)Math.floor(time);
        float position;
        if(Dtime%2==0)
        {
            textButton.setPosition(positionX+2*range*(time-Dtime-0.5f),positionY);
            position = positionX+2*range*(time-Dtime-0.5f);
        }
        else
        {
            textButton.setPosition(positionX-2*range*(time-Dtime-0.5f),positionY);
            position = positionX-2*range*(time-Dtime-0.5f);
        }

        return position;

    }


    public static float[] SolveEquation(float xStart, float yStart, float xEnd, float yEnd, float xSys)
    {
        float[] R = new float[3];

        R[0] = -(yStart-yEnd)/((xStart-xEnd)*(xStart+xEnd-2*xSys));
        R[1] = -(2*xSys*(yStart-yEnd))/((xStart-xEnd)*(xStart+xEnd-2*xSys));
        R[2] = -(xEnd*xEnd*yStart - 2*xEnd*xSys*yStart - xStart*xStart*yEnd + 2*xStart*xSys*yEnd)/((xStart-xEnd)*(xStart+xEnd-2*xSys));

        return R;
    }

    public static boolean DoctorJump(float[] R, Stage stage,Actor actorJump, Actor actorfall, float time, float startTime, float speed,float xStart, float yStart, float xEnd, float yEnd, float xSys)
    {
        boolean flag = false;

        float x = xStart+(xEnd - xStart)*speed*(time - startTime);
        if( x< xSys && x> xStart)
        {
            actorfall.remove();
            stage.addActor(actorJump);
            actorJump.setPosition(x, -R[0]*x*x + R[1]*x+R[2]);
        }
        else if (x<xEnd)
        {
            actorJump.remove();
            stage.addActor(actorfall);
            actorfall.setPosition(x, -R[0]*x*x + R[1]*x+R[2]);
        }
        else
        {
            actorfall.remove();
            stage.addActor(actorJump);
            lineMove(actorJump,time,xEnd,yEnd,20);
            flag = true;
        }
        return flag;
    }
}
