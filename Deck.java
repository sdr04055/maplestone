import java.util.Vector;


public class Deck {
	Vector<Card> cards = new Vector();
	boolean isDrawed[];
	int size;
	int monsters = 15;
	int spells = 6;
	int left;
	int demage;
	Deck(){
		size = monsters + spells;
		//cards = new Card[size];
		isDrawed = new boolean[size];
		for(int i=0;i<monsters;i++){
			//cards[i] = new Card(i+1, 0, 0, false);
			cards.add(new Card(i+1, 0, 0, false));
			isDrawed[i] = false;
		}
		for(int i=1;i<=spells;i++){
			cards.add(new Card(Spell.name[i], Spell.cost[i], Spell.type[i], Spell.effect[i]));
			//cards[size-i] = new Card(Spell.name[i], Spell.cost[i], Spell.type[i], Spell.effect[i]);
		}
		left = size;
		demage = 0;
	}
	
	Card randomDraw(){
		if(left == 0){
			demage++;
			return (new Card(0, 0, 0, false));
		}
		int t = (int)Math.floor(Math.random()*size);
		while(isDrawed[t] == true){
			t = (int)Math.floor(Math.random()*size);
		}
		isDrawed[t] = true;
		left--;
		return cards.elementAt(t);
	}
	
	void insert(Card c){
		cards.add(c);
	}
}
