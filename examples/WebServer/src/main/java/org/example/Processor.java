package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Processor of HTTP request.
 */
public class Processor implements Runnable{
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }




    public void process() throws IOException, InterruptedException {
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();
        PrintWriter output = new PrintWriter(socket.getOutputStream());


        if (request.getRequestLine().toString().equals("GET /create/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Item is creating</title></head>");
            output.println("<body><p>Item created</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }

        //different response for different requests
        else if (request.getRequestLine().toString().equals("GET /delete/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Item is deleting</title></head>");
            output.println("<body><p>Item deleted!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }

        else if (request.getRequestLine().toString().equals("GET /exec/item1 HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Item is executing</title></head>");
            output.println("<body><p>Item executed!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
        //different response for different requests
        else {
            // We are returning a simple web page now.

            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Hello</title></head>");
            Thread.sleep(3000);
            output.println("<body><p>Hello, world!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
    }

    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}