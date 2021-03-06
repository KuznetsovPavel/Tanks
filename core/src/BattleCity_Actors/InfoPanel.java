package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InfoPanel extends Actor {
	
	private static final int HEIGHT_INFO_PANEL = BattleCityScreen.getScreenHeight()/Map.getRow() * 3;
	private static final int WIDTH_INFO_PANEL = BattleCityScreen.getScreenWight()/Map.getCol() * 5;
	private Texture _texture;
	
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
