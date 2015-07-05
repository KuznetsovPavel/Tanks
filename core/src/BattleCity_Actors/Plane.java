package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import BattleCity_independent_code.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Plane extends Actor {
	
	private static final int HEIGHT_ACTOR = BattleCityScreen.SCREEN_HEIGHT*4/Map.ROW;
	private static final int WIDTH_ACTOR = BattleCityScreen.SCREEN_WIGHT*4/Map.COL;
	
	private Texture _texture = new Texture("resourse/graphics/plane.png");
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(get_texture(), this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_ACTOR, HEIGHT_ACTOR, this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				get_texture().getWidth(), get_texture().getHeight(), false, false);
	}
	
	public void action(int coordX , int coordY, int ang){
		
		this.addAction(
				Actions.sequence(
					Actions.moveTo(coordX, coordY, 1.5f),
					Actions.parallel(Actions.rotateTo(180, 1f), Actions.moveTo(coordX + WIDTH_ACTOR, coordY + HEIGHT_ACTOR, 0.5f)), 
					Actions.moveTo(-WIDTH_ACTOR, -HEIGHT_ACTOR, 2f), 
					Actions.removeActor(this))
				);
	}
	
	
	public Texture get_texture() {
		return _texture;
	}

}
