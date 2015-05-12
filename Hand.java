import java.util.Vector;


public class Hand {
	Board link;
	Champion myChampion;
	Deck deck;
	Vector<Card> cards = new Vector();
	int size;
	boolean selectingCard;
	Card selecting;
	int selectingIndex;
	int star, nowstar;
	int nowx, nowy;
	
	
	Card ownSkill = new Card("", 2, 1, 0, 0, 0, true, false, true);
	
	
	Hand(boolean isComputer){
		this.size = 0;
		this.selectingCard = false;
		this.selecting = new Card(0, 0, 0, false);
		this.selectingIndex = -1;
		this.star = this.nowstar = 0;
		if(isComputer)this.nowy = 100;
		else this.nowy = 375;
		this.nowx = 220;
		this.ownSkill.type = 3;
	}
	
	void remove(int index){
		for(int i=index;i<this.size-1;i++){
			cards.elementAt(i).name = cards.elementAt(i+1).name;
			cards.elementAt(i).cost = cards.elementAt(i+1).cost;
			cards.elementAt(i).atk = cards.elementAt(i+1).atk;
			cards.elementAt(i).hp = cards.elementAt(i+1).hp;
			cards.elementAt(i).hpmax = cards.elementAt(i+1).hpmax;
			cards.elementAt(i).isCharge = cards.elementAt(i+1).isCharge;
			cards.elementAt(i).isTaunt = cards.elementAt(i+1).isTaunt;
			cards.elementAt(i).type = cards.elementAt(i+1).type;
			cards.elementAt(i).effect = cards.elementAt(i+1).effect;
			cards.elementAt(i).starVisible = true;
		}
		cards.removeElementAt(size-1);
		this.size--;
		nowx-=80;
	}
	
	void draw(){
		//TODO animation
		Card q = deck.randomDraw();
		if(q.name==""){
			myChampion.hp-=deck.demage;
			return;
		}
		if(size>=7)return;
		q.x = this.nowx;
		q.y = this.nowy;
		nowx+=80;
		cards.add(q);
		size++;
	}
	
	void insert(Card c){
		c.x = this.nowx;
		c.y = this.nowy;
		nowx+=80;
		cards.add(c);
		size++;
	}
}
