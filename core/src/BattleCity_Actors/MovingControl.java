package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MovingControl extends Actor {

	public static final int HEIGHT_CONTROL = BattleCityScreen.SCREEN_HEIGHT/Map.ROW*5;
	public static final int WIDTH_CONTROL = BattleCityScreen.SCREEN_WIGHT/Map.COL*5;
	private static Texture[] textures = new Texture[5];
	private Texture _texture;
	
	
	static {
			getTextures()[0] = new Texture(Gdx.files.internal("resourse/graphics/joy_basic.png"));
			getTextures()[1] = new Texture(Gdx.files.internal("resourse/graphics/joy_up.png"));
			getTextures()[2] = new Texture(Gdx.files.internal("resourse/graphics/joy_down.png"));
			getTextures()[3] = new Texture(Gdx.files.internal("resourse/graphics/joy_left.png"));
			getTextures()[4] = new Texture(Gdx.files.internal("resourse/graphics/joy_right.png"));
		
	}

	public MovingControl(int textureIndex) {
		set_texture(getTextures()[textureIndex]);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(get_texture(), this.getX(), getY(), this.getOriginX(), this.getOriginY(), WIDTH_CONTROL, 
				HEIGHT_CONTROL,  this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
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
		MovingControl.textures = textures;
	}
	
}
