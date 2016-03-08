package com.verywildbanana.chiis.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.verywildbanana.chiis.logger.LoggerInterceptor;
import com.verywildbanana.chiis.resolver.CustomMapArgumentResolver;


@Configuration
@EnableWebMvc
@EnableAsync // @Async 어노테이션을 사용하기 위함
@ComponentScan(
    basePackages="com.verywildbanana.chiis",
    excludeFilters=@ComponentScan.Filter(Configuration.class)
)
public class MvcConfig extends WebMvcConfigurerAdapter // 인터셉터를 추가하기 위해 WebMvcConfigurerAdapter 를 상속한다
{
	Logger log = Logger.getLogger(this.getClass());
	
    @Bean
    public ViewResolver viewResolver()
    {
    	log.debug("=============== MvcConfig viewResolver ");
    	
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }
 
    /**
     * 인터셉터 추가
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggerInterceptor());
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	
    	argumentResolvers.add(new CustomMapArgumentResolver());
    	
    	super.addArgumentResolvers(argumentResolvers);
    }
 
    @Bean
    public DefaultAnnotationHandlerMapping defaultAnnotationHandlerMapping() {
    	
    	log.debug("=============== MvcConfig defaultAnnotationHandlerMapping ");
    	
         DefaultAnnotationHandlerMapping def = new DefaultAnnotationHandlerMapping();
        return def;
    }

    
    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
    	
    	log.debug("=============== MvcConfig beanNameViewResolver ");
    	
    	BeanNameViewResolver bnvr = new BeanNameViewResolver();
    	bnvr.setOrder(0);
        return bnvr;
    }
    
    @Bean(name = "jsonView")
    public MappingJacksonJsonView mappingJacksonJsonView() {
    	
    	log.debug("=============== MvcConfig mappingJacksonJsonView ");
    	
    	MappingJacksonJsonView mjjv = new MappingJacksonJsonView();
        return mjjv;
    }
    
    
}