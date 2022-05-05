package tools;


import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class Data {

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final long IMUNITYFRAME = 500;
	public static final long ATTACKSPEED_CAP = 200;
	public static final double ATTACKSPEED_UPGRADE_CAP = 0.1;
	public static final double PERCENTAGE_REGEN_ARMOUR = 0.1;
	public static final long ATTACKSPEED_MULTIPLYER = 1000;
	public static final long ATTACKSPEED_MULTIPLYER_DOUBLE = 1500;
	public static final long ATTACKSPEED_MULTIPLYER_TRIPPLE = 2000;
	public static final long ATTACKSPEED_MULTIPLYER_QUADRA = 2500;
	public static final long AROUNDSHOT_CD = 5000;
	public static final int PIXEL = 32;
	public static final long SECOND = 1000;
	public static final int BOARDERLEFT = 0 + PIXEL;
	public static final int BOARDERRIGHT = WIDTH - PIXEL;
	public static final int BOARDERTOP = 0 + (4* PIXEL);
	public static final int BOARDERDOWN = HEIGHT - PIXEL;
	public static final double DECREASE_LIFE_PER_SECOND = 0.05;
	public static final double ACCEPTABLE_RANGE = 112;
	public static final double BAR_LENGTH = 200;
	public static final double BAR_HEIGHT = 25;
	public static final long ARMOUR_COOLDOWN = 3000;
	public static final int ICONSIZE = 25;
	public static final int GUNSIZE = 20;
	public static final int BASE_HP_NM = 20;
	public static final int HP_PER_WAVE_NM = 2;
	public static final int BASE_HP_CM = 40;
	public static final int HP_PER_WAVE_CM = 5;
	public static final int BASE_PW_NM = 3;
	public static final double PW_PER_WAVE_NM = 0.1;
	public static final int BASE_PW_CM = 6;
	public static final double PW_PER_WAVE_CM = 0.2;
	public static final double BASE_S_NM = 0.2;
	public static final double BASE_S_CM = 1.5;
	public static final double BASE_SP_NM = 2.0;
	public static final double SP_PER_WAVE_NM = 0.1;
	public static final double BASE_SP_CM = 4.0;
	public static final double SP_PER_WAVE_CM = 0.2;
	public static final double BASE_AS_NM = 0.5;
	public static final double BASE_AS_CM = 0.3;
	public static final double RAND_RANGE_AS = 1.0;
	public static final long CORPSE_DELAY =3000;
	public static final int BASE_XP_NM = 1;
	public static final int BASE_XP_CM = 10;
	public static final double XP_PER_WAVE_NM = 0.5;
	public static final int XP_PER_WAVE_CM = 2;
	public static final long SHOT_DELAY = 100;
	public static final int ITEMSIZE = 15;
	public static final long ITEM_DELAY_DELETE = 10000;
	public static final long ITEM_DELAY_WARNING = 7000;
	public static final double ACCEPTABLE_RANGE_NOTUPDATE = 0.5;
	public static final double HEALTH_DROP_CHANCE = 0.10;
	public static final double SKILL_DROP_CHANCE = 0.025;
	public static final long OPEN_DELAY = 3000;
	public static final double ATTACKSPEED_UP = 0.1;
	public static final int POWER_UP = 1;
	public static final int ARMOUR_UP = 3;
	public static final int HEALTH_UP = 5;
	public static final double SHOTSPEED_UP = 0.5;
	public static final double SKILLTABLE_LOC_X = 200;
	public static final double SKILLTABLE_LOC_Y = 200;
	public static final Image mineSprite = new Image(ClassLoader.getSystemResource("Mine.png").toString());
	public static final Image HEART = new Image(ClassLoader.getSystemResource("pic/heart.png").toString());
	public static final Image LEVEL = new Image(ClassLoader.getSystemResource("pic/level.png").toString());
	public static final int[] LEVELTABLE = {5,7,9,11,13,20,40,80,170,200,230,240,400,450,550,750,950,1100,1222,1300,1400,1700,1800,2000,2200,2500,2700,3000,3300,3610,5555,7520,11222,15212};
	public static final Font FONT12 = Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 12);
	public static final Font FONT16 = Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 16);
	public static final Font FONT24 = Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 24);
	public static final Font FONT36 = Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 36);
	public static final Font FONT48 = Font.loadFont(ClassLoader.getSystemResource("font.ttf").toString(), 48);
	public static final int CHANGE_IMG_COUNTER = 20;
}