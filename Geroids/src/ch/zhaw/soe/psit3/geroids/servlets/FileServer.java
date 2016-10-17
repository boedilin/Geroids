package ch.zhaw.soe.psit3.geroids.servlets;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Simple Jetty FileServer. This is a simple example of Jetty configured as a
 * FileServer.
 */
public class FileServer {
	public static void main(String[] args) throws Exception {
		// Create a basic Jetty server object that will listen on port 8080.
		// Note that if you set this to port 0
		// then a randomly available port will be assigned that you can either
		// look in the logs for the port,
		// or programmatically obtain it for use in test cases.
		Server server = new Server(8080);

		WebSocketHandler wsHandler = new WebSocketHandler() {
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(MyWebSocketHandler.class);
			}
		};

		// Create the ResourceHandler. It is the object that will actually
		// handle the request for a given file. It is
		// a Jetty Handler object so it is suitable for chaining with other
		// handlers as you will see in other examples.
		ResourceHandler resource_handler = new ResourceHandler();

		// Configure the ResourceHandler. Setting the resource base indicates
		// where the files should be served out of.
		// In this example it is the current directory but it can be configured
		// to anything that the jvm has access to.
		// brauchts nicht!
		// resource_handler.setDirectoriesListed(true);
		// brauchts nicht!
		// resource_handler.setWelcomeFiles(new String[]{ "index.html" });
		resource_handler.setResourceBase("./public");

		// Add the ResourceHandler to the server.
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { wsHandler, resource_handler, new DefaultHandler() });

		// von mir eingefügt! Der FileServer würde sonst nicht funktiobieren!
		server.setHandler(handlers);

		// Start things up! By using the server.join() the server thread will
		// join with the current thread.
		// See
		// "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
		// for more details.
		server.start();
		server.join();
	}
}