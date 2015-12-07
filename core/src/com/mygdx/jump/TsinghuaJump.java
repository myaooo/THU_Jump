package com.mygdx.jump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.jump.MenuScreen.MainMenuScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

public class TsinghuaJump extends Game {
	MainMenuScreen menuScreen;
	@Override
	public void create()
	{
		Settings.load();
		Assets.load();
		Settings.setScreen(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

}
