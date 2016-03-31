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
import com.verywildbanana.chiis.data.ApiStatusInfo;
import com.verywildbanana.chiis.data.DetailDentistParserData;
import com.verywildbanana.chiis.file.FileInfo;
import com.verywildbanana.chiis.file.FileUploadResponse;
import com.verywildbanana.chiis.file.UploadService;
import com.verywildbanana.chiis.util.DateUtil;



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
	public ResponseEntity<ApiStatusInfo> insertDentist(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		ApiStatusInfo info = new ApiStatusInfo();
		
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

				log.debug("InsertDentist doGet 1 ");

				info.code = Constants.API_ERROR_CODE_DENTAL_1;
				info.message =  Constants.API_ERROR_CODE_DENTAL_1_TXT;
				return new ResponseEntity<ApiStatusInfo>(
						info, HttpStatus.CREATED);

			}
			log.info("InsertDentist doGet 2");

			sampleService.insertBoard(commandMap.getMap());

			log.info("InsertDentist doGet 3");

		} 
		catch (Exception e) {

			e.printStackTrace();

			info.code = Constants.API_ERROR_CODE_TOTAL_1;
			info.message =  e.toString();
			return new ResponseEntity<ApiStatusInfo>(
					info, HttpStatus.CREATED);
			
		}


		info.code = "200.0000";
		info.message =   "success";
		return new ResponseEntity<ApiStatusInfo>(
				info, HttpStatus.CREATED);
		
	}

	@RequestMapping(value="/api/files.do", method = RequestMethod.POST, headers = "Content-Type!=multipart/form-data")
	public ResponseEntity<FileUploadResponse> uploadFileBody(
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		FileUploadResponse fres  = new FileUploadResponse();
		
		FileInfo fileInfo = null;

		try {

			fileInfo = uploadService.saveFile(request.getInputStream());

		} catch (IOException e) {

			
			fres.code =  Constants.API_ERROR_CODE_TOTAL_1;
			fres.message =  e.toString();
			return new ResponseEntity<FileUploadResponse>(
					fres, HttpStatus.CREATED);
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

		
		fres.code =  "200.0000";
		fres.message =  "success";
		fres.setLocation(fileInfo.getDownloadUrl());

		return new ResponseEntity<FileUploadResponse>(
				fres, HttpStatus.CREATED);
	}

	@RequestMapping(value="/api/getDentistInfo.do", method = RequestMethod.GET)
	public ResponseEntity<DetailDentistParserData> getDentistInfo(CommandMap commandMap, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		log.info("getDentistInfo start ");

		DetailDentistParserData  parserData = new DetailDentistParserData(); 
		
		Map<String, Object>  mapData = null;

		try {

			mapData = sampleService.selectIdDentist(commandMap.getMap());

			log.info("getDentistInfo id  " + commandMap.get("ID"));

			if(mapData == null || mapData.isEmpty()) {

				parserData.code =  Constants.API_ERROR_CODE_DENTAL_2;
				parserData.message =  Constants.API_ERROR_CODE_DENTAL_2_TXT;
				
				return new ResponseEntity<DetailDentistParserData>(parserData, HttpStatus.OK);

			}

		} 
		catch (Exception e) {

			e.printStackTrace();

			parserData.code =  Constants.API_ERROR_CODE_TOTAL_1;
			parserData.message =   e.toString();
			
			return new ResponseEntity<DetailDentistParserData>(parserData, HttpStatus.OK);
		}


		parserData.code =  "200.0000";
		parserData.message =  "success";
		parserData.dentist.NO = (Integer) mapData.get("NO");
		parserData.dentist.ID = (String) mapData.get("ID");
		parserData.dentist.PASSWD = (String) mapData.get("PASSWD");
		parserData.dentist.NAME = (String) mapData.get("NAME");
		parserData.dentist.ADDRESS1 = (String) mapData.get("ADDRESS1");
		parserData.dentist.ADDRESS2 = (String) mapData.get("ADDRESS2");
		parserData.dentist.ADDRESS3 = (String) mapData.get("ADDRESS3");
		parserData.dentist.ADDRESS4 = (String) mapData.get("ADDRESS4");
		parserData.dentist.ADDRESS5 = (String) mapData.get("ADDRESS5");
		parserData.dentist.LAT = (String) mapData.get("LAT");
		parserData.dentist.LNG = (String) mapData.get("LNG");
		parserData.dentist.ACTIVE_TIME1 = (String) mapData.get("ACTIVE_TIME1");
		parserData.dentist.ACTIVE_TIME2 = (String) mapData.get("ACTIVE_TIME2");
		parserData.dentist.ACTIVE_TIME3 = (String) mapData.get("ACTIVE_TIME3");
		parserData.dentist.DES = (String) mapData.get("DES");
		parserData.dentist.IMG_1 = (String) mapData.get("IMG_1");
		parserData.dentist.IMG_2 = (String) mapData.get("IMG_2");
		parserData.dentist.IMG_3 = (String) mapData.get("IMG_3");
		parserData.dentist.IMG_4 = (String) mapData.get("IMG_4");
		parserData.dentist.DT_1_NAME = (String) mapData.get("DT_1_NAME");
		parserData.dentist.DT_1_DES = (String) mapData.get("DT_1_DES");
		parserData.dentist.DT_1_IMG = (String) mapData.get("DT_1_IMG");
		parserData.dentist.DT_2_NAME = (String) mapData.get("DT_2_NAME");
		parserData.dentist.DT_2_DES = (String) mapData.get("DT_2_DES");
		parserData.dentist.DT_2_IMG = (String) mapData.get("DT_2_IMG");
		parserData.dentist.DT_3_NAME = (String) mapData.get("DT_3_NAME");
		parserData.dentist.DT_3_DES = (String) mapData.get("DT_3_DES");
		parserData.dentist.DT_3_IMG = (String) mapData.get("DT_3_IMG");
		Date date = (Date) mapData.get("REG_TIME");
		parserData.dentist.REG_TIME =  DateUtil.getDateFormat(date, DateUtil.DATE_FORMAT_6);

		return new ResponseEntity<DetailDentistParserData>(parserData, HttpStatus.OK);
		
	}
}
