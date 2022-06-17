package com.company.models;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private  Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e)
        {
            closeEverythingForClient(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage()
    {
        try
        {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected())
            {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e)
        {
            closeEverythingForClient(socket, bufferedReader, bufferedWriter);
        }
    }


    public void listenForMessages ()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messagesFromGroupChat;
                while (socket.isConnected())
                try
                {
                     messagesFromGroupChat = bufferedReader.readLine();
                    System.out.println(messagesFromGroupChat);
                } catch (IOException e)
                {
                    closeEverythingForClient(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    private void closeEverythingForClient(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try
        {
            if(bufferedReader!=null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            if(socket!= null)
            {
                socket.close();
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
