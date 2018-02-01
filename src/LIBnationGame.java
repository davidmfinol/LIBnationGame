package libnation;

import javax.swing.JApplet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import sun.awt.AppContext;  
import sun.awt.SunToolkit;

/**
 *@(#)LIBnationGame.java
 *
 *The Original Fighting Game
 *
 *@author David Finol
 *@version 1.0 12/17/06
*/
public class LIBnationGame extends JApplet implements Runnable
{
	private Thread GameRun;//keeps the the game running
	private final int APPLET_WIDTH = 500;
	private final int APPLET_HEIGHT = 375;
	private Image background, backbuffer;//sets the background image
	private AudioClip music;//background, later coming attack and hurt sounds
	private Player p1, p2;
	private String name1 = "Mossflower", name2 = "Nick";//character select
	private String level = "Arrival_of_Dawn", victory;//causes level select
	private String healthsign1, healthsign2;//health output
	private int p1width = 45, p1height = 30, p1atk = 4, p1def = 2, p1speed = 5, p1range = 1, health = 500;
	private int p2width = 40, p2height = 40, p2atk = 3, p2def = 2, p2speed = 1, p2range = 5, teles = 10;
	private boolean gravity, offscreen, dissappear, teleport, team, com;//features and options
	private boolean GameRunning, spec1, spec2, death1, death2;//in-game events
	private boolean p1moveLeft, p1moveRight, p1jump, p1fall, p1attack, p1tele;//boolean flags
	private boolean p2moveLeft, p2moveRight, p2jump, p2fall, p2attack, p2tele;//for moving at the same time
	private Graphics backg;//prevents flickering
	private Graphics2D Stringmaker;//for health and names
	private Rectangle2D bounds, bounds2;//to find where to put names & health
	private Font font = new Font("SansSerif", Font.BOLD+Font.ITALIC, 24);//for health & names
	private FontRenderContext context;//for outputting names & health
	private KeyListener Controller;
/**The control movements for a player
 */
	class Controls implements KeyListener
	{
/**Unused Method
 *@param the key typed
 */
		public void keyTyped (KeyEvent typed)
		{
            System.out.println("keytyped " + typed.toString());
		}
/**Controls the characters (starts movement)
 *@param the key pressed
 */
		public void keyPressed (KeyEvent pressed)
		{
            System.out.println("keyPressed " + pressed.toString());
			if(pressed.getKeyCode() == KeyEvent.VK_A)
				p1moveLeft = true;
			if(pressed.getKeyCode() == KeyEvent.VK_W)
				p1jump = true;
			if(pressed.getKeyCode() == KeyEvent.VK_D)
				p1moveRight = true;
			if(pressed.getKeyCode() == KeyEvent.VK_S)
				p1fall = true;
			if(pressed.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
				p1attack = true;
			if(pressed.getKeyCode() == KeyEvent.VK_F)
				p1tele = true;
			if(pressed.getKeyCode() == KeyEvent.VK_LEFT)
				p2moveLeft = true;
			if(pressed.getKeyCode() == KeyEvent.VK_UP)
				p2jump = true;
			if(pressed.getKeyCode() == KeyEvent.VK_RIGHT)
				p2moveRight = true;
			if(pressed.getKeyCode() == KeyEvent.VK_DOWN)
				p2fall = true;
			if(pressed.getKeyCode() == KeyEvent.VK_NUMPAD0)
				p2attack= true;
			if(pressed.getKeyCode() == KeyEvent.VK_CONTROL)
				p2tele = true;
		}
/**Causes the the player to stop moving
 *@param the key released
 */
		public void keyReleased (KeyEvent released)
		{
            System.out.println("keyreleased " + released.toString());
			if(released.getKeyCode() == KeyEvent.VK_A)
				p1moveLeft = false;
			if(released.getKeyCode() == KeyEvent.VK_W)
				p1jump = false;
			if(released.getKeyCode() == KeyEvent.VK_D)
				p1moveRight = false;
			if(released.getKeyCode() == KeyEvent.VK_S)
				p1fall = false;
			if(released.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
				p1attack = false;
			if(released.getKeyCode() == KeyEvent.VK_F)
				p1tele = false;
			if(released.getKeyCode() == KeyEvent.VK_LEFT)
				p2moveLeft = false;
			if(released.getKeyCode() == KeyEvent.VK_UP)
				p2jump = false;
			if(released.getKeyCode() == KeyEvent.VK_RIGHT)
				p2moveRight = false;
			if(released.getKeyCode() == KeyEvent.VK_DOWN)
				p2fall = false;
			if(released.getKeyCode() == KeyEvent.VK_NUMPAD0)
				p2attack= false;
			if(released.getKeyCode() == KeyEvent.VK_CONTROL)
				p2tele = false;
		}
	}
/**Initializes the game by loading all necessary files
 */
	public void init ()
	{
		setSize (APPLET_WIDTH, APPLET_HEIGHT);
		setFocusable(true);
		background = getImage (getCodeBase(), level+".jpg");
		music = getAudioClip (getCodeBase(), level+".au");
		backbuffer = createImage(APPLET_WIDTH, APPLET_HEIGHT);
		backg = backbuffer.getGraphics();
		Stringmaker = (Graphics2D) backg; //helps with context
		context = Stringmaker.getFontRenderContext(); //helps with drawP1&P2
		backg.setFont (font);
		Controller = new Controls();
		GameRun = new Thread(this);
	}
/**Starts the game by starting the thread looping the music
 */
	public void start ()
	{
		if(AppContext.getAppContext() == null)
			SunToolkit.createNewAppContext();
		GameRunning = true;
		p1 = new Player(getImage (getCodeBase(), name1+"l1.png"), getImage (getCodeBase(), name1+"l2.png"),
			getImage (getCodeBase(), name1+"r1.png"), getImage (getCodeBase(), name1+"r2.png"),
			getImage (getCodeBase(), name1+"jl.png"), getImage (getCodeBase(), name1+"jr.png"),
			getImage (getCodeBase(), name1+"fl.png"), getImage (getCodeBase(), name1+"fr.png"),
			getImage (getCodeBase(), name1+"al1.png"),getImage (getCodeBase(), name1+"al2.png"),
			getImage (getCodeBase(), name1+"ar1.png"),getImage (getCodeBase(), name1+"ar2.png"),
			getImage (getCodeBase(), name1+"dl.png"), getImage (getCodeBase(), name1+"dr.png"),
			getImage (getCodeBase(), name1+"die.png"),
			name1, p1width, p1height, 1, health, p1atk, p1def, p1speed, p1range, teles);
		p2 = new Player(getImage (getCodeBase(), name2+"l1.png"), getImage (getCodeBase(), name2+"l2.png"),
			getImage (getCodeBase(), name2+"r1.png"), getImage (getCodeBase(), name2+"r2.png"),
			getImage (getCodeBase(), name2+"jl.png"), getImage (getCodeBase(), name2+"jr.png"),
			getImage (getCodeBase(), name2+"fl.png"), getImage (getCodeBase(), name2+"fr.png"),
			getImage (getCodeBase(), name2+"al1.png"),getImage (getCodeBase(), name2+"al2.png"),
			getImage (getCodeBase(), name2+"ar1.png"),getImage (getCodeBase(), name2+"ar2.png"),
			getImage (getCodeBase(), name2+"dl.png"), getImage (getCodeBase(), name2+"dr.png"),
			getImage (getCodeBase(), name2+"die.png"),
			name2, p2width, p2height, 2, health, p2atk, p2def, p2speed, p2range, teles);
		music.loop();
		addKeyListener(Controller);//adds controls for game
		GameRun.start();//starts the game
		requestFocusInWindow();//tries to make sure we get input
	}
/**Runs a game loop(thread) to for the game to occur in with a time limit
 */
 	public void run ()
 	{
 		try
 		{
 			while(GameRunning)
 			{
 				update();
 				repaint();
 				GameRun.sleep(50);
 			}
 			removeKeyListener(Controller);//stop the player controls
 		}
 		catch (InterruptedException e) {}
 	}
/**Handles all the game logic and movements of the characters
 */
	public void update ()
	{
		if(p1moveLeft)//allows  same time movement
			p1.moveLeft();
		if(p1moveRight)
			p1.moveRight();
		if(p1attack)
			p1.attack(p2);
		if(p1jump)
			p1.jump();
		if(p1tele)
			p1.teleport();
		if(p2moveLeft)
			p2.moveLeft();
		if(p2moveRight)
			p2.moveRight();
		if(p2attack)
			p2.attack(p1);
		if(p2jump)
			p2.jump();
		if(p2tele)
			p2.teleport();
		if(p1.isDead() && p2.isDead())//victory signs
		{
			victory = "You have tied.";
			GameRunning = false;
		}
		else if(p1.isDead())
		{
			victory = name2+" has won.";
			GameRunning = false;
		}
		else if(p2.isDead())
		{
			victory = name1+" has won.";
			GameRunning = false;
		}
		p1.gravity();
		p2.gravity();
	}
/**Draws the screen
 *@param screen the screen that is drawn on
 */
	public void paint (Graphics screen)
	{
		drawLevel();
		drawHealth();
		drawPlayers();
		if((p1 != null && p1.isDead()) || (p2 != null && p2.isDead()))
		{
			backg.setColor(Color.RED);
			backg.drawString (victory, 187, 250);
		}
		screen.drawImage(backbuffer, 0, 0, this );
	}
/**Draws the health
 */
	public void drawHealth ()
	{
		backg.setColor(Color.blue);
		if(p1 != null && p1.isDead())
			backg.setColor(Color.RED);
		bounds = font.getStringBounds(name1, context); //to find where to put string
		backg.drawString (name1, 0, (int) bounds.getHeight());
		healthsign1 = p1 != null ? p1.getHealth() : String.valueOf(health);
		bounds2 = font.getStringBounds(healthsign1, context);
		backg.drawString (healthsign1, 0, (int) bounds.getHeight()+ (int) bounds2.getHeight());
		backg.setColor(Color.blue);
		if(p2 != null && p2.isDead())
			backg.setColor(Color.RED);
		bounds = font.getStringBounds(name2, context); //to find where to put strings
		backg.drawString (name2, APPLET_WIDTH- (int) bounds.getWidth(), (int) bounds.getHeight());
		healthsign2 = p2 != null ? p2.getHealth() : String.valueOf(health);
		bounds2 = font.getStringBounds(healthsign2, context);
		backg.drawString (healthsign2, APPLET_WIDTH- (int) bounds2.getWidth(), (int) bounds.getHeight()+ (int) bounds2.getHeight());
	}
/**Draws the players
 */
	public void drawPlayers ()
	{
		if(p1 != null)
			backg.drawImage (p1.getSprite(), p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight(), this);
		if(p2 != null)
			backg.drawImage (p2.getSprite(), p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight(), this);
	}
/**Draws the level and plays out the effects it has on the players
 */
 	public void drawLevel()
 	{
		backg.drawImage(background, 0, 0, APPLET_WIDTH, APPLET_HEIGHT, this);
 		//more effects could have been filled in
 	}
/**Stops the game
 */
	public void stop ()
	{
		music.stop();
		GameRunning = false;
	}
}
