package com.mygdx.jump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.jump.MenuScreen.MainMenuScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.jump.MenuScreen.ShopScreen;
import com.mygdx.jump.MenuScreen.StartScreen;
import com.mygdx.jump.Resource.Assets;
import com.mygdx.jump.Settings;

public class TsinghuaJump extends Game {
	StartScreen startScreen;
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

}
