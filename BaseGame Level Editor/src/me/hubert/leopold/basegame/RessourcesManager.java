package me.hubert.leopold.basegame;

public class RessourcesManager {
	public static String wallPath = "/images/wall.png";
	public static String playerPath = "/images/player.png";
	public static String bumpPath = "/images/bump.png";
	public static String spikesPath = "/images/spikes.png";
	public static String liquidPath = "/images/liquid.png";
	public static String doorPath = "/images/door.png";
	
	public static String missingPath = "/images/missing.png";
	
	public static String getTexturePath(char c) {
		if(c == 'x') {return wallPath;}
		if(c == 'b') {return bumpPath;}
		if(c == 'p') {return spikesPath;}
		if(c == 'l') {return liquidPath;}
		if(c == 'd') {return doorPath;}
		return missingPath;
	}
}
