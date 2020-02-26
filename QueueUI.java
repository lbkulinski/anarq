import java.awt.*;
import java.util.PriorityQueue;
import javax.swing.*;
 
public class QueueUI extends JFrame {
 
   // Constructor to setup the GUI components and event handlers
   public QueueUI(PriorityQueue<SongRequest> queue) {
      Container cp = getContentPane();
      cp.setLayout(new FlowLayout());
 
      for (SongRequest song : queue) {
        cp.add(new JLabel(song.printSongInfo()));
      }
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("RequestQueue");
      setSize(800, 500);
      setVisible(true);
   }
 
   // The entry main() method
   public static void main(String[] args) {
      // Run the GUI construction in the Event-Dispatching thread for thread-safety
      
   }
}