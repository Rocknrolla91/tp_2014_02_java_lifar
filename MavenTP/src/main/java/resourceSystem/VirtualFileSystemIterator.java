package resourceSystem;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Alena on 5/18/14.
 */
public class VirtualFileSystemIterator implements Iterator<String>, Iterable<String> {

    private Queue<File> files = new LinkedList<>();

    public VirtualFileSystemIterator(String path)
    {
        files.add(new File(path));
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return !files.isEmpty();
    }

    @Override
    public String next() {
        File file = files.peek();

        if(file.isDirectory())
        {
            File[] filesInDirectory = file.listFiles();
            if(filesInDirectory != null)
            {
                Collections.addAll(files, filesInDirectory);
            }
        }
        return files.poll().getAbsolutePath();
    }
}
