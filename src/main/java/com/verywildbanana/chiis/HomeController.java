package com.verywildbanana.chiis;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.verywildbanana.chiis.common.CommandMap;
import com.verywildbanana.chiis.dao.SampleService;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {


	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="sampleService")
	private SampleService sampleService;


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
		ModelAndView mv = new ModelAndView("/sample/boardWrite");

		return mv;
	}

	 @RequestMapping(value="/sample/insertBoard.do")
	    public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
	        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	         
	        sampleService.insertBoard(commandMap.getMap(), request);
	         
	        return mv;
	    }
	
}
