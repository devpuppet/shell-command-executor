package gui;

import shell.CommandExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppWindow {
    CommandExecutor commandExecutor;

    JFrame frame = new JFrame("Shell Command Executor");
    JTextField commandField = new JTextField();
    JButton runButton = new JButton("Run");
    JTextArea outputArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(outputArea);

    public AppWindow(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 400);
        outputArea.setEditable(false);
        ActionListener runButtonAction = (ActionEvent e) -> {
            String command = this.commandField.getText().trim();
            if (!command.isEmpty()) {
                String result = commandExecutor.runShellCommand(command);
                outputArea.setText(result);
            }
        };
        runButton.addActionListener(runButtonAction);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.commandField, BorderLayout.NORTH);
        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(runButton, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }

}
