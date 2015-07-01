package Actors;

import BattleCity_LibGDX.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InfoPanel extends Actor {
	
	private static final int HEIGHT_INFO_PANEL = BattleCityScreen.SCREEN_HEIGHT/Map.ROW * 3;
	private static final int WIDTH_INFO_PANEL = BattleCityScreen.SCREEN_WIGHT/Map.COL * 5;
	Texture _texture;
	
	public InfoPanel() {
		_texture = new Texture(Gdx.files.internal("resourse/graphics/info_table.png"));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_INFO_PANEL, HEIGHT_INFO_PANEL, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
	}

	
}
