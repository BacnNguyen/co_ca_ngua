package gui;

import model.Dice;
import model.Horse;
import model.Map;
import model.Position;
import utils.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GameManager {
    public ArrayList<Map> arrMap = new ArrayList<>();
    public static int START_RED = 0;
    public static int START_GREEN = 12;
    public static int START_BLUE = 24;
    public static int START_YELLOW = 36;

    private int[] startPos = {
      START_RED,START_GREEN,START_BLUE,START_YELLOW
    };
    public int index_horse;



    private Position[] positions = {
            //1 - 48
            new Position(0,0, 350),

            new Position(1,0, 450), new Position(2,50, 450), new Position(3,100, 450),
            new Position(4,150, 450), new Position(5,200, 450), new Position(6,250, 450),

            new Position(7,250, 500), new Position(8,250, 550), new Position(9,250, 600),
            new Position(10,250, 650), new Position(11,250, 700),

            new Position(12,350, 700),
            new Position(13,450, 700), new Position(14,450, 650), new Position(15,450, 600),
            new Position(16,450, 550), new Position(17,450, 500), new Position(18,450, 450),

            new Position(19,500, 450), new Position(20,550, 450), new Position(21,600, 450),
            new Position(22,650, 450), new Position(23,700, 450),

            new Position(24,700, 350),

            new Position(25,700, 250), new Position(26,650, 250), new Position(27,600, 250),
            new Position(28,550, 250), new Position(29,500, 250), new Position(30,450, 250),

            new Position(31,450, 200), new Position(32,450, 150), new Position(33,450, 100),
            new Position(34,450, 50), new Position(35,450, 0),

            new Position(36,350, 0),
            new Position(37,250, 0), new Position(38,250, 50), new Position(39,250, 100),
            new Position(40,250, 150), new Position(41,250, 200), new Position(42,250, 250),

            new Position(43,200, 250), new Position(44,150, 250),
            new Position(45,100, 250), new Position(46,50, 250), new Position(47,0, 250),

            //home red 48-49-50-51
            new Position(48,50, 50), new Position(49,50, 150),
            new Position(50,150, 50), new Position(51,150, 150),

            //home blue 52-53-54-55
            new Position(52,50, 550), new Position(53,50, 650),
            new Position(54,150, 550), new Position(55,150, 650),

            //home green 56-57-58-59
            new Position(56,550, 550), new Position(57,550, 650),
            new Position(58,650, 550), new Position(59,650, 650),

            //home yellow 60-61-62-63
            new Position(60,550, 50), new Position(61,550, 150),
            new Position(62,650, 50), new Position(63,650, 150),

            //special red 64-65-66-67-68-69
            new Position(64,50, 350), new Position(65,100, 350), new Position(66,150, 350),
            new Position(67,200, 350), new Position(68,250, 350), new Position(69,300, 350),

            //special green 70-71-72-73-74-75
            new Position(70,350, 650), new Position(71,350, 600), new Position(72,350, 550),
            new Position(73,350, 500), new Position(74,350, 450), new Position(75,350, 400),

            //special blue 76-77-78-79-80-81
            new Position(76,650, 350), new Position(77,600, 350), new Position(78,550, 350),
            new Position(79,500, 350), new Position(80,450, 350), new Position(81,400, 350),

            //special yellow 82-83-84-85-86-87
            new Position(82,50, 350), new Position(83,100, 350), new Position(84,150, 350),
            new Position(85,200, 350), new Position(86,250, 350), new Position(87,300, 350)
    };

    public Vector<Horse> red_horses = new Vector<>();
    public Vector<Horse> green_horses = new Vector<>();
    public Vector<Horse> blue_horses = new Vector<>() ;
    public Vector<Horse> yellow_horses = new Vector<>();

    Vector<Vector<Horse>> typeHorse = new Vector<>();



    private Dice dice;
    private int timeDice = -1;
    private int timeHorse = -1;
    private int[] NUM_HORSE_SAFE = {0,0,0,0};
    private int distance= 1;
    private int number_player = -1;
    public void initGame() {
        arrMap = MapLoader.readMap("map.txt");

        //init red horse
        red_horses.add(new Horse(positions[48],1));
        red_horses.add(new Horse(positions[49],1));
        red_horses.add(new Horse(positions[50],1));
        red_horses.add(new Horse(positions[51],1));

        //init blue horse
        green_horses.add(new Horse(positions[52],2));
        green_horses.add(new Horse(positions[53],2));
        green_horses.add(new Horse(positions[54],2));
        green_horses.add(new Horse(positions[55],2));
        //init green horse

        blue_horses.add(new Horse(positions[56],3));
        blue_horses.add(new Horse(positions[57],3));
        blue_horses.add(new Horse(positions[58],3));
        blue_horses.add(new Horse(positions[59],3));
        //init yellow horse

        yellow_horses.add(new Horse(positions[60],4));
        yellow_horses.add(new Horse(positions[61],4));
        yellow_horses.add(new Horse(positions[62],4));
        yellow_horses.add(new Horse(positions[63],4));


        typeHorse.add(red_horses);
        typeHorse.add(green_horses);
        typeHorse.add(blue_horses);
        typeHorse.add(yellow_horses);

        dice = new Dice(1);
    }

    public void RollDice(int number_player){
        this.number_player = number_player;
        index_horse = chooseHorse();
        Horse h= typeHorse.get(number_player).get(index_horse);
        if(h.getPosition().getIndex()>47) typeHorse.get(number_player).get(index_horse).setPosition(positions[startPos[number_player]]);
        Random random = new Random();
        int dice_number = random.nextInt(100)%6+1;
        System.out.println("Roll Dice :"+dice_number);
        dice.setNumber(dice_number);
        timeDice = 15;
        timeHorse = 20;
        distance = 0;
    }


    public boolean checkHasMoved(){
        if(distance==dice.getNumber()) return true;
        else return false;
    }
    public void AI(int number_player,int num_dice,int index_horse){
        this.number_player = number_player;
        this.index_horse = index_horse;
        Horse h= typeHorse.get(number_player).get(index_horse);
        if(h.getPosition().getIndex()>47) typeHorse.get(number_player).get(index_horse).setPosition(positions[startPos[number_player]]);
        dice.setNumber(num_dice);
        timeDice = 15;
        timeHorse = 20;
        distance = 0;
    }


    public void drawDice(Graphics2D g2d){
       if(timeDice >0){
           Random random = new Random();
           int n = random.nextInt(100)%6+1;
           new Dice(n).draw(g2d);
           timeDice--;
       }
       else dice.draw(g2d);
    }

    public int chooseHorse(){
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if(!typeHorse.get(number_player).get(i).isSafe()){
                arr.add(i+"");
            }
        }

        String[] option = new String[arr.size()];
        option = arr.toArray(option);
        String x = (String) JOptionPane.showInputDialog(null,
                "Which Horse Would You Like To Move?",
                "Choose Horse", JOptionPane.QUESTION_MESSAGE, null,
                option, null);
        return Integer.parseInt(x);
    }

    public void move(int step){
        switch (number_player){
            case 0:
                int pos = typeHorse.get(number_player).get(index_horse).getPosition().getIndex();
                if(pos<47) pos +=step;
                else if(!typeHorse.get(number_player).get(index_horse).isSafe()) {
                    pos = 69-NUM_HORSE_SAFE[number_player];
                    NUM_HORSE_SAFE[number_player]+=1;
                    typeHorse.get(number_player).get(index_horse).setSafe(true);
                }
                typeHorse.get(number_player).get(index_horse).setPosition(positions[pos]);
                break;
            case 1:
                int pos1 = typeHorse.get(number_player).get(index_horse).getPosition().getIndex();
                if((pos1>=12&&pos1<47) || (pos1>=0&&pos1<11) ) {
                    pos1+= step;
                }
                else if (pos1==47){
                    pos1=0;
                }
                else if(pos1==11){
                    pos1 = 75-NUM_HORSE_SAFE[number_player];
                    NUM_HORSE_SAFE[number_player]+=1;
                    typeHorse.get(number_player).get(index_horse).setSafe(true);
                }
                typeHorse.get(number_player).get(index_horse).setPosition(positions[pos1]);
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    public void DrawRedHorses(){
        if(distance< dice.getNumber()&& timeHorse ==0) {
            move(1);
            distance++;
        }
        else timeHorse--;
    }

//
//    public void moveBlue(){
//        int pos = blue_horses.get(index_blue).getPosition().getIndex();
//        if(pos ==47) pos = -1;
//        blue_horses.get(index_blue).setPosition(positions[pos+1]);
//    }
//
//    public void moveGreen(){
//        int pos = green_horses.get(index_green).getPosition().getIndex();
//        if(pos ==47) pos = -1;
//        green_horses.get(index_green).setPosition(positions[pos+1]);
//    }
//
//    public void moveYellow(){
//        int pos = yellow_horses.get(index_yellow).getPosition().getIndex();
//        if(pos ==47) pos = -1;
//        yellow_horses.get(index_yellow).setPosition(positions[pos+1]);
//    }

    public void draw(Graphics2D g2d) {
        for (Map m : arrMap) {
            m.draw(g2d);
        }

        if(number_player>-1) DrawRedHorses();
        drawDice(g2d);
        //draw red
        for (Horse h:red_horses) {
            h.draw(g2d);
        }


        //draw blue
        for (Horse h:blue_horses) {
            h.draw(g2d);
        }

        //draw green
        for (Horse h:green_horses) {
            h.draw(g2d);
        }

        //draw yellow
        for (Horse h:yellow_horses) {
            h.draw(g2d);
        }
    }

    public int getIndexHorse() {
        return index_horse;
    }

    public int getNumberDice() {
        return dice.getNumber();
    }
}
