package com.verywildbanana.chiis.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.verywildbanana.chiis.filter.RestAuthenticationFilter;



public class Initializer implements WebApplicationInitializer
{
	Logger log = Logger.getLogger(this.getClass());

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException
	{

		log.debug("=============== Initializer onStartup ");

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		this.addUtf8CharacterEncodingFilter(servletContext);

		this.addDispatcherServlet(servletContext);

		FilterRegistration.Dynamic filter = servletContext.addFilter("AuthenticationFilter", new RestAuthenticationFilter());
		filter.addMappingForUrlPatterns(null, false, "*.do");
	}

	/**
	 * Dispatcher Servlet 을 추가한다.
	 * CORS 를 가능하게 하기 위해서 dispatchOptionsRequest 설정을 true 로 한다.
	 * @param servletContext
	 */
	private void addDispatcherServlet(ServletContext servletContext)
	{
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.getEnvironment().addActiveProfile("production");
		applicationContext.register(MvcConfig.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(applicationContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.addMapping("*.do");
	    dispatcher.setInitParameter("dispatchOptionsRequest", "true"); // CORS 를 위해서 option request 도 받아들인다.
	}

	/**
	 * UTF-8 캐릭터 인코딩 필터를 추가한다.
	 * @param servletContext
	 */
	private void addUtf8CharacterEncodingFilter(ServletContext servletContext)
	{
		FilterRegistration.Dynamic filter = servletContext.addFilter("CHARACTER_ENCODING_FILTER", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.setInitParameter("forceEncoding", "true");
		filter.addMappingForUrlPatterns(null, false, "/*");
	}
}
