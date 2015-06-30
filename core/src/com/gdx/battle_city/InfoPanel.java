package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InfoPanel extends Actor {
	
	Texture _texture;
	
	public InfoPanel() {
		_texture = new Texture(Gdx.files.internal("resourse/graphics/info_table.png"));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT/Map.COL * 5, BattleCityScreen.SCREEN_HEIGHT/Map.ROW * 3, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
	}

	
}
