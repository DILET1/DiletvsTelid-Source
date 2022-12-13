//TODO: Add hitboxes for hazards on maps. Figure out TELID Animations. Start tinkering with map and how 2 implement, map class?.
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class DiletvsTelid extends PApplet{
    PlayerChar Telid  = new PlayerChar(50,25,25);
    PlayerChar Dilet  = new PlayerChar(50,25,35);
    boolean left1, right1, up1, down1, left2, right2, up2, down2, slap, kick,fr1,fl1;
    float player1X = 40;
    float player1Y = 40;
    float player2X = 360;
    float player2Y = 360;
    public boolean player1left = false;
    public boolean player1right = false;
    public boolean player2left = false;
    public boolean player2right = false;
    public boolean slapCharged = false;
    public boolean kickCharged = false;
    public boolean Diletwins = false;
    public boolean Telidwins = false;
    public boolean slapping = false;
    ArrayList<PImage> maptAnim =  new ArrayList<>();
    ArrayList<PImage> mapvAnim =  new ArrayList<>();
    ArrayList<PImage> mapzAnim =  new ArrayList<>();
    ArrayList<PImage> preAnim =  new ArrayList<>();
    ArrayList<PImage> diletDeathAnim =  new ArrayList<>();
    ArrayList<PImage> telidDeathAnim =  new ArrayList<>();
    ArrayList<PImage> DiletLegAnim = new ArrayList<>();
    ArrayList<PImage> DiletLPawAnim = new ArrayList<>();
    ArrayList<PImage> DiletRPawAnim = new ArrayList<>();
    ArrayList<PImage> DiletRIP = new ArrayList<>();
    ArrayList<PImage> TelidRIP = new ArrayList<>();
    ArrayList<PImage> Dilet_WS2 = new ArrayList<>();
    ArrayList<PImage> Dilet_DSlap = new ArrayList<>();
    PImage Dilet_Left;
    PImage Dilet_Right;
    PImage Dilet_RP;
    PImage Dilet_LP;
    PImage Dilet_RB;
    PImage Dilet_LB;
    PImage Dilet_LS;
    PImage diletSprite;
    PImage telidSprite;
    PImage Dilet_UB;
    PImage Dilet_DB;
    int counter = 0;
    int startTime = 0;
    int stayTime = 0;
    int slapTime = 0;
    int kickTime = 0;
    int nmc = 0;
    int f = 0;
    boolean fu1 = false;
    boolean fd1 = false;
    PFont font;

    enum GameState {
        OVER, RUNNING,START,RUNNING2,RUNNING3,IDLE
    }

    static GameState currentState;

    public static void main(String[] args) {
        PApplet.main("DiletvsTelid");
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        //load ALL THE ASSETS XD
        noStroke();
        for(int i = 0; i<= 39;i++){
            DiletRIP.add(loadImage("Images/Dilet_RIP/sprite_" + i + ".png"));
            DiletRIP.get(i).resize(64, 64);
        }
        for(int i = 0; i<= 58;i++){
            TelidRIP.add(loadImage("Images/Telid_RIP/sprite_" + i + ".png"));
            TelidRIP.get(i).resize(64, 64);
        }
        for (int i = 1; i <= 34; i++){

            maptAnim.add(loadImage("Images/Arena1/sprite_" + i + ".png"));
            maptAnim.get(i - 1).resize(800, 800);
        }
        for (int i = 1; i <= 2; i++){

            mapvAnim.add(loadImage("Images/Arena2/sprite_" + i + ".png"));
            mapvAnim.get(i - 1).resize(800, 800);
        }
        for (int i = 1; i <= 2; i++){

            mapzAnim.add(loadImage("Images/Arena3/sprite_" + i + ".png"));
            mapzAnim.get(i - 1).resize(800, 800);
        }
        for (int i=1;i<=39;i++){
            preAnim.add(loadImage("Images/Precreds/sprite_" + i + ".png"));
            preAnim.get(i - 1).resize(800, 800);
        }
        diletSprite = loadImage("Images/DIlet.png");
        diletSprite.resize(64,64);
        telidSprite = loadImage("Images/Telid.png");
        telidSprite.resize(64,64);
        surface.setTitle("Dilet Combat Tester");
        font = createFont("Arial",32,true);
        currentState=GameState.IDLE;
        for (int i = 0; i < 2; i++) {
            DiletLegAnim.add(loadImage("Images/Dilet_Legs/sprite_" + i + ".png"));
            DiletLegAnim.get(i).resize(64, 64);
            DiletLPawAnim.add(loadImage("Images/Dilet_SLeft/sprite_" + i + ".png"));
            DiletLPawAnim.get(i).resize(64, 64);
            DiletRPawAnim.add(loadImage("Images/Dilet_SRright/sprite_" + i + ".png"));
            DiletRPawAnim.get(i).resize(64, 64);
        }
        for(int i = 0; i < 4; i ++){
            Dilet_DSlap.add(loadImage("Images/Dilet_DSlap/sprite_"+i+".png"));
            Dilet_DSlap.get(i).resize(64,64);
            Dilet_WS2.add(loadImage("Images/Dilet_WS2/sprite_"+i+".png"));
            Dilet_WS2.get(i).resize(64,64);
        }
        Dilet_Left = loadImage("Images/Dilet_LBody/sprite_0.png");
        Dilet_Left.resize(64, 64);
        Dilet_Right = loadImage("Images/Dilet_RBody/sprite_0.png");
        Dilet_Right.resize(64, 64);
        Dilet_RP = loadImage("Images/Dilet_PawStillR/sprite_0.png");
        Dilet_RP.resize(64, 64);
        Dilet_LP = loadImage("Images/Dilet_PawStillL/sprite_0.png");
        Dilet_LP.resize(64, 64);
        Dilet_RB = loadImage("Images/Dilet_RBody/sprite_0.png");
        Dilet_RB.resize(64, 64);
        Dilet_LB = loadImage("Images/Dilet_LBody/sprite_0.png");
        Dilet_LB.resize(64, 64);
        Dilet_LS = loadImage("Images/DIlet.png");
        Dilet_LS.resize(64,64);
        Dilet_UB = loadImage("Images/Dilet_UBody/sprite_0.png");
        Dilet_UB.resize(64,64);
        Dilet_DB = loadImage("Images/Dilet_DBody/sprite_0.png");
        Dilet_DB.resize(64,64);
    }

    public void draw() {
        if(millis() - startTime >= 1000/8) {
            startTime = millis();
            counter++;
        }

        switch(currentState){
            case START:
                drawStart();
                break;
            case RUNNING:
                background(maptAnim.get(counter%34));
                drawPlayer();
                if (Telid.HP < 1){
                    currentState = GameState.OVER;
                }
                if (Dilet.HP < 1){
                    currentState = GameState.OVER;
                }
                if(millis() - startTime >= 1000/8){
                    startTime = millis();
                    counter++;
                }
                break;
            case RUNNING2:
                background(mapvAnim.get(counter%2));
                drawPlayer();
                if (Telid.HP < 1){
                    currentState = GameState.OVER;
                }
                if (Dilet.HP < 1){
                    currentState = GameState.OVER;
                }
                if(millis() - startTime >= 1000/8){
                    startTime = millis();
                    counter++;
                }
                break;
            case RUNNING3:
                background(mapzAnim.get(counter%2));
                drawPlayer();
                if (Telid.HP < 1){
                    currentState = GameState.OVER;
                }
                if (Dilet.HP < 1){
                    currentState = GameState.OVER;
                }
                if(millis() - startTime >= 1000/8){
                    startTime = millis();
                    counter++;
                }
                break;
            case OVER:
                //anim
                drawScore();
                break;
            case IDLE:
                background(preAnim.get(counter%39));
                if(millis() > 5000){
                    background(0,0,0);
                    if(millis() > 7500){
                        currentState=GameState.START;
                    }
                }
                break;
        }


    }
    public void drawPlayer(){
        player1X = constrain(player1X, 0, width);
        player1Y = constrain(player1Y, 0, height);
        imageMode(3);
        image(diletSprite,player1X, player1Y);
        diletSprite.resize(64,64);
        if(fr1){
            if (slapping){
                imageMode(CENTER);
                image(DiletRPawAnim.get(counter % 2), player1X, player1Y);

            }
            else{
                imageMode(CENTER);
                image(Dilet_RP,player1X,player1Y);
            }
            if(right1 && !up1 && !down1){
                fu1 = false;
                fd1 = false;
                imageMode(CENTER);
                image(DiletLegAnim.get(counter%2),player1X,player1Y);
                nmc = millis();
                f = 0;
            }
            else{
                f = millis()-nmc;
                imageMode(CENTER);
                image(DiletLegAnim.get(0),player1X,player1Y);
            }
            imageMode(CENTER);
            image(Dilet_RB,player1X,player1Y);
            if(f > 5000){
                fr1 = false;
                fl1 = false;
            }
        }
        if(fl1){
            if (slapping){
                imageMode(CENTER);
                image(DiletLPawAnim.get(counter % 2), player1X, player1Y);
            }
            else{
                imageMode(CENTER);
                image(Dilet_LP,player1X,player1Y);
            }
            if(left1 && !up1 && !down1){
                fu1 = false;
                fd1 = false;
                imageMode(CENTER);
                image(DiletLegAnim.get(counter%2),player1X,player1Y);
                nmc = millis();
                f = 0;
            }
            else{
                imageMode(CENTER);
                image(DiletLegAnim.get(0),player1X,player1Y);
                f = millis()-nmc;

            }
            imageMode(CENTER);
            image(Dilet_LB,player1X,player1Y);
            if(f > 5000){
                fr1 = false;
                fl1 = false;
            }
        }
        if(!fr1 && !fl1){
            imageMode(3);
            image(diletSprite,player1X, player1Y);
            diletSprite.resize(64,64);
        }
        if(fu1){
            if(up1 && !right1 && !left1){
                fr1 = false;
                fl1 = false;
                imageMode(CENTER);
                image(Dilet_WS2.get(counter%4),player1X,player1Y);
            }
            else{
                imageMode(CENTER);
                image(Dilet_WS2.get(0),player1X,player1Y);
            }
            imageMode(CENTER);
            image(Dilet_UB,player1X,player1Y);
        }
        if(fd1){
            if(down1 && !right1 && !left1){
                fr1 = false;
                fl1 = false;
                imageMode(CENTER);
                image(Dilet_WS2.get(counter%4),player1X,player1Y);
            }
            else{
                imageMode(CENTER);
                image(Dilet_WS2.get(0),player1X,player1Y);
            }
            imageMode(CENTER);
            image(Dilet_DB,player1X,player1Y);
            if(slapping){
                imageMode(CENTER);
                image(Dilet_DSlap.get(counter%4),player1X,player1Y);
            }
            else{
                imageMode(CENTER);
                image(Dilet_DSlap.get(0),player1X,player1Y);
            }
        }




        noStroke();
        fill (255,0,0);
        rectMode(3);
        rect(player1X,player1Y-20,25,7);
        fill (0,255,0);
        rectMode(3);
        rect(player1X,player1Y-20,Dilet.HP/2,7);
        //p2
        imageMode(3);
        player2X = constrain(player2X, 0, width);
        player2Y = constrain(player2Y, 0, height);
        image(telidSprite,player2X, player2Y);
        telidSprite.resize(32,32);
        fill (255,0,0);
        rectMode(3);
        rect(player2X,player2Y-20,25,7);
        fill (0,255,0);
        rectMode(3);
        rect(player2X,player2Y-20,Telid.HP/3,7);
        if (up1) {
            player1Y -= 1.5;
        }
        if (left1) {
            player1X -= 1.5;
        }
        if (right1) {
            player1X += 1.5;
        }
        if (down1) {
            player1Y += 1.5;
        }
        if (up2) {
            player2Y -= 1.5;
        }
        if (left2) {
            player2X -= 1.5;
        }
        if (right2) {
            player2X += 1.5;
        }
        if (down2) {
            player2Y += 1.5;
        }
        if(!Dilet.isAlive()){
            Telidwins = true;
            nmc = millis();
            if(Dilet.HP < 1){
                if(millis()-nmc < 5000){
                    image(DiletRIP.get(counter%39),player1X,player1Y);
                }
            }
            currentState=GameState.OVER;
        }
        if(!Telid.isAlive()){
            Diletwins = true;
            nmc = millis();
            if(Telid.HP < 1){
                if(millis()-nmc < 7300){
                    image(TelidRIP.get(counter%58),player2X,player2Y);
                }
            }
            currentState=GameState.OVER;

        }
    }



    public void keyPressed() {
        if (key == 'w') {
            up1 = true;
            fu1 = true;
            fd1 = false;

        }
        if (key == 'a') {
            left1 = true;
            fl1=true;
            fr1=false;
        }
        if (key == 's') {
            down1 = true;
            fu1 = false;
            fd1 = true;
        }
        if (key == 'd') {
            right1 = true;
            fl1=false;
            fr1=true;
        }
        if (key == 'i') {
            up2 = true;
        }
        if (key == 'j') {
            left2 = true;
            player2left = true;
        }
        if (key == 'k') {
            down2 = true;
        }
        if (key == 'l') {
            right2 = true;
            player2right = true;
        }
        if (key == 'z'){
            //hit detection works!!!!!! :D
            slapping=true;
            if(millis() - slapTime >= 1000/2){
                slapTime = millis();
                slapCharged = true;

            }
            if(fl1){
               if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X>player2X) && slapCharged){
                    Dilet.attack(Telid);
                    slapCharged = false;
                }
            }
            else if(fr1){
                if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X<player2X) && slapCharged){
                    Dilet.attack(Telid);
                    slapCharged = false;
                }
            }
            else if(fd1){
                if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X<player2X) && slapCharged){
                    Dilet.attack(Telid);
                    slapCharged = false;
                }
            }
            else if(fu1){
                if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X<player2X) && slapCharged){
                    Dilet.attack(Telid);
                    slapCharged = false;
                }
            }
        }
        if (key=='m'){
            if(millis() - kickTime >= 1000/2){
                slapTime = millis();
                kickCharged = true;

            }
            if(player2left = true){
                if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X<player2X) && kickCharged){
                    Telid.attack(Dilet);
                    kickCharged = false;
                }
            }
            if(player2right = true){
                if(dist(player1X,player1Y,player2X,player2Y)<35 && (player1X>player2X) && kickCharged){
                    Telid.attack(Dilet);
                    kickCharged = false;
                }
            }

        }

    }
    public void keyReleased() {
        if (key == 'w') {
            up1 = false;
        }
        if (key == 'a') {
            left1 = false;
            nmc = millis();
        }
        if (key == 's') {
            down1 = false;
        }
        if (key == 'd') {
            right1 = false;
            nmc = millis();

        }
        if (key == 'i') {
            up2 = false;
        }
        if (key == 'j') {
            left2 = false;
            player2left = false;
        }
        if (key == 'k') {
            down2 = false;
        }
        if (key == 'l') {
            right2 = false;
            player2right = false;
        }
        if(key=='z'){
            slapping=false;
        }

    }
    public void drawScore() {
        noStroke();
        fill(235,123,143);
        rect(0,0,1600,1600);
        textFont(font);
        fill(255, 255, 255);
        textAlign(CENTER);
        text("Game Over",400,100);
        if(Diletwins){
            text("Dilet Wins!",400,200);
        }
        if (Telidwins){
            text("Telid wins!",400,200);
        }
        fill(0,0,0);
        rectMode(CENTER);
        rect(200,300,150,50);
        rect(200,225,150,50);
        rect(200,150,150,50);

        fill(255,255,255);
        textAlign(CENTER);
        text("Mill Rematch",200,150);
        text("Farm Rematch",200,225);
        text("Factory Rematch",200,300);
        image(diletSprite,50,50);
        image(telidSprite,330,50);


    }
    public void drawStart() {
            noStroke();
            fill(235,123,143);
            rect(0,0,1600,1600);
            textFont(font);
            fill(255, 255, 255);
            textAlign(CENTER);
            text("Dilet vs Telid",200,100);
            fill(0,0,0);
            rectMode(CENTER);
            rect(200,300,150,50);
            rect(200,225,150,50);
            rect(200,150,150,50);

            fill(255,255,255);
            textAlign(CENTER);
            text("Mill",200,150);
            text("Farm",200,225);
            text("Factory",200,300);
            noStroke();

            image(diletSprite,50,50);
            diletSprite.resize(50,50);
            image(telidSprite,310,50);
            telidSprite.resize(50,50);


    }

    public void mousePressed() {
        switch (currentState) {
            case START:
                if (mouseX > (125) && mouseX < (275) && mouseY > 275 && mouseY < 325) {
                    currentState = GameState.RUNNING;
                }
                if (mouseX > (125) && mouseX < (275) && mouseY > 200 && mouseY < 250) {
                    currentState = GameState.RUNNING2;
                }
                if (mouseX > (125) && mouseX < (275) && mouseY > 125 && mouseY < 175) {
                    currentState = GameState.RUNNING3;
                }
            case OVER:
                if (mouseX > (125) && mouseX < (275) && mouseY > 275 && mouseY < 325) {
                    player1X = 40;
                    player1Y = 40;
                    player2X = 360;
                    player2Y = 360;
                    Dilet.HP = 50;
                    Telid.HP = 75;
                    Diletwins = false;
                    Telidwins = false;
                    background(maptAnim.get(counter%34));
                    currentState = GameState.RUNNING;
                }
                if (mouseX > (125) && mouseX < (275) && mouseY > 200 && mouseY < 250) {
                    player1X = 40;
                    player1Y = 40;
                    player2X = 360;
                    player2Y = 360;
                    Dilet.HP = 50;
                    Telid.HP = 75;
                    Diletwins = false;
                    Telidwins = false;
                    background(maptAnim.get(counter%2));
                    currentState = GameState.RUNNING2;
                }
                if (mouseX > (125) && mouseX < (275) && mouseY > 125 && mouseY < 175) {
                    player1X = 40;
                    player1Y = 40;
                    player2X = 360;
                    player2Y = 360;
                    Dilet.HP = 50;
                    Telid.HP = 75;
                    Diletwins = false;
                    Telidwins = false;
                    fr1=false;
                    fl1=false;
                    background(maptAnim.get(counter%2));
                    currentState = GameState.RUNNING3;
                }

                break;

        }

    }


//NOTE: Still very much WIP - DiletDev

}
