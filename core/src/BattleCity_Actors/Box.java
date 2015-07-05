package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {
	
	private static final int HEIGHT_ACTOR = BattleCityScreen.getScreenHeight()/Map.getRow();
	private static final int WIDTH_ACTOR = BattleCityScreen.getScreenWight()/Map.getCol();

	private static Texture[] textures = new Texture[9];
	private Texture _texture;
	
	
	static {
		for (int i = 0; i < textures.length ; i++) {
			textures[i] = new Texture(Gdx.files.internal("resourse/graphics/block_" + i + ".png"));
		}
		
	}

	public Box(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_ACTOR, HEIGHT_ACTOR, this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				_texture.getWidth(), _texture.getHeight(), false, false);
	}

	public void setTexture(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
}
