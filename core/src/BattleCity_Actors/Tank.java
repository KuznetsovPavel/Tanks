package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tank extends Actor {
	
	private static final int HEIGHT_TANK = BattleCityScreen.getScreenHeight()/Map.getRow();
	private static final int WIDTH_TANK = BattleCityScreen.getScreenWight()/Map.getCol();
	private static Texture[] textures = new Texture[3];
	private Texture _texture;
	private static Texture[] explosion = new Texture[34];
	private int numbTextExpl = 0;
	
	static{
		
		for (int i = 0; i < getExplosion().length; i++) {
			getExplosion()[i] = new Texture(Gdx.files.internal("resourse/graphics/explosion/expl" + i + ".png"));
		}
		
		getTextures()[0] = new Texture(Gdx.files.internal("resourse/graphics/tank2.png"));
		getTextures()[1] = new Texture(Gdx.files.internal("resourse/graphics/tank.png"));
		getTextures()[2] = new Texture(Gdx.files.internal("resourse/graphics/deadTank.png"));
		
	}

	public Tank (int textureIndex) {
		set_texture(getTextures()[textureIndex]);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(get_texture(), this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_TANK, HEIGHT_TANK, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, get_texture().getWidth(), get_texture().getHeight(), false, false);
	}
	
	public int getNumbTextExpl() {
		return numbTextExpl;
	}

	public void setNumbTextExpl(int numbTextExpl) {
		this.numbTextExpl = numbTextExpl;
	}

	public Texture get_texture() {
		return _texture;
	}

	public void set_texture(Texture _texture) {
		this._texture = _texture;
	}

	public static Texture[] getExplosion() {
		return explosion;
	}

	public static void setExplosion(Texture[] explosion) {
		Tank.explosion = explosion;
	}

	public static Texture[] getTextures() {
		return textures;
	}

	public static void setTextures(Texture[] textures) {
		Tank.textures = textures;
	}
	
}
