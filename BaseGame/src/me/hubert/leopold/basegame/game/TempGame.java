package me.hubert.leopold.basegame.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.hubert.leopold.basegame.RessourcesManager;

public class TempGame {
	
	public static int uniqId = 0;
	
	public static final String gameName = "THE NAME";
	
	public static ArrayList<Node> elements = new ArrayList<Node>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static int mapw = 30;
	public static int maph = 17;
	
	public static int plan = 1;
	public static int oldplan = 0;
	
	public static int tilewidth = 30;
	
	public static Group root;
	public static Scene scene;
	
	public static double scw;
	public static double sch;
	
	public static void start(Stage primaryStage) throws Exception {
		
		System.out.println("start");
		
		root = new Group();
		scene = new Scene(root, tilewidth*mapw, tilewidth*maph);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        System.exit(0);
		    }
		});
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	InputManager.inputKey(event);
            }
        });
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	InputManager.released(event);
            }
        });
		
		scene.setFill(Color.BLACK);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		primaryStage.setWidth(screenSize.getWidth());
		primaryStage.setHeight(screenSize.getHeight());
		primaryStage.setFullScreen(true);
		primaryStage.setTitle(gameName);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scw = screenSize.getWidth();
		sch = screenSize.getHeight();
		
		int tmpw1 = (int) (scw / mapw);
		int tmpw2 = (int) (sch / maph);
		tilewidth = tmpw1<tmpw2?tmpw1:tmpw2;
		
		addPlayer("UP", "RIGHT", "DOWN", "LEFT", "ENTER", 2, 2);
		
		//addPlayer("Z", "D", "S", "Q", "ENTER", 4, 4);
		
		LevelManager.setLevel(0);
		
		Runtime task = new Runtime();
		task.start();
		
		/*for (int i = 0; i < elements.size(); i++) {
			System.out.println("id : "+elements.get(i).getId());
			if(i >= 20 && i <= 40) {
				//root.getChildren().remove(elements.get(i));
				Rectangle rec = (Rectangle) elements.get(i);
				rec.setFill(Color.DEEPPINK);
			}
		}*/
	}
	
	public static void updateLight() {
		
		if(TempGame.oldplan == TempGame.plan) {
			return;
		}
		
		for (int j = 0; j < TempGame.elements.size(); j++) {
			
			ImageView object = null;
			
			if(TempGame.elements.get(j) instanceof ImageView) {
				object = (ImageView) TempGame.elements.get(j);
			}
			
			if(object != null) {
				
				int mapx = (int) (object.getX() / tilewidth);
				int mapy = (int) (object.getY() / tilewidth);
				
				//System.out.println("old: "+TempGame.oldplan+" plan: "+TempGame.plan);
				if(!object.getId().startsWith(TempGame.plan+"-") && object.getId().contains("-") && LevelManager.loadedLevel.map[0][mapy][mapx] != LevelManager.loadedLevel.map[1][mapy][mapx]) {
					//System.out.println("assomb");
					ColorAdjust colorAdjust = new ColorAdjust();
				    colorAdjust.setBrightness(-0.5);
					object.setEffect(colorAdjust);
				}else {
					//System.out.println("eclairs");
					ColorAdjust colorAdjust = new ColorAdjust();
				    colorAdjust.setBrightness(0);
					object.setEffect(colorAdjust);
				}
			}
		}
		TempGame.oldplan = TempGame.plan;
	}
	
	public static void addPlayer(String moveUp, String moveRight, String moveDown, String moveLeft, String interract, double startX, double startY) {
		ImageView pl = getImageFromFile(RessourcesManager.playerPath);
		pl.setFitHeight(tilewidth-tilewidth/10);
		pl.setFitWidth(tilewidth-tilewidth/10);
		Player player1 = new Player(moveUp, moveRight, moveDown, moveLeft, interract, pl, startX, startY);
		player1.number = players.size()+1;
		pl.setX(player1.startX*tilewidth);
		pl.setY(player1.startY*tilewidth);
		pl.setId(""+uniqId);
		uniqId++;
		elements.add(pl);
		players.add(player1);
		root.getChildren().add(pl);
		/*ImageView sol = getImageFromFile(RessourcesManager.playerPath);
		sol.setFitHeight(2);
		sol.setFitWidth(pl.getFitWidth()-pl.getFitWidth()/4);
		sol.setId(""+pl.getId()+"sol");
		sol.setVisible(false);
		player1.sol = sol;
		root.getChildren().add(sol);
		ImageView plfnd = getImageFromFile(RessourcesManager.playerPath);
		plfnd.setFitHeight(2);
		plfnd.setFitWidth(pl.getFitWidth()-pl.getFitWidth()/4);
		plfnd.setId(""+pl.getId()+"plfnd");
		plfnd.setVisible(false);
		player1.plfnd = plfnd;
		root.getChildren().add(plfnd);*/
	}
	
	public static void renderMap() {
		
		ImageView rec;
		
		/*for (int i = 0; i < map.length; i++) {
					
			for (int o = 0; o < map[1].length; o++) {
				rec = new Rectangle();
				rec.setFill(Color.WHITE);
				rec.setX(o*TempGame.tilewidth);
				rec.setY(i*TempGame.tilewidth);
				rec.setHeight(TempGame.tilewidth);
				rec.setWidth(TempGame.tilewidth);
				rec.setId(""+uniqId);
				uniqId++;
				elements.add(rec);
				root.getChildren().add(rec);
			}
			
		}*/
			
		for (int i = 0; i < LevelManager.loadedLevel.map.length; i++) {
			
			for (int o = 0; o < LevelManager.loadedLevel.map[0].length; o++) {
				
				for (int e = 0; e < LevelManager.loadedLevel.map[0][0].length; e++) {
					
					/*if(map[i][o][e]) {
						if(o != 0 && e !=0 && o%maph != 0 && e%mapw != 0) {
							rec.setId(""+i+"-"+uniqId);
						}else {
							rec.setId(""+uniqId);
						}
					}*/
					if(LevelManager.loadedLevel.map[i][o][e] != '0') {
						rec = getImageFromFile(RessourcesManager.getTexturePath(LevelManager.loadedLevel.map[i][o][e]));
						rec.setX(e*tilewidth);
						rec.setY(o*tilewidth);
						rec.setFitHeight(tilewidth);
						rec.setFitWidth(tilewidth);

						if(o != 0 && e !=0 && maph-o-1 != 0 && mapw-e-1 != 0) {
							rec.setId(""+i+"-"+uniqId);
						}else {
							rec.setId(""+uniqId);
						}
						
						uniqId++;
						elements.add(rec);
						root.getChildren().add(rec);
					}
					
					
				}
				
			}
			
		}
		
		oldplan = -1;
		plan = 1;
		
		updateLight();
		for (int i = 0; i < TempGame.players.size(); i++) {
			players.get(i).pl.toFront();
		}
		
	}
	
	public static void killPlayer(Player pl) {
		pl.pl.setX(pl.startX*TempGame.tilewidth);
		pl.pl.setY(pl.startY*TempGame.tilewidth);
		pl.canJump = false;
		pl.inertieX = 0.0;
		pl.inertieY = 0.0;
	}
	
	public static ImageView getImageFromFile(String name) {
		Image image = null;
		try {
			image = new Image(TempGame.class.getResource(name).toURI().toURL().toString());
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		} 
		
		return new ImageView(image);
	}
	
}
