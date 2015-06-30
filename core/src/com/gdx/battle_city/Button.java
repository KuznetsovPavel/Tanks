package com.gdx.battle_city;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button  extends Actor {

	static Texture[] textures = new Texture[6];
	Texture _texture;
	int _coordX;
	int _coordY;
	
	static{
		textures[0] = new Texture(Gdx.files.internal("resourse/graphics/play_button.png"));
		textures[1] = new Texture(Gdx.files.internal("resourse/graphics/play_button_down.png"));
		textures[2] = new Texture(Gdx.files.internal("resourse/graphics/play_again.png"));
		textures[3] = new Texture(Gdx.files.internal("resourse/graphics/play_again_down.png"));
		textures[4] = new Texture(Gdx.files.internal("resourse/graphics/volume_on.png"));
		textures[5] = new Texture(Gdx.files.internal("resourse/graphics/volume_off.png"));
	}

	public Button (int textureIndex, int coordX, int coordY) {
		_texture = textures[textureIndex];
		_coordX = coordX;
		_coordY = coordY;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (_texture.equals(textures[4]) || _texture.equals(textures[5])) {
			batch.draw(_texture, _coordX, _coordY, this.getOriginX(), this.getOriginY(),
					BattleCityScreen.SCREEN_WIGHT/10, BattleCityScreen.SCREEN_HEIGHT/10, this.getScaleX(), this.getScaleY(), 
					this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
		}else {
		batch.draw(_texture, _coordX, _coordY, this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT/4, BattleCityScreen.SCREEN_HEIGHT/10, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
		}
	}
	
}
