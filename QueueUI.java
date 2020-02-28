import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.swing.*;
 
public class QueueUI {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
 
   // Constructor to setup the GUI components and event handlers
   public QueueUI() {
    frame = new JFrame("RequestQueue");
    // If you running your program from cmd, this line lets it comes
    // out of cmd when you click the top-right  RED Button.
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();

    frame.setContentPane(panel);
    frame.setSize(800, 500);
    frame.setVisible(true);
   }

   public void populateQueue(PriorityQueue<SongRequest> queue) {
    frame.remove(panel);
    panel = new JPanel();
    frame.setContentPane(panel);
    ArrayList<SongRequest> temp = new ArrayList<SongRequest>();
    while (!queue.isEmpty()) {
        SongRequest song = queue.poll();
        temp.add(song);
        label = new JLabel(song.printSongInfo());
        panel.add(label);
    }
    frame.validate();
    frame.repaint();
    while (!temp.isEmpty()) {
        queue.add(temp.remove(0));
    }
   }
}