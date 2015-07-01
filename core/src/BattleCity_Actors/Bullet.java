package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends Actor {

	Texture _texture = new Texture(Gdx.files.internal("resourse/graphics/bullet.png"));
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		int width_bullet = BattleCityScreen.SCREEN_WIGHT/Map.COL/(_texture.getWidth() - 4);
		int height_bullet = BattleCityScreen.SCREEN_HEIGHT/Map.ROW/(_texture.getHeight() - 4);
		
		batch.draw(_texture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
				width_bullet, height_bullet, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
	}
	
}
