package BattleCity_Actors;



import BattleCity_Scene_Screen.BattleCityScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button  extends Actor {

	private static final int WIDTH_BUT_PLAY = BattleCityScreen.getScreenWight()/4;
	private static final int HEIGHT_BUT_PLAY = BattleCityScreen.getScreenHeight()/10;
	private static final int HEIGHT_BUT_VOLUME = BattleCityScreen.getScreenHeight()/8;
	private static final int WIDTH_BUT_VOLUME = BattleCityScreen.getScreenWight()/10;

	private static final int BUTTON_VOLUME_OFF = 5;
	private static final int BUTTON_VOLUME_ON = 4;

	private static Texture[] textures = new Texture[6];
	private Texture _texture;
	int _coordX;
	int _coordY;
	
	static{
		getTextures()[0] = new Texture(Gdx.files.internal("resourse/graphics/play_button.png"));
		getTextures()[1] = new Texture(Gdx.files.internal("resourse/graphics/play_button_down.png"));
		getTextures()[2] = new Texture(Gdx.files.internal("resourse/graphics/play_again.png"));
		getTextures()[3] = new Texture(Gdx.files.internal("resourse/graphics/play_again_down.png"));
		getTextures()[4] = new Texture(Gdx.files.internal("resourse/graphics/volume_on.png"));
		getTextures()[5] = new Texture(Gdx.files.internal("resourse/graphics/volume_off.png"));
	}

	public Button (int textureIndex, int coordX, int coordY) {
		set_texture(getTextures()[textureIndex]);
		_coordX = coordX;
		_coordY = coordY;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (get_texture().equals(getTextures()[BUTTON_VOLUME_ON]) || get_texture().equals(getTextures()[BUTTON_VOLUME_OFF])) {
			batch.draw(get_texture(), _coordX, _coordY, this.getOriginX(), this.getOriginY(),
					getWidthButVolume(), getHeightButVolume(), this.getScaleX(), this.getScaleY(), 
					this.getRotation(), 0, 0, get_texture().getWidth(), get_texture().getHeight(), false, false);
		}else {
		batch.draw(get_texture(), _coordX, _coordY, this.getOriginX(), this.getOriginY(),
				getWidthButPlay(), getHeightButPlay(), this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, get_texture().getWidth(), get_texture().getHeight(), false, false);
		}
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
		Button.textures = textures;
	}

	public static int getWidthButVolume() {
		return WIDTH_BUT_VOLUME;
	}

	public static int getHeightButVolume() {
		return HEIGHT_BUT_VOLUME;
	}

	public static int getHeightButPlay() {
		return HEIGHT_BUT_PLAY;
	}

	public static int getWidthButPlay() {
		return WIDTH_BUT_PLAY;
	}
	
}
