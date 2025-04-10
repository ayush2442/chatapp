import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    
    //constructor...
    //setting the port number 8000
    public server() {
        try {
            server = new ServerSocket(8000);
            System.out.println("System is ready to establish connection");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // thread will read data

        Runnable r1 = ()->{
            System.out.println("Reader started...");

            try {
                while(true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");

                        socket.close();
                        
                        break;
                    }
                    System.out.println("Client: "+ msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is closed!!!");
            }
        };

        new Thread(r1).start();
        
    }

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
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is closed!!!");
            }
            System.out.println("Thank You!!!");

        };
        
        new Thread(r2).start();
        
    }
    
    public static void main(String[] args) {
        System.out.println("This is server going to start...");
        new server();
    }
}