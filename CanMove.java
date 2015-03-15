public interface CanMove
   {
   public static final boolean CLOCKWISE = true;
   public static final boolean COUNTERCLOCKWISE = false;
   
   public abstract void turn( boolean direction );
   public abstract void move( int direction );
   public abstract void drop();
   public abstract void fall();
   } // end interface CanTurn
