package Actors;

import BattleCity_LibGDX.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Bonus extends Actor {
	
	private static final int HEIGHT_ACTOR = BattleCityScreen.SCREEN_HEIGHT/Map.ROW;
	private static final int WIDTH_ACTOR = BattleCityScreen.SCREEN_WIGHT/Map.COL;
	
	private static Texture[] textures = new Texture[3];
	private Texture _texture;
	
	
	static {
			getTextures()[0] = new Texture(Gdx.files.internal("resourse/graphics/bonus_HP.png"));
			getTextures()[1] = new Texture(Gdx.files.internal("resourse/graphics/bonus_speed.png"));
			getTextures()[2] = new Texture(Gdx.files.internal("resourse/graphics/bonus_bullet_speed.png"));
	}
		public Bonus(int bonusIndex){
			set_texture(getTextures()[bonusIndex]);
		}
		
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(get_texture(), this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_ACTOR, HEIGHT_ACTOR, this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
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
		Bonus.textures = textures;
	}
	
}
