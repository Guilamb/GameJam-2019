package me.hubert.leopold.basegame.game;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import me.hubert.leopold.basegame.Main;
import me.hubert.leopold.basegame.RessourcesManager;

public class Runtime extends Thread {
	
	int everx = 10; // every x milisecondes
	
	public static boolean isOnDoor = false;
	
	@SuppressWarnings("unused")
	public void run() {
		long ms = System.currentTimeMillis();
		while (Main.runtime) {
			if(System.currentTimeMillis()-ms >= everx) {
				ms = System.currentTimeMillis();
				
				for (int i = 0; i < TempGame.players.size(); i++) {
					
					Player pl = TempGame.players.get(i);
					
					if(pl.inertieX < pl.maxvitHoriz*(-1)) {
						pl.inertieX = pl.maxvitHoriz*(-1);
					}
					if(pl.inertieX > pl.maxvitHoriz) {
						pl.inertieX = pl.maxvitHoriz;
					}
					
					if(pl.inertieY < pl.maxvitup*(-1)) {
						pl.inertieY = pl.maxvitup*(-1);
					}
					
					if(pl.inertieY < 0.0) {
						pl.canJump = false;
					}
					
					if(pl.inertieY > pl.maxvitdown) {
						pl.inertieY = pl.maxvitdown;
					}
					
					pl.inertieY += pl.gravite;
					
					if(pl.rightPressed||pl.leftPressed) {
						if(pl.rightPressed) {
							pl.inertieX += pl.acceleration;
						}else {
							pl.inertieX -= pl.acceleration;
						}
					}else {
						if(pl.inertieX > pl.decceleration) {
							pl.inertieX -= pl.decceleration;
						}else if(pl.inertieX < pl.decceleration*(-1)) {
							pl.inertieX += pl.decceleration;
						}else {
							pl.inertieX = 0;
						}
					}
					
					TempGame.updateLight();
					
					isOnDoor = false;
					
					for (int j = 0; j < TempGame.elements.size(); j++) {
						
						ImageView object = null;
						
						if(TempGame.elements.get(j) instanceof ImageView) {
							object = (ImageView) TempGame.elements.get(j);
						}


						if(object != null) {
							
							int omapposX = (int) (object.getX()/TempGame.tilewidth);
							int omapposY = (int) (object.getY()/TempGame.tilewidth);
							
							if(omapposX < LevelManager.loadedLevel.map[0][0].length && omapposY < LevelManager.loadedLevel.map[0].length && omapposX >=0 && omapposY >= 0) {
							
								//System.out.println("omapX : "+omapposX+" omapY : "+omapposY);
								//System.out.println("mapw : "+TempGame.mapw+" maph : "+TempGame.maph);
								
								char corresp = LevelManager.loadedLevel.map[TempGame.plan][omapposY][omapposX];
								
								if(RessourcesManager.iscolide(corresp) || RessourcesManager.isdie(corresp)) {
									
									if(RessourcesManager.iscolide(corresp)){
										if(pl.pl.getBoundsInParent().intersects(TempGame.elements.get(j).getBoundsInParent()) && !pl.pl.getId().equals(object.getId())) {
											//System.out.println("objx : "+omapposX+" objy : "+omapposY);
											if(object.getY() >= pl.pl.getY()+TempGame.tilewidth-pl.maxvitdown*2.5) {
												if(corresp == 'b') {
													pl.pl.setY(object.getY()-TempGame.tilewidth+pl.maxvitdown*1.5);
													pl.inertieY = pl.inertieY>=0.0?-10.0:pl.inertieY;
													//pl.inertieY -= 2.0;
													//System.out.println("b grv: "+pl.inertieY);
													pl.canJump = true;
												}else {
													pl.pl.setY(object.getY()-TempGame.tilewidth+pl.maxvitdown*1.5);
													pl.inertieY = pl.inertieY>=0.0?0.0:pl.inertieY;
													//pl.inertieY -= 2.0;
													//System.out.println("b grv: "+pl.inertieY);
													pl.canJump = true;
												}
												
											}else if(object.getY() <= pl.pl.getY()-TempGame.tilewidth+10 && object.getX() <= pl.pl.getX()+TempGame.tilewidth-10 && object.getX() >= pl.pl.getX()-TempGame.tilewidth+10) {
												System.out.println("h ply: "+pl.pl.getY()+" objy: "+object.getY());
												pl.pl.setY(object.getY()+TempGame.tilewidth+0.1);
												pl.inertieY = pl.inertieY<=0.0?0.0:pl.inertieY;
												//pl.inertieX += 2.0;
												//System.out.println("g grv: "+pl.inertieX);
											}else if(object.getX() >= pl.pl.getX()) {
												pl.pl.setX(object.getX()-TempGame.tilewidth-0.1);
												pl.inertieX = pl.inertieX>=0.0?0.0:pl.inertieX;
												//pl.inertieX -= 2.0;
												//System.out.println("d grv: "+pl.inertieX);
											}else if(object.getX() <= pl.pl.getX()) {
												pl.pl.setX(object.getX()+TempGame.tilewidth+0.1);
												pl.inertieX = pl.inertieX<=0.0?0.0:pl.inertieX;
												//pl.inertieX += 2.0;
												//System.out.println("g grv: "+pl.inertieX);
											}
										}/*else if(pl.plfnd.getBoundsInParent().intersects(TempGame.elements.get(j).getBoundsInParent()) && pl.pl.getBoundsInParent().intersects(TempGame.elements.get(j).getBoundsInParent()) && !pl.pl.getId().equals(object.getId())) {
											//System.out.println("objx : "+omapposX+" objy : "+omapposY);
											if(object.getY() <= pl.pl.getY()) {
												pl.pl.setY(object.getY()+TempGame.tilewidth+0.2);
												pl.inertieY = pl.inertieY<=0.0?0.0:pl.inertieY;
												//pl.inertieY += 1.2;
												//System.out.println("h grv: "+pl.inertieY);
											}
										}else if(pl.sol.getBoundsInParent().intersects(TempGame.elements.get(j).getBoundsInParent()) && !pl.pl.getId().equals(object.getId())) {
											//System.out.println("objx : "+omapposX+" objy : "+omapposY);
											//System.out.println("solid : "+pl.sol.getId()+" objid : "+object.getId());
											if(corresp == 'b') {
												if(object.getY() >= pl.pl.getY()) {
													pl.sol.setY(object.getY()-TempGame.tilewidth-0.0000001);
													pl.inertieY = pl.inertieY>=0.0?-6.0:pl.inertieY;
													//pl.inertieY -= 2.0;
													//System.out.println("b grv: "+pl.inertieY);
													pl.canJump = true;
												}
											}else {
												if(object.getY() >= pl.pl.getY()) {
													pl.sol.setY(object.getY()-TempGame.tilewidth-0.0000001);
													pl.inertieY = pl.inertieY>=0.0?0.0:pl.inertieY;
													//pl.inertieY -= 2.0;
													//System.out.println("b grv: "+pl.inertieY);
													pl.canJump = true;
												}
											}
										}*/
									}
									
									
									
									
									
									
									
									
									if(RessourcesManager.isdie(corresp)) {
										if(pl.pl.getBoundsInParent().intersects(object.getBoundsInParent()) && (object.getX()+TempGame.tilewidth-5 >= pl.pl.getX() && object.getX() <= pl.pl.getX()+TempGame.tilewidth)) {
											System.out.println("die");
											TempGame.killPlayer(pl);
										}
									}
									
								}
								
								if(pl.pl.getBoundsInParent().intersects(object.getBoundsInParent()) && (object.getX()+TempGame.tilewidth-5 >= pl.pl.getX() && object.getX() <= pl.pl.getX()+TempGame.tilewidth) && corresp == 'd') {
									isOnDoor = true;
								}
								
								
							}
						}
						
					}
					
					
					
					pl.pl.setX(pl.pl.getX() + pl.inertieX);
					
					pl.pl.setY(pl.pl.getY() + pl.inertieY);
					
				}
				
				
				
			}
			
		}
		
		System.exit(0);
		
	}

}
