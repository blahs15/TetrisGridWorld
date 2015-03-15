import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import info.gridworld.actor.*;

public class Controller implements KeyListener
   {
   private static ActorWorld world;
   private static BoundedGrid<Actor> grid;
   private static JFrame control;
   
   private static int score;
   private static int lvl;
   private static int linesCleared;
   private static int linesToLvl;
   private static int combo;
   
   private static int piecesUsed;
   private static int numOfTetris;
   private static int screenClears;
   private static int highestCombo;
   
   private static boolean tetrisCombo;
   private static boolean hasStoredThisTurn;
   private static int dropHeight;
   
   private static Piece activePiece;
   private static Piece nextPiece;
   private static Piece nextPiece2;
   private static Piece nextPiece3;
   private static Piece storagePiece;
   
   
   public Controller()
      {
      grid = new BoundedGrid<Actor>( 19, 16 );
      world = new ActorWorld( grid );
      world.show();
      
      score = -1000;
      lvl = 0;
      linesCleared = 0;
      linesToLvl = 5;
      combo = 0;
      
      piecesUsed = 0;
      numOfTetris = 0;
      screenClears = -1;
      highestCombo = 0;
      
      tetrisCombo = false;
      hasStoredThisTurn = false;
      dropHeight = 0;
      
      activePiece = null;
      nextPiece = null;
      nextPiece2 = null;
      nextPiece3 = null;
      storagePiece = null;
      
      addBlocks();
      createNextPiece3();
      createNextPiece3();
      createNextPiece3();
      addPiece();
      
      control = new JFrame();
      control.setTitle( "Tetris Controller" );
      control.setSize( 350, 300 );
      control.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      control.setFocusable( true );
      control.addKeyListener( this );
      
      JLabel[] blank = new JLabel[7];
      for( int index = 0; index < blank.length; index++ )
         {
         blank[index] = new JLabel( "" );
         } // end for
      JLabel start = new JLabel( "   Click Run to begin." );
      JLabel pause = new JLabel( "Click Stop to pause." );
      JLabel gameController = new JLabel( "   ***************Click this window" );
      JLabel gameController2 = new JLabel( "to control the game.***************" );
      JLabel left = new JLabel( "   Move LEFT: ← or A" );
      JLabel right = new JLabel( "Move RIGHT: → or D" );
      JLabel clockwise = new JLabel( "   TURN Clockwise: ↑ or W or X" );
      JLabel counterClockwise = new JLabel( "Turn Counter-Clockwise: Z" );
      JLabel fall = new JLabel( "   FALL: ↓ or S" );
      JLabel drop = new JLabel( "DROP: SPACEBAR" );
      JLabel store = new JLabel( "   STORE Piece: SHIFT" );
      JLabel speed = new JLabel( "   Adjust Speed for Difficulty." );
      JLabel quit = new JLabel( "Quit: ESC" );
      
      control.setLayout( new GridLayout( 0, 2, 10, 10 ) );
      
      control.getContentPane().add( start );
      control.getContentPane().add( pause );
      control.getContentPane().add( gameController );
      control.getContentPane().add( gameController2 );
      control.getContentPane().add( blank[0] );
      control.getContentPane().add( blank[1] );
      control.getContentPane().add( left );
      control.getContentPane().add( right );
      control.getContentPane().add( clockwise );
      control.getContentPane().add( counterClockwise );
      control.getContentPane().add( blank[2] );
      control.getContentPane().add( blank[3] );
      control.getContentPane().add( fall );
      control.getContentPane().add( drop );
      control.getContentPane().add( store );
      control.getContentPane().add( blank[4] );
      control.getContentPane().add( blank[5] );
      control.getContentPane().add( blank[6] );
      control.getContentPane().add( speed );
      control.getContentPane().add( quit );
      
      control.setLocationRelativeTo( null );
      control.setVisible( true );
      control.pack();
      
      }// enc contructor Controller
   
   
   public void keyTyped ( KeyEvent e )
      {
      } // end method keyTyped
   
   public void keyReleased ( KeyEvent e )
      {
      } // end method keyReleased
   
   public void keyPressed ( KeyEvent e )
      {
      if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A )
         {
         activePiece.move( Location.WEST );
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D )
         {
         activePiece.move( Location.EAST );
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_SPACE )
         {
         dropHeight =  grid.getNumRows() - activePiece.getLocation().getRow();
         activePiece.drop();
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W
               || e.getKeyCode() == KeyEvent.VK_X )
         {
         activePiece.resetTurnCounter();
         activePiece.turn( CanMove.CLOCKWISE );
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S )
         {
         activePiece.fall();
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_Z )
         {
         activePiece.resetTurnCounter();
         activePiece.turn( CanMove.COUNTERCLOCKWISE );
         } // end if
      else if( e.getKeyCode() == KeyEvent.VK_SHIFT )
         {
         storePiece();
         } // end if
      
      else if( e.getKeyCode() == KeyEvent.VK_ESCAPE )
         {
         gameOver();
         } // end CLOSE if
      } // end method keyPressed
      
   
   
   
   public static void addPiece()
      {
      clearLines();
      world.setMessage( "Score: " + score + "\tLevel: " + lvl + 
            "\nCombo: " + combo + "\tLines to Level: " + linesToLvl );
      dropHeight = 0;
      
      createNextPiece3();
      
      if( grid.get( new Location(0,4) ) != null )
         {
         gameOver();
         } // end if
      
      activePiece.resetPiece();
      nextPiece.resetPiece();
      nextPiece2.resetPiece();
      nextPiece3.resetPiece();
      try{
         activePiece.removeSelfFromGrid();
         } // end try
      catch (Exception e ){}
      try{
         nextPiece.removeSelfFromGrid();
         } // end try
      catch (Exception e ){}
      try{
         nextPiece2.removeSelfFromGrid();
         } // end try
      catch (Exception e ){}
      try{
         nextPiece3.removeSelfFromGrid();
         } // end try
      catch (Exception e ){}
      
      world.add( new Location(0,4), activePiece );
      world.add( new Location(8,12), nextPiece );
      world.add( new Location(12,12), nextPiece2 );
      world.add( new Location(16,12), nextPiece3 );
      nextPiece3.createPiece();
      nextPiece2.createPiece();
      nextPiece.createPiece();
      activePiece.createPiece();
      activePiece.setActivePiece( true );
      piecesUsed++;
      
      hasStoredThisTurn = false;
      } // end method addPiece
   
   private static void clearLines()
      {
      int scoreGain = 50;
      int tempLinesCleared = 0;
      
      for( int r = grid.getNumRows()-1; r >= 0; r-- )
         {
         int counter = 0;
         for( int c = 0; c < grid.getNumCols()-6; c++ )
            {
            if( grid.get( new Location(r,c) ) != null )
               {
               if( !(grid.get( new Location(r,c) ) instanceof Rock) )
                  {
                  counter++;
                  }
               } // end if
            else
               {
               break;
               } // end else
            } // end for
         if( counter == grid.getNumCols()-6 )
            {
            for( int c = 0; c < grid.getNumCols()-6; c++ )
               {
               grid.get( new Location(r,c) ).removeSelfFromGrid();
               } // end for
            for( int r2 = r-1; r2 >= 0; r2-- )
               {
               for( int c = 0; c < grid.getNumCols()-6; c++ )
                  {
                  if( grid.get( new Location(r2,c) ) != null )
                     {
                     grid.get( new Location(r2,c) ).moveTo( new Location(r2+1,c) );
                     } // end if
                  } // end for
               } // end for
            r++;
            tempLinesCleared++;
            scoreGain *= 2;
            } // end if
         } // end for
      
      linesCleared += tempLinesCleared;
      
      linesToLvl -= tempLinesCleared;
      while( linesToLvl <= 0 )
         {
         lvl++;
         linesToLvl = 5*lvl + 5 + linesToLvl;
         if( lvl%2 == 0 && lvl < 12 )
            {
            ArrayList<Location> loc = grid.getOccupiedLocations();
            for( Location l : loc )
               {
               if( l.getCol() < grid.getNumCols()-6 )
                  {
                  Actor a = grid.get(l);
                  l = l.getAdjacentLocation( Location.NORTH );
                  a.moveTo( l );
                  } // end if
               } // end for
            for( int c = 0; c < grid.getNumCols()-6; c++ )
               {
               Block b = new Block();
               b.setColor( Color.black );
               world.add( new Location( grid.getNumRows()-1, c ), b );
               } // end for
            } // end if
         } // end if
      
      combo++;
      if( tempLinesCleared == 0 )
         {
         combo = 0;
         } // end if
      if( combo > highestCombo )
         {
         highestCombo = combo;
         } // end if
      if( combo > 0 )
         {
         for( int index = combo; index > 1; index-- )
            {
            scoreGain += index*25 - 25;
            } // end for
         } // end if
         
      if( tempLinesCleared == 4 )
         {
         if( tetrisCombo )
            {
            scoreGain += 400;
            } // end if
         tetrisCombo = true;
         numOfTetris++;
         } // end if
      else
         {
         tetrisCombo = false;
         } // end else
         
      boolean screenCleared = true;
      for( int r = grid.getNumRows()-1; r >= 0; r-- )
         {
         for( int c = 0; c < grid.getNumCols()-6; c++ )
            {
            if( grid.get( new Location(r,c) ) != null 
                && !(grid.get( new Location(r,c) ) instanceof Rock ) )
               {
               screenCleared = false;
               break;
               } // end if
            } // end for
         if( screenCleared == false )
            {
            break;
            } // end if
         } // end for
      if( screenCleared )
         {
         screenClears++;
         score += 1000;
         } // end if
      
      if( scoreGain >= 100 )
         {
         score += scoreGain;
         } // end if
      
      score += dropHeight;
      } // end method clearLines
   
   private static void createNextPiece3()
      {
      activePiece = nextPiece;
      nextPiece = nextPiece2;
      nextPiece2 = nextPiece3;
      
      int randPiece = (int) ( Math.random() * 23 );
      if( randPiece < 3 )
         {
         nextPiece3 = new IPiece();
         } // end if
      else if( randPiece < 6 )
         {
         nextPiece3 = new OPiece();
         } // end if
      else if( randPiece < 9 )
         {
         nextPiece3 = new TPiece();
         } // end if
      else if( randPiece < 12 )
         {
         nextPiece3 = new JPiece();
         } // end if
      else if( randPiece < 15 )
         {
         nextPiece3 = new LPiece();
         } // end if
      else if( randPiece < 18 )
         {
         nextPiece3 = new SPiece();
         } // end if
      else if( randPiece < 21 )
         {
         nextPiece3 = new ZPiece();
         } // end if
      else if( randPiece < 22 )
         {
         nextPiece3 = new Bomb();
         } // end if
      else if( randPiece < 23 )
         {
         nextPiece3 = new Oil();
         } // end if
      
      nextPiece3.setActivePiece( false );
      
      } // end method createNextPiece2
   
   public static void storePiece()
      {
      if( !hasStoredThisTurn )
         {
         try{
            storagePiece.removeSelfFromGrid();
            } // end try
         catch (Exception e ){}
         try{
            activePiece.removeSelfFromGrid();
            } // end try
         catch (Exception e ){}
         
         Piece tempPiece;
         tempPiece = activePiece;
         activePiece = storagePiece;
         storagePiece = tempPiece;
         
         storagePiece.resetPiece();
         storagePiece.setPieceOrientation( 0 );
         world.add( new Location(3,12), storagePiece );
         storagePiece.createPiece();
         storagePiece.setActivePiece(false);
         
         if( activePiece == null )
            {
            addPiece();
            } // end if
         else
            {
            activePiece.resetPiece();
            world.add( new Location(0,4), activePiece );
            activePiece.createPiece();
            activePiece.setActivePiece(true);
            } // end else
         } // end if
      
      hasStoredThisTurn = true;
      } // end method storePiece()
   
   
   public static void addToWorld( Location l, Piece p )
      {
      if( true )
         {
         world.add( l, p );
         } // end else
      } // end method addToWorld
   
   
   private static void addBlocks()
      {
      for( int r = 0; r < grid.getNumRows(); r++ )
         {
         for( int c = grid.getNumCols()-6; c < grid.getNumCols(); c++ )
            {
            world.add( new Location( r, c ), new Block() );
            } //  end for
         } // end for
      for( int c = grid.getNumCols()-5; c < grid.getNumCols()-1; c++ )
         {
         for( int r = 2; r < 5; r++ )
            {
            grid.get( new Location( r, c ) ).removeSelfFromGrid();
            } // end for
         for( int r = 7; r < 10; r++ )
            {
            grid.get( new Location( r, c ) ).removeSelfFromGrid();
            } // end for
         for( int r = 11; r < 14; r++ )
            {
            grid.get( new Location( r, c ) ).removeSelfFromGrid();
            } // end for
         for( int r = 15; r < 18; r++ )
            {
            grid.get( new Location( r, c ) ).removeSelfFromGrid();
            } // end for
         } // end for
      world.add( new Location( 7, grid.getNumCols()-5 ), new N() );
      world.add( new Location( 7, grid.getNumCols()-4 ), new E() );
      world.add( new Location( 7, grid.getNumCols()-3 ), new X() );
      world.add( new Location( 7, grid.getNumCols()-2 ), new T() );
      world.add( new Location( 11, grid.getNumCols()-5 ), new N() );
      world.add( new Location( 11, grid.getNumCols()-4 ), new E() );
      world.add( new Location( 11, grid.getNumCols()-3 ), new X() );
      world.add( new Location( 11, grid.getNumCols()-2 ), new T() );
      world.add( new Location( 15, grid.getNumCols()-5 ), new N() );
      world.add( new Location( 15, grid.getNumCols()-4 ), new E() );
      world.add( new Location( 15, grid.getNumCols()-3 ), new X() );
      world.add( new Location( 15, grid.getNumCols()-2 ), new T() );
      } // end method addBlocks
   
   private static void gameOver()
      {
      String stats = "YOU LOSE" +
                     "\n\nFinal Score: " + score +
                     "\nFinal Level: " + lvl +
                     "\nLines Cleared: " + linesCleared +
                     "\nHighest Combo: " + highestCombo +
                     "\nNumber of Tetris Clears: " + numOfTetris +
                     "\nNumber of Screen Clears: " + screenClears +
                     "\nPieces Used: " + piecesUsed +
                     "\n\nPlayAgain??";
      int restart = JOptionPane.showConfirmDialog( null, stats, 
         "There's always next time...", JOptionPane.YES_NO_OPTION, 
         JOptionPane.PLAIN_MESSAGE );
      if( restart == JOptionPane.YES_OPTION )
         {
         control.dispose();
         new Controller();
         } // end if
      else
         {
         System.exit(0);
         } // end else
      } // end method gameOver
   } // end class Controller