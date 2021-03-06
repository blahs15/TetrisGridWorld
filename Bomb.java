import java.util.ArrayList;
import info.gridworld.actor.*;
import info.gridworld.grid.*;

public class Bomb extends Piece
   {
   
   public void createPiece()
      {
      } // end method createPiece
   public void setSideL()
      {
      } // end method setSideL
   public void adjustSidePieces()
      {
      } // end method adjustSidePieces
   public void turn( boolean clockPath )
      {
      } // end method turn
   
   public void act()
      {
      if( getCenterPiece() && isActivePiece() )
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
            setActivePiece( false );
            ArrayList<Actor> actors = getGrid().getNeighbors( getLocation() );
            for( Actor a : actors )
               {
               if( !(a instanceof Rock) )
                  {
                  a.removeSelfFromGrid();
                  } // end method if
               } // end for
            removeSelfFromGrid();
            Controller.addPiece();
            } // end else
         } // end if
      } // end method act()
   
   
   } // end class Bomb