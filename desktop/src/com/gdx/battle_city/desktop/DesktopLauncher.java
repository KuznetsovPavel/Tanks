package com.gdx.battle_city.desktop;

import BattleCity_Scene_Screen.BattleCityGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Battle City";
		config.width = 1000;
		config.height = 600;
		new LwjglApplication(new BattleCityGame(), config);
	
	}
} 
  