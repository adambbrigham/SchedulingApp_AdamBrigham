package Utilities;

import java.util.ResourceBundle;

/**
 * Class for Resource Bundles
 */
public class ResourceBundles
{
    /**
     * gets the Resource Bundle
     * @return resource bundle
     */
    public static ResourceBundle getResource()
    {
        return ResourceBundle.getBundle("ResBundle", java.util.Locale.getDefault());
    }
}
