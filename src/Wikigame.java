import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class WikiGame {

    private int maxDepth;
    private ArrayList<String> path = new ArrayList<>();

    public static void main(String[] args) {
        WikiGame w = new WikiGame();
    }

    public WikiGame() {

        String startLink = "";  // beginning link, where the program will start
        String endLink = "";    // ending link, where the program is trying to get to
        maxDepth = 1;           // start this at 1 or 2, and if you get it going fast, increase

        if (findLink(startLink, endLink, 0)) {
            System.out.println("found it********************************************************************");
            path.add(startLink);
        } else {
            System.out.println("did not found it********************************************************************");
        }

    }

    // recursion method
    public boolean findLink(String startLink, String endLink, int depth) {

        System.out.println("depth is: " + depth + ", link is: https://en.wikipedia.org" + startLink);

        // BASE CASE
        if () {

        } else if () {

        }

        // GENERAL RECURSIVE CASE
        else {

        }

        return false;
    }

    class Wikigame implements ActionListener {
        private JFrame mainFrame;
        private JLabel headerLabel;
        private JTextArea statusLabel;
        private JPanel controlPanel;
        private JMenuBar mb;
        private JMenu file, edit, help;
        private JMenuItem cut, copy, paste, selectAll;
        private JTextArea ta;
        private JTextArea tb;
        private int WIDTH = 800;
        private int HEIGHT = 700;
        private JScrollPane scrollPane;
        private JScrollPane scrollPane1;
        private JScrollPane scrollPane2;


        public void SwingControlDemo() {
            prepareGUI();
        }

        public static void main(String[] args) {
            //SwingControlDemo swingControlDemo = new SwingControlDemo();
            // swingControlDemo.showEventDemo();
            Wikigame h = new Wikigame();
            h.SwingControlDemo();
            h.showEventDemo();
        }

        private void prepareGUI() {
            mainFrame = new JFrame("Java SWING Examples");
            mainFrame.setSize(WIDTH, HEIGHT);
            mainFrame.setLayout(new GridLayout(3, 1));


            cut = new JMenuItem("cut");
            copy = new JMenuItem("copy");
            paste = new JMenuItem("paste");
            selectAll = new JMenuItem("selectAll");
            cut.addActionListener(this);
            copy.addActionListener(this);
            paste.addActionListener(this);
            selectAll.addActionListener(this);

            mb = new JMenuBar();
            file = new JMenu("File");
            edit = new JMenu("Edit");
            help = new JMenu("Help");
            edit.add(cut);
            edit.add(copy);
            edit.add(paste);
            edit.add(selectAll);
            mb.add(file);
            mb.add(edit);
            mb.add(help);

            ta = new JTextArea();
            ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
            tb = new JTextArea();
            tb.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
            mainFrame.add(mb);
            mainFrame.setJMenuBar(mb);

            headerLabel = new JLabel("", JLabel.CENTER);
            statusLabel = new JTextArea();
            statusLabel.setSize(350, 100);

            mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });
            controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout());

            scrollPane = new JScrollPane(statusLabel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane1 = new JScrollPane(ta);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane2 = new JScrollPane(tb);
            scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            // mainFrame.add(headerLabel);
            mainFrame.add(scrollPane1);
            mainFrame.add(scrollPane2);
            mainFrame.add(controlPanel);
            mainFrame.add(scrollPane);


            //mainFrame.add(statusLabel);
            mainFrame.setVisible(true);

        }

        private void showEventDemo() {
            headerLabel.setText("Control in action: Button");

            JButton okButton = new JButton("Search");


            okButton.setActionCommand("OK");


            okButton.addActionListener(new ButtonClickListener());


            controlPanel.add(okButton);


            mainFrame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cut)
                ta.cut();
            if (e.getSource() == paste)
                ta.paste();
            if (e.getSource() == copy)
                ta.copy();
            if (e.getSource() == selectAll)
                ta.selectAll();
        }

        private class ButtonClickListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();

                if (command.equals("OK")) {
                    statusLabel.setText("Ok Button clicked.\n");
                    try {
                        System.out.println();
                        System.out.print("hello \n");
                        URL url = new URL(ta.getText());
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(url.openStream())
                        );
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println("og: " + line);

                            if (line.contains("href") && line.contains(tb.getText())) {
                                System.out.println("og: " + line);

                                int index = line.indexOf("href");
                                line = line.substring(index + 6);
                                if (line.contains("http")) {
                                    index = line.indexOf("http");
                                    line = line.substring(index);
                                    int endD = line.indexOf("\"");
                                    int endS = line.indexOf("\'");
                                    int end = 0;
                                    if (endD == -1) {
                                        end = endS;
                                    }
                                    if (endS == -1) {
                                        end = endD;
                                    }
                                    if (endD != -1 && endS != -1) {
                                        if (endD > endS) {
                                            end = endS;
                                        } else {
                                            end = endD;

                                        }
                                    }


                                    System.out.println(line.substring(0, end));
                                    statusLabel.append(line.substring(0, end) + "\n");
                                }
                            }

                        }
                        reader.close();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }


                } else if (command.equals("Submit")) {
                    statusLabel.setText("Submit Button clicked.\n");
                } else {
                    statusLabel.setText("Cancel Button clicked.");
                }
            }
        }
    }
}