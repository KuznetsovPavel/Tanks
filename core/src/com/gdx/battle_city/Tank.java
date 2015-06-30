package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Tank extends Actor {
	
	static Texture[] textures = new Texture[3];
	Texture _texture;
	static Texture[] explosion = new Texture[34];
	int numbTextExpl = 0;
	
	static{
		
		for (int i = 0; i < explosion.length; i++) {
			explosion[i] = new Texture(Gdx.files.internal("resourse/graphics/explosion/expl" + i + ".png"));
		}
		
		textures[0] = new Texture(Gdx.files.internal("resourse/graphics/tank2.png"));
		textures[1] = new Texture(Gdx.files.internal("resourse/graphics/tank.png"));
		textures[2] = new Texture(Gdx.files.internal("resourse/graphics/deadTank.png"));
		
	}

	public Tank (int textureIndex) {
		_texture = textures[textureIndex];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT/Map.COL, BattleCityScreen.SCREEN_HEIGHT/Map.ROW, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
	}
	
}
