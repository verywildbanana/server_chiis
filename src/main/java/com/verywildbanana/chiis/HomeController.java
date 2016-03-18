package com.verywildbanana.chiis;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.verywildbanana.chiis.common.CommandMap;
import com.verywildbanana.chiis.dao.SampleService;
import com.verywildbanana.chiis.dao.SampleServiceImpl;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {


	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="sampleService")
	private SampleServiceImpl sampleService;


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		log.debug("Welcome home! The client locale is " + locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home";
	}

	@RequestMapping(value="/sample/openBoardList.do")
	public ModelAndView openSampleBoardList(Map<String,Object> commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardList");

		List<Map<String,Object>> list = sampleService.selectBoardList(commandMap);
		mv.addObject("list", list);

		return mv;
	}

	@RequestMapping(value="/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{

		log.debug("openBoardWrite ");

		ModelAndView mv = new ModelAndView("/sample/boardWrite");

		return mv;
	}

	@RequestMapping(value="/sample/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{

		log.debug("insertBoard ");

		sampleService.insertBoard(commandMap.getMap(), request);

		return null;
	}

	@RequestMapping(value="/api/insertDentist.do", method = RequestMethod.POST)
	public void insertDentist(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String id = request.getParameter("ID");
		String password = request.getParameter("PASSWD");
		String name = request.getParameter("NAME");
		String address1 = request.getParameter("ADDRESS1");
		String address2 = request.getParameter("ADDRESS2");
		String address3 = request.getParameter("ADDRESS3");
		String phone = request.getParameter("PHONE");

		log.debug("insertDentist " + id);

		try {

			int count = sampleService.selectBoardLikeIdCount(commandMap.getMap());
			log.debug("InsertDentist count " + count);

			if(count > 0) {

				JSONObject json = new JSONObject();
				json = new JSONObject();
				json.put("error"     ,  "id 이미 존재합니다.");
				response.setContentType("application/json");
				response.getWriter().write(json.toString());

				log.debug("InsertDentist doGet 1 ");

				return;

			}
			log.debug("InsertDentist doGet 2");

			sampleService.insertBoard(commandMap.getMap(), request);

			log.debug("InsertDentist doGet 3");

		} 
		catch (Exception e) {

			e.printStackTrace();
			JSONObject json = new JSONObject();
			json = new JSONObject();
			json.put("error"     , e.toString());
			response.setContentType("application/json");
			response.getWriter().write(json.toString());

			return;
		}

		JSONObject json = new JSONObject();
		json = new JSONObject();
		json.put("ID"     , id);
		json.put("PASSWD" , password);
		json.put("NAME"   , name);
		json.put("ADDRESS1" , address1);
		json.put("ADDRESS2" , address2);
		json.put("ADDRESS3" , address3);
		json.put("PHONE", phone);

		response.setContentType("application/json");
		response.getWriter().write(json.toString());

	}
}
