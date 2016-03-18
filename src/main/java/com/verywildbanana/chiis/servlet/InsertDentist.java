package com.verywildbanana.chiis.servlet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.verywildbanana.chiis.common.CommandMap;
import com.verywildbanana.chiis.dao.SampleDAO;
import com.verywildbanana.chiis.dao.SampleServiceImpl;

/**
 * Servlet implementation class InsertDentist
 */
@WebServlet("/InsertDentist.do")
public class InsertDentist extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertDentist() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
//		
//		String id = request.getParameter("ID");
//		String password = request.getParameter("PASSWD");
//		String name = request.getParameter("NAME");
//		String address1 = request.getParameter("ADDRESS1");
//		String address2 = request.getParameter("ADDRESS2");
//		String address3 = request.getParameter("ADDRESS3");
//		String phone = request.getParameter("PHONE");
//
//		CommandMap commandMap = new CommandMap();
//		commandMap.put("ID", id);
//		commandMap.put("PASSWD", password);
//		commandMap.put("NAME", name);
//		commandMap.put("ADDRESS1", address1);
//		commandMap.put("ADDRESS2", address2);
//		commandMap.put("ADDRESS3", address3);
//		commandMap.put("PHONE", phone);
//		
//		
//		log.debug("------------- InsertDentist doGet id " + id);
//		
//		try {
//			
//			int count = sampleService.selectBoardLikeIdCount(commandMap.getMap());
//			log.debug("InsertDentist doGet 1 ");
//			
//			if(count > 0) {
//				
//				JSONObject json = new JSONObject();
//				json = new JSONObject();
//				json.put("error"     ,  "id 이미 존재합니다.");
//				response.setContentType("application/json");
//				response.getWriter().write(json.toString());
//				
//				log.debug("InsertDentist doGet 2 ");
//				
//				return;
//				
//			}
//			log.debug("InsertDentist doGet 3");
//			
//			sampleService.insertBoard(commandMap.getMap(), request);
//			
//			log.debug("InsertDentist doGet 4");
//			
//		} 
//		catch (Exception e) {
//
//			e.printStackTrace();
//			JSONObject json = new JSONObject();
//			json = new JSONObject();
//			json.put("error"     , e.toString());
//			response.setContentType("application/json");
//			response.getWriter().write(json.toString());
//			
//			return;
//		}
//		
//		JSONObject json = new JSONObject();
//		json = new JSONObject();
//		json.put("ID"     , id);
//		json.put("PASSWD" , password);
//		json.put("NAME"   , name);
//		json.put("ADDRESS1" , address1);
//		json.put("ADDRESS2" , address2);
//		json.put("ADDRESS3" , address3);
//		json.put("PHONE", phone);
//
//		response.setContentType("application/json");
//		response.getWriter().write(json.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
