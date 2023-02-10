package utils;

import model.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MapLoader {
    public static ArrayList<Map> readMap(String name) {
        ArrayList<Map> arr = new ArrayList<>();
        try {
            File file = new File("src/map/" + name);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            int i = 0;
            String line = reader.readLine();
            while (line != null) {
                for (int j = 0; j < line.length(); j++) {
                    int bit = -1;
                    if(line.charAt(j)!='\t'&&line.charAt(j)!='a'&&line.charAt(j)!='b')  bit = Integer.parseInt(line.charAt(j) + "");
                    if (bit > -1) {
                        int x = j * 50;
                        int y = i * 50;
                        Map m = new Map(x, y, bit);
                        arr.add(m);
                    }
                }
                i++;
                line = reader.readLine();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return arr;
    }
}
