package org.mq.mqMall.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			while (DriverManager.getDrivers().hasMoreElements()) {
				DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
			}
			AbandonedConnectionCleanupThread.uncheckedShutdown();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String rootPath = context.getRealPath("/");
		System.setProperty("rootPath", rootPath);
	}
}
