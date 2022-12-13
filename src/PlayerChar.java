public class PlayerChar {
    public int HP;//health
    public int AT;//attack
    public int DF;//defense
    public String name; //p1, p2
public PlayerChar(int health,int defense,int attack){
    super();
    this.AT = attack;
    this.DF = defense;
    this.HP = health;
}
    //attack
    public int attack(PlayerChar target){
        int damage=this.AT*2;
        int damageDealt=target.takedamage(damage);
        return damageDealt;
    }
    //taking hits
    public int takedamage(int damage){
        int damageTaken=damage-this.DF;
        System.out.println(damageTaken);
        if(damageTaken<0){
            damageTaken = 0;
        }

        HP=HP-damageTaken;
        return HP;
    }
    //am i alive???NO
    public boolean isAlive(){
        return this.HP > 0;
    }
}
