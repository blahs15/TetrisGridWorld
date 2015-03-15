import java.util.ArrayList;
import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.*;

public class Oil extends Bomb
   {
   
   public Oil()
      {
      setColor( Color.green );
      } // end constructor Oil
   
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
            ArrayList<Piece> pieces = new ArrayList<Piece>();
            for( int index = getGrid().getNumRows()-1; index > getLocation().getRow(); 
                  index-- )
               {
               Actor a = getGrid().get( new Location( index, getLocation().getCol() ) );
               if( a != null )
                  {
                  if( !(a instanceof Rock ) )
                     {
                     Piece p = (Piece) a;
                     pieces.add( p );
                     } // end if
                  } // end if
               } // end for
            for( Piece p : pieces )
               {
               while( getGrid().isValid( p.getLocation().getAdjacentLocation(
                     Location.SOUTH ) ) )
                  {
                  if( getGrid().get( p.getLocation().getAdjacentLocation(
                        Location.SOUTH ) ) == null )
                     {
                     Location l = p.getLocation();
                     Location newLocation = l.getAdjacentLocation( Location.SOUTH );
                     p.moveTo( newLocation );
                     } // end if
                  else
                     {
                     break;
                     } // end else
                  } // end while
               } // end for
            removeSelfFromGrid();
            Controller.addPiece();
            } // end else
         } // end if
      } // end method act()
   
   } // end class Oil