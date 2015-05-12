
public class Champion {
	int x,y;
	int width, height;
	String name;
	int hp, hpmax;
	int ownSkillx, ownSkilly, ownSkillwidth, ownSkillheight;
	//size : 200*80
	Champion(int job, boolean isComputer){
		//TODO job, own skill
		if(isComputer){
			this.name = "com";
			this.x = 400;
			this.y = 10;
			this.ownSkillx = 290;
			this.ownSkilly = 10;
			this.ownSkillwidth = 80;
			this.ownSkillheight = 80;
		}
		else{
			this.name = "player";
			this.x = 400;
			this.y = 475;
			this.ownSkillx = 630;
			this.ownSkilly = 475;
			this.ownSkillwidth = 80;
			this.ownSkillheight = 80;
		}
		this.hp = 20;
		this.hpmax = 20;
		this.width = 200;
		this.height = 80;
	}
}
