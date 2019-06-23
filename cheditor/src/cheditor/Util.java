package cheditor;

import javafx.scene.text.Font;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static boolean saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    public static String readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        String st = "";
        String k;
        while ((k = br.readLine()) != null)
            st += k + "\n";
        return st;
    }


    public static String[] getFonts() {
        String[] fonts = new String[]{"Arial", "Arial Black", "Arial Narrow", "Arial Unicode MS", "Book Antiqua", "Bookman Old Style", "Calibri", "Cambria", "Candara", "Century", "Century Gothic", "Comic Sans MS", "Consolas", "Constantia", "Corbel", "Courier New", "Franklin Gothic Medium", "Garamond", "Georgia", "Impact", "Lucida Console", "Lucida Sans Unicode", "Microsoft Sans Serif", "Mistral", "Monotype Corsiva", "Palatino Linotype", "Segoe Print", "Segoe Script", "Segoe UI", "Sylfaen", "Tahoma", "Times New Roman", "Trebuchet MS", "Verdana"};
        return fonts;
    }
}

