
package chatclient;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.border.Border;
import java.lang.*;
import java.util.HashSet;
import java.util.Set;
public class ChatClient extends JFrame{
    
    static String name,pass;
    static ChatClient win ,win2,accwin,dis,gamewin;
    static int guestnum=1;
    static JPanel p1,P1,accp;
    static JLabel l1,L1,L2,lo,lo2,LUS,LPASS,accl1,accl2,accl3,accl4;
    static JTextArea a1,ao;
    static JTextField F1,accf1,accf2,accf3;
    static JPasswordField F2;
    static JTextField a2;  
    static JButton B1,b1,cls,music,stop,acc,accb2,back,game,guest;
    static boolean cq=true;
    private static BufferedReader in;
    static PrintWriter out;
    static String x;
    static Socket c;
    static File f=new File("Music\\\\theScript.wav");
    static AudioInputStream audios;
    static Clip clip;
    
    
 public static void frame(){
      P1=new JPanel();
 
     P1.setLayout(new FlowLayout(FlowLayout.LEFT));
     
     L1 = new JLabel("Welcome to the Server!");
     L1.setSize(120,18);
     L1.setLocation(10,10);
     L1.setFont(new Font("Arial",Font.BOLD,18));
     P1.add(L1);
     
     L2 = new JLabel("Enter your credentials ");
     L2.setSize(150,18);
     L2.setLocation(10,40);
     L2.setFont(new Font("Arial",Font.BOLD,18));
     P1.add(L2);
     
     LUS = new JLabel("Username: ");
     LUS.setFont(new Font("Arial",Font.BOLD,18));
     P1.add(LUS);
     
     F1 = new JTextField("");
     F1.setLocation(120,42);
     F1.setPreferredSize(new Dimension(200,20));
     F1.setFont(new Font("Arial",Font.BOLD,14));
     F1.setEditable(true);
     P1.add(F1);

     
     LPASS = new JLabel("Password: ");
     LPASS.setFont(new Font("Arial",Font.BOLD,18));
     P1.add(LPASS);
     
     F2 = new JPasswordField("");
     F2.setLocation(120,42);
     F2.setPreferredSize(new Dimension(200,20));
     F2.setFont(new Font("Arial",Font.BOLD,14));
     F2.setEditable(true);
     P1.add(F2);


     B1 = new JButton("Login");
     B1.setSize(120,30);
     B1.setLocation(90,70);
 
     B1.addActionListener(new ActionListener(){
         
    
     public void actionPerformed(ActionEvent e){
       String result ="" ;
     name= F1.getText();
     pass=String.valueOf(F2.getPassword());
     if(name.equals("")||pass.equals("")){
     JOptionPane.showMessageDialog(win, "You have left your name or password blank","Warning",JOptionPane.ERROR_MESSAGE);
     }
     else{
     out.println("LOGIN");
     
out.println(name);
 out.println(pass);
 try{ 
 result=in.readLine();
 
   }catch(IOException t){
     t.printStackTrace();
     }
         System.out.println(result);
 if(result.equals("Error")){
JOptionPane.showMessageDialog(win, "Wrong credentials","Error",JOptionPane.ERROR_MESSAGE);

 }
 else if(result.equals("Success")){
   win.dispose();
     newframe(name);
      new inputTaker().start();
 }
 else if(result.equals("online")){
 
 JOptionPane.showMessageDialog(win, "This account is already online","Warning",JOptionPane.WARNING_MESSAGE);
 }
     }
    
     
   
     }

     
     });
     P1.add(B1);
 
     acc = new JButton("Create Account");
     acc.setSize(120,30);
     acc.setLocation(90,70);
     acc.addActionListener(new ActionListener(){
         
     public void actionPerformed(ActionEvent e){
         
         AccFrame();
     }

     
     });
     P1.add(acc);
 
 
        guest = new JButton("Login as Guest");
     guest.setSize(120,30);
     guest.setLocation(90,70);
     guest.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e){
    out.println("GUEST");
    String res="";
    try{
    res=in.readLine();
    }catch(IOException r){}
    if(res.equals("good")){
            win.dispose();
     newframe(name);
      new inputTaker().start();
    }
     }
     });
     P1.add(guest);
 

 }
 
 
public static void AccFrame(){

 accwin = new ChatClient();
 accp = new JPanel();
         
accwin.setLayout(new FlowLayout(FlowLayout.LEFT));

accwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
accwin.setVisible(true);
accwin.setContentPane(accp);
accwin.setSize(370,180);
accwin.setLocation(300,300);
accwin.setResizable(false);

accl1 = new JLabel("                           Create Account                   ");
accl1.setFont(new Font("Arial",Font.BOLD,20));
accp.add(accl1);

accl2 = new JLabel(" Username: ");
accl2.setFont(new Font("Arial",Font.BOLD,16));
accp.add(accl2);

accf1 = new JTextField("");
accf1.setPreferredSize(new Dimension(200,20));
accp.add(accf1);

accl3 = new JLabel(" Password: ");
accl3.setFont(new Font("Arial",Font.BOLD,16));
accp.add(accl3);

accf2 = new JTextField("");
accf2.setPreferredSize(new Dimension(200,20));
accp.add(accf2);


      accb2= new JButton("Confirm");
       accb2.addActionListener(new ActionListener(){     
       public void actionPerformed(ActionEvent IO){
       
           String newuser,newpass;
           newuser=accf1.getText();
           newpass=accf2.getText();
           if(newuser.equals("")||newpass.equals("")){
           
            JOptionPane.showMessageDialog(accwin, "You have left your name or password blank","Warning",JOptionPane.ERROR_MESSAGE);
           }
           else{
           out.println("CA");
           out.println(newuser);
           out.println(newpass);
            try{
           String result = in.readLine();
           if(result.equals("Symbols")){
               JOptionPane.showMessageDialog(accwin, "Username can only contain numbers and letters!","Error",JOptionPane.WARNING_MESSAGE);
           }
           else if(result.equals("TooBig")){
               JOptionPane.showMessageDialog(accwin, "Username cant be larger than 32 characters","Error",JOptionPane.WARNING_MESSAGE);
           }
           else if(result.equals("AlreadyExists")){
               JOptionPane.showMessageDialog(accwin, "Username already exists!","Error",JOptionPane.ERROR_MESSAGE);
           }
           else if(result.equals("OK")){
               JOptionPane.showMessageDialog(accwin, "Your account has succesfully created!","Success",JOptionPane.INFORMATION_MESSAGE);
               accwin.dispose();
           }
            }catch(Exception d){
                        
       }
           } 
       }
       });
       accp.add(accb2);
       
       
          back= new JButton("Back");
       back.addActionListener(new ActionListener(){     
       public void actionPerformed(ActionEvent IOs){
       accwin.dispose();
         
       
            
       }
       });
          accp.add(back);
}
 
  
 public static void newframe(String u){
      Border b = BorderFactory.createLineBorder(Color.ORANGE, 2);
      Border b2= BorderFactory.createLineBorder(Color.RED ,2);
      p1 = new JPanel();
         
       p1.setLayout(new FlowLayout(FlowLayout.LEFT));
      win2 = new ChatClient();
 
      win2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      win2.setVisible(true);
      win2.setTitle("ChatClient");
      win2.setContentPane(p1);
      win2.setSize(750,550);
      win2.setLocation(400,200);
      win2.setResizable(false);
      win2.addWindowListener(new WindowAdapter(){
      
      public void windowClosing(WindowEvent e){ 
          out.close();
          try{
      in.close();
      c.close();
          }catch(IOException t){
          
          }
      
      }
      
      
      });
    
      

        lo = new JLabel("Hello Client!                                                                                                                      "); 
        lo.setFont(new Font("Arial", Font.PLAIN, 16));  
        lo.setForeground(Color.BLACK);
        
       lo2= new JLabel("Online Users: 0/100");
       lo2.setFont (new Font("Arial", Font.BOLD, 17));
       lo2.setForeground(Color.BLACK);
       
       a1= new JTextArea(25,50);
       a1.setEditable(false);
       a1.setBorder(b2);
        
       b1 = new JButton("Send");
       b1.addActionListener(new ActionListener(){
       
       public void actionPerformed(ActionEvent e){
  String stream= a2.getText();
    
        out.println(stream);
        a2.setText("");
       
       }
   
       });
       
       cls= new JButton("Clear Screen");
       cls.addActionListener(new ActionListener(){     
       public void actionPerformed(ActionEvent IO){
       a1.setText(null);
       }
       });
       
             game= new JButton("Play Math Game");
       game.addActionListener(new ActionListener(){     
       public void actionPerformed(ActionEvent IO){
      PlayGame();
       }
       });
       
       
               try{
      audios = AudioSystem.getAudioInputStream(f);
      clip=AudioSystem.getClip();
      clip.open(audios);
        }catch(Exception d){
                        
       }
       music = new JButton("Play Music");
       
       music.addActionListener(new ActionListener(){    
       public void actionPerformed(ActionEvent e){
       clip.start();

       }

       });
       
       stop = new JButton("Stop Music");
      stop.addActionListener(new ActionListener(){
       
       public void actionPerformed(ActionEvent e){
          clip.stop();
       }

       });
        
       
       
       a2 = new JTextField("Type here...                                                                                                                                 ");
       a2.setEditable(true);
       a2.setPreferredSize(new Dimension(648,20));
       a2.setFont(new Font("Arial", Font.PLAIN, 16));
       a2.addMouseListener(new MouseAdapter(){
       public void mouseClicked(MouseEvent e){
        a2.addKeyListener(new KeyAdapter(){
       
            
       public void keyPressed(KeyEvent k){
       if(k.getKeyCode()==KeyEvent.VK_ENTER){
       String stream= a2.getText();
    
        out.println(stream);
        a2.setText(null);
       }
       
       }
       
       }); 
    
       if(cq==true){
       a2.setText(null);
       cq=false;
       }
       }
       });

       ao= new JTextArea(18,10);
       ao.setEditable(false);
       ao.setBorder(b);
       ao.setFont(new Font("Arial",Font.ITALIC,18));
ao.setBackground(Color.ORANGE);
      p1.add(lo);
      p1.add(lo2);
      p1.add(a1);     
      p1.add(new JScrollPane(a1));
      p1.add(ao);
      p1.add(new JScrollPane(ao));
      p1.add(a2);
      p1.add(b1);
      p1.add(cls);
      p1.add(music);
      p1.add(stop);
    //  p1.add(game);
  

 }
 
 public static void startCommunication()throws IOException{

     try{
   
  c= new Socket("31.216.99.191",6666);

    frame();
      win = new ChatClient();
      win.setContentPane(P1);
      win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      win.setVisible(true);
     
      win.setTitle("ChatClient");
      win.setSize(245,270);
      win.setLocation(500,200);
      win.setResizable(false);
 out= new PrintWriter(c.getOutputStream(),true);

 in = new BufferedReader(new InputStreamReader(c.getInputStream()));

 
   
 }catch(IOException e){
 
}
   
 }
 public static void PlayGame(){
 gamewin=new ChatClient();
 gamewin.setBackground(Color.yellow);
 gamewin.setVisible(true);
 gamewin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 gamewin.setTitle("Math Game!");
 
 
 }
 public static class inputTaker extends Thread{

 public void run(){
   boolean ch=true;
   String us="";
   while(true){
    try{
         x=in.readLine();
         if(x.equals("%%%UPDATELIST%%%")){
             int count=0;
             ao.setText("");
             us=in.readLine();
         while(!us.equals("%%%END%%%")){
         count++;
         ao.append(" - "+us+"\n");
         us=in.readLine();
         }
         lo2.setText("Online Users: "+ count+"/100");
         x=in.readLine();
         }
     if(!a1.getText().equals("")){
     a1.append("\n");
     }
       a1.append(x);
   
 } catch (IOException e) {
            e.printStackTrace();
 }
 }
 }
 }
 
 public static void main(String[] args) {
  
      try{
      startCommunication();
      }catch(IOException r){
      
      }
    }
    
}
