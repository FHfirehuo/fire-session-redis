package session;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import session.web.WebMvcConfig;

public class Initializer extends AbstractHttpSessionApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// 初始化web
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();

		webApplicationContext.register(WebMvcConfig.class, Config.class);

		webApplicationContext.setServletContext(servletContext);

		DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);

		String filterName = DEFAULT_FILTER_NAME;
		DelegatingFilterProxy springSessionRepositoryFilter = new DelegatingFilterProxy(filterName);

		javax.servlet.FilterRegistration.Dynamic registration = servletContext.addFilter(filterName,
				springSessionRepositoryFilter);
		if (registration == null) {
			throw new IllegalStateException("Duplicate Filter registration for '" + filterName
					+ "'. Check to ensure the Filter is only configured once.");
		}
		registration.setAsyncSupported(isAsyncSessionSupported());
		EnumSet<DispatcherType> dispatcherTypes = getSessionDispatcherTypes();
		registration.addMappingForUrlPatterns(dispatcherTypes, false, "/*");

		// 设置session跟踪的cookie的生命周期（以秒为单位）
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setMaxAge(1800);
		System.out.println("=================file server Startup====================");

	}

	/*
	 * public Initializer() { super(WebMvcConfig.class, Config.class); }
	 * 
	 * @Override protected void afterSessionRepositoryFilter(ServletContext
	 * servletContext) {
	 * 
	 * 
	 * }
	 */
}
