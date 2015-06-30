package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MovingControl extends Actor {

	static final int HEIGHT_CONTROL = BattleCityScreen.SCREEN_HEIGHT/Map.ROW*5;
	static final int WIDTH_CONTROL = BattleCityScreen.SCREEN_WIGHT/Map.COL*5;
	static Texture[] textures = new Texture[5];
	Texture _texture;
	
	
	static {
			textures[0] = new Texture(Gdx.files.internal("resourse/graphics/joy_basic.png"));
			textures[1] = new Texture(Gdx.files.internal("resourse/graphics/joy_up.png"));
			textures[2] = new Texture(Gdx.files.internal("resourse/graphics/joy_down.png"));
			textures[3] = new Texture(Gdx.files.internal("resourse/graphics/joy_left.png"));
			textures[4] = new Texture(Gdx.files.internal("resourse/graphics/joy_right.png"));
		
	}

	public MovingControl(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), WIDTH_CONTROL, 
				HEIGHT_CONTROL,  this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				_texture.getWidth(), _texture.getHeight(), false, false);
	}
	
}
