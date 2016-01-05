import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.text.StyleConstants;

public class ToolBar extends JPanel implements ActionListener
{
    boolean subscriptActive = false;
    boolean superscriptActive = false;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ToolBar()
    {
        super(new BorderLayout());
        JToolBar toolBar = new JToolBar("Still Draggable");
        toolBar.setSize(new Dimension(10, 30));
        toolBar.setOrientation(JToolBar.VERTICAL);

        addButtons(toolBar);

        setPreferredSize(new Dimension(75, 75));
        add(toolBar, BorderLayout.PAGE_START);

        setVisible(true);
    }

    private void addButtons(JToolBar toolBar)
    {

        JButton upperAbutton = makeNavigationButton("upperA", "INCREASE", "Increase Font Size",
                "Bigger Font");
        toolBar.add(upperAbutton);

        JButton lowerAbutton = makeNavigationButton("lowerA", "DECREASE", "Decrease Font Size",
                "Smaller Font");
        toolBar.add(lowerAbutton);

        JButton underlineButton = makeNavigationButton("underline", "UNDERLINE", "Underline Font",
                "Underline Font");
        toolBar.add(underlineButton);

        JButton superscriptButton = makeNavigationButton("superscript", "SUPERSCRIPT", "Superscript",
                "Supercript");
        toolBar.add(superscriptButton);
        
        JButton subscriptButton = makeNavigationButton("subscript", "SUBSCRIPT", "Subscript",
                "Subcript");
        toolBar.add(subscriptButton);

        JButton bulletButton = makeNavigationButton("bullet", "BULLET", "Insert Bullet ",
                "Insert Bullet");
        toolBar.add(bulletButton);

        JButton fontColorButton = makeNavigationButton("fontColor", "FONTCOLOR", "Font Color ",
                "Font Color");
        toolBar.add(fontColorButton);

        JButton highlightButton = makeNavigationButton("highlighter", "HIGHLIGHTER",
                "Highlight Font", "HighLight Font");
        toolBar.add(highlightButton);

    }

    protected JButton makeNavigationButton(String imageName, String actionCommand,
            String toolTipText, String altText)
    {

        URL imageURL = ClassLoader.getSystemResource("Resources/" + imageName + ".jpg");
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null)
        {
            try
            {
                Image img = ImageIO.read(imageURL);
                img = img.getScaledInstance(50, 50, 0);
                button.setIcon(new ImageIcon(img, altText));
            }
            catch (IOException e)
            {
                System.out.println("Cannot read image path");
                e.printStackTrace();
            }

        }
        else
        {
            button.setText(altText);
            System.err.println("Resource not Found: ");
        }

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand() == "INCREASE")
        {
            NotesArea.fontSize += 4;
            StyleConstants.setFontSize(NotesArea.biggerFont, NotesArea.fontSize);
            NotesArea.getPane().setCharacterAttributes(NotesArea.biggerFont, true);
        }
        else if (e.getActionCommand() == "DECREASE")
        {
            NotesArea.fontSize -= 4;
            StyleConstants.setFontSize(NotesArea.smallerFont, NotesArea.fontSize);
            NotesArea.getPane().setCharacterAttributes(NotesArea.smallerFont, true);
        }
        else if (e.getActionCommand() == "UNDERLINE")
        {
            StyleConstants.setUnderline(NotesArea.underline, true);
            NotesArea.getPane().setCharacterAttributes(NotesArea.underline, true);

        }
        else if (e.getActionCommand() == "BULLET")
        {
            StyleConstants.setIcon(NotesArea.bullet, new ImageIcon(NotesArea.bulletImg));
            NotesArea.getPane().setCharacterAttributes(NotesArea.bullet, true);
        }
        else if (e.getActionCommand() == "FONTCOLOR")
        {
            Color selectedColor;

            selectedColor = JColorChooser.showDialog(MainWindow.getMainWindow(),
                    "Choose Text Color", Color.BLACK);

            StyleConstants.setForeground(NotesArea.fontColor, selectedColor);
            NotesArea.getPane().setCharacterAttributes(NotesArea.fontColor, true);
        }
        else if (e.getActionCommand() == "HIGHLIGHTER")
        {
            Color selectedColor;

            selectedColor = JColorChooser.showDialog(MainWindow.getMainWindow(),
                    "Choose Highlight Color", Color.BLACK);

            StyleConstants.setBackground(NotesArea.background, selectedColor);
            NotesArea.getPane().setCharacterAttributes(NotesArea.background, true);
        }
        else if (e.getActionCommand() == "SUBSCRIPT")
        {

            if (subscriptActive == false)
            {
                StyleConstants.setSubscript(NotesArea.subscript, true);
                NotesArea.getPane().setCharacterAttributes(NotesArea.subscript, true);
                subscriptActive = true;
            }
            else
            {
                StyleConstants.setSubscript(NotesArea.subscript, false);
                NotesArea.getPane().setCharacterAttributes(NotesArea.subscript, true);
                subscriptActive = false;
            }

        }
        else if (e.getActionCommand() == "SUPERSCRIPT")
        {

            if (superscriptActive == false)
            {
                StyleConstants.setSuperscript(NotesArea.superscript, true);
                NotesArea.getPane().setCharacterAttributes(NotesArea.superscript, true);
                superscriptActive = true;
            }
            else
            {
                StyleConstants.setSuperscript(NotesArea.superscript, false);
                NotesArea.getPane().setCharacterAttributes(NotesArea.superscript, true);
                superscriptActive = false;
            }

        }


    }
}
