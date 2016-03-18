package com.verywildbanana.chiis.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class BaseServlet extends HttpServlet{

	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 0L;
	
	protected final String TAG = this.getClass().getName();
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public BaseServlet() {
	
		log.debug(TAG + " constructor" );
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
}
