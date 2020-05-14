package forgefuck.team.configmanager.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileProvider extends File {
    
    public FileProvider(String path) {
        super(path);
    }
    
    public boolean writeContent(String content) {
        try (OutputStream out = new FileOutputStream(this, false)) {
            out.write(new String(content).getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String readContent() {
        StringBuilder out = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this), StandardCharsets.UTF_8))) {
            String line;
            while ((line = in.readLine()) != null) {
                out.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}
