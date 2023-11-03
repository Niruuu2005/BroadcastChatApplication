package programs.minijavapro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static PrintWriter out;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Lite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        textArea.setBackground(new Color(99, 255, 232));
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JTextField textField = new JTextField("Enter your Message...");
        inputPanel.add(textField, BorderLayout.CENTER);
        textField.setBounds(0, 0, 0, 100);

        JButton sendButton = new JButton("Send"); 
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);

        textField.addFocusListener(new FocusListener(){
           public void focusGained(FocusEvent e){
               textField.setText("");
           } 
           public void focusLost(FocusEvent e){
               if(textField.getText().equals("")){
                   textField.setText("Enter you Message...");
               }
           }
        });
        
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField, textArea);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(textField, textArea);
            }
        });

        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 2006);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                textArea.append(message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(JTextField textField, JTextArea textArea) {
        String message = textField.getText();
        out.println(message);
        textArea.append("You: " + message + "\n");
        textField.setText("");
    }
}