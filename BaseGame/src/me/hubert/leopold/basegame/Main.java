package me.hubert.leopold.basegame;

import javafx.application.Application;
import javafx.stage.Stage;
import me.hubert.leopold.basegame.game.TempGame;

public class Main extends Application{

	public static boolean runtime = true;
	
	public static void main(String[] args) {
		System.out.println("init");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		TempGame.start(primaryStage);
		
	}

}
