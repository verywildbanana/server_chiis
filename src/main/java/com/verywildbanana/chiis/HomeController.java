package com.verywildbanana.chiis;

import java.io.IOException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.verywildbanana.chiis.common.CommandMap;
import com.verywildbanana.chiis.dao.SampleServiceImpl;
import com.verywildbanana.chiis.file.FileInfo;
import com.verywildbanana.chiis.file.FileUploadResponse;
import com.verywildbanana.chiis.file.UploadService;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {


	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="sampleService")
	private SampleServiceImpl sampleService;

	@Resource(name="uploadService")
	private UploadService uploadService;
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

		sampleService.insertBoard(commandMap.getMap());

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

		log.info("insertDentist " + id);

		try {

			int count = sampleService.selectBoardLikeIdCount(commandMap.getMap());
			log.info("InsertDentist count " + count);

			if(count > 0) {

				JSONObject json =  getErrorJsonData(Constants.API_ERROR_CODE_DENTAL_1, Constants.API_ERROR_CODE_DENTAL_1_TXT);
				response.setContentType("application/json");
				response.getWriter().write(json.toString());

				log.debug("InsertDentist doGet 1 ");

				return;

			}
			log.info("InsertDentist doGet 2");

			sampleService.insertBoard(commandMap.getMap());

			log.info("InsertDentist doGet 3");

		} 
		catch (Exception e) {

			e.printStackTrace();

			JSONObject json =  getErrorJsonData(Constants.API_ERROR_CODE_TOTAL_1, e.toString());
			response.setContentType("application/json");
			response.getWriter().write(json.toString());

			return;
		}

		JSONObject json =  getErrorJsonData("200.0000", "success");
		response.setContentType("application/json");
		response.getWriter().write(json.toString());

	}
	private JSONObject getErrorJsonData(String code, String message) {

		JSONObject json = new JSONObject();
		json.put("code"     ,  code);
		json.put("message"     ,  message);

		JSONObject inner = new JSONObject();
		inner.put("required_version"     , Constants.required_version);
		inner.put("update_url"     ,  Constants.update_url);
		json.put("update_info"     ,  inner);

		return json;

	}

	@RequestMapping(value="/api/files.do", method = RequestMethod.POST, headers = "Content-Type!=multipart/form-data")
	public ResponseEntity<FileUploadResponse> uploadFileBody(
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		FileInfo fileInfo = null;

		try {
		
			fileInfo = uploadService.saveFile(request.getInputStream());
		
		} catch (IOException e) {

			JSONObject json =  getErrorJsonData(Constants.API_ERROR_CODE_TOTAL_1, e.toString());
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		}

		String imgPosition = request.getHeader("img_position");
		String id = request.getHeader("insert_id");
		
		log.info("uploadFileBody insert_id  " + id);
		log.info("uploadFileBody img_position  " + imgPosition);
		
		int position = Integer.parseInt(imgPosition);
		
		CommandMap commonMap = new CommandMap();
		commonMap.put("IMG", "" + fileInfo.getDownloadUrl());
		commonMap.put("ID", id);
		sampleService.updateImg(position, commonMap.getMap());
		
		FileUploadResponse fres  = new FileUploadResponse("200.0000", "success");
		fres.setLocation(fileInfo.getDownloadUrl());
		
		return new ResponseEntity<FileUploadResponse>(
				fres, HttpStatus.CREATED);
	}

}
