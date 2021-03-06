package BattleCity_Scene_Screen;

import BattleCity_Actors.Button;
import BattleCity_Actors.Headband;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen, InputProcessor{

	private static final int VOLUME_MAX = 100;
	private static final int VOLUME_OFF = 0;
	
	private static final int MENU_SCREEN_HEADBAND = 0;
	private static final int SOUND_IS_ON = 4;
	private static final int SOUND_IS_OFF = 5;
	private static final int BUTTON_PLAY_IS_NOT_PUT = 3;
	private static final int BUTTON_PLAY_IS_PUT = 2;
	
	private static final int HEIGHT_BUT_PLAY = Button.getHeightButPlay();
	private static final int LENGHT_BUT_PLAY = Button.getWidthButPlay();
	private static final double COORD_BUT_PLAY_X = BattleCityScreen.getScreenWight()*0.1;
	private static final double COORD_BUT_PLAY_Y = BattleCityScreen.getScreenHeight()*0.8;
	private static final double COORD_BUT_PLAYFORLIVE_X = BattleCityScreen.getScreenWight() - BattleCityScreen.getScreenWight()*0.1 - Button.getWidthButPlay();
	private static final double COORD_BUT_PLAYFORLIVE_Y = BattleCityScreen.getScreenHeight()*0.8;
	private static final double COORD_BUT_VOLUME_X = BattleCityScreen.getScreenWight()*0.01;
	private static final double COORD_BUT_VOLUME_Y = BattleCityScreen.getScreenHeight()*0.85;
	private static final double COORD_BUT_INFO_X = BattleCityScreen.getScreenWight() - (BattleCityScreen.getScreenHeight() * 0.1);
	private static final double COORD_BUT_INFO_Y = BattleCityScreen.getScreenHeight()*0.01;

	private static final int LENGHT_BUT_VOLUME = Button.getWidthButVolume();
	private static final int HEIGHT_BUT_VOLUME = Button.getHeightButVolume();

	private OrthographicCamera camera;
	 
	private boolean isPlay = false;
	private boolean isPlayForLive =  false;
	private BattleCityGame game = new BattleCityGame();
	private Headband headband = new Headband(MENU_SCREEN_HEADBAND);
	private Button play_game = new Button(BUTTON_PLAY_IS_NOT_PUT, (int) COORD_BUT_PLAY_X, (int) (BattleCityScreen.getScreenHeight()*0.1));
	private Button play_gameforlive = new Button(6, (int) COORD_BUT_PLAYFORLIVE_X, (int) (BattleCityScreen.getScreenHeight()*0.1));
	private Button info = new Button(7, (int)(COORD_BUT_INFO_X), (int)(BattleCityScreen.getScreenHeight() * 0.9));
	private Button _volume = new Button(SOUND_IS_ON, (int) (COORD_BUT_VOLUME_X), (int) (COORD_BUT_INFO_Y));
	private Button text = new Button(8, (int)(BattleCityScreen.getScreenWight() / 2 - 256), 20);
	private boolean isDrowText = false;

	 public MenuScreen(BattleCityGame newGame) {
		 	game = newGame;
	        camera = new OrthographicCamera();
	        camera.setToOrtho(false, BattleCityScreen.getScreenHeight(), BattleCityScreen.getScreenWight());
	    }
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        camera.update();

	        game.batch.begin();
	        headband.draw(game.batch, 0);
	        if (isPlay) {
				play_game.set_texture(Button.getTextures()[BUTTON_PLAY_IS_PUT]);
				play_gameforlive.set_texture(Button.getTextures()[6]);
			}else if(game.music_mute){
				_volume.set_texture(Button.getTextures()[SOUND_IS_OFF]);
			}else if (!game.music_mute) {
				_volume.set_texture(Button.getTextures()[SOUND_IS_ON]);
			}
	        play_game.draw(game.batch, 0);
			play_gameforlive.draw(game.batch, 0);
			info.draw(game.batch, 0);
	        _volume.draw(game.batch, 0);
			if (isDrowText)
				text.draw(game.batch, 0);
	        game.batch.end();
	        Gdx.input.setInputProcessor(this);

	     
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointInArea(screenX, screenY, COORD_BUT_PLAY_X, COORD_BUT_PLAY_Y, LENGHT_BUT_PLAY, HEIGHT_BUT_PLAY)) {
			isPlay = true;
			return true;
		} else if(pointInArea(screenX, screenY, COORD_BUT_PLAYFORLIVE_X, COORD_BUT_PLAYFORLIVE_Y, LENGHT_BUT_PLAY, HEIGHT_BUT_PLAY)){
			isPlayForLive = true;
			return true;
		} else if(pointInArea(screenX, screenY, COORD_BUT_INFO_X, COORD_BUT_INFO_Y, Button.getInfoSize(), Button.getInfoSize())){
			isDrowText = !isDrowText;
			return true;
		}else if (pointInArea(screenX, screenY,COORD_BUT_VOLUME_X, COORD_BUT_VOLUME_Y,
				LENGHT_BUT_VOLUME, HEIGHT_BUT_VOLUME )) {
			if (!game.music_mute) {
				game.music_mute = true;
			}else{
				game.music_mute = false;
			}
			return true;				
		}
		return false;
	}

	private boolean pointInArea(int screenX, int screenY, double coordButPlayX, double coordButPlayY,
			int buttLenght, int buttHeight) {
		return screenX > coordButPlayX && screenX < coordButPlayX + buttLenght 
			&& screenY > coordButPlayY && screenY < coordButPlayY + buttHeight;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (isPlay) {
			game.setScreen(new BattleCityScreen(game, true));
			return true;
		} else if (isPlayForLive) {
			game.setScreen(new BattleCityScreen(game, false));
			return true;
		}else if (game.music_mute) {
			game.main.setVolume(VOLUME_OFF);
			game.getAircraft().setVolume(VOLUME_OFF);
			game.music_gameOver.setVolume(VOLUME_OFF);
		}else if(!game.music_mute){
			game.main.setVolume(VOLUME_MAX);
			game.getAircraft().setVolume(VOLUME_MAX);
			game.music_gameOver.setVolume(VOLUME_MAX);
		}
		return false;
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
