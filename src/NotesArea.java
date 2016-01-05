import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/*
 * Class to handle the Notes in noteTaker 1.0.
 */
public class NotesArea 
{
    static DefaultStyledDocument doc;
    static StyleContext context;
    static DefaultStyledDocument defaultDoc;
    static JTextPane pane;
    static Style italic;
    static Style underline;
    static Style biggerFont;
    static Style smallerFont;
    static Style superscript;
    static Style subscript;
    static Style background;
    static Style fontColor;
    static int fontSize;
    static Style bullet; 
    static Image bulletImg;
    JPanel panel; 
    JScrollPane scroll;

    NotesArea()
    {
        context = new StyleContext();
        
        doc = new DefaultStyledDocument(context);
        
        pane = new JTextPane(doc);
        
        this.panel = new JPanel();
        this.scroll = new JScrollPane(pane);
        
        underline = context.addStyle("underline", null);
        biggerFont = context.addStyle("bigger", null);
        smallerFont = context.addStyle("smaller", null);
        superscript = context.addStyle("superscript", null);
        subscript = context.addStyle("subscript", null);
        background = context.addStyle("background", null);
        fontColor = context.addStyle("fontColor", null);
        
        fontSize = 22;
        
        setStyles(); 
        setScrollPaneRules();
       
        panel.add(scroll); 
        panel.setVisible(true);    
    }
    
    public void setScrollPaneRules()
    {
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(400, 600));
    }
    public void setStyles() 
    {
       // StyleConstants.setItalic(italic, true);
        StyleConstants.setUnderline(underline, true);
        StyleConstants.setFontSize(biggerFont, fontSize);
        StyleConstants.setFontSize(smallerFont, fontSize);
        StyleConstants.setSuperscript(superscript, true);
        StyleConstants.setSubscript(subscript, true);
        StyleConstants.setBackground(background, Color.YELLOW);
        StyleConstants.setForeground(fontColor, Color.BLACK);
        
        bullet = context.addStyle("bullet", null);
        
        
        try
        {
            Image temp = ImageIO.read(ClassLoader.getSystemResource("Resources/bullet.jpg"));
            bulletImg = temp.getScaledInstance(20, 20, 0);
            StyleConstants.setIcon(bullet, new ImageIcon(bulletImg));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       
    }
       
    static public void increaseFontSize()
    {
        fontSize = fontSize++;
    }
    
    public static void decreaseFontSize()
    {
        NotesArea.fontSize = fontSize--;
    }
    
    public static JTextPane getPane()
    {
        return pane;
    }
}
