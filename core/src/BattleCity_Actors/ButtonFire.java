package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonFire extends Actor {
	
	private static final int HEIGHT_BUTTON = BattleCityScreen.getScreenHeight()/Map.getRow()*2;
	private static final int WIDTH_BUTTON = BattleCityScreen.getScreenWight()/Map.getCol()*2;
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
				getWidthButton(), getHeightButton(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
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

	public static int getHeightButton() {
		return HEIGHT_BUTTON;
	}

	public static int getWidthButton() {
		return WIDTH_BUTTON;
	}

}
