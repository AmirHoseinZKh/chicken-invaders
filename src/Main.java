import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.sound.SoundFile;

import java.sql.*;
import java.util.ArrayList;

public class Main extends PApplet {
    public static PApplet processing;

    public final int chickenWidth = 90;
    public final int chickenHeight = 100;
    public static ArrayList<Chicken> chickens = new ArrayList<>();
    public static ArrayList<Chicken> lastChicken = new ArrayList<>();
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    private boolean startGame = false;
    private boolean gameOver = false;
    private boolean checkStartOver = false;
    private boolean checkExitOver = false;
    private static int score ;
    private static int highScore;
    public static SoundFile sound1;
    public static SoundFile sound2;
    Tank tank = new Tank();

    PFont allText;
    PFont welcome;
    PImage bg;
    PImage bg2;
    PImage chickenPic2;
    PImage chickenPic3;
    PImage lastChickenPic;
    PImage lastChickenPic2;
    PImage lastChickenPic3;
    PImage king;
    PImage star;

    public static void main(String[] args) {
        PApplet.main("Main",args);
    }
    @Override
    public void setup(){
        processing = this;
        allText = createFont("SitkaVF.ttf" , 30);
        welcome = createFont("mvboli.ttf" , 30);
        bg2 = loadImage("base.jpg");
        chickenPic2 = loadImage("chicken2.png");
        chickenPic3 = loadImage("chicken3.png");
        lastChickenPic = loadImage("last chicken.png");
        lastChickenPic2 = loadImage("last chicken2.png");
        lastChickenPic3 = loadImage("last chicken3.png");
        king = loadImage("king.png");
        Chicken.createChicken();
        Chicken.createLastChicken();
        getHighScore();
        sound1 = new SoundFile(this , "sound1.wav");
        sound2 = new SoundFile(this , "sound2.wav");
        sound1.play();
    }

    @Override
    public void settings() {
        size(500 , 700);
    }

    @Override
    public void draw(){
        updateArea();
        if(startGame){
            if (!gameOver) {
                image(bg2, 0, 0, 500, 700);
                tank.createTank();
                showChicken();
                moveChicken();
                showBullet();
                moveBullet();
                setBackground();

                if (mousePressed && frameCount % 9 == 0) {
                    Bullet.createBullet();
                }


                for (int i = 0; i < chickens.size(); i++) {
                    for (int j = 0; j < bullets.size(); j++) {
                        if (bullets.get(j).getXPos() >= chickens.get(i).getChickenX() && bullets.get(j).getXPos() <= chickens.get(i).getChickenX() + 90 &&
                                bullets.get(j).getYPos() >= chickens.get(i).getChickenY() && bullets.get(j).getYPos() <= chickens.get(i).getChickenY() + 100) {
                            chickens.get(i).setLives(chickens.get(i).getLives() - 1);

                            if (chickens.get(i).getLives() == 2) {
                                chickens.get(i).chickenPic = chickenPic2;
                            }
                            else if (chickens.get(i).getLives() == 1){
                                chickens.get(i).chickenPic = chickenPic3;
                            }
                            else{
                                chickens.remove(chickens.get(i));
                                score++;
                            }

                            bullets.remove(bullets.get(j));
                            break;
                        }
                    }
                }

                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).getYPos() <= 0) {
                        bullets.remove(bullets.get(i));
                        break;
                    }
                }

                for (int i = 0; i < chickens.size(); i++) {
                    int tankYPos = 570;
                    if (tank.getTankXPos() >= chickens.get(i).getChickenX() && tank.getTankXPos() <= chickens.get(i).getChickenX() + 90 &&
                            tankYPos >= chickens.get(i).getChickenY() && tankYPos <= chickens.get(i).getChickenY() + 90) {
                        gameOver = true;
                        loseScreen(score);
                    }
                }

                for (int i=0 ; i<chickens.size() ; i++) {
                    if (chickens.get(i).getChickenY() + 100 >= 650){
                        gameOver = true;
                        loseScreen(score);
                    }
                }

                if (chickens.size() == 0) {
                    showLastChicken();
                    moveLastChicken();

                    for (int i=0 ; i<bullets.size() ; i++){
                        if (bullets.get(i).getXPos() >= lastChicken.get(0).getChickenX() && bullets.get(i).getXPos() <= lastChicken.get(0).getChickenX() + 300 &&
                        bullets.get(i).getYPos() >= lastChicken.get(0).getChickenY() && bullets.get(i).getYPos() <= lastChicken.get(0).getChickenY() + 290){
                            lastChicken.get(0).setLives(lastChicken.get(0).getLives() - 1);
                            bullets.remove(bullets.get(i));

                            if (lastChicken.get(0).getLives() == 30){
                                lastChickenPic = lastChickenPic2;
                            }

                            if (lastChicken.get(0).getLives() == 15){
                                lastChickenPic = lastChickenPic3;
                            }

                            if (lastChicken.get(0).getLives() == 0){
                                gameOver = true;
                                score = score + 50;
                                winScreen(score);
                                break;
                            }

                        }
                    }

                    for (int i=0 ; i<lastChicken.size() ; i++){
                        if (tank.getTankXPos() >= lastChicken.get(i).getChickenX() && tank.getTankXPos() <= lastChicken.get(i).getChickenX() + 300 &&
                        570 >= lastChicken.get(i).getChickenY() && 570 <= lastChicken.get(i).getChickenY() + 300){
                            gameOver = true;
                            loseScreen(score);
                        }
                    }
                }
            }
        }
        else{
            startMenu();
        }
    }

    public void startMenu(){
        bg = loadImage("Menu.jpg");
        image(bg , 0 , 0 , 500 , 700);

        fill(0);
        textFont(welcome);
        textSize(26);
        text("Welcome to the Chicken Invaders" , 50 , 110);


        fill(0);
        textFont(allText);
        textSize(35);
        text("Start Game" , 160 ,280);
        fill(255 , 0 , 0);
        textFont(allText);
        textSize(35);
        text("Exit" , 215 , 370);
        fill(0);
        textFont(welcome);
        textSize(25);
        text("Created By AHZ" ,150 , 630);

    }

    public boolean checkStartHover(int x , int y , int width , int height){
        return mouseX >= x && mouseX<= x+width && mouseY >= y && mouseY <= y + height;
    }

    public boolean checkExitHover(int x , int y , int width , int height){
        return mouseX >= x && mouseX<= x+width && mouseY >= y && mouseY <= y + height;
    }

    public boolean checkExitHover2(int x , int y , int width , int height){
        return mouseX >= x && mouseX<= x+width && mouseY >= y && mouseY <= y + height;
    }

    public void updateArea(){
        if (checkStartHover(165 , 260 , 160 , 20)){
            checkStartOver = true;
            checkExitOver = false;
        }
        else if (checkExitHover(218 , 350 , 49 , 20)){
            checkExitOver = true;
            checkStartOver = false;
        }
        else if (checkExitHover2(228 , 460 , 49 , 20)){
            checkExitOver = true;
            checkStartOver = false;
        }
        else {
            checkStartOver = false;
            checkExitOver = false;
        }
    }

    @Override
    public void mousePressed() {
        if (checkStartOver){
            sound1.stop();
            startGame = true;
            if (!sound2.isPlaying()){
                sound2.play();
            }
        }
        else if(checkExitOver) {
            exit();
        }
    }

    public void setBackground(){
        fill(75 , 40 , 12);
        noStroke();
        rect(0 , 650 , 500 , 50);
        fill(255 , 255 , 255);
        textFont(allText);
        text("Score :" , 10 ,685);
        textSize(30);
        text(score , 450 , 685);
    }

    public void loseScreen(int score){
        bg = loadImage("Menu.jpg");
        image(bg , 0 ,0 , 500 , 700);

        if (score <= 10) {
            textFont(welcome);
            textSize(30);
            fill(0);
            text("Game Over :(" , 155 , 150);
            fill(255 ,0 , 0);
            text("_______________" , 150 , 160);
            star = loadImage("0star.png");
            image(star , 95 , 200 , 320 , 90);
            textFont(allText);
            textSize(30);
            fill(0);
            text("Your Score :", 120, 380);
            textSize(30);
            textFont(welcome);
            text(score, 330, 380);
            textFont(allText);
            textSize(30);
            fill(255 , 0 ,0);
            text("Exit" , 225 , 480);
            textFont(allText);
            textSize(30);
            fill(0);
            text("High Score :" , 120 , 430);
            textFont(allText);
            textSize(30);
            fill(0);
            textFont(welcome);
            text(highScore , 330 , 430);
            textFont(welcome);
            textSize(25);
            text("Created By AHZ" ,150 , 630);

        }
        else if (score <= 20){
            textFont(welcome);
            textSize(30);
            fill(0);
            text("Game Over :(" , 155 , 150);
            fill(255 ,0 , 0);
            text("_______________" , 150 , 160);
            star = loadImage("1star.png");
            image(star , 95 , 200 , 320 , 90);
            textFont(allText);
            textSize(30);
            fill(0);
            text("Your Score :", 120, 380);
            textSize(30);
            textFont(welcome);
            fill(0);
            text(score, 330, 380);
            textFont(allText);
            textSize(30);
            fill(255 ,0 ,0);
            text("Exit" , 225 , 480);
            textFont(allText);
            textSize(30);
            fill(0);
            text("High Score :" , 120 , 430);
            textFont(allText);
            textSize(30);
            fill(0);
            textFont(welcome);
            text(highScore , 330 , 430);
            textFont(welcome);
            textSize(25);
            text("Created By AHZ" ,150 , 630);

        }
        else if (score <= 30){
            textFont(welcome);
            textSize(30);
            fill(0);
            text("Game Over :(" , 155 , 150);
            fill(255 ,0 , 0);
            text("_______________" , 150 , 160);
            star = loadImage("2star.png");
            image(star , 95 , 200 , 320 , 90);
            textFont(allText);
            textSize(30);
            fill(0);
            text("Your Score :", 120, 380);
            textSize(30);
            textFont(welcome);
            fill(0);
            text(score, 330, 380);
            textFont(allText);
            textSize(30);
            fill(255 , 0 , 0);
            text("Exit" , 225 , 480);
            textFont(allText);
            textSize(30);
            fill(0);
            text("High Score :" , 120 , 430);
            textFont(allText);
            textSize(30);
            fill(0);
            textFont(welcome);
            text(highScore , 330 , 430);
            textFont(welcome);
            textSize(25);
            text("Created By AHZ" ,150 , 630);
        }
        else {
            textFont(welcome);
            textSize(30);
            fill(0);
            text("Game Over :(" , 155 , 150);
            fill(255 ,0 , 0);
            text("_______________" , 150 , 160);
            star = loadImage("3star.png");
            image(star , 95 , 200 , 320 , 90);
            textFont(allText);
            textSize(30);
            fill(0);
            text("Your Score :", 120, 380);
            textSize(30);
            textFont(welcome);
            fill(0);
            text(score, 330, 380);
            textFont(allText);
            textSize(30);
            fill(255 , 0 , 0);
            text("Exit" , 225 , 480);
            textFont(allText);
            textSize(30);
            fill(0);
            text("High Score :" , 120 , 430);
            textFont(allText);
            textSize(30);
            fill(0);
            textFont(welcome);
            text(highScore , 330 , 430);
            textFont(welcome);
            textSize(25);
            text("Created By AHZ" ,150 , 630);

        }
        if (score > highScore){
            updateHighScore();
        }
    }

    public void winScreen(int score){
        bg = loadImage("Menu.jpg");
        image(bg , 0 ,0 , 500 , 700);
        textFont(welcome);
        textSize(30);
        fill(0);
        text("You Win" , 155 , 150);
        fill(255 ,0 , 0);
        text("_______________" , 150 , 160);
        image(king , 280 , 95 , 100 , 70);
        star = loadImage("3star.png");
        image(star , 95 , 200 , 320 , 90);
        textFont(allText);
        textSize(30);
        fill(0);
        text("Your Score :", 120, 380);
        textSize(30);
        textFont(welcome);
        fill(0);
        text(score, 330, 380);
        textFont(allText);
        textSize(30);
        fill(255 , 0 , 0);
        text("Exit" , 225 , 480);
        textFont(allText);
        textSize(30);
        fill(0);
        text("High Score :" , 120 , 430);
        textFont(allText);
        textSize(30);
        fill(0);
        textFont(welcome);
        text(highScore , 330 , 430);
        textFont(welcome);
        textSize(25);
        text("Created By AHZ" ,150 , 630);
    }


    public void showChicken(){
        for (Chicken c : chickens){
            image(c.chickenPic , c.getChickenX() , c.getChickenY() , chickenWidth , chickenHeight);
        }
    }

    public void moveChicken(){
        if (chickens.size() <= 20) {
            for (Chicken c : chickens) {
                c.setChickenY(c.getChickenY() + 3);
            }
        }
        else{

            for (Chicken c : chickens) {
                c.setChickenY(c.getChickenY() + 3);
            }
        }
    }
    int speedy;
    public void showLastChicken(){
        image(lastChickenPic , lastChicken.get(0).getChickenX() ,lastChicken.get(0).getChickenY() , 300 , 300);
    }

    public void moveLastChicken(){

        lastChicken.get(0).setChickenY(lastChicken.get(0).getChickenY() + speedy);
        if (lastChicken.get(0).getChickenY() <0){
            speedy=1;
        }
        if (lastChicken.get(0).getChickenY() == 40){
            speedy=2;
        }
        if (lastChicken.get(0).getChickenY()  > 240 ){
            speedy=-2;
        }
    }

    public void showBullet(){
        for (Bullet b : bullets){
            fill(0);
            noStroke();
            image(b.bulletPic , b.getXPos() , b.getYPos() , 20 , 20);
        }
    }
    public void moveBullet(){
        for (Bullet b : bullets){
            b.setYPos(b.getYPos() - 12);
        }
    }

    public static void getHighScore(){
        String username = "root";
        String password = "ahz8213";
        String url = "jdbc:mysql://localhost:3306/dodgeup";
        try{
            Connection connection = DriverManager.getConnection(url , username , password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from score");
            while (resultSet.next()){
                highScore = resultSet.getInt(1);
            }
        }
        catch (Exception e){}
    }

    public static void updateHighScore(){
        String username = "root";
        String password = "ahz8213";
        String url = "jdbc:mysql://localhost:3306/dodgeup";
        try{
            Connection connection = DriverManager.getConnection(url , username , password);
            PreparedStatement statement = connection.prepareStatement("update score set idscore = ?");
            statement.setInt(1 , score);
            statement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}