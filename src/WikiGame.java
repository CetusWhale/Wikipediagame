import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class WikiGame implements ActionListener {

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
    private int maxDepth;
    String startLink;
    String endLink;
    private ArrayList<String> path = new ArrayList<>();

    public static void main(String[] args) {
        WikiGame w = new WikiGame();

          w.showEventDemo();
    }

    public WikiGame() {
        SwingControlDemo();


    }

    // recursion method
    public boolean findLink(String startLink, String endLink, int depth) {

        System.out.println("depth is: " + depth + ", link is: https://en.wikipedia.org" + startLink);

        // BASE CASE
        if (endLink.equals(startLink)) {
            System.out.println("found it");
            return true;
        } else if (depth==maxDepth) {
            System.out.println("cannot find it");
            return false;
        }

        // GENERAL RECURSIVE CASE
        else {
            return readhtml(startLink);
        }
    }
    public boolean readhtml(String start) {
        boolean found = false;
        try {
            System.out.println();
            System.out.print("hello \n");
            URL url = new URL(start);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String ogline;
            while ((ogline = reader.readLine()) != null) {
             //  System.out.println("og: " + ogline);

                while (ogline.contains("href")) {
                    String line = ogline;
                    System.out.println("line: " + line);

                    int index = line.indexOf("href");
                    line = line.substring(index + 6);
                    int end = ogline.length();
                    if (line.contains("/wiki/")) {
                        index = line.indexOf("/wiki/");
                       // System.out.println(index);
                        line = line.substring(index);
                        int endD = line.indexOf("\"");
                        int endS = line.indexOf("\'");

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
                      // System.out.println(end);
                        line = line.substring(0,end);
                        System.out.println(line);
                        line = "https://en.wikipedia.org"+line;
                        if(line.equals(endLink)){
                            System.out.println("success");
                            found = true;
                            break;
                        }


                    }
                    ogline = ogline.substring(end);
                }
                if(found == true){
                    break;
                }
            }
        }catch (Exception ex) {
            System.out.println(ex);
        }
        return found;
    }

  //  class Wikigame implements ActionListener {



        public void SwingControlDemo() {
            prepareGUI();
        }

//        public static void main(String[] args) {
//            //SwingControlDemo swingControlDemo = new SwingControlDemo();
//            // swingControlDemo.showEventDemo();
//            Wikigame h = new Wikigame();
//            h.SwingControlDemo();
//            h.showEventDemo();
//        }

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

            ta = new JTextArea("start");
            ta.setBounds(50, 5, WIDTH - 100, HEIGHT - 50);
            tb = new JTextArea("end");
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

                    startLink = ta.getText();//"https://en.wikipedia.org/wiki/Casey_Larson";  // beginning link, where the program will start
                    endLink = tb.getText();//"https://en.wikipedia.org/wiki/Chicago";    // ending link, where the program is trying to get to
                    maxDepth = 1;           // start this at 1 or 2, and if you get it going fast, increase

                    if (findLink(startLink, endLink, 0)) {
                        System.out.println("found it********************************************************************");
                        path.add(startLink);
                        statusLabel.setText("Success");
                    } else {
                        System.out.println("did not found it********************************************************************");
                        statusLabel.setText("Did not find it");
                    }

                }
            }
        }
   // }
}