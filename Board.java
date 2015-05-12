import java.util.Vector;


public class Board {
	Board opp;
	Champion goal;
	Hand hand;
	Vector<Card> cards = new Vector();
	int size = 8;
	boolean selectingCard;
	boolean Taunt;
	Card selecting;
	int selectingIndex;
	Board(boolean isComputer){
		for(int i=0;i<size;i++)cards.add(new Card(0, 75*i+195, isComputer?190:285, true));
		this.size = size;
		this.selectingCard = false;
		this.selecting = new Card(0, 0, 0, true);
		this.selectingIndex = -1;
	}
	
	void remove(int index){
		this.cards.elementAt(index).name = "";
	}
}
