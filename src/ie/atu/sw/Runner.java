package ie.atu.sw;

public class Runner {

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		String result = encoder.encode("test");
		System.out.println(result);
	}
	
}
