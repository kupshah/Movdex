
public class APIReaderTest {

	
	public static void main(String[] args){
		
		APIReader reader = new APIReader();
		String movieID = "";
		String videoPath ="";
		try {
		movieID = reader.getIDFromTitle("Logan");
		}
		catch (Exception e){
			System.out.println("Failed at getting ID");
		}
		
		try {
			videoPath = reader.getTrailerFromID(movieID);
		}
		catch(Exception e){System.out.println("failed to g et trailer path");
		}
		
		System.out.println(movieID);
		System.out.println(videoPath);
		
	}
}
