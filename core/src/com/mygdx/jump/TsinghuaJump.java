package com.mygdx.jump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.mygdx.jump.MenuScreen.StartScreen;
import com.mygdx.jump.Record.GameRecord;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

import java.util.ArrayList;

public class TsinghuaJump extends Game {

	StartScreen startScreen;
	//public ArrayList<GameRecord> records = new ArrayList<>();
	@Override
	public void create()
	{
		Settings.load();
		Assets.load();
		Settings.setScreen(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		startScreen = new StartScreen(this);
		setScreen(startScreen);
	}

	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

    @Override
    public void resize(int width, int height){
        Settings.setScreen(width,height);
    }

}
