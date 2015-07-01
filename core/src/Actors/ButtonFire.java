package Actors;

import BattleCity_LibGDX.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonFire extends Actor {
	
	public static final int HEIGHT_BUTTON = BattleCityScreen.SCREEN_HEIGHT/Map.ROW*2;
	public static final int WIDTH_BUTTON = BattleCityScreen.SCREEN_WIGHT/Map.COL*2;
	private static Texture[] textures = new Texture[2];
	private Texture _texture;
	
	
	static {
			getTextures()[0] = new Texture(Gdx.files.internal("resourse/graphics/but_fire_up.png"));
			getTextures()[1] = new Texture(Gdx.files.internal("resourse/graphics/but_fire_down.png"));
	}

	public ButtonFire(int textureIndex) {
		set_texture(getTextures()[textureIndex]);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(get_texture(), this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_BUTTON, HEIGHT_BUTTON, this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				get_texture().getWidth(), get_texture().getHeight(), false, false);
	}

	public Texture get_texture() {
		return _texture;
	}

	public void set_texture(Texture _texture) {
		this._texture = _texture;
	}

	public static Texture[] getTextures() {
		return textures;
	}

	public static void setTextures(Texture[] textures) {
		ButtonFire.textures = textures;
	}

}
