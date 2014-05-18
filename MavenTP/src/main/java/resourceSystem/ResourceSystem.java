package resourceSystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alena on 5/18/14.
 */
public class ResourceSystem {
    private Map<String, Resource> resources = new HashMap<>();
    private Map<String, Map<String, String>> configFiles = new HashMap<>();
    private static ResourceSystem instance;
    private static final String RESOURCES = "./resources";
    private static final String CONFIG_FILES = "./configFiles";

    public static ResourceSystem getInstance()
    {
        if(instance == null)
        {
            instance = new ResourceSystem();
            instance.reloadResources();
            instance.reloadConfigFiles();
        }
        return instance;
    }

    public Resource getResource(String fileName)
    {
        return resources.get(fileName + ".xml");
    }

    public Map<String, String> getConfigFile(String fileName)
    {
        return configFiles.get(fileName + ".xml");
    }

    public void reloadResources()
    {
        VirtualFileSystem virtualFileSystem = new VirtualFileSystemImpl(RESOURCES);
        resources.clear();

        for(String fileName: virtualFileSystem.iterate(RESOURCES))
        {
            if(!virtualFileSystem.isDirectory(fileName))
            {
                try
                {
                    Resource resource = SAXParser.parse(virtualFileSystem.getUTF8Text(fileName));
                    resources.put(virtualFileSystem.getFileName(fileName), resource);
                } catch (ParserException | IOException e) {
                    System.out.println("error while loading config " + fileName);
                    e.printStackTrace();
                }
            }
        }

    }

    public void reloadConfigFiles()
    {
        VirtualFileSystem virtualFileSystem = new VirtualFileSystemImpl(CONFIG_FILES);
        configFiles.clear();

        for(String fileName: virtualFileSystem.iterate(CONFIG_FILES))
        {
            if(!virtualFileSystem.isDirectory(fileName))
            {
                try
                {
                    Map<String, String> config = DOMParser.parse(virtualFileSystem.getUTF8Text(fileName));
                    configFiles.put(virtualFileSystem.getFileName(fileName), config);
                }
                catch (ParserException | IOException e)
                {
                    System.out.println("error while loading resource " + fileName);
                    e.printStackTrace();
                }
            }
        }
    }
}
