package com.even;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project Name: ai
 * Des:
 * Created by Even on 2019/4/3 13:59
 */
public class TextFields extends JFrame {
    private JButton b1 = new JButton("Get Text"), b2 = new JButton("Set Text");
    private JTextField t1 = new JTextField(30), t2 = new JTextField(30), t3 = new JTextField(30);
    private String s = "";

    private UpperCasedoucment ucd = new UpperCasedoucment();

    public TextFields() {
        t1.setDocument(ucd);
//        ucd.addDocumentListener(new T1());
//        b1.addActionListener(new B1());
//        b2.addActionListener(new B2());
//        t1.addActionListener(new T1A());
//        setLayout(new FlowLayout());
//        add(b1);
//        add(b2);
        add(t1);
//        add(t2);
//        add(t3);
    }

    class T1 implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            t2.setText(t1.getText());
            t3.setText("Text:" + t1.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            t2.setText(t1.getText());

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    class T1A implements ActionListener {
        private int count = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            t3.setText("t1 Action Event " + count++);
        }
    }

    class B1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (t1.getSelectedText() == null)
                s = t1.getText();
            else
                s = t1.getSelectedText();
            t1.setEditable(true);
        }
    }

    class B2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ucd.setUpperCase(false);
            t1.setText("Inserted by Button 2: " + s);
            ucd.setUpperCase(true);
            t1.setEditable(false);
        }
    }

    public static void main(String[] args) {
        TextFields textFields = new TextFields();
        textFields.setTitle(textFields.getClass().getSimpleName());
        textFields.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textFields.setSize(375,200);
        textFields.setVisible(true);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class UpperCasedoucment extends PlainDocument {
        private boolean upperCase = true;

        public void setUpperCase(boolean upperCase) {
            this.upperCase = upperCase;
        }

        public void insertString(int offset, String str, AttributeSet attset) throws BadLocationException {
            if (upperCase) str = str.toUpperCase();
            super.insertString(offset, str, attset);
        }
    }

}
