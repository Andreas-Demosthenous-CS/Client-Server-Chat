
package chatserver;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.border.Border;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.lang.*;
public class ChatServer extends JFrame{
    
//variables  
    
    static int  maxNumberOfClients = 100,cnt=0,guestnum=1;
    static JPanel p1;   
    static JLabel lo,lo2;
    static JButton b1,cls,music,stop;
    static JTextArea a1,ao;
    static JTextField a2;
    static JScrollPane js;
    static ChatServer win;
    static ServerSocket s;
    static boolean Ready[]= new boolean[maxNumberOfClients],ch=true,ch2=true;
    static BufferedReader in[] = new BufferedReader[maxNumberOfClients];
    static PrintWriter out[]= new PrintWriter[maxNumberOfClients];  
    static Socket c[]= new Socket[maxNumberOfClients];
    static String users[]= new String[maxNumberOfClients];
    static File f=new File("Music\\\\theScript.wav");
    static AudioInputStream audios;
    static Clip clip;
    //class's constructor that initializes the user's environment(window).
    public ChatServer(){    
        
        
       Border b = BorderFactory.createLineBorder(Color.ORANGE, 2);
       Border b2= BorderFactory.createLineBorder(Color.RED ,2);
       
       
       p1 = new JPanel();
       p1.setLayout(new FlowLayout(FlowLayout.LEFT));
  

      a1= new JTextArea(25,50);
      a1.setLineWrap(true);
      a1.setEditable(false);
      a1.setBorder(b2);
     
      
       lo= new JLabel("Hello Server!                                                                                            " );
       lo.setFont (new Font("Arial", Font.ITALIC, 17));
       lo.setForeground(Color.BLACK);
        
       lo2= new JLabel("Online Users: 0/100");
       lo2.setFont (new Font("Arial", Font.BOLD, 17));
       lo2.setForeground(Color.BLACK);
       
       ao= new JTextArea(18,10);
       ao.setEditable(false);
       ao.setBorder(b);
       ao.setFont(new Font("Arial",Font.ITALIC,18));
       
       b1 = new JButton("Send");
       b1.addActionListener(new ActionListener(){
       
       public void actionPerformed(ActionEvent e){
           String text=a2.getText();
       a2.setText("");
       String editedText="SERVER: "+text;
       
  if(!a1.getText().equals("")){
     a1.append("\n");
     }
       a1.append(editedText);
 
     
       for(int i =0;i<cnt;i++){
       out[i].println(editedText);
       }
       
       }
   
       });
       
       cls= new JButton("Clear  Screen");
       cls.addActionListener(new ActionListener(){     
       public void actionPerformed(ActionEvent e){
       a1.setText("");
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
       
       
       a2 = new JTextField("Type here...                                                                                                                                             ");
       a2.setEditable(true);
       a2.setFont(new Font("Arial", Font.PLAIN, 16));
       
       a2.addKeyListener(new KeyAdapter(){    
       public void keyPressed(KeyEvent k){
       if(k.getKeyCode()==KeyEvent.VK_ENTER){
       String text=a2.getText();
       a2.setText("");
       String editedText="SERVER: "+text;
            if(!a1.getText().equals("")){
     a1.append("\n");
     }
       a1.append(editedText);
   
     
       for(int i =0;i<cnt;i++){
           
       out[i].println(editedText);
       }
       }
       }
       });       
       
       a2.addMouseListener(new MouseAdapter(){
       
       public void mouseClicked(MouseEvent e){
         if(ch==true){
       a2.setText("");
       ch=false;
       }
       
       
       }
       
       
       });
       

       
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
    
}
   
    
    public static void startServer(){
    try{
        
    s= new ServerSocket(6666); 
    while(true){
         
    new ClientHandling(s.accept()).start();

    }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    public static void UsersLoad(){       
        int count=0;
    ao.setText("");
    for(int i =0;i<cnt;i++){
    if(!users[i].equals("")){
     count++;   
    ao.append(" - "+users[i]+ "\n");
    }    
    }  
    lo2.setText("Online Users: "+count+"/100");
    }

    
    public static void terminate(int myNum, Socket c[],PrintWriter out[],BufferedReader in[],String username){
    
    //Closing the thread since client has disconnected.
          if(!a1.getText().equals("")){
     a1.append("\n");
     }
    a1.append( username + " is now disconnected");
    users[myNum]="";

    UpdateList(out,users,username,false);
    try{
    in[myNum].close();
    out[myNum].close();
    c[myNum].close();
    }catch(IOException t){}


    
    }
    
    
    public static void UpdateList(PrintWriter out[],String users[],String username,boolean con){
     for(int i =0;i<cnt;i++){
         if(Ready[i]==true){
   if(con==true){ 
   out[i].println(username + " is now connected.");
   }
   else{
    out[i].println(username + " is now disconnected.");
   }
   out[i].println("%%%UPDATELIST%%%");
   for(int j=0;j<cnt;j++){
   if(!"".equals(users[j])){
   out[i].println(users[j]);
   }
   }
   out[i].println("%%%END%%%");
   }
     }
    
    }
    
 
    public static class ClientHandling extends Thread{
     
      int myNum=cnt;
       boolean done=false;
      String username="",text="",editedText="",pass="";

    //handling the incoming socket
    public ClientHandling(Socket soc){

       c[myNum]=soc;
    }
  public boolean AccountCheck(String u,String p){
        FileReader fr1;
       
        String user="",passw="";
        char finder='f';
        int c;
     try{  
     fr1 = new FileReader("user.txt");
      
  
     while((c=fr1.read())!=-1){
         
     finder=(char) c;
  
 if(finder=='!'){
     user="";
 while(finder!='?'){
     c=fr1.read();
     finder=(char) c;
     if(finder!='?'){
     user+=finder;
     }
 }
     
   passw="";
   while(finder!='_'){
     c=fr1.read();
     finder=(char) c;
     if(finder!='_')
     passw+=finder;
     
 }

   if(u.equals(user)&&p.equals(passw)){
   return true;
   }
     
 }
       
     }
      fr1.close();
         }catch(IOException e){
        }
     
    return false;
    }
    
  public void Login(){
      boolean tr=true;
      try{
          username= in[myNum].readLine();
        pass= in[myNum].readLine();
    }catch(IOException e){
      
      }    boolean check=AccountCheck(username,pass);  
      for(int j=0;j<cnt;j++){
       if(username.equals(users[j])){
       tr=false;
       break;
       }
       }
     
       
     if(tr==false&&check==true){
     out[myNum].println("online");
     }
      else if(check==false){
        out[myNum].println("Error");
       } 
       else{
      
           done=true;
          
        out[myNum].println("Success");
              if(!a1.getText().equals("")){
     a1.append("\n");
     }
              win.a1.append(username + " is now connected");
     
  Ready[myNum]=true;           
  users[myNum]=username;
  UsersLoad();      
 UpdateList(out,users,username,true);
       }
  
  }
  
  public void GUEST(){
      
  username="GUEST_"+guestnum;
  guestnum++; 
  out[myNum].println("good");
                if(!a1.getText().equals("")){
     a1.append("\n");
     }
  win.a1.append(username + " is now connected");
  done=true;   
  Ready[myNum]=true;           
  users[myNum]=username;
  UsersLoad();      
 UpdateList(out,users,username,true);

       }
  
  public void CA( ){
  String newu,newp;
  try{
  newu=in[myNum].readLine();
  newp=in[myNum].readLine();
  
  String result=UserValidation(newu,newp);
  out[myNum].println(result);
  }catch(IOException e){
        e.printStackTrace();
        }
  
  }
  
  public String UserValidation(String newu,String newp){
      
  FileReader fr2;
  String users="";
  int c;
  char finder;
    try{
 fr2=new FileReader("user.txt");
 
 for(int i =0;i<newu.length();i++){
 if(!(newu.charAt(i)>='0'&&newu.charAt(i)<='9'||newu.charAt(i)>='a'&&newu.charAt(i)<='z'||newu.charAt(i)>='A'&&newu.charAt(i)<='Z')){
     return "Symbols";
    }
   
 
 }
if(newu.length()>=32){
return "TooBig";
}

 
while((c=fr2.read())!=-1){
finder = (char)c;
if(finder=='!'){
users="";
while(finder!='?'){

c=fr2.read();
finder=(char)c;
if(finder!='?'){
    users+=finder;
}
}
if(users.equals(newu)){
return "AlreadyExists";
}

}
}

 fr2.close();
  }catch(Exception e){
        e.printStackTrace();
        
        }
     

  //adding the account
  FileWriter pr2;
  try{
 
  pr2 = new FileWriter("user.txt",true); 
  BufferedWriter writer = new BufferedWriter(pr2);
  writer.newLine();
  writer.write("!"+newu+"?"+newp+"_");
  writer.close();
  pr2.close();
  
  }catch(IOException e){
  
  } 
      
  return "OK";
  }
    //Runnable for each client/thread.
     @Override
     public void run(){
         
         cnt++;  
   
      
      String option;
    
       
        try{         
           
    //Creates the stream that reads text from the client.
    in[myNum] = new BufferedReader(new InputStreamReader(c[myNum].getInputStream()));   
    //Creates the stream that will send text to the client.
 
    out[myNum]= new PrintWriter(c[myNum].getOutputStream(),true);
      
    
   
    
    while(done==false){
        option = in[myNum].readLine();
 if(option.equals("CA")){
    CA( );
    
 }
    if(option.equals("LOGIN")){
    Login(); 
    }
     if(option.equals("GUEST")){
    GUEST();
    }
    }
            
    //handling the continuous conversation with the client while he is connected.
   
          
  
    while((text=in[myNum].readLine())!=null){
if(!text.equals("")){
    editedText=  username + ": " + text ;    
         if(!a1.getText().equals("")){
     win.a1.append("\n");
     }
    win. a1.append( editedText);
   
    for(int k =0;k<cnt;k++){
       
   if(!users[k].equals("")&&Ready[k]==true){  
        
    out[k].println(editedText);
  
   }
   }
    }
 }
guestnum--;
     terminate(myNum, c,out,in,username);    
     UsersLoad(); 

    }catch(IOException e){
        e.printStackTrace();
        }
  
    }
   
    }

    //to be made soon
    public static class PlayGame{
    
        
    public PlayGame(){
    

    
    }
    
    
    }
    
    
     public static void main(String[] args) {
      win = new ChatServer();
      win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      win.setVisible(true);
      win.setContentPane(p1);
      win.setTitle("ChatServer");
      win.setSize(750,570);
      win.setResizable(false);
 win.setLocation(400, 400);
      startServer();
      
    }
     
}

