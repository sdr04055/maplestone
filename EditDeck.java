import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JFrame;


public class EditDeck extends JFrame{
	EditDeck(){
		super("Edit Deck!");
		
		//FileIO();

		setSize(1000, 600);
		setVisible(true);
		setResizable(false);
	}
	
	void FileIO(){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
			writer.write("Hello");
			writer.close();
		}catch(Exception ex){
			
		}
	}
}
