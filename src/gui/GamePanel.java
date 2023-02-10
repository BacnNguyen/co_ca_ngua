package gui;

import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GamePanel extends JPanel implements Runnable, ActionListener {
    public GameManager manager = new GameManager();
    private JButton rollDice = new JButton(new ImageIcon(ImageLoader.getImage("dice.png",getClass())));
    private int number_player = 1;
    private int component = 0;
    private boolean yourturn = true;

    private int PLAYER_1 = 0;
    private int PLAYER_2 = 1;

    private Socket socket;
    private Socket socketChat;
    private ServerSocket serverSocket;
    private ServerSocket serverSocketChat;
    private boolean accepted = false;
    
    private DataInputStream dis;
    private DataOutputStream dos;
    
    private DataInputStream disChat;
    private DataOutputStream dosChat;
    
    private String ip = "localhost";
    private int port = 10000;
    private int portChat = 10001;
    private boolean unableToConnectComponent = false;
    private JTextArea textArea;
    private JTextField textField;
    private JButton btn_send;
    public GamePanel() {
        setFocusable(true);
        setLayout(null);
        rollDice.setBounds(900,300,170,170);
        add(rollDice);
        rollDice.addActionListener(this);
        manager.initGame();
        
        //add a label Chat
        JLabel label = new JLabel("Chat :");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setBounds(780,480,100,30);
        add(label);

        //add a text area
         textArea= new JTextArea();
        textArea.setBounds(780,520,380,150);
        textArea.setEditable(false);
        
        
        //scroll view
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(780,520,380,150);
        add(scrollPane);
        scrollPane.setViewportView(textArea);
        
        //add a text field and a button
        textField= new JTextField();
        btn_send= new JButton("Send");
        textField.setBounds(780,680,280,30);
        btn_send.setBounds(1070,680,90,30);

        btn_send.setFont(new Font("Arial", Font.BOLD, 15));
        add(textField);
        add(btn_send);

        btn_send.addActionListener(this);
        
        if(!connect()){
            initServer();
        }
        Thread t = new Thread(this);
        t.start();
        
        
       
    }


    public boolean connect (){
        try{
            socket = new Socket(ip,port);
            socketChat = new Socket(ip,portChat);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            
            dosChat = new DataOutputStream(socketChat.getOutputStream());
            disChat = new DataInputStream(socketChat.getInputStream());
            
            accepted = true;
            yourturn= false;
            number_player = PLAYER_2;
            component = PLAYER_1;
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: "+ip+":"+port+"|Starting server...");
            return false;
        }
        return true;
    }

    public  void  initServer() {
        try{
            serverSocket = new ServerSocket(port,8, InetAddress.getByName(ip));
            serverSocketChat = new ServerSocket(portChat,8,InetAddress.getByName(ip));
            System.out.println("Server is working at :"+ip+"-"+port);
            number_player = PLAYER_1;
            component = PLAYER_2;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            unableToConnectComponent = true;
        } catch (IOException e) {
            unableToConnectComponent = true;
            e.printStackTrace();
        }
        yourturn = true;
    }

    public void listenForClientRequest(){
        Socket socket1  = null;
        Socket socketChat = null;
        try{
            socket1 = serverSocket.accept();
            socketChat = serverSocketChat.accept();
            
            dos = new DataOutputStream(socket1.getOutputStream());
            dis = new DataInputStream(socket1.getInputStream());
            
            dosChat = new DataOutputStream(socketChat.getOutputStream());
            disChat = new DataInputStream(socketChat.getInputStream());
            
            accepted = true;
            yourturn = true;
            System.out.println("Client has joined !");
        } catch (IOException e) {
            unableToConnectComponent = true;
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
//        Image img = ImageLoader.getImage("grass.png",getClass());
//        g2d.drawImage(img,x,y,50,50,null);
        manager.draw(g2d);
    }


    @Override
    public void run() {
        if(!accepted){
            listenForClientRequest();
        }
        ChatThread chatThread = new ChatThread(textArea, disChat);
        chatThread.start();
        while (true){
            if (unableToConnectComponent){
                JOptionPane.showMessageDialog(this,"Unable communicate with component !");
                System.exit(0);
            }
            if(!yourturn) {
                rollDice.setEnabled(false);
               if(manager.checkHasMoved()){
//                   int num = new Random().nextInt(100)%6+1;
//                   manager.AI(1,num,1);
                   receiveInfo();
               }
            }
            else {
                rollDice.setEnabled(true);
            }
           
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
  

    private void sendInfo() {
        int index_horse = manager.getIndexHorse();
        int num_dice = manager.getNumberDice();

        try {
            dos.writeInt(index_horse);
            dos.writeInt(num_dice);
            System.out.println("Sent data..");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void receiveInfo() {
        try {
        	System.out.println("Receive data:");
            int index_horse = dis.readInt();
            int num_dice = dis.readInt();
            System.out.println("Index horse :"+index_horse+"-"+ num_dice);
            manager.AI(component,num_dice,index_horse);
            yourturn = true;
        } catch (IOException e) {
        	System.out.println("Exeption");
        	unableToConnectComponent = true;
            return;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource().equals(rollDice)){
            manager.RollDice(number_player);
            rollDice.setEnabled(false);
            yourturn = false;
            sendInfo();
        }
        else if(e.getSource().equals(btn_send)){
            String data = textField.getText();
            try {
                if(!data.equals("")){
                    dosChat.writeUTF(data);
                    textArea.setText(textArea.getText()+"\nYou :"+data);
                    textField.setText("");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
