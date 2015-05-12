import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainPage extends JFrame implements ActionListener{
	JButton gameStart, editDeck;
	MainPage(){
		gameStart = new JButton("Game Start");
		gameStart.addActionListener(this);
		add(gameStart);
		
		editDeck = new JButton("Edit My Deck");
		editDeck.addActionListener(this);
		add(editDeck, BorderLayout.EAST);
		
		pack();
		super.setLocation(500, 300);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gameStart){
			this.dispose();
			new Stone();
		}
		if(e.getSource() == editDeck){
			//TODO edit Deck
			this.dispose();
			new EditDeck();
			//for(int i=1;i<=CardList)
		}
	}
}
