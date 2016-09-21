package BattleCity_Actors;

import BattleCity_Scene_Screen.BattleCityScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by kuznetsov_pavel on 21.09.16.
 */
public class Picture extends Actor {

    private Texture _texture = new Texture(Gdx.files.internal("resourse/graphics/Lvl.png"));

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(_texture, BattleCityScreen.getScreenWight()/ 2 - _texture.getWidth() / 2,
                BattleCityScreen.getScreenHeight()/2 - _texture.getHeight() / 2,
                this.getOriginX(), this.getOriginY(),
                _texture.getWidth(), _texture.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
    }

}
