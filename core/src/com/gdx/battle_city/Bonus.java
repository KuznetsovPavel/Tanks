package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Bonus extends Actor {
	
	static Texture[] textures = new Texture[3];
	Texture _texture;
	
	
	static {
			textures[0] = new Texture(Gdx.files.internal("resourse/graphics/bonus_HP.png"));
			textures[1] = new Texture(Gdx.files.internal("resourse/graphics/bonus_speed.png"));
			textures[2] = new Texture(Gdx.files.internal("resourse/graphics/bonus_bullet_speed.png"));
	}
		public Bonus(int bonusIndex){
			_texture = textures[bonusIndex];
		}
		
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT/Map.COL, BattleCityScreen.SCREEN_HEIGHT/Map.ROW, 
				this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				_texture.getWidth(), _texture.getHeight(), false, false);
	}
	
}
