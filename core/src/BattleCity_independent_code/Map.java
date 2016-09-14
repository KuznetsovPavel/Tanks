package BattleCity_independent_code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class Map {
	
	private static final int COL = 25;
	private static final int ROW = 15;
	private int[][] _data;
	private static Random random = new Random();

	public static FileHandle[] MAPS = {Gdx.files.internal("resourse/map/map0.txt"),
		Gdx.files.internal("resourse/map/map1.txt"),
		Gdx.files.internal("resourse/map/map2.txt"),
		Gdx.files.internal("resourse/map/map3.txt"),
		Gdx.files.internal("resourse/map/map4.txt")};

	public Map() {
		set_data(new int [getRow()][getCol()]);
	}

	public static int[][] readMap(int mapNumber){
		int[][] data = new int[getRow()][getCol()];
		String str = MAPS[mapNumber].readString();
		System.out.println(str);
		String[] split = str.split("\n");


		for (int i = 0; i < data.length; i++) {
			String[] s = split[i].split(" ");
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = Integer.parseInt(s[j]);
			}
		}

		return data;
		
	}
	
	public int getWidth() {
		return get_data()[0].length;
	}

	public int getHeight() {
		return get_data().length;
	}

	public int[][] getData() {
		return get_data();
	}

	public static Map randomMap() {
		Map map = new Map();
		map.set_data(readMap(random.nextInt(MAPS.length - 1)));
		return map;
	}

	public static int getRow() {
		return ROW;
	}

	public static int getCol() {
		return COL;
	}

	public int[][] get_data() {
		return _data;
	}

	public void set_data(int[][] _data) {
		this._data = _data;
	} 
	
}
