package com.verywildbanana.chiis.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.verywildbanana.chiis.Constants;

@Configuration
public class RootConfig {

 
    Logger log = Logger.getLogger(this.getClass());
    
 
    @Bean(name = "dataSource")
    public BasicDataSource dataSource()
    {
    	
    	log.debug("=============== RootConfig dataSource ");
    	
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/chiis");
        dataSource.setUsername("root");
        
        if(Constants.RELEASE_BUILD) {
        	
        	dataSource.setPassword("chiis123");
        }
        else {
        	
        	dataSource.setPassword("password123");
        	
        }
        
        return dataSource;
    }
 
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver()
    {
    	
    	log.debug("=============== RootConfig commonsMultipartResolver ");
    	
    	CommonsMultipartResolver cmr = new CommonsMultipartResolver();
    	cmr.setMaxUploadSize(100000000);
    	cmr.setMaxInMemorySize(100000000);
        return cmr;
    }
 
    @Bean(name = "sqlSession")
    public SqlSessionFactory sqlSessionFactoryBean() {
    
    	
    	log.debug("=============== RootConfig sqlSessionFactoryBean ");
    	
    	SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
    	ssfb.setDataSource(dataSource());
    	try {
    		
    		ssfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*_SQL.xml"));
            return (SqlSessionFactory) ssfb.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() {
    
    	log.debug("=============== RootConfig sqlSessionTemplate ");
    	
    	SqlSessionTemplate sst = new SqlSessionTemplate(sqlSessionFactoryBean());
        return sst;
    }
    
    
}