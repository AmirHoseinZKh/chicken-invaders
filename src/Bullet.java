import processing.core.PImage;

public class Bullet {

    private float XPos;
    private float YPos;
    PImage bulletPic = Main.processing.loadImage("bullet.png");

    public Bullet(float xPos , float yPos){
        this.XPos = xPos;
        this.YPos = yPos;
    }

    public static void createBullet(){

        Main.bullets.add(new Bullet(Main.processing.mouseX + 28 , 570));
    }

    public float getXPos() {
        return XPos;
    }

    public float getYPos() {
        return YPos;
    }

    public void setYPos(float yPos) {
        this.YPos = yPos;
    }
}
