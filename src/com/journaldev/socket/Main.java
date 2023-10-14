package com.journaldev.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author Rafał Chojncaki
 *
 */
class SocketServerExample {
    private static ServerSocket server;
    private static String message = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        server = new ServerSocket( 9877);
        try
        {
            while (true)
            {
                System.out.println("Waiting for the client request");
                Socket socket = server.accept();
                ClientHandler ch = new ClientHandler(socket);

                Thread th = new Thread(ch);
                th.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        BufferedReader input;
        PrintStream output;
        String messageInput;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());
        }

        public void run() {
            System.out.println("THREAD STARTED");
            try {
                messageInput = input.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            System.out.println("Message Received: " + messageInput);

            try
            {
                switch (messageInput) {
                    case "Integer" -> this.<Integer>HandleTreeOperations(new ParseInt());
                    case "Double" -> this.<Double>HandleTreeOperations(new ParseDouble());
                    case "String" -> this.<String>HandleTreeOperations(new ParseString());
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        public <T extends Comparable<T>> void HandleTreeOperations(GenericParser parser) throws IOException {
            Tree<T> myTree = new Tree<>();
            Parser<T> parser1 = new Parser<>(parser);
            output.println("Success");
            boolean shouldRun = true;

            while (shouldRun) {
                try {
                    String line = input.readLine();
                    String[] t = line.split(";", 0);
                    String response = CommandToDo(t[0], t[1], myTree, parser1);
                    output.println(response);
                } catch (IOException e) {
                    input.close();
                    output.close();
                    clientSocket.close();
                    shouldRun = false;
                }
            }
        }

        public <T extends Comparable<T>> String CommandToDo(String command, String value, Tree<T> tree, Parser<T> parser) {
            String st = "";
            T Tvalue = parser.parseValue(value);
            if(Tvalue == null)
            {
                return "Invalid Type";
            }
            switch (command) {
                case "insert" -> {
                    tree.insert(Tvalue);
                    st = "Value inserted: " + value + ",  tree : " + tree.IterativeinOrder();
                }
                case "search" -> {
                    boolean found = tree.searchNode(Tvalue);
                    st = "Value found: " + found;
                }
                case "delete" -> {
                    tree.delete(Tvalue);
                    st = "Value deleted: " + value + ",  tree : " + tree.IterativeinOrder();
                }
                case "draw" -> {
                    String s = tree.IterativeinOrder();
                    st = "Tree drawn: " + s;
                }
                default -> st = "Command not correct";
            }
            return st;
        }
    }
}