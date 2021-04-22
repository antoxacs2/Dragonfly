package com.cs2.game;

import com.badlogic.gdx.Game;
import com.cs2.game.loader.ResourseLoader;
import com.cs2.game.screens.SplashScreen;

public class FlyGame extends Game {

	
	@Override
	public void create () {
		ResourseLoader.load();
		setScreen(new SplashScreen(this));
	}
	
	@Override
	public void dispose () {
		super.dispose();
		ResourseLoader.dispose();
	}
}
