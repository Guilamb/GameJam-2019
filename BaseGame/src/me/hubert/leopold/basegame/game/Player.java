package me.hubert.leopold.basegame.game;

import javafx.scene.image.ImageView;

public class Player {
	
	public double maxvitdown = 6;
	public double maxvitup = 10.0;
	public double maxvitHoriz = 2.0;
	
	public double acceleration = 1.6;
	public double decceleration = 0.10;
	public double gravite = 0.15;
	
	public double jump = 8.0;
	
	public double inertieX = 0.0;
	public double inertieY = 0.0;

	public ImageView pl;
	public String moveUp,moveRight,moveDown,moveLeft,interract,t0,t1,t2,t3,t4,t5,t6,t7,t8,t9;
	public int number;
	public double startX;
	public double startY;
	
	public boolean upPressed = false;
	public boolean rightPressed = false;
	public boolean downPressed = false;
	public boolean leftPressed = false;
	
	public boolean canJump = false;
	
	Player(String moveUp,String moveRight,String moveDown,String moveLeft,String interract,ImageView pl,double startX,double startY,String t0,String t1,String t2,String t3,String t4,String t5,String t6,String t7,String t8,String t9){
		this.moveUp = moveUp;
		this.moveRight = moveRight;
		this.moveDown = moveDown;
		this.moveLeft = moveLeft;
		this.interract = interract;
		this.startX = startX;
		this.startY = startY;
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
		this.t5 = t5;
		this.t6 = t6;
		this.t7 = t7;
		this.t8 = t8;
		this.t9 = t9;
	}
	
	Player(String moveUp,String moveRight,String moveDown,String moveLeft,String interract,ImageView pl,double startX,double startY){
		this.moveUp = moveUp;
		this.moveRight = moveRight;
		this.moveDown = moveDown;
		this.moveLeft = moveLeft;
		this.interract = interract;
		this.pl = pl;
		this.startX = startX;
		this.startY = startY;
	}
	
}
