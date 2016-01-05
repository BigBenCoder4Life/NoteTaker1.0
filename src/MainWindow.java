import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

/**
 * This class is used for UI purposes.
 * 
 * @author Ben Kirtley
 * @version 1.0
 * @date 3/7/14
 * 
 */
public class MainWindow extends JFrame
{
    /**
     * Serial Unit
     */
    private static final long serialVersionUID = 1L;
    private static JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu lectureMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenuItem exitItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem aboutUsItem;
    private JMenuItem helpItem;
    private JMenuItem printItem;
    private JMenuItem fontStyleItem;
    private JCheckBoxMenuItem recordItem;
    JFrame fontFrame;
    NotesArea doc;
    private static JTextPane noteArea;
    private JPanel slidePanel;
    private static JLabel slideLabel;
    private JPanel arrowPanel;
    private Presentation pres;
    private final int WINDOW_WIDTH = 970;
    private final int WINDOW_HEIGHT = 800;
    ToolsUtilities tools;
    Container c;

    /*
     * Constructor for the main Window.
     */
    MainWindow()
    {
        mainFrame = new JFrame();

        mainFrame.setLayout(new BorderLayout());

        mainFrame.setTitle("NoteTaker 1.0");

        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildToolBar();
        buildNoteAreaPanel();
        buildSlidePanel();
        buildSlideNavigationPanel();

        tools = new ToolsUtilities(noteArea);
        buildMenuBar();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();

        mainFrame.setVisible(true);
    }

    /*
     * To build the GUI toolBar
     */
    private void buildToolBar()
    {
        ToolBar bar = new ToolBar();
        mainFrame.add(bar, BorderLayout.CENTER);
        mainFrame.pack();
    }

    /*
     * To build the slide panel
     */
    private void buildSlidePanel()
    {
        slidePanel = new JPanel();
        slidePanel.setLayout(new BorderLayout());
        slidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        slideLabel = new JLabel();
        ImageIcon slideIcon = new ImageIcon(ClassLoader.getSystemResource("Resources/welcome.jpg"));
        slideLabel.setIcon(slideIcon);
        slidePanel.add(slideLabel, BorderLayout.EAST);
        mainFrame.add(slidePanel, BorderLayout.EAST);
    }

    /*
     * To build the note Area Panel.
     */
    private void buildNoteAreaPanel()
    {
        doc = new NotesArea();
        noteArea = NotesArea.pane;
        mainFrame.add(doc.panel, BorderLayout.WEST);
        mainFrame.pack();
    }

    /*
     * Arrow panel to hold slide transition buttons.
     */
    private void buildSlideNavigationPanel()
    {
        arrowPanel = new JPanel();
        
        ImageIcon leftArrow = new ImageIcon(
                ClassLoader.getSystemResource("Resources/leftArrow.png"));
        ImageIcon rightArrow = new ImageIcon(
                ClassLoader.getSystemResource("Resources/rightArrow.png"));

        JButton leftButton = new JButton(leftArrow);
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);

        JButton rightButton = new JButton(rightArrow);
        rightButton.setContentAreaFilled(false);
        rightButton.setBorderPainted(false);

        rightButton.addActionListener(new rightButtonlistener());
        leftButton.addActionListener(new leftButtonlistener());

        arrowPanel.add(leftButton);
        arrowPanel.add(rightButton);
        slidePanel.add(arrowPanel, BorderLayout.SOUTH);
        mainFrame.pack();

    }

    /*
     * To build the menu bar.
     */
    private void buildMenuBar()
    {
        menuBar = new JMenuBar();

        buildFileMenu();
        buildLectureMenu();
        buildEditMenu();
        buildHelpMenu();

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(lectureMenu);
        menuBar.add(helpMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    /*
     * To build the file menu.
     */
    public void buildFileMenu()
    {
        fileMenu = new JMenu("File");

        exitItem = new JMenuItem("Exit");
        openItem = new JMenuItem("Open File...");
        saveItem = new JMenuItem("Save File...");
        printItem = new JMenuItem("Print...");

        openItem.addActionListener(new openListener());
        exitItem.addActionListener(new exitListener());
        saveItem.addActionListener(new saveListener());
        printItem.addActionListener(new printListener());

        exitItem.setMnemonic(KeyEvent.VK_X);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(printItem);
        fileMenu.add(exitItem);
    }

    /*
     * To build the option menu.
     */
    public void buildLectureMenu()
    {
        lectureMenu = new JMenu("Lecture");

        JMenuItem loadSlide = new JMenuItem("Load Slides...");
        loadSlide.addActionListener(new slideListener());

        JMenuItem slideItem = new JMenuItem("Order Slides...");
        slideItem.addActionListener(new orderSlideListener());

        recordItem = new JCheckBoxMenuItem("Record Lecture", false);
        recordItem.addActionListener(new recordListener());

        lectureMenu.add(loadSlide);
        lectureMenu.add(slideItem);
        lectureMenu.addSeparator();
        lectureMenu.add(recordItem);
    }

    /*
     * To build the Edit menu
     */
    public void buildEditMenu()
    {
        editMenu = new JMenu("Edit");

        fontStyleItem = new JMenuItem("Font");
        fontStyleItem.addActionListener(new fontStyleListener());
        editMenu.add(fontStyleItem);

        javax.swing.Action temp = tools.getActionByName(DefaultEditorKit.cutAction);
        temp.putValue(AbstractAction.NAME, "Cut");
        editMenu.add(temp);

        temp = tools.getActionByName(DefaultEditorKit.copyAction);
        temp.putValue(AbstractAction.NAME, "Copy");
        editMenu.add(temp);

        temp = tools.getActionByName(DefaultEditorKit.pasteAction);
        temp.putValue(AbstractAction.NAME, "Paste");
        editMenu.add(temp);

        temp = tools.getActionByName(DefaultEditorKit.selectAllAction);
        temp.putValue(AbstractAction.NAME, "Select All");
        editMenu.add(temp);

        editMenu.addSeparator();
    }

    /*
     * To build the help menu
     */
    public void buildHelpMenu()
    {
        helpMenu = new JMenu("Help");

        aboutUsItem = new JMenuItem("About Developer");

        helpItem = new JMenuItem("Help");

        helpItem.addActionListener(new helpListener());

        aboutUsItem.addActionListener(new aboutUsListener());

        helpMenu.add(helpItem);
        helpMenu.add(aboutUsItem);
    }

    /*
     * This is a class to handle opening text files
     */
    private class openListener implements ActionListener
    {
        /*
         * To perform a action on activation of button.
         */
        public void actionPerformed(ActionEvent arg0)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt file", "txt");

            fileChooser.addChoosableFileFilter(filter);
            int status = fileChooser.showOpenDialog(null);

            if (status == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getAbsolutePath();
                File file = new File(fileName);

                try
                {
                    noteArea.setText(null);
                    Scanner inputFile = new Scanner(file);
                    String str = "";

                    while (inputFile.hasNext())
                    {
                        str += inputFile.nextLine() + "\n";
                    }

                    noteArea.setText(str);

                    inputFile.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * This is a class to handle a exiting the program.
     */
    private class exitListener implements ActionListener
    {
        /*
         * To perform a action on activation of button.
         */
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    /*
     * This is a class handle to save notes from the notes area.
     */
    private class saveListener implements ActionListener
    {
        /*
         * To perform a action on activation of button.
         */
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt file", "txt");

            saveFileChooser.addChoosableFileFilter(filter);
            int status = saveFileChooser.showSaveDialog(mainFrame);

            try
            {
                FileWriter out;

                if (status == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = saveFileChooser.getSelectedFile();

                    if (!selectedFile.isFile())
                        out = new FileWriter(selectedFile + ".txt");
                    else
                        out = new FileWriter(selectedFile);

                    noteArea.write(out);
                    out.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /*
     * Private class to handle the presentation of slides in the GUI.
     */
    private class slideListener implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int status = fileChooser.showOpenDialog(null);

            if (status == JFileChooser.APPROVE_OPTION)
            {
                pres = new Presentation(fileChooser.getSelectedFile().toPath(),
                        getMainWindow());

                if (pres.slideTotal != 0)
                {
                    pres.loadFirstSlide();
                }
            }
        }
    }

    /*
     * Listen for Right Arrow activation
     */
    private class rightButtonlistener implements ActionListener
    {
        public void actionPerformed(ActionEvent action)
        {
            if (pres != null)
                pres.loadRightSlide();
        }
    }

    /*
     * Listen for Left Arrow activation
     */
    private class leftButtonlistener implements ActionListener
    {
        public void actionPerformed(ActionEvent action)
        {
            if (pres != null)
                pres.loadLeftSlide();
        }
    }

    /*
     * This inner class is for the about us menu item.
     */
    private class helpListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JFrame helpFrame = new JFrame();

            JButton url = new JButton("Click for tutorials");

            Border raisedEtched = BorderFactory.createBevelBorder(EtchedBorder.RAISED);
            url.setBorder(raisedEtched);

            helpFrame.setLayout(new FlowLayout());

            helpFrame.setTitle("Help");

            Dimension d1 = new Dimension(460, 350);
            Dimension d2 = new Dimension(5, 5);

            helpFrame.setPreferredSize(d1);
            url.setMaximumSize(d2);

            helpFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JTextArea textArea = new JTextArea(10, 10);

            String greeting = "\t  Welcome to the help menu for NoteTaker 1.0.";

            String saveFile = "\n\nTo save a text file:\n"
                    + "1. Click File at top of window\n2. Click Save File...\n3. Type your file name in the file name input box."
                    + "\n4. Click save at bottom of save dialog box.";

            String loadSlides = "\n\nTo Load slides:\n"
                    + "1. Click Lecture at top of window.\n2. Click Load Slides..."
                    + "\n3. Wait for open dialog window to open...\n4. **IMPORTANT: All slides must be stored in same directory "
                    + "and\n     have the file extension .png, .gif, or .jpg**"
                    + " Search for Directory with your slide files.\n"
                    + "     Example: C:\\Home\\MySlides\\"
                    + "\n6. Click open at bottom of open dialog window.";

            textArea.setEditable(false);
            textArea.setText(greeting + saveFile + loadSlides);

            url.addActionListener(new tutorialsButtonListener());

            helpFrame.add(textArea);
            helpFrame.add(url);
            helpFrame.setResizable(false);
            helpFrame.setLocationRelativeTo(mainFrame);
            helpFrame.pack();
            helpFrame.setLocationRelativeTo(mainFrame);
            helpFrame.setVisible(true);
        }
    }

    /*
     * This inner class is for the about us menu item.
     */
    private class aboutUsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JFrame aboutUsFrame = new JFrame();

            aboutUsFrame.setLayout(new BorderLayout());

            aboutUsFrame.setTitle("About Developer");

            aboutUsFrame.setSize(285, 250);

            aboutUsFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JTextArea textArea = new JTextArea(10, 10);
            String text = "Application Name: NoteTaker 1.0"
                    + "\nVersion: 1.0\nRelease Month: Aug 2014\nDeveloper:"
                    + " Ben Kirtley\nEmail: kirtlepb@dukes.jmu.edu\n" + "\nPersonal Note:\n"
                    + "Ben is a undegraduate student at \n" + "James Madison University\n"
                    + "He enjoys music, boxing, and learning.\n" + "If you have"
                    + " ideas on improvements\n" + "for this application "
                    + "please feel free to email him" + "\nwith suggestions.";

            textArea.setEditable(false);
            textArea.setText(text);

            aboutUsFrame.add(textArea);
            aboutUsFrame.setBackground(getBackground());

            aboutUsFrame.setResizable(false);
            aboutUsFrame.setLocationRelativeTo(mainFrame);
            aboutUsFrame.setVisible(true);
        }
    }

    /*
     * Handles opening URL in Default Internet explorer.
     */
    private class tutorialsButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0)
        {
            if (Desktop.isDesktopSupported())
            {
                Desktop d = Desktop.getDesktop();

                try
                {
                    d.browse(new URI("http://youtu.be/uA9mXOPZ4vk"));
                }
                catch (IOException | URISyntaxException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /*
     * This inner class is for the order slides... menu item.
     */
    private class orderSlideListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JFrame orderSlidesFrame = new JFrame();
            JTextArea input1 = new JTextArea("Please Enter Slide number");
            input1.setSize(5, 5);
            orderSlidesFrame.setLayout(new BorderLayout());

            orderSlidesFrame.setTitle("Order Slides");

            orderSlidesFrame.setSize(285, 250);

            orderSlidesFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            orderSlidesFrame.add(input1);
            orderSlidesFrame.setLocationRelativeTo(mainFrame);
            orderSlidesFrame.setVisible(true);
        }
    }

    /*
     * This inner class is for Printing the note Area.
     */
    private class printListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(mainFrame, "PRINTING.....");
        }
    }

    /*
     * Private class that handles recording lectures
     */
    private class recordListener implements ActionListener
    {
        public void actionPerformed(ActionEvent source)
        {
            JOptionPane.showMessageDialog(mainFrame, "Recording.....");
        }
    }

    /*
     * Private class to handle font size change in note area.
     */
    private class fontStyleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent source)
        {
            JToolBar toolBar = new JToolBar("HELLO");
            toolBar.setSize(new Dimension(30, 30));
        }
    }

     /*
     * To get the mainFrame GUI instance.
     */
    public static JFrame getMainWindow()
    {
        return  mainFrame;
    }

    /*
     * To get the slideLabel instance inside the mainFrame GUI.
     */
    public static JLabel getSlideLabel()
    {
        return slideLabel;
    }

    /*
     * To get the NoteArea JPanel.
     */
    static public JTextPane getNoteArea()
    {
        return noteArea;
    }
}
