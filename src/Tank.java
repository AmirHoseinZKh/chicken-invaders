import processing.core.PImage;

public class Tank {
    private float tankXPos;

    PImage tank;

    public void createTank(){
        tank = Main.processing.loadImage("tank.png");
        if (Main.processing.mouseX <= 425){
           tankXPos = Main.processing.mouseX;
        }
        Main.processing.image(tank , tankXPos , 570 , 75 , 85);

    }

    public float getTankXPos() {
        return tankXPos;
    }
}
