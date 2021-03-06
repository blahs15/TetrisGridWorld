import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.*;

public abstract class Piece extends Actor implements CanMove
   {
   private boolean centerPiece;
   private boolean activePiece;
   protected Piece[] sideP;
   private int pieceOrientation;
   protected Location[] sideL;
   
   private int turnCounter;
   
   public Piece()
      {
      setColor( null );
      centerPiece = true;
      activePiece = true;
      sideP = new Piece[3];
      pieceOrientation = 0;
      sideL = new Location[sideP.length];
      } // end constructor Piece
   
   public abstract void createPiece();
   public abstract void setSideL();
   
   public void resetTurnCounter()
      {
      turnCounter = 0;
      } // end method resetTurnCounter
   
   public void turn( boolean clockPath )
      { //@param CLOCKWISE or COUNTERCLOCKWISE from interface CanMove
      setPieceOrientation( clockPath );
      setSideL();
      for( Location l : sideL )
         {
         if( getGrid().isValid( l ) )
            {
            Actor a = getGrid().get(l);
            if( a != null )
               {
               if( a instanceof Rock )
                  {
                  revertTurn( clockPath );
                  break;
                  } // end if
               Piece p = (Piece) a;
               if( !p.isActivePiece() )
                  {
                  revertTurn( clockPath );
                  break;
                  } // end if
               }
            } // end if
         else
            {
            revertTurn( clockPath );
            break;
            } // end else
         } // end for
      adjustSidePieces();
      } // end method turn
   
   private void revertTurn( boolean clockPath )
      {
      turnCounter++;
      setPieceOrientation( !clockPath );
      if( turnCounter == 1 || turnCounter == 2 )
         {
         move( Location.WEST );
         turn( clockPath );
         } // end if
      else if( turnCounter == 3 || turnCounter == 4 )
         {
         move( Location.EAST );
         turn( clockPath );
         } // end if
      else
         {
         setSideL();
         } // end else
      } // end method revertTurn
   
   public void move( int direction )
      { // @param the static int direction in gridworld class Location
      if( !isBlocked( direction ) )
         {
         Location l = getLocation();
         Location newLocation = l.getAdjacentLocation( direction );
         moveTo( newLocation );
         adjustSidePieces();
         } // end if
      } // end method move
      
   public void drop()
      {
      while( activePiece )
         {
         act();
         } // end while
      } // end method drop
      
   public void fall()
      {
      act();
      } // end method fall
      
   public void act()
      {
      if( centerPiece && activePiece )
         {
         if( !isBlocked( Location.SOUTH ) )
            {
            Location l = getLocation();
            Location newLocation = l.getAdjacentLocation( Location.SOUTH );
            moveTo( newLocation );
            adjustSidePieces();
            } // end if
         else
            {
            activePiece = false;
            for( Piece p : sideP )
               {
               p.setActivePiece( false );
               } // end for
            Controller.addPiece();
            } // end else
         } // end if
      } // end method act()
   
   public boolean isBlocked( int direction )
      { // @param the static int direction in gridworld class Location
      Location l = getLocation();
      
      if( ( direction == Location.WEST && l.getCol() == 0 ) ||
          ( direction == Location.EAST && l.getCol() == ( getGrid().getNumCols()-6 - 1 ) ) ||
          ( direction == Location.SOUTH && l.getRow() == ( getGrid().getNumRows() - 1 ) ) )
         {
         return true;
         } // end if
      
      
      if( centerPiece )
         {
         try
            {
            for( Piece sidePiece : sideP )
               {
               if( sidePiece.isBlocked( direction ) )
                  {
                  return true;
                  } // end if
               } // end for
            } // end try
         catch( Exception e )
            {
            } // end catch
         } // end if
      
      
      Location lCheck = l.getAdjacentLocation(direction);
      Actor a = getGrid().get( lCheck );
      if( a == null )
         {
         return false;
         } // end if
      if( a instanceof Rock )
         {
         return true;
         } // end if
      Piece p = (Piece) a;
      if( p.isActivePiece() == false )
         {
         return true;
         } // end if
      
      return false;
      } // end method isBlocked
   
   public void setPieceOrientation( boolean turnDirection )
      {
      if( turnDirection == CLOCKWISE )
         {
         pieceOrientation++;
         if( pieceOrientation == 4 )
            {
            pieceOrientation = 0;
            } // end if
         } // end if
      
      else if( turnDirection == COUNTERCLOCKWISE )
         {
         pieceOrientation--;
         if( pieceOrientation == -1 )
            {
            pieceOrientation = 3;
            } // end if
         } // end if
      } // end method setPieceOrientation
   public void setPieceOrientation( int orientation )
      {
      pieceOrientation = orientation;
      } // end method setPieceOrientation
   public int getPieceOrientation()
      {
      return pieceOrientation;
      } // end method getPieceOrientation
   
   
   public void adjustSidePieces()
      {
      resetPiece();
      setSideL();
         
      for( int index = 0; index < sideP.length; index++ )
         {
         Controller.addToWorld( sideL[index], sideP[index] );
         } // end for
      } // end method adjectSidePieces()
   
   
   public void resetPiece()
      {
      for( Piece p : sideP )
         {
         try
            {
            p.removeSelfFromGrid();
            } // end try
         catch( Exception e )
            {
            } // end catch
         } // end for
      } // end method resetPiece
   
   
   public boolean getCenterPiece()
      {
      return centerPiece;
      } // end method getCenterPiece
   public void setCenterPiece( boolean isCenter )
      {
      centerPiece = isCenter;
      } // end method setCenterPiece
   public boolean isActivePiece()
      {
      return activePiece;
      } // end method isActivePiece
   public void setActivePiece( boolean isActivePiece )
      {
      activePiece = isActivePiece;
      } // end method setActivePiece
   
   } // end class Piece