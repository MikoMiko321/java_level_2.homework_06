package java_level_2.homework_06.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainClient {

    public static void main (String[] args) {
        final String IP_ADRESS = "localhost";
        final int PORT = 8189;
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Scanner scanner = new Scanner(socket.getInputStream());
                            String str = scanner.nextLine();
                            //String str = in.readUTF();
                            if(str.equals("/end")) {
                                System.out.println("Server closed connection!");
                                break;
                            }
                            System.out.println("Server " + str);
                            //server.broadcastMsg(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Scanner scanner = new Scanner(System.in);
                            String str = scanner.nextLine();
                            if(str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }
                            out.writeUTF(str+'\n');
//                            server.broadcastMsg(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchElementException e){
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
