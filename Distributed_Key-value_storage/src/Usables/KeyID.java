package Usables;

public class KeyID {
	public static String generateId(){
		int id = Math.abs(((int)((System.currentTimeMillis() % 100000) ^ System.nanoTime()) % 100000));
		return String.format("%05d", id);
}
}
