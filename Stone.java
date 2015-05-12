import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Stone extends JFrame implements ActionListener{
	static int card_size = 60;
	final int n_cards = 7;
	static int update_period = 50;
	boolean turn = true;	//true:player's, false:com's
	Deck pdeck, cdeck;
	Hand player, com; //TODO AI
	Board pboard, cboard;
	Champion pchamp, cchamp;
	ImageIcon ic = new ImageIcon("hp.jpg");
	Image hp = ic.getImage();
	ImageIcon ic2 = new ImageIcon("atk.jpg");
	Image atk = ic2.getImage();
	ImageIcon ic3 = new ImageIcon("star.jpg");
	Image star = ic3.getImage();
	JButton turnend;
	
	Stone(){
		super("MAPLESTONE");
		
		pdeck = new Deck();
		cdeck = new Deck();
		player = new Hand(false);
		com = new Hand(true);
		player.deck = pdeck;
		com.deck = cdeck;
		pchamp = new Champion(0, false);
		cchamp = new Champion(0, true);
		pboard = new Board(false);
		cboard = new Board(true);
		player.link = pboard;
		player.myChampion = pchamp;
		com.link = cboard;
		com.myChampion = cchamp;
		pboard.opp = cboard;
		pboard.goal = cchamp;
		pboard.hand = player;
		cboard.opp = pboard;
		cboard.goal = pchamp;
		cboard.hand = com;
		
		turnend = new JButton("TURN END");
		turnend.addActionListener(this);
		
		MyPanel panel = new MyPanel();
		add(panel);
		add(turnend, BorderLayout.EAST);

		setSize(1000, 600);
		setVisible(true);
		setResizable(false);
	}
	
	void Deal(Hand h, int num){
		for(int i=0;i<num;i++)h.draw();
	}
	
	class MyPanel extends JPanel implements MouseListener, ActionListener{
		int x, y;
		Card data;
		ImageIcon ic4;
		Image image;
		
		MyPanel(){
			addMouseListener(this);
			//player : first, 1star, 3+1cards // com : second, 1star, 3+1cards + specialstar
			turn = false;
			Deal(player, 3);
			Deal(com, 3);
			
			com.draw();
			com.insert(new Card(Spell.name[0], Spell.cost[0], Spell.type[0], Spell.effect[0]));
			nextturn();
			

			Timer timer = new Timer(update_period, this);
			timer.start();
		}
		
		public void drawCard(Graphics g, Image image, Card data, int size, boolean Conceal) {
			final int font = size/3;
			int x = data.x;
			int y = data.y;
			boolean focus = false;
			
			Point point = MouseInfo.getPointerInfo().getLocation();
			int nowy = 335;
			if(x<=point.x && point.x<=x+60 && y<=point.y && point.y<=y+90){
				focus = true;
			}
			
			if(Conceal){
				g.drawImage(image, x, y, size, size*3/2, this);
				return;
			}
			switch(data.type){
			case 0:case 1:
				g.setColor(Color.black);
				g.setFont(new Font("default", Font.BOLD, font));
				g.drawImage(image, x, y, size, size, this);
				g.drawImage(atk, x, y+size, size/2, size/2, this);
				g.drawString(data.atk+"", x+font/2, y+size+(size-font)/2);
				g.drawImage(hp, x+size/2, y+size, size/2, size/2, this);
				g.drawString(data.hp+"", x+font/2+size/2, y+size+(size-font)/2);
				if(focus){
					g.drawImage(image, 20, 200, 80, 80, this);
					g.drawImage(atk, 20, 280, 40, 40, this);
					g.drawString(data.atk+"", 33, 200+80+(80-26)/2);
					g.drawImage(hp, 60, 280, 40, 40, this);
					g.drawString(data.hp+"", 73, 200+80+(80-26)/2);
					g.setColor(new Color(165,42,42));
					nowy+=3;
					g.setFont(new Font("default", Font.BOLD, 18));
					g.drawString(data.name, 20, nowy);
					nowy+=15;
				}
				break;
			case 2:case 3:case 4:case 5:
				g.drawImage(image, x, y, size, size*3/2, this);
				if(focus){
					g.drawImage(image, 20, 200, 80, 120, this);
					switch(data.type){
					case 2:
						g.setColor(Color.black);
						g.setFont(new Font("default", Font.PLAIN, 15));
						g.drawString("카드 "+data.effect+"장 드로우", 20, nowy);
						nowy+=15;
						break;
					case 3:
						g.setColor(Color.black);
						g.setFont(new Font("default", Font.PLAIN, 15));
						if(data.effect>0)g.drawString("캐릭터에게 "+data.effect+"피해", 20, nowy);
						else g.drawString("캐릭터체력 "+(-data.effect)+"회복", 20, nowy);
						nowy+=15;
						break;
					case 4:
						g.setColor(Color.black);
						g.setFont(new Font("default", Font.PLAIN, 15));
						g.drawString("즉시 별"+data.effect+"개 얻음", 20, nowy);
						nowy+=15;
						break;
					case 5:
						g.setColor(Color.black);
						g.setFont(new Font("default", Font.PLAIN, 15));
						g.drawString("모든적에게 "+data.effect+"피해", 20, nowy);
						nowy+=15;
						break;
					}
				}
				break;
			}
			if(focus){
				g.setColor(Color.black);
				g.setFont(new Font("default", Font.BOLD, 26));
				g.drawImage(star, 60, 200, 40, 40, this);
				g.drawString(data.cost+"", 73, 227);
				if(data.isTaunt){
					g.setColor(Color.red);
					g.drawRect(20, 200, 80, 120);
				}
				if(data.isCharge){
					g.setColor(Color.magenta);
					g.fillOval(20, 200, 15, 15);
				}
				
				g.setColor(Color.black);
				if(data.isCharge){
					g.setFont(new Font("default", Font.BOLD, 15));
					g.drawString("돌진", 20, nowy);
					g.setFont(new Font("default", Font.PLAIN, 15));
					g.drawString("낸 턴에 공격가능", 20, nowy+15);
					nowy+=30;
				}
				if(data.isTaunt){
					g.setFont(new Font("default", Font.BOLD, 15));
					g.drawString("도발", 20, nowy);
					g.setFont(new Font("default", Font.PLAIN, 15));
					g.drawString("몬스터의 우선공격대상", 20, nowy+15);
					nowy+=30;
				}
			}
			if(data.starVisible){
				g.setColor(Color.black);
				g.setFont(new Font("default", Font.BOLD, font));
				g.drawImage(star, x+size/2, y, size/2, size/2, this);
				g.drawString(data.cost+"", x+size/2+font/2, y+(size-font)/2);
			}
			if(data.isTaunt){
				g.setColor(Color.red);
				g.drawRect(x, y, size, 3*size/2);
			}
			if((data.isCharge && data.inField == false) || (data.canAttack && data.inField)){
				g.setColor(Color.magenta);
				g.fillOval(x, y, 10, 10);
			}
			//real size : size * 3size/2
		}
		
		void printStar(Graphics g){
			g.setColor(Color.blue);
			for(int i=0;i<com.nowstar;i++)
				g.drawImage(new ImageIcon("fillstar.jpg").getImage(), 120, 20+50*i, 50, 50, this);
			for(int i=com.nowstar;i<com.star;i++)
				g.drawImage(new ImageIcon("blankstar.jpg").getImage(), 120, 20+50*i, 50, 50, this);

			for(int i=0;i<player.nowstar;i++)
				g.drawImage(new ImageIcon("fillstar.jpg").getImage(), 800, 500-50*i, 50, 50, this);
			for(int i=player.nowstar;i<player.star;i++)
				g.drawImage(new ImageIcon("blankstar.jpg").getImage(), 800, 500-50*i, 50, 50, this);
		}
		
		void printDeck(Graphics g){
			g.setColor(Color.black);
			g.setFont(new Font("default", Font.BOLD, 30));
			g.drawString(cdeck.left+"", 50, 60);
			g.drawRect(40, 25, 60, 45);
			g.drawString(pdeck.left+"", 50, 515);
			g.drawRect(40, 480, 60, 45);
		}
		
		int checkGameover(){
			if(pchamp.hp<=0 && cchamp.hp>0)return 1;	//com win
			if(cchamp.hp<=0 && pchamp.hp>0)return 2;	//player win
			if(pchamp.hp<=0 && pchamp.hp<=0)return 3;	//both lose
			return 0;									//not finished
		}
		
		void dieCheck(){
			for(int index=0;index<8;index++){
				if(pboard.cards.elementAt(index).hp<=0)pboard.remove(index);
				if(cboard.cards.elementAt(index).hp<=0)cboard.remove(index);
			}

			cboard.Taunt = false;
			for(int tt=0;tt<8;tt++){
				if(cboard.cards.elementAt(tt).name == "")continue;
				cboard.Taunt = cboard.Taunt | cboard.cards.elementAt(tt).isTaunt;
			}
			pboard.Taunt = false;
			for(int tt=0;tt<8;tt++){
				if(pboard.cards.elementAt(tt).name == "")continue;
				pboard.Taunt = pboard.Taunt | pboard.cards.elementAt(tt).isTaunt;
			}
		}
		
		@Override
		public void paintComponent(Graphics g){
			
			dieCheck();
			
			switch(checkGameover()){
			case 1:System.out.println("Computer WINS!");System.exit(0);break;
			case 2:System.out.println("Player WINS!");System.exit(0);break;
			case 3:System.out.println("Both LOSE!");System.exit(0);break;
			default:
			}
			
			super.paintComponent(g);
			
			g.setColor(Color.red);
			g.drawRect(pchamp.x, pchamp.y, pchamp.width, pchamp.height);
			g.drawRect(cchamp.x, cchamp.y, cchamp.width, cchamp.height);
			g.drawOval(pchamp.ownSkillx, pchamp.ownSkilly, pchamp.ownSkillwidth, pchamp.ownSkillheight);
			g.drawOval(cchamp.ownSkillx, cchamp.ownSkilly, cchamp.ownSkillwidth, cchamp.ownSkillheight);
			g.setColor(Color.black);
			g.setFont(new Font("default", Font.BOLD, 50));
			g.drawString(pchamp.hp+"", pchamp.x+120, pchamp.y+60);
			g.drawString(cchamp.hp+"", cchamp.x+120, cchamp.y+60);
			
			for(int index=0;index<player.size;index++){
				data = player.cards.elementAt(index);
				ic4 = new ImageIcon(data.name+".gif");
				if(!turn)ic4 = new ImageIcon("back.jpg");
				image = ic4.getImage();
				drawCard(g, image, data, Stone.card_size, !turn);
			}
			for(int index=0;index<com.size;index++){
				data = com.cards.elementAt(index);
				ic4 = new ImageIcon(data.name+".gif");
				if(turn)ic4 = new ImageIcon("back.jpg");
				image = ic4.getImage();
				drawCard(g, image, data, Stone.card_size, turn);
			}
			
			printStar(g);
			printDeck(g);
			
			if(player.selectingCard && TypeDef.toMyField[player.selecting.type]){
				for(int index=0;index<8;index++){
					data = pboard.cards.elementAt(index);
					if(data.name == ""){
						g.setColor(Color.green);
						g.fillRect(data.x, data.y, Stone.card_size, Stone.card_size*3/2);
					}
					else{
						ic4 = new ImageIcon(data.name+".gif");
						image = ic4.getImage();
						drawCard(g, image, data, Stone.card_size, false);
					}
				}
			}
			else for(int index=0;index<8;index++){
				data = pboard.cards.elementAt(index);
				data.starVisible = false;
				if(data.name == "")continue;
				ic4 = new ImageIcon(data.name+".gif");
				image = ic4.getImage();
				drawCard(g, image, data, Stone.card_size, false);
			}
			
			if(com.selectingCard && TypeDef.toMyField[com.selecting.type]){
				for(int index=0;index<8;index++){
					data = cboard.cards.elementAt(index);
					if(data.name == ""){
						g.setColor(Color.green);
						g.fillRect(data.x, data.y, Stone.card_size, Stone.card_size*3/2);
					}
					else{
						ic4 = new ImageIcon(data.name+".gif");
						image = ic4.getImage();
						drawCard(g, image, data, Stone.card_size, false);
					}
				}
			}
			else for(int index=0;index<8;index++){
				data = cboard.cards.elementAt(index);
				data.starVisible = false;
				if(data.name == "")continue;
				ic4 = new ImageIcon(data.name+".gif");
				image = ic4.getImage();
				drawCard(g, image, data, Stone.card_size, false);
			}
			
			/*System.out.println("player");
			for(int i=0;i<pboard.size;i++){
				if(pboard.cards.elementAt(i).canAttack){
					System.out.print(i + " ");
				}
			}
			System.out.println();
			System.out.println("com");
			for(int i=0;i<cboard.size;i++){
				if(cboard.cards.elementAt(i).canAttack){
					System.out.print(i + " ");
				}
			}
			System.out.println();*/
		}
		
		void clickHand(Hand h, int x, int y){
			for(int index=0;index<h.size;index++){
				if(h.cards.elementAt(index).x <= x && x <= h.cards.elementAt(index).x+60
					&& h.cards.elementAt(index).y <= y && y <= h.cards.elementAt(index).y+90){
					if(h.cards.elementAt(index).cost>h.nowstar)continue;
					h.cards.elementAt(index).starVisible = false;
					h.selectingCard = true;
					h.selecting = h.cards.elementAt(index);
					h.selectingIndex = index;
				}
				else h.cards.elementAt(index).starVisible = true;
			}
		}
		
		void clickBoard(Board b, int x, int y){
			for(int index=0;index<pboard.size;index++){
				if(b.cards.elementAt(index).x <= x && x <= b.cards.elementAt(index).x+60
						&& b.cards.elementAt(index).y <= y && y <= b.cards.elementAt(index).y+90){
					if(b.cards.elementAt(index).canAttack == false){
						System.out.println("해당 몬스터는 공격을 할 수 없습니다.");
						continue;
					}
					b.selectingCard = true;
					b.selecting = b.cards.elementAt(index);
					b.selectingIndex = index;
				}
			}
		}
		
		void clickOwnSkill(Hand h, int x, int y){
			if(Point.distance(x, y, h.myChampion.ownSkillx+h.myChampion.ownSkillwidth/2, h.myChampion.ownSkilly+h.myChampion.ownSkillheight/2) <= h.myChampion.height/2){
				if(h.ownSkill.cost <= h.nowstar && h.ownSkill.canAttack){
					h.selectingCard = true;
					h.selecting = h.ownSkill;
					h.selectingIndex = 10;
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point point = e.getPoint();
			int xx = (int)point.getX(); int yy = (int)point.getY();
			if(turn){
				clickHand(player, xx, yy);
				clickBoard(pboard, xx, yy);
				clickOwnSkill(player, xx, yy);
			}
			else{
				clickHand(com, xx, yy);
				clickBoard(cboard, xx, yy);
				clickOwnSkill(com, xx, yy);
			}
		}
		
		void releaseCard(Hand h, int x, int y){
			//h에서 x,y에 마우스를 놓았을때 카드 내기
			Board myBoard = h.link;
			Board oppBoard = myBoard.opp;
			h.selectingCard = false;
			if(myBoard.cards.elementAt(0).x<=x && x<=myBoard.cards.elementAt(7).x+60 
					&& myBoard.cards.elementAt(0).y<=y && y<=myBoard.cards.elementAt(0).y+90){
				if(TypeDef.nonTarget[h.selecting.type]){
					switch(h.selecting.type){
					case 2:
						int tmp = h.selecting.effect;
						h.remove(h.selectingIndex);
						for(int i=0;i<tmp;i++)h.draw();
						break;
					case 4:
						h.nowstar+=h.selecting.effect;
						h.remove(h.selectingIndex);
						break;
					case 5:
						for(int index=0;index<8;index++){
							fight(h.selecting, oppBoard.cards.elementAt(index));
						}
						fight(h.selecting, true);
						h.remove(h.selectingIndex);
						break;
					}
					return;
				}
			}
			for(int index=0;index<8;index++){
				if(myBoard.cards.elementAt(index).x<=x && x<=myBoard.cards.elementAt(index).x+60
						&& myBoard.cards.elementAt(index).y<=y && y<=myBoard.cards.elementAt(index).y+90){
					if(myBoard.cards.elementAt(index).name!="")continue;	//그 자리에 이미 뭐가 있음
					myBoard.cards.elementAt(index).name = h.selecting.name;
					myBoard.cards.elementAt(index).cost = h.selecting.cost;
					myBoard.cards.elementAt(index).atk = h.selecting.atk;
					myBoard.cards.elementAt(index).hp = h.selecting.hp;
					myBoard.cards.elementAt(index).hpmax = h.selecting.hpmax;
					myBoard.cards.elementAt(index).canAttack = h.selecting.isCharge;
					myBoard.cards.elementAt(index).isCharge = h.selecting.isCharge;
					myBoard.cards.elementAt(index).isTaunt = h.selecting.isTaunt;
					myBoard.cards.elementAt(index).type = 1;	//monster!
					myBoard.cards.elementAt(index).effect = h.selecting.effect;
					myBoard.Taunt = myBoard.Taunt | h.selecting.isTaunt;
					h.nowstar -= h.selecting.cost;
					
					int tmp = h.selecting.type;
					h.remove(h.selectingIndex);
					

					switch(tmp){
					case 0:case 1:break;
					case 3:
						//demage
						break;
					}
				}
			}
		}
		
		boolean attackCard(Hand h, Card c, int x, int y){
			Board b = h.link;
			Board oppBoard = b.opp;
			Champion oppChampion = b.goal;
			Champion myChampion = h.myChampion;
			boolean ret = false;
			
			if(TypeDef.toOppMonster[c.type])
				for(int index=0;index<8;index++){
					if(oppBoard.cards.elementAt(index).x<=x && x<=oppBoard.cards.elementAt(index).x+60
							&& oppBoard.cards.elementAt(index).y<=y && y<=oppBoard.cards.elementAt(index).y+90){
						if(oppBoard.cards.elementAt(index).name == "")continue;	//공격할 대상이 없음
						if(oppBoard.Taunt == true && oppBoard.cards.elementAt(index).isTaunt == false && TypeDef.careTaunt[c.type]){
							System.out.println("도발 능력이 있는 몬스터를 먼저 공격해야 합니다.");
							continue;
						}
						fight(c, oppBoard.cards.elementAt(index));
						ret = true;
					}
				}
			
			if(TypeDef.toOppChampion[c.type]){
				if(oppChampion.x <= x && x <= oppChampion.x+oppChampion.width
						&& oppChampion.y <= y && y <= oppChampion.y+oppChampion.height && TypeDef.toOppChampion[c.type]){
					if(oppBoard.Taunt == true && TypeDef.careTaunt[c.type])System.out.println("도발 능력이 있는 몬스터를 먼저 공격해야 합니다.");
					else{
						fight(c, true);
						ret = true;
					}
				}
			}
			
			if(TypeDef.toMyMonster[c.type])
				for(int index=0;index<8;index++){
					if(b.cards.elementAt(index).x<=x && x<=b.cards.elementAt(index).x+60
							&& b.cards.elementAt(index).y<=y && y<=b.cards.elementAt(index).y+90){
						if(b.cards.elementAt(index).name == "")continue;	//공격할 대상이 없음
						if(b.Taunt == true && b.cards.elementAt(index).isTaunt == false && TypeDef.careTaunt[c.type]){
							System.out.println("도발 능력이 있는 몬스터를 먼저 공격해야 합니다.");
							continue;
						}
						fight(c, b.cards.elementAt(index));
						ret = true;
					}
				}
			
			if(TypeDef.toMyChampion[c.type]){
				if(myChampion.x <= x && x <= myChampion.x+myChampion.width
						&& myChampion.y <= y && y <= myChampion.y+myChampion.height && TypeDef.toMyChampion[c.type]){
					if(b.Taunt == true && TypeDef.careTaunt[c.type])System.out.println("도발 능력이 있는 몬스터를 먼저 공격해야 합니다.");
					else{
						fight(c, false);
						ret = true;
					}
				}
			}
			
			b.selectingCard = false;
			h.selectingCard = false;
			return ret;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point point = e.getPoint();
			///System.out.println(player.selectingCard+"" + pboard.selectingCard + com.selectingCard + cboard.selectingCard);

			if(player.selectingCard && TypeDef.toMyField[player.selecting.type])releaseCard(player, (int)point.getX(), (int)point.getY());
			if(pboard.selectingCard && TypeDef.toOppMonster[pboard.selecting.type])attackCard(player, pboard.selecting, (int)point.getX(), (int)point.getY());
			if(player.selectingCard && TypeDef.toOppMonster[player.selecting.type]){
				boolean chk = attackCard(player, player.selecting, (int)point.getX(), (int)point.getY());
				if(chk){
					player.nowstar -= player.selecting.cost;
					if(player.selectingIndex<10)player.remove(player.selectingIndex);
				}
			}
			if(player.selectingCard && TypeDef.nonTarget[player.selecting.type])releaseCard(player, (int)point.getX(), (int)point.getY());
			
			if(com.selectingCard && TypeDef.toMyField[com.selecting.type])releaseCard(com, (int)point.getX(), (int)point.getY());
			if(cboard.selectingCard && TypeDef.toOppMonster[cboard.selecting.type])attackCard(com, cboard.selecting, (int)point.getX(), (int)point.getY());
			if(com.selectingCard && TypeDef.toOppMonster[com.selecting.type]){
				boolean chk = attackCard(com, com.selecting, (int)point.getX(), (int)point.getY());
				if(chk){
					com.nowstar -= com.selecting.cost;
					if(com.selectingIndex<10)com.remove(com.selectingIndex);
				}
			}
			if(com.selectingCard && TypeDef.nonTarget[com.selecting.type])releaseCard(com, (int)point.getX(), (int)point.getY());
			
			
			repaint();
		}
		
		void fight(Card c1, Card c2){
			//TODO animation
			c1.canAttack = false;
			if(c1.type == 1){
				MovingCard.data = c1;
				MovingCard.goal = c2;
				MovingCard.goalx = c2.x;
				MovingCard.goaly = c2.y;
				MovingCard.isMoving = true;
				MovingCard.nowx = c1.x;
				MovingCard.nowy = c1.y;
				MovingCard.speedx = (c2.x-c1.x)/(MovingCard.movingTime*1.0/Stone.update_period);
				MovingCard.speedy = (c2.y-c1.y)/(MovingCard.movingTime*1.0/Stone.update_period);
				MovingCard.retx = c1.x;
				MovingCard.rety = c1.y;
			}
			else{
				c1.hp -= c2.atk;
				c2.hp -= c1.atk;
				if(c1.hp>c1.hpmax)c1.hp = c1.hpmax;
				if(c2.hp>c2.hpmax)c2.hp = c2.hpmax;
			}
		}
		
		void fight(Card c, boolean toOpp){
			MovingCard.data = c;
			MovingCard.nowx = c.x;
			MovingCard.nowy = c.y;
			MovingCard.retx = c.x;
			MovingCard.rety = c.y;
			if(turn ^ toOpp){
				//pchamp
				if(c.type == 1){
					MovingCard.goal = new Card(0, pchamp.x, pchamp.y, false);
					MovingCard.goalChamp = pchamp;
					MovingCard.goalx = pchamp.x;
					MovingCard.goaly = pchamp.y;
					MovingCard.isMoving = true;
					MovingCard.speedx = (pchamp.x-c.x)/(MovingCard.movingTime*1.0/Stone.update_period);
					MovingCard.speedy = (pchamp.y-c.y)/(MovingCard.movingTime*1.0/Stone.update_period);
				}
				else pchamp.hp-=c.atk;
			}
			else{
				//cchamp
				if(c.type == 1){
					MovingCard.goal = new Card(0, cchamp.x, cchamp.y, false);
					MovingCard.goalChamp = cchamp;
					MovingCard.goalx = cchamp.x;
					MovingCard.goaly = cchamp.y;
					MovingCard.isMoving = true;
					MovingCard.speedx = (cchamp.x-c.x)/(MovingCard.movingTime*1.0/Stone.update_period);
					MovingCard.speedy = (cchamp.y-c.y)/(MovingCard.movingTime*1.0/Stone.update_period);
				}
				else cchamp.hp-=c.atk;
			}
			if(pchamp.hpmax<pchamp.hp)pchamp.hp = pchamp.hpmax;
			if(cchamp.hpmax<cchamp.hp)cchamp.hp = cchamp.hpmax;
			c.canAttack = false;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(MovingCard.isMoving)MovingCard.update();
			repaint();
		}
	}
	
	void nextturn(){
		//턴 넘기기
		turn = !turn;
		if(turn == true){
			//player's turn
			if(player.star<10)player.star++;
			player.nowstar = player.star;
			for(int i=0;i<pboard.size;i++){
				if(pboard.cards.elementAt(i).name!=""){
					pboard.cards.elementAt(i).canAttack = true;
				}
			}
			for(int i=0;i<cboard.size;i++)cboard.cards.elementAt(i).canAttack = false;
			player.ownSkill.canAttack = true;
			player.draw();
		}
		else{
			//com's turn
			if(com.star<10)com.star++;
			com.nowstar = com.star;
			for(int i=0;i<cboard.size;i++){
				if(cboard.cards.elementAt(i).name!=""){
					cboard.cards.elementAt(i).canAttack = true;
				}
			}
			for(int i=0;i<pboard.size;i++)pboard.cards.elementAt(i).canAttack = false;
			com.ownSkill.canAttack = true;
			com.draw();
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == turnend){
			nextturn();
			setTitle(turn?"Player's turn":"Computer's turn");
		}
	}
}
