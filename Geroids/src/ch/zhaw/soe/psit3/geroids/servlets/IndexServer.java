package ch.zhaw.soe.psit3.geroids.servlets;


import java.io.File;
import java.nio.file.Path;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;

/**
 * Simple Jetty FileServer.
 * This is a simple example of Jetty configured as a FileServer.
 */
public class IndexServer
{
    public static void main(String[] args) throws Exception
    {
        
        Server server = new Server(8082);
        
        // Creates the path the the WebContent Folder which contains html/js/css
        Path webRootPath = new File("WebContent/").toPath().toRealPath();
        
        System.err.println("WebRoot is " + webRootPath);
 
        // Creates a handler with the previous created path to WebContent to serve files (specifically index.html);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(new PathResource(webRootPath));
        // if no welcomefile (see WEB-inf => web.xml  for search order) can be found to display, will list all files and subdirectories from the handlers working directory
        resourceHandler.setDirectoriesListed(true);
        
        //passes handler to server, after starting, system will try to launch welcomefile (see WEB-inf => web.xml for search order),
        // if not possible will response with 403 forbidden
        server.setHandler(resourceHandler);
        
        
        // Start things up! By using the server.join() the server thread will join with the current thread.
        // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
        server.start();
        server.join();
    }
}