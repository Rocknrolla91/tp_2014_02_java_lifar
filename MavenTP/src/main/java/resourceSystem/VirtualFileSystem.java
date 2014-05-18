package resourceSystem;

import java.io.IOException;

/**
 * Created by Alena on 5/18/14.
 */
public interface VirtualFileSystem {
    boolean isExist(String path);
    boolean isDirectory(String path);
    String getAbsolutePath(String file);
    String getFileName(String path);
    byte[] getBytes(String file) throws IOException;
    String getUTF8Text(String file) throws IOException;
    Iterable<String> iterate(String startDirectory);
}
