import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * A Individual slide object.
 */
public class Slide 
{
    Image slide;
    int slideNumber;
    
    Slide(Path selectedFile)
    {
      try
      {
        this.slide = ImageIO.read(selectedFile.toFile());
      }
      catch (IOException e)
      {  
          System.out.println("Cannot Read Image..");
          e.printStackTrace();
      }
    }
    
    public Slide getSlide()
    {
        return this;
    }
    public Icon getImage()
    {                                                       
        return new ImageIcon(getSlide().slide.getScaledInstance(700, 500, 0));
    }
    
    public Icon resizeImage()
    {
        return  new ImageIcon(getSlide().slide.getScaledInstance(200, 200, 0));
    }
    
    public void setSlideNumber(int num)
    {
        this.slideNumber = num;
    }
    
    public int getSlideNumber()
    {
        return slideNumber;
    }
}
