package gui;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

public class ChatThread extends Thread{
    private JTextArea textArea;
    private DataInputStream dis;

    public ChatThread(JTextArea textArea, DataInputStream dis) {
        this.textArea = textArea;
        this.dis = dis;
    }

    @Override
    public void run() {
        while (true){
            try {
                String data = dis.readUTF();
                if(!data.equals("")){
                    textArea.setText(textArea.getText()+"\nComponent >>>"+data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
