import processing.core.PImage;

public class Chicken {

    private static int chickenUp = -100;
    private float chickenY;
    private float chickenX;
    private int lives;
    PImage chickenPic = Main.processing.loadImage("chicken1.png");;

    public Chicken(float chickenX , float chickenY){
        this.chickenX = chickenX;
        this.chickenY = chickenY;
        this.lives = 3;
    }

    public Chicken(float chickenX , float chickenY , int lives){
        this.chickenX = chickenX;
        this.chickenY = chickenY;
        this.lives = lives;
    }
    public static void createChicken(){


        for (int i = 0 ; i <= 3 ; i++) {
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
            Main.chickens.add(new Chicken((int) Main.processing.random(400), chickenUp));
            chickenUp -= Main.processing.random(100, 150);
        }
    }

    public static void createLastChicken(){
        Main.lastChicken.add(new Chicken(100 , -300 , 50));
    }

    public float getChickenY() {
        return chickenY;
    }

    public float getChickenX() {
        return chickenX;
    }

    public void setChickenY(float chickenY) {
        this.chickenY = chickenY;
    }

    public void setChickenX(float chickenX) {
        this.chickenX = chickenX;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
