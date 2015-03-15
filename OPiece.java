import info.gridworld.grid.*;
import java.awt.*;

public class OPiece extends Piece
   {
      
   public OPiece()
      {
      setColor( Color.yellow );
      } // end constructor OPiece
   
   public void createPiece()
      {
      for( int index = 0; index < sideP.length; index++ )
         {
         sideP[index] = new OPiece();
         sideP[index].setCenterPiece( false );
         } // end for
      adjustSidePieces();
      } // end method CreatePiece
   
   
      
   public void setSideL()
      {
      int r = getLocation().getRow();
      int c = getLocation().getCol();
      if( getPieceOrientation() == 0 )
         {
         sideL[0] = new Location( r, c+1 );
         sideL[1] = new Location( r+1, c+1 );
         sideL[2] = new Location( r+1, c );
         } // end if
      else if( getPieceOrientation() == 1 )
         {
         sideL[0] = new Location( r+1, c );
         sideL[1] = new Location( r+1, c-1 );
         sideL[2] = new Location( r, c-1 );
         } // end if
      else if( getPieceOrientation() == 2 )
         {
         sideL[0] = new Location( r, c-1 );
         sideL[1] = new Location( r-1, c-1 );
         sideL[2] = new Location( r-1, c );
         } // end if
      else if( getPieceOrientation() == 3 )
         {
         sideL[0] = new Location( r-1, c );
         sideL[1] = new Location( r-1, c+1 );
         sideL[2] = new Location( r, c+1 );
         } // end if
      
      } // end method setSideL
      
   } // end class OPiece