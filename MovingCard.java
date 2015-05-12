
public class MovingCard {
	static Card data, goal;
	static Champion goalChamp;
	static int goalx, goaly;
	static int movingTime = 1500;
	static double speedx, speedy;
	static double nowx, nowy;
	static int retx, rety;
	static boolean isMoving;
	
	static void update(){
		nowx += speedx;
		nowy += speedy;
		if(goalx == retx && goaly == rety){
			//returning
			if(Math.abs(nowx-goalx)<=Math.abs(speedx) || Math.abs(nowy-goaly)<=Math.abs(speedy)){
				isMoving = false;
				nowx = retx;
				nowy = rety;
			}
		}
		else if(Math.abs(goalx-nowx)<60 && Math.abs(goaly-nowy)<90){
			//isMoving = false;isMoving = true;
			if(goal.name!=""){
				data.hp -= goal.atk;
				goal.hp -= data.atk;
				if(data.hpmax<data.hp)data.hp = data.hpmax;
				if(goal.hpmax<goal.hp)goal.hp = goal.hpmax;
			}
			else{
				goalChamp.hp -= data.atk;
				if(goalChamp.hpmax<goalChamp.hp)goalChamp.hp = goalChamp.hpmax;
			}
			goalx = retx;
			goaly = rety;
			speedx *= -1;
			speedy *= -1;
		}
		data.x = (int)nowx;
		data.y = (int)nowy;
	}
}
