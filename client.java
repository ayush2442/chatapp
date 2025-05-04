import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //Declaring GUI Components
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);
    
    //Constructor
    public client() {
        try {
            System.out.println("Sending request to server...");
            socket = new Socket("127.0.0.1",8000);
            System.out.println("Connect established successfully");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            
            startReading();
            // startWriting();

        } catch (Exception e) {

        }
    }

    private void handleEvents() {

        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {

                // System.out.println("Key Released: " + e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me: " + contentToSend + "\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
            
        });
    }

    private void createGUI() {
        //GUI Code
        this.setTitle("Client Messager[END]");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //coding for components
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("chatre.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageArea.setEditable(false);
        
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);
        
        //setting frame layout
        this.setLayout(new BorderLayout());

        //adding components to frame
        this.add(heading, BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        
        this.add(messageArea, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);
        
        this.setVisible(true);
        
    }

    //Start Reading method
    public void startReading() {
        // thread will read data

        Runnable r1 = ()->{
            System.out.println("Reader started...");

            try {
                while(true) {
                    String msg = br.readLine();
                    if (msg.equals("Exit")) {
                        System.out.println("Server terminated the chat");
                        JOptionPane.showMessageDialog(this, "Server Terminated the chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    // System.out.println("Server: "+ msg);
                    messageArea.append("Server: "+msg+"\n");
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is closed by Server!!!");
            }
        };

        new Thread(r1).start();
        
    }

    //Start writing
    public void startWriting() {
        // thread will take data from user and send it to client

        Runnable r2 = ()->{
            System.out.println("Writer Started...");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        };
        
        new Thread(r2).start();
        
    }
    
    public static void main(String[] args) {
        System.out.println("This is Client...");
        new client();
    }
}
