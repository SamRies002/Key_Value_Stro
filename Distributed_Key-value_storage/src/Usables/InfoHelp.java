package Usables;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class InfoHelp {
	public static String readFromInput(BufferedReader in) throws IOException{
		return in.readLine();
	}
	
	public static void writeToOutput(BufferedWriter out, String message) throws IOException{
		out.write(message + "\n");
		out.flush();
}
}
