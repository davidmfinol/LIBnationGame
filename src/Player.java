import java.awt.*;
import java.util.Random;
/**Player.java
 *The Representation of a Player for the Game
 *@author David Finol 
 *@version 1.0 12/17/06
*/
public class Player
{
	private int width, height, X, Y, player, health, atk, defense, speed, range, teles;
	private Image sprite, right1, right2, left1, left2;
	private Image jumpLeft, jumpRight, fallLeft, fallRight;
	private Image attackLeft1, attackLeft2, attackRight1, attackRight2;
	private Image damageLeft, damageRight, die;
	private Random teleporter;
	private boolean dead;
/**Constructs a player with the name and stats specified
 *@param l1 there are so many images, I won't state them all, use common sense
 *@param l2 for example, this is the image for moving left2
 *@param name the name of the player(mossflower, nick, etc)
 *@param w the width size of the player
 *@param h the height size of the player
 *@param p the player character (player 1 or player 2)
 *@param hp the health of the player
 *@param at the atk stat of the player
 *@param def the defense stat of the player
 *@param s the movement speed stat of the player
 *@param r the attack range stat of the player
 *@param t the number of teleports available to the player per game
 */
	public Player(Image l1, Image l2, Image r1, Image r2, Image jl, Image jr, Image fl,
			Image fr, Image al1, Image al2, Image ar1, Image ar2, Image dl, Image dr, Image dd,
			String name, int w, int h, int p, int hp, int at, int def, int s, int r, int t)
	{
		//loads the paramaters, there are many
		left1 = l1;
		left2 = l2;
		right1 = r1;
		right2 = r2;
		jumpLeft = jl;
		jumpRight = jr;
		fallLeft = fl;
		fallRight = fr;
		attackLeft1 = al1;
		attackLeft2 = al2;
		attackRight1 = ar1;
		attackRight2 = ar2;
		damageLeft = dl;
		damageRight = dr;
		die = dd;
		teleporter = new Random();
		width = w;
		height = h;
		player = p;
		if(player == 1)
		{
			X = 0;
			sprite = right1;
		}
		else
		{
			X = 500-width;
			sprite = left1;
		}
		Y = 375-height;
		health = hp;
		atk = at;
		defense = def;
		speed = 2*s;
		range = 70*r;
		teles = t;
		dead = false;
	}
/**Gets the current image of the player
 *@return the image
 */
	public Image getSprite()
	{
		return sprite;
	}
/**Gets the current health of the player
 *@return the health
 */
	public String getHealth()
	{
		return ""+health+"";
	}
/**Gets the attack stat of the player
 *@return the atk stat
 */
	public int getAtk()
	{
		return atk;
	}
/**Gets the defense stat of the player
 *@return the defense stat
 */
	public int getDef()
	{
		return defense;
	}
/**Gets the width size of the player image
 *@return the width
 */
	public int getWidth()
	{
		return width;
	}
/**Gets the height size of the player image
 *@return the height
 */
	public int getHeight()
	{
		return height;
	}
/**Gets the location of the player on the X axis
 *@return the X-axis location
 */
	public int getX()
	{
		return X;
	}
/**Gets the location of the player on the Y axis
 *@return the Y-axis location
 */
	public int getY()
	{
		return Y;
	}
/**Moves the player to the right
 */
	public void moveRight()
	{
		if(sprite == right1)//chooses correct image
			sprite = right2;
		else
			sprite = right1;
		if(X <= 500-getWidth())//stops from going off screend]
			X+= speed;
	}
/**Moves the player to the left
 */
	public void moveLeft()
	{
		if(sprite == left1)//chooses correct animation
			sprite = left2;
		else
			sprite = left1;
		if(X >= 0)//stops from going off the screen
			X-= speed;
	}
/**Makes the player jump up
 */
	public void jump()
	{
		if(sprite==left1||sprite==left2||sprite==attackLeft1||sprite==attackLeft2)
			sprite = jumpLeft;//chooses correct animation
		else if(sprite==fallLeft||sprite==jumpLeft||sprite==damageLeft)
			sprite = jumpLeft;
		else if(sprite==right1||sprite==right2||sprite==attackRight1||sprite==attackRight2)
			sprite = jumpRight;
		else if(sprite==fallRight||sprite==jumpRight||sprite==damageRight)
			sprite = jumpRight;
		if(Y >= 0)//stops form going off screen
			Y-= 40;
	}
/**Keeps the character falling down
 */
	public void gravity()
	{
		if(Y <= 375-getHeight()-20)
			Y+= 20;
	}
/**Teleports the player to a random spot
 */
	public void teleport()
	{
		if(teles!= 0)
		{
			//chooses random location to tele
			X = teleporter.nextInt(500-getWidth());
			teles-= 1;
		}
	}
/**Heals the player
 */
	public void heal()
	{
		health+= 1;
	}
/**Attacks the enemy player
 *@param the player that you are trying to attack
 */
	public void attack(Player enemy)
	{
		//chooses the correct sprite
		if(sprite==left1||sprite==left2||sprite==attackLeft2)
			sprite = attackLeft1;//chooses correct animation
		else if(sprite==fallLeft||sprite==jumpLeft||sprite==damageLeft)
			sprite = attackLeft1;
		else if(sprite == attackLeft1)
			sprite = attackLeft2;
		else if(sprite==right1||sprite==right2||sprite==attackRight2)
			sprite = attackRight1;
		else if(sprite==fallRight||sprite==jumpRight||sprite==damageRight)
			sprite = attackRight1;
		else if(sprite == attackRight1)
			sprite = attackRight2;
		//checks to see if in range
		for(int check=0; check<=range; check++)
			if(getX()==enemy.getX()+(range-check) || getX()==enemy.getX()-(range-check))
				for(int y=0;y<=enemy.getHeight();y++)
				 	if(getY()==enemy.getY()+(enemy.getHeight()-y) || getY()==enemy.getY()-(enemy.getHeight()-y)) 
						enemy.damage(this);
	}
/**Loses health for the player when attacked
 *@param the player who attacked you
 */
	private void damage(Player attacker)
	{
		if(sprite==left1||sprite==left2||sprite==attackLeft1||sprite==attackLeft2)
			sprite = damageLeft;//chooses correct animation
		else if(sprite==fallLeft||sprite==jumpLeft||sprite==damageLeft)
			sprite = damageLeft;
		else if(sprite==right1||sprite==right2||sprite==attackRight1||sprite==attackRight2)
			sprite = damageRight;
		else if(sprite==fallRight||sprite==jumpRight||sprite==damageRight)
			sprite = damageRight;
		health-= attacker.getAtk();//needs to be modified
		if(health <= 0)//checks to see if dead
		{
			health = 0;
			die();
		}
	}
/**Causes the player to use death sprite
 */
	private void die()
	{
		sprite = die;
		dead = true;
	}
/**Checks to see if the player is dead
 *@return the death status of the player
 */
	public boolean isDead()
	{
		return dead;
	}
}