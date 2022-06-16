package com.company.models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }


    public void startServer ()
    {

        try
        {
            while(!serverSocket.isClosed())
            {
                Socket socket =  serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler handler = new ClientHandler(socket);

                Thread thread = new Thread(handler);
                thread.start();
            }
        }  catch (IOException e)
        {

        }
    }

    public void closeServerSocket ()
    {
        try
        {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        } catch (IOException exc)
        {
            exc.printStackTrace();
        }
    }
}
