import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class ReadWriteExcel{
	
public static void main(String [] args){
try {
        // Create a URL for the desired page
        URL url = new URL("");       

        // Read all the text returned by the server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        while ((str = in.readLine()) != null) {
            str = in.readLine().toString();
            System.out.println(str);
            // str is one line of text; readLine() strips the newline character(s)
        }
        in.close();
    } catch (MalformedURLException e) {
    } catch (IOException e) {
    }
}
}