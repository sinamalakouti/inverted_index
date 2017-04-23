package invertedIndex.gui;


import invertedIndex.managers.CommandManager;
import invertedIndex.managers.FileManager;
import invertedIndex.utils.Constants;
import invertedIndex.utils.datastructres.hash.HashTable;
import invertedIndex.utils.datastructres.stack.Stack;
import invertedIndex.utils.datastructres.trees.Tree;
import invertedIndex.utils.datastructres.trees.bst.BstTree;
import invertedIndex.utils.datastructres.trees.bst.balanceBst.BalancedBst;
import invertedIndex.utils.datastructres.trees.trie.Trie;
import invertedIndex.utils.datastructres.trees.tst.Tst;
import invertedIndex.utils.datastructres.trees.tst.balancedTst.BalancedTst;
import invertedIndex.utils.fileutils.Fileutils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Created by sina on 11/30/16.
 */

// TODO : executing commaands + commadns go up with narrow keys
public class ProgramPanel extends JPanel implements ActionListener, KeyListener {

    private MyTextField filePathTextfield;
    private MyTextArea cmdTextArea;
    private MyTextField cmdTextFeild;
    private MyButton buildButton;
    private MyButton resetButton;
    private MyButton exitButton;
    private MyButton okButton;
    private MyRadioButton bst;
    private MyRadioButton trie;
    private MyRadioButton tst;
    private MyRadioButton hash;
    private MyRadioButton bst_balance;
    private MyRadioButton tst_balance;
    private String text = "";
    private boolean isUserCmd = false;
    private Stack cmdStack = new Stack();

    public ProgramPanel(Dimension d) {
        this.setSize(d);
        this.setLocation(0, 0);
        this.setBackground(Color.gray);
        filePathTextfield = new MyTextField(new Dimension(550, 25), 50, 50);
        MyButton browseButton = new MyButton(new Dimension(100, 50), 750, 40, "Browse");
        this.add(browseButton);
        browseButton.addActionListener(this);
        cmdTextArea = new MyTextArea(new Dimension(950, 500), 25, 100);
        JScrollPane scroll = new JScrollPane(cmdTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        cmdTextArea.setEditable(false);
        this.add(scroll);
        this.add(cmdTextArea);
        this.add(filePathTextfield);
        this.add(new MyLabel(new Dimension(200, 25), 70, 630, "Please enter your command :"));
        this.showRadioButtons();
        cmdTextFeild = new MyTextField(new Dimension(500, 25), 50, 680);
        this.add(cmdTextFeild);
        okButton = new MyButton(new Dimension(100, 50), 600, 660, "Ok");
        this.add(okButton);
        okButton.addActionListener(this);
        buildButton = new MyButton(new Dimension(100, 50), 150, 750, "Build");
        resetButton = new MyButton(new Dimension(100, 50), 350, 750, "Reset");
        exitButton = new MyButton(new Dimension(100, 50), 550, 750, "Exit");
        buildButton.addActionListener(this);
        exitButton.addActionListener(this);
        resetButton.addActionListener(this);
        this.add(buildButton);
        this.add(resetButton);
        this.add(exitButton);
        this.addKeyListener(this);
        cmdTextFeild.addKeyListener(this);
        cmdTextArea.addKeyListener(this);
        this.setFocusable(true);


        this.setLayout(null);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (((MyButton) e.getSource()).getText().equals("Browse")) {

            String str = Fileutils.chooseFile(this, "");
            filePathTextfield.setText(str);
            try {
                Fileutils.readingFiles(Fileutils.getFileFomPath(str));
            } catch (NullPointerException e1) {

            }


        } else if (((MyButton) e.getSource()).getText().equals("Build")) {
            Constants.dataStructre = null;

            if (hash.isSelected()) {

                FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());
                JFrame frame = new JFrame("input dialog");
                String size = JOptionPane.showInputDialog(frame, "what should you hash table size be ?");
                Constants.dataStructre = new HashTable<>(Integer.parseInt(size));
                long start = System.currentTimeMillis();
                for (File file : FileManager.getFileManagerSharedInstance().getFiles()) {
                    filePathTextfield.setText("");
                    if (!FileManager.getFileManagerSharedInstance().getExecutedFiles().contains(file)) {

                        FileManager.getFileManagerSharedInstance().addToExecutedFiles(file);
                        CommandManager.getSharedInstance().addCommand(file);

                    } else {

                        cmdTextArea.setText("err  : " + Fileutils.gettingNameOfFile(file) + ":  this file is already executed");
                    }
                }

                long elapsed = System.currentTimeMillis() - start;
                cmdTextArea.append(elapsed + " ");
                cmdTextArea.append("number of executed Files From beginning is   " + FileManager.getFileManagerSharedInstance()
                        .getExecutedFiles().size());


                cmdTextArea.append("number of words in the tree is     " + "" + Constants.dataStructre.traverse(new ArrayList<>()).size());


            } else {

                if (Constants.dataStructre == null || ((Tree) Constants.dataStructre).isEmpty())
                    FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());

                if (bst.isSelected()) {
                    if (Constants.dataStructre == null || Constants.dataStructre != null && !Constants.dataStructre.getClass().equals(BstTree.class)) {
                        Constants.dataStructre = new BstTree();
                        FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());

                    }


                } else if (bst_balance.isSelected()) {
                    if (Constants.dataStructre == null || Constants.dataStructre != null && !Constants.dataStructre.getClass().equals(BalancedBst.class)) {

                        Constants.dataStructre = new BalancedBst<String>();
                        FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());
                    }
                } else if (tst_balance.isSelected()) {

                    if (Constants.dataStructre == null || Constants.dataStructre != null && !Constants.dataStructre.getClass().equals(BalancedTst.class)) {
                        Constants.dataStructre = new BalancedTst();
                        FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());

                    }
                } else if (trie.isSelected()) {

                    if (Constants.dataStructre == null || Constants.dataStructre != null && !Constants.dataStructre.getClass().equals(Trie.class)) {
                        Constants.dataStructre = new Trie();
                        FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());

                    }


                } else if (tst.isSelected()) {


                    if (Constants.dataStructre == null || Constants.dataStructre != null && !Constants.dataStructre.getClass().equals(Tst.class)) {
                        FileManager.getFileManagerSharedInstance().setExecutedFiles(new ArrayList<>());
                        Constants.dataStructre = new Tst();
                    }
                }

                long start = System.currentTimeMillis();
                for (File file : FileManager.getFileManagerSharedInstance().getFiles()) {
                    filePathTextfield.setText("");
//                if (!FileManager.getFileManagerSharedInstance().getExecutedFiles().contains(file)) {

                    FileManager.getFileManagerSharedInstance().addToExecutedFiles(file);
                    CommandManager.getSharedInstance().addCommand(file);

//                } else {

//                    cmdTextArea.setText("err  : " + Fileutils.gettingNameOfFile(file) + ":  this file is already executed");
//                }
                }

                long elapsed = System.currentTimeMillis() - start;
                cmdTextArea.append(elapsed + "\n ");
                cmdTextArea.append("number of executed Files From beginning is  " + FileManager.getFileManagerSharedInstance()
                        .getExecutedFiles().size());


                cmdTextArea.append("number of words in the tree is     " + "" + ((Tree) Constants.dataStructre).numberOfWords());
            }

        } else if (((MyButton) e.getSource()).getText().equals("Reset")) {

            this.cmdTextFeild.setText("");

        } else if (((MyButton) e.getSource()).getText().equals("Exit")) {

            if (Constants.dataStructre != null && ! Constants.dataStructre.getClass().equals(HashTable.class)&& ((Tree) Constants.dataStructre).getSize() != 0) {
                long start = System.currentTimeMillis();
                this.cmdTextArea.setText("height of the tree is \t" + ((Tree) Constants.dataStructre).getHeight());
                long elapsed = System.currentTimeMillis() - start;
                JOptionPane.showMessageDialog(null, "height is : \t" + ((Tree) Constants.dataStructre).getHeight() + "\n time is: \t" + elapsed);
            } else {

                JOptionPane.showMessageDialog(null, "you have not chosen your tree and add any data so there is nothing to show :D");

            }

            System.exit(0);
        } else if (((MyButton) e.getSource()).getText().equals("Ok")) {

            this.isUserCmd = true;

            String str[] = cmdTextFeild.getText().split("\\s");


//            for (int i = 0; i < str.length; i++)
            if (str.length >= 1) {


                if (str.length != 1 || str[0].compareTo("") != 0) {
                    CommandManager.getSharedInstance().getPreviousCommands().push(cmdTextFeild.getText());
                    cmdTextArea.setText(cmdTextFeild.getText());
//                    System.out.println("command processed commetn \t" + CommandManager.getSharedInstance().commandProcessor(str));
                    cmdTextFeild.setText("");

                    this.isUserCmd = false;
                    cmdTextArea.setText(CommandManager.getSharedInstance().commandProcessor(str));
                    this.isUserCmd = true;
                }

            }

            this.isUserCmd = false;

        }

    }

    private void showRadioButtons() {


        bst = new MyRadioButton("Bst", 300, 620, new Dimension(100, 50));
        trie = new MyRadioButton("Trie", 400, 620, new Dimension(100, 50));
        tst = new MyRadioButton("Tst", 500, 620, new Dimension(100, 50));
        hash = new MyRadioButton("Hash", 600, 620, new Dimension(100, 50));
        bst_balance = new MyRadioButton("Balanced Bst", 700, 620, new Dimension(150, 50));
        tst_balance = new MyRadioButton("Balanced Tst", 850, 620, new Dimension(150, 50));
        ButtonGroup group = new ButtonGroup();
        group.add(bst);
        group.add(trie);
        group.add(tst);
        group.add(hash);
        group.add(bst_balance);
        group.add(tst_balance);
        bst.setSelected(true);
        this.add(bst);
        this.add(trie);
        this.add(tst);
        this.add(hash);
        this.add(bst_balance);
        this.add(tst_balance);
//        this.add(group);


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {

            try {
                cmdTextFeild.setText((String) CommandManager.getSharedInstance().getPreviousCommands().pop());
            } catch (EmptyStackException e1) {

            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class MyTextField extends TextField {


        public MyTextField(Dimension d, int xCord, int yCord) {
            this.setSize(d);
            this.setLocation(xCord, yCord);
        }

        public void setText(String str) {

            super.setText(str);
        }
    }

    class MyButton extends JButton {


        public MyButton(Dimension d, int xCord, int yCord, String text) {

            this.setLocation(xCord, yCord);
            this.setSize(d);
            this.setText(text);

        }


    }

    class MyTextArea extends TextArea {

        private String user = ">>";


        public MyTextArea(Dimension d, int xCord, int yCord) {

            this.setSize(d);
            this.setLocation(xCord, yCord);
        }

        public void setText(String str) {


            if (text.compareTo("") != 0) {
                if (isUserCmd) {
                    cmdStack.push(str);
                    this.append("\n >>\t" + str);
//                    text = text + "\n >>\t" + str;

                } else {
                    this.append("\n\t" + str);
//                    text = text + "\n\t" + str;
                }

            } else {
                if (isUserCmd) {
                    cmdStack.push(str);
                    append(" >>\t" + str);
//                    text = text + " >>\t" + str;
                } else {
                    append("\t" + str);
//                    text = text + "\t" + str;
                }
            }
            append("\n");
        }

    }

    class MyLabel extends JLabel {
        public MyLabel(Dimension d, int xCord, int yCord, String text) {
            this.setSize(d);
            this.setLocation(xCord, yCord);
            this.setText(text);
        }
    }

    class MyRadioButton extends JRadioButton {
        public MyRadioButton(String str, int x, int y, Dimension d) {
            super(str);
            setLocation(x, y);
            setSize(d);

        }


    }

    public String getText() {
        return text;
    }

    public void setText(String text) {

        this.cmdTextArea.setText(text);
    }


}