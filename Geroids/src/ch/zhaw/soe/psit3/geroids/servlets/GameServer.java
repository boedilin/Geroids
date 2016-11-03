package ch.zhaw.soe.psit3.geroids.servlets;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class GameServer
{
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8080);

        WebSocketHandler wsHandler = new WebSocketHandler() {
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(MyWebSocketHandler.class);
			}
		};

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        //which folder static files comes from
        context.setResourceBase("./public");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { wsHandler, context, new DefaultHandler() });
        server.setHandler(handlers);

        // Add dump servlet
        context.addServlet(DumpServlet.class, "/dump/*");
        // Add default servlet to serve static content from ./public
        context.addServlet(DefaultServlet.class, "/");

        server.start();
        server.join();
    }
}
