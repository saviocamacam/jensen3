package jensen2;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		try {
			System.out.println( "ok" );
			Thread.sleep( 5 * 1000 );
			System.out.println( "ok" );  
			Thread.sleep( 10 * 1000 );
			System.out.println( "ok" ); 
		}catch (InterruptedException e) {
			e.printStackTrace();
 		}
	}

}
