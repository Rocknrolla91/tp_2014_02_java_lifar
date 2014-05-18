package resourceSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alena on 5/18/14.
 */
public class VirtualFileSystemImpl implements VirtualFileSystem {

    private String root;

    VirtualFileSystemImpl(String root)
    {
        this.root = root;
    }

    @Override
    public boolean isExist(String path) {
        return new File(getAbsolutePath(path)).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(getAbsolutePath(path)).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        if(Paths.get(file).isAbsolute())
            return file;
        else
            return Paths.get(root, file).toAbsolutePath().toString();
    }

    @Override
    public String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    @Override
    public byte[] getBytes(String file) throws IOException {
        return Files.readAllBytes(Paths.get(getAbsolutePath(file)));
    }

    @Override
    public String getUTF8Text(String file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try
        {
            FileInputStream fileInputStream = new FileInputStream(getAbsolutePath(file));
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String string;

            while ((string = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(string);
                }
            bufferedReader.close();
        }
        catch (Exception e)
        {
            System.err.print(e.getMessage());
        }
        finally
        {
            if(bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    System.err.print(e.getMessage());
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Iterable<String> iterate(String startDirectory) {
        return new VirtualFileSystemIterator(startDirectory);
    }
}
