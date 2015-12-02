package com.mygdx.jump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.jump.MenuScreen.MainMenuScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.jump.Settings;

public class TsinghuaJump extends Game {
	MainMenuScreen menuScreen;
	@Override
	public void create()
	{
		Settings.setScreen(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
	}

}
