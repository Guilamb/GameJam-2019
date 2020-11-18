package me.hubert.leopold.basegame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	
	public static String savePath = Paths.get(".").toAbsolutePath().normalize().toString()+"\\save.txt";

	static int mapwidth = 20;//30
	static int mapheight = 11;//17
	static int plans = 2;
	
	public static ArrayList<Node> elements = new ArrayList<Node>();
	
	public static char[][][] map = new char[plans][mapheight][mapwidth];
	
	public static int tilewidth = 20;
	
	public static void main(String[] args) {
		System.out.println("init");
		initMap();
		launch(args);
	}
	
	public static void initMap() {
		
		for (int i = 0; i < map.length; i++) {
			
			for (int o = 0; o < map[0].length; o++) {
				
				for (int e = 0; e < map[0][0].length; e++) {
					
					if(o != 0 && e !=0 && mapheight-o-1 != 0 && mapwidth-e-1 != 0) {
						map[i][o][e] = '0';
					}else {
						map[i][o][e] = 'x';
					}
					
				}
				
			}
			
		}
		
	}
	
	static String tilenum = "a";
	
	static boolean isCtrl = false;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("start");
		
		Group root = new Group();
		Scene scene = new Scene(root, tilewidth*mapwidth, tilewidth*mapheight);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	System.out.println("key : "+event.getCode());
            	tilenum = event.getText();
            	if(event.getCode().toString() == "ENTER") {
            		save();
            	}
            	if(event.getCode().toString() == "CONTROL") {
            		isCtrl = !isCtrl;
            	}
            }
        });
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getX()+" "+event.getY()+" "+event.getTarget());
				int mapx = (int) (event.getX()/tilewidth);
				System.out.println(mapx);
				int mapy = (int) (event.getY()/tilewidth);
				System.out.println(mapy);
				ImageView rec = null;
				System.out.println(tilenum);
				
				if(isCtrl) {
					System.out.println("ctrl");
					for (int i = 0; i < elements.size(); i++) {
						if(elements.get(i).getId().equals("0-"+mapx+"-"+mapy)) {
							root.getChildren().remove(elements.get(i));
						}
					}
					
					if(tilenum.equals("a")) {
						map[0][mapy][mapx] = 'x';
						rec = getImageFromFile(RessourcesManager.wallPath);
					}else if(tilenum.equals("b")) {
						map[0][mapy][mapx] = 'b';
						rec = getImageFromFile(RessourcesManager.getTexturePath('b'));
					}else if(tilenum.equals("p")) {
						map[0][mapy][mapx] = 'p';
						rec = getImageFromFile(RessourcesManager.getTexturePath('p'));
					}else if(tilenum.equals("l")) {
						map[0][mapy][mapx] = 'l';
						rec = getImageFromFile(RessourcesManager.getTexturePath('l'));
					}else {
						map[0][mapy][mapx] = '0';
					}
					
					if(rec != null) {
						rec.setX(mapx*tilewidth);
						rec.setY(mapy*tilewidth);
						rec.setFitHeight(tilewidth);
						rec.setFitWidth(tilewidth);
						rec.setId("0-"+mapx+"-"+mapy);
						ColorAdjust colorAdjust = new ColorAdjust();
					    colorAdjust.setBrightness(0.5);
					    rec.setEffect(colorAdjust);

						elements.add(rec);
						root.getChildren().add(rec);
					}
					
				}else {
					System.out.println("nrml");
					for (int i = 0; i < elements.size(); i++) {
						if(elements.get(i).getId().equals("1-"+mapx+"-"+mapy)) {
							root.getChildren().remove(elements.get(i));
						}
					}
					
					if(tilenum.equals("a")) {
						map[1][mapy][mapx] = 'x';
						rec = getImageFromFile(RessourcesManager.wallPath);
					}else if(tilenum.equals("b")) {
						map[1][mapy][mapx] = 'b';
						rec = getImageFromFile(RessourcesManager.bumpPath);
					}else if(tilenum.equals("p")) {
						map[1][mapy][mapx] = 'p';
						rec = getImageFromFile(RessourcesManager.getTexturePath('p'));
					}else if(tilenum.equals("l")) {
						map[1][mapy][mapx] = 'l';
						rec = getImageFromFile(RessourcesManager.getTexturePath('l'));
					}else {
						map[1][mapy][mapx] = '0';
					}
					
					if(rec != null) {
						rec.setX(mapx*tilewidth);
						rec.setY(mapy*tilewidth);
						rec.setFitHeight(tilewidth);
						rec.setFitWidth(tilewidth);
						rec.setId("1-"+mapx+"-"+mapy);

						elements.add(rec);
						root.getChildren().add(rec);
					}
					
				}
				
				
				
				
				
				
			}
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        System.exit(0);
		    }
		});
		
		scene.setFill(Color.BLACK);
		
		primaryStage.setTitle("TITLE");
		primaryStage.setScene(scene);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		primaryStage.setWidth(screenSize.getWidth());
		primaryStage.setHeight(screenSize.getHeight());
		primaryStage.setFullScreen(true);
		primaryStage.show();
		
		int tmpw1 = (int) (primaryStage.getWidth() / mapwidth);
		int tmpw2 = (int) (primaryStage.getHeight() / mapheight);
		tilewidth = tmpw1<tmpw2?tmpw1:tmpw2;
		
		showmap(root, scene);
		
	}
	
	public static void save() {
		try{    
	       FileWriter fw = new FileWriter(savePath);    
	       fw.write("= new char[][][] {\n");
	       for (int i = 0; i < map.length; i++) {
	    	   fw.write("{\n");
	    	   for (int o = 0; o < map[0].length; o++) {
	    		   fw.write("{");
	    		   for (int e = 0; e < map[0][0].length; e++) {
	    			   if(e != map[0][0].length-1) {
		    			   fw.write("'"+map[i][o][e]+"',");
		    		   }else {
		    			   fw.write("'"+map[i][o][e]+"'");
		    		   }
	    		   }
	    		   if(o != map[0].length-1) {
	    			   fw.write("},\n");
	    		   }else {
	    			   fw.write("}\n");
	    		   }
		       }
	    	   if(i != map.length-1) {
	    		   fw.write("},\n");
    		   }else {
    			   fw.write("}\n");
    		   }
	    	   
	       }
	       fw.write("};");
	       fw.close();
	    }catch(Exception e){}
	}
	
	public static void showmap(Group root,Scene scene) {
		
		ImageView rec;
		for (int i = 0; i < map.length; i++) {
			
			for (int o = 0; o < map[0].length; o++) {
				
				for (int e = 0; e < map[0][0].length; e++) {
					
					/*if(map[i][o][e]) {
						if(o != 0 && e !=0 && o%maph != 0 && e%mapw != 0) {
							rec.setId(""+i+"-"+uniqId);
						}else {
							rec.setId(""+uniqId);
						}
					}*/
					
					if(map[i][o][e] == 'x') {
						rec = getImageFromFile(RessourcesManager.wallPath);
						rec.setX(e*tilewidth);
						rec.setY(o*tilewidth);
						rec.setFitHeight(tilewidth);
						rec.setFitWidth(tilewidth);
						rec.setId(""+i+"-"+o+"-"+e);

						elements.add(rec);
						root.getChildren().add(rec);
					}
					
				}
				
			}
		}
	}
	
	public static ImageView getImageFromFile(String name) {
		Image image = null;
		try {
			image = new Image(Main.class.getResource(name).toURI().toURL().toString());
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		} 
		
		return new ImageView(image);
	}
	
	
}
