package me.hubert.leopold.basegame.game;

import javafx.scene.input.KeyEvent;

public class InputManager {
	
	private static boolean pressedSkip = false;
	
	static double zoomval = 0.0;
	
	public static void inputKey(KeyEvent event) {
		
		System.out.println(event.getCode().toString());
		
		for (int i = 0; i < TempGame.players.size(); i++) {
			
			Player pl = TempGame.players.get(i);
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveRight)) {
				pl.rightPressed = true;
			}
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveLeft)) {
				pl.leftPressed = true;
			}
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveUp)) {
				if(pl.canJump) {
					pl.inertieY -= pl.jump;
				}
				pl.canJump = false;
			}

			if(event.getCode().toString().equalsIgnoreCase(pl.moveDown)) {
				if(!pl.downPressed) {
					TempGame.plan = TempGame.plan == 1?0:1;
				}
				pl.downPressed = true;
			}
			
			if(event.getCode().toString().equalsIgnoreCase(pl.interract)) {
				if(Runtime.isOnDoor) {
					LevelManager.setLevel(LevelManager.actualLevel+1);
				}
			}
			
			if(event.getCode().toString().equalsIgnoreCase("NUMPAD9")) {
				if(!pressedSkip) {
					pressedSkip = true;
					LevelManager.setLevel(LevelManager.actualLevel+1);
				}
			}
			
			if(event.getCode().toString().equalsIgnoreCase("ADD")) {
				
			}
			
		}
		
	}
	
	public static void released(KeyEvent event) {
		for (int i = 0; i < TempGame.players.size(); i++) {
			
			Player pl = TempGame.players.get(i);
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveRight)) {
				pl.rightPressed = false;
			}
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveLeft)) {
				pl.leftPressed = false;
			}
			
			if(event.getCode().toString().equalsIgnoreCase(pl.moveUp)) {

			}

			if(event.getCode().toString().equalsIgnoreCase(pl.moveDown)) {
				pl.downPressed = false;
			}
			
			if(event.getCode().toString().equalsIgnoreCase("NUMPAD9")) {
				pressedSkip = false;
			}
			
		}
	}
	
}
