
public class Card{
	String name;
	int cost, atk, hp, hpmax;
	int effect;
	int x, y;
	boolean starVisible;
	boolean canAttack;
	boolean inField;
	boolean isCharge, isTaunt;
	int type;	//0:monster
	Card(String name, int cost, int atk, int hp, int x, int y, boolean isCharge, boolean isTaunt, boolean inField){
		this.name = name;
		this.cost = cost;
		this.atk = atk;
		this.hp = hp;
		this.hpmax = hp;
		this.x = x;
		this.y = y;
		this.starVisible = true;
		this.canAttack = false;
		this.type = 0;
		this.isCharge = isCharge;
		this.isTaunt = isTaunt;
		this.inField = inField;
	}
	Card(String name, int cost, int type, int effect){
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.effect = effect;
		if(type == 3 || type == 5)this.atk = effect;
		this.starVisible = true;
	}
	Card(int index, int x, int y, boolean inField){
		this(CardList.name[index], CardList.cost[index], CardList.atk[index], CardList.hp[index], x, y,
				CardList.isCharge[index], CardList.isTaunt[index], inField);
	}
	//TODO ??
	/*Card clone(Card b){
		return b;
	}*/
}
