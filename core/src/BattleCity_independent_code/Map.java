package BattleCity_independent_code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class Map {
	
	public static final int COL = 25;
	public static final int ROW = 15;
	int[][] _data;
	private static Random random = new Random();
	
	
	public static File[] MAPS = {new File("resourse/map/map0.txt"), 
		new File("resourse/map/map1.txt"), 
		new File("resourse/map/map2.txt"), 
		new File("resourse/map/map3.txt"), 
		new File("resourse/map/map4.txt")};

	public Map() {
		_data = new int [ROW][COL];
	}

	public static int[][] readMap(int mapNumber){
		int[][] data = new int[ROW][COL];
		try {
			Scanner scanner = new Scanner(MAPS[mapNumber]);
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {					
						data[i][j] = scanner.nextInt();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
		
	}
	
	public int getWidth() {
		return _data[0].length;
	}

	public int getHeight() {
		return _data.length;
	}

	public int[][] getData() {
		return _data;
	}

	public static Map randomMap() {
		Map map = new Map();
		map._data = readMap(random.nextInt(MAPS.length - 1));
		return map;
	} 
	
}
