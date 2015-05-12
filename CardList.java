
public class CardList{
	static int size = 15;
	static String name[] = {"", "달팽이", "파란달팽이", "빨간달팽이", "스포아", "스텀프", "슬라임", "돼지", "다크스텀프", "엑스텀프", "다크엑스텀프", "버블링", "주황버섯", "초록버섯", "뿔버섯", "리본돼지"};
	static int atk[] = {    0 , 2     , 2       ,  4       , 4     , 1    , 3     , 2    , 3       , 5      , 4         , 5     , 2      , 7      , 6     , 7      };
	static int hp[] = {     0 , 1     , 3       ,  5       , 1     , 5    , 3     , 4    , 5       , 4      , 8         , 4     , 1      , 4      , 6     , 8      };
	static int cost[] = {   0 , 1     , 2       ,  4       , 4     , 3    , 3     , 3    , 4       , 5      , 6         , 5     , 2      , 5      , 7     , 8      };
	static boolean isCharge[] = {false, false, false, false, true  , false, true  , true , false   , false  , false     , true  , true   , false  , true  , true   };
	static boolean isTaunt[] = {false, false, false, false , false , true , false , false, true    , true   , true      , false , false  , false  , true  , false  };
}

class Spell{
	static String name[] = { "star1", "draw2", "draw4", "demage2", "demage6", "heal8", "all1"};
	static int cost[] = {          0, 2      , 5      , 1        , 4        , 3      , 2     };
	static int type[] = {          4, 2      , 2      , 3        , 3        , 3      , 5     };
	//0:손 안의 몬스터, 1:필드 위의 몬스터 , 2:카드 드로우, 3:피해를, 4:스타얻기, 5:광역딜
	static int effect[] = {        1, 2      , 4      , 2        , 6        , -8     , 1     };
}

class TypeDef{
	static boolean toMyField[] = {true   , false, false, false, false, false};
	static boolean toMyMonster[] = {false, false, false, true , false, false};
	static boolean toMyChampion[]= {false, false, false, true , false, false};
	static boolean toOppField[] = {false , false, false, false, false, false};
	static boolean toOppMonster[] = {false, true, false, true , false, false};
	static boolean toOppChampion[]={false, true , true , true , false, false};
	static boolean careTaunt[] = {false  , true , false, false, false, false};
	static boolean nonTarget[] = {false  , false, true , false, true , true };
}
