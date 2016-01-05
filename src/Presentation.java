import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * Class to handle functionality of presentations objects.
 */
public class Presentation
{
    ArrayList<Slide> slides;
    File  files[];
    JFrame mainFrame;
    int slideTotal = 0;
    int slideCounter = 0;
    JLabel slideNumber;
     
     /*
      * Constructor for Presentation Objects.
      */
     Presentation(Path dir, JFrame jFrame)
     {
        slides = new ArrayList<Slide>();
        this.mainFrame = jFrame;
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
           int slideNumber = 1;
            for (Path file: stream)
            {
                String fileName = file.getFileName().toString();
                
                if(fileName.toLowerCase().endsWith(".png") 
                        || fileName.toLowerCase().endsWith(".jpg") 
                        || fileName.toLowerCase().endsWith(".gif"))
                   {      
                     Slide temp = new Slide(file.toAbsolutePath());
                     temp.setSlideNumber(slideNumber);
                     slides.add(temp);
                     slideNumber++;
                     slideTotal++;
                   }
            }
            
            if(slides.isEmpty())
            {
                JOptionPane.showMessageDialog(mainFrame, "No .png, .jpg, or .gif files found\n"
                        + "in choosen directory");
            }
        } 
        catch (Exception ex) 
        {
            System.err.println(ex);
        }
     }
     
    public void loadFirstSlide()
     {
       MainWindow.getSlideLabel().setIcon(slides.get(slideCounter).getImage());
       slideNumber = new JLabel("Slide " + slides.get(slideCounter).getSlideNumber());
       MainWindow.getMainWindow().add(slideNumber,BorderLayout.SOUTH);
       MainWindow.getMainWindow().pack();
     }
       
     /*
      * Loads image into slideArea.
      */
     public void loadRightSlide()
     {
         slideCounter++;
         if(!(slideCounter >= 0 && slideCounter < slideTotal)) 
         {
            slideCounter--;
            return;
         }
         
         MainWindow.getSlideLabel().setIcon(slides.get(slideCounter).getImage());
         
         slideNumber.setText("Slide " + slides.get(slideCounter).getSlideNumber());       
     }
     
     /*
      * Loads image into slideArea.
      */
     public void loadLeftSlide()
     {
         slideCounter--;
         if(!(slideCounter >= 0 && slideCounter < slideTotal)) 
         {
            slideCounter++;
            return;
         }

         MainWindow.getSlideLabel().setIcon(slides.get(slideCounter).getImage());
         slideNumber.setText("Slide " + slides.get(slideCounter).getSlideNumber());   
     }        
}

