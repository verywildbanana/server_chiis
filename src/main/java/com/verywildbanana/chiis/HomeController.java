package com.verywildbanana.chiis;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.util.ArrayList;
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
import com.verywildbanana.chiis.data.DentistData;
import com.verywildbanana.chiis.data.DentistListParserData;
import com.verywildbanana.chiis.data.DetailDentistParserData;
import com.verywildbanana.chiis.data.DetailUserParserData;
import com.verywildbanana.chiis.data.LoginParserData;
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
	
	
	@RequestMapping(value="/api/updateDentistThemes.do", method = RequestMethod.POST)
	public ResponseEntity<ApiStatusInfo> updateDentistThemes(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		ApiStatusInfo info = new ApiStatusInfo();

		log.info("updateDentistThemes start " + commandMap.get("THEME_1"));
		

		try {

			sampleService.updateDentistThemes(commandMap.getMap());
			info.code = "200.0000";
			info.message =   "success";
			return new ResponseEntity<ApiStatusInfo>(
					info, HttpStatus.CREATED);


		} 
		catch (Exception e) {

			e.printStackTrace();

			info.code = Constants.API_ERROR_CODE_TOTAL_1;
			info.message =  e.toString();
			return new ResponseEntity<ApiStatusInfo>(
					info, HttpStatus.CREATED);

		}


	}
	
	@RequestMapping(value="/api/updateDentistHashTags.do", method = RequestMethod.POST)
	public ResponseEntity<ApiStatusInfo> updateDentistHashTags(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		ApiStatusInfo info = new ApiStatusInfo();

		log.info("updateDentistHashTags start " + commandMap.get("HASH_TAG_1"));
		

		try {

			sampleService.updateDentistHashTags(commandMap.getMap());
			info.code = "200.0000";
			info.message =   "success";
			return new ResponseEntity<ApiStatusInfo>(
					info, HttpStatus.CREATED);


		} 
		catch (Exception e) {

			e.printStackTrace();

			info.code = Constants.API_ERROR_CODE_TOTAL_1;
			info.message =  e.toString();
			return new ResponseEntity<ApiStatusInfo>(
					info, HttpStatus.CREATED);

		}

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
		parserData.dentist.PHONE = (String) mapData.get("PHONE");
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
		
		parserData.dentist.THEME_1 = (String) mapData.get("THEME_1");
		parserData.dentist.THEME_2 = (String) mapData.get("THEME_2");
		parserData.dentist.THEME_3 = (String) mapData.get("THEME_3");
		parserData.dentist.THEME_4 = (String) mapData.get("THEME_4");
		
		if(mapData.get("HASH_TAG_1") != null) parserData.dentist.HASH_TAG_1 = (String) mapData.get("HASH_TAG_1");

		return new ResponseEntity<DetailDentistParserData>(parserData, HttpStatus.OK);

	}

	@RequestMapping(value="/api/getDentistList.do", method = RequestMethod.GET)
	public ResponseEntity<DentistListParserData> getDentistList(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{


		int page = 0;
		int size = 20;
		
		
		log.info("req getDentistList commandMap PAGE : " + commandMap.get("PAGE"));
		
		if(commandMap.get("PAGE") != null) {

			page = Integer.parseInt((String) commandMap.get("PAGE"));
			
			if(commandMap.get("SIZE") != null) {

				size = Integer.parseInt((String) commandMap.get("SIZE"));
				
			}
			
		}
		
		
		log.info("req getDentistList page : " + page + " size : " + size);
		

		DentistListParserData  parserData = new DentistListParserData(); 

		List<Map<String,Object>> list = null;

		try {

			int total_count = sampleService.selectDentistListCount(commandMap.getMap());

			log.info("req getDentistList  total_count " + total_count );

			if (total_count > 0) { 

				commandMap.put("FROM", page*size);
				commandMap.put("TO", page*size + size);

			}

			list = sampleService.selectDentistList(commandMap.getMap());


			if(list == null || list.isEmpty()) {

				parserData.code =  Constants.API_ERROR_CODE_DENTAL_2;
				parserData.message =  Constants.API_ERROR_CODE_DENTAL_2_TXT;

				return new ResponseEntity<DentistListParserData>(parserData, HttpStatus.OK);

			}


			parserData.code =  "200.0000";
			parserData.message =  "success";
			parserData.TOTAL_COUNT =  total_count;
			
			if(total_count > (page*size + size) ) {
			
				parserData.NEXT = true;
				
			}
			else {
				
				parserData.NEXT = false;
				
			}
			 
					
			for (int i = 0; i < list.size(); i++) {

				Map<String,Object> mapData =  list.get(i);

				DentistData dData = new DentistData();
				dData.NO = (Integer) mapData.get("NO");
				dData.ID = (String) mapData.get("ID");
				dData.NAME = (String) mapData.get("NAME");
				dData.ADDRESS1 = (String) mapData.get("ADDRESS1");
				dData.ADDRESS2 = (String) mapData.get("ADDRESS2");
				dData.ADDRESS3 = (String) mapData.get("ADDRESS3");
				dData.ADDRESS4 = (String) mapData.get("ADDRESS4");
				dData.LAT = (String) mapData.get("LAT");
				dData.LNG = (String) mapData.get("LNG");
				dData.PHONE = (String) mapData.get("PHONE");
				dData.IMG_1 = (String) mapData.get("IMG_1");
				Date date = (Date) mapData.get("REG_TIME");
				dData.REG_TIME =  DateUtil.getDateFormat(date, DateUtil.DATE_FORMAT_6);
				if(mapData.get("HASH_TAG_1") != null) dData.HASH_TAG_1 = (String) mapData.get("HASH_TAG_1");
				
				parserData.dentist.add(dData);

			}

			return new ResponseEntity<DentistListParserData>(parserData, HttpStatus.OK);


		} 
		catch (Exception e) {

			e.printStackTrace();

			parserData.code =  Constants.API_ERROR_CODE_TOTAL_1;
			parserData.message =   e.toString();

			return new ResponseEntity<DentistListParserData>(parserData, HttpStatus.OK);
		}

	}



	@RequestMapping(value="/api/insertFBUser.do", method = RequestMethod.POST)
	public ResponseEntity<LoginParserData> insertFBUser(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String FB_ID = request.getParameter("FB_ID");

		log.info("insertFBUser  FB_ID " + FB_ID);

		LoginParserData parserData  = new LoginParserData();
		Map<String, Object>  mapData = null;
		String uuid = null;

		try {

			mapData = sampleService.selectFBIdUser(commandMap.getMap());

			if(mapData == null || mapData.isEmpty()) {


				log.info("insertFBUser mapData empty ");


				SecureRandom rnd = new SecureRandom();
				StringBuffer buf = new StringBuffer();

				buf.append(FB_ID).append("_");

				for (int i = 0; i < 10; i++) {
					if (rnd.nextBoolean()) {
						buf.append((char) ((int) (rnd.nextInt(26)) + 97));
					} else {
						buf.append((rnd.nextInt(10)));
					}
				}

				uuid = buf.toString();

				commandMap.put("ID", uuid);

				sampleService.insertUser(commandMap.getMap());

			}
			else {

				log.info("insertFBUser mapData not empty");
				uuid = (String) mapData.get("ID");
			}

		} 
		catch (Exception e) {

			e.printStackTrace();


			parserData.code =  Constants.API_ERROR_CODE_TOTAL_1;
			parserData.message =   e.toString();

			return new ResponseEntity<LoginParserData>(parserData, HttpStatus.OK);

		}


		log.info("insertFBUser login.ID " + uuid);

		parserData.code =  "200.0000";
		parserData.message =  "success";
		parserData.login.ID = uuid;

		return new ResponseEntity<LoginParserData>(parserData, HttpStatus.OK);
	}

	@RequestMapping(value="/api/insertKAKAOUser.do", method = RequestMethod.POST)
	public ResponseEntity<LoginParserData> insertKAKAOUser(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String KAKAO_ID = request.getParameter("KAKAO_ID");

		log.info("insertKAKAOUser  KAKAO_ID " + KAKAO_ID);

		LoginParserData parserData  = new LoginParserData();
		Map<String, Object>  mapData = null;
		String  uuid = null;

		try {

			mapData = sampleService.selectKAKAOIdUser(commandMap.getMap());

			if(mapData == null || mapData.isEmpty()) {


				log.info("insertKAKAOUser mapData empty ");


				SecureRandom rnd = new SecureRandom();
				StringBuffer buf = new StringBuffer();

				buf.append(KAKAO_ID).append("_");

				for (int i = 0; i < 10; i++) {
					if (rnd.nextBoolean()) {
						buf.append((char) ((int) (rnd.nextInt(26)) + 97));
					} else {
						buf.append((rnd.nextInt(10)));
					}
				}

				uuid = buf.toString();

				commandMap.put("ID", uuid);

				sampleService.insertUser(commandMap.getMap());

			}
			else {

				log.info("insertKAKAOUser mapData not empty");
				uuid = (String) mapData.get("ID");

			}

		} 
		catch (Exception e) {

			e.printStackTrace();


			parserData.code =  Constants.API_ERROR_CODE_TOTAL_1;
			parserData.message =   e.toString();
			return new ResponseEntity<LoginParserData>(parserData, HttpStatus.OK);

		}


		log.info("insertKAKAOUser login.ID " + uuid);

		parserData.code =  "200.0000";
		parserData.message =  "success";
		parserData.login.ID = uuid;

		return new ResponseEntity<LoginParserData>(parserData, HttpStatus.OK);
	}

	@RequestMapping(value="/api/getUserInfo.do", method = RequestMethod.GET)
	public ResponseEntity<DetailUserParserData> getUserInfo(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{

		DetailUserParserData parserData  = new DetailUserParserData();
		Map<String, Object>  mapData = null;

		log.info("getUserInfo id  " + commandMap.get("ID"));



		try{

			mapData = sampleService.selectIdUser(commandMap.getMap());

			if(mapData == null || mapData.isEmpty()) {

				parserData.code =  Constants.API_ERROR_CODE_DENTAL_1_2;
				parserData.message =  Constants.API_ERROR_CODE_DENTAL_1_2_TXT;

				return new ResponseEntity<DetailUserParserData>(parserData, HttpStatus.OK);

			}

			parserData.code =  "200.0000";
			parserData.message =  "success";
			parserData.user.NO = (Integer) mapData.get("NO");
			parserData.user.ID = (String) mapData.get("ID");
			parserData.user.PASSWD = (String) mapData.get("PASSWD");
			parserData.user.NAME = (String) mapData.get("NAME");
			parserData.user.ADDRESS1 = (String) mapData.get("ADDRESS1");
			parserData.user.ADDRESS2 = (String) mapData.get("ADDRESS2");
			parserData.user.ADDRESS3 = (String) mapData.get("ADDRESS3");
			parserData.user.ADDRESS4 = (String) mapData.get("ADDRESS4");
			parserData.user.ADDRESS5 = (String) mapData.get("ADDRESS5");
			parserData.user.PHONE = (String) mapData.get("PHONE");
			parserData.user.EMAIL = (String) mapData.get("EMAIL");
			parserData.user.DES = (String) mapData.get("DES");
			parserData.user.IMG_1 = (String) mapData.get("IMG_1");
			parserData.user.IMG_2 = (String) mapData.get("IMG_2");
			parserData.user.IMG_3 = (String) mapData.get("IMG_3");
			parserData.user.IMG_4 = (String) mapData.get("IMG_4");
			Date date = (Date) mapData.get("REG_TIME");
			parserData.user.REG_TIME =  DateUtil.getDateFormat(date, DateUtil.DATE_FORMAT_6);
			parserData.user.FB_ID = (String) mapData.get("FB_ID");
			parserData.user.KAKAO_ID = (String) mapData.get("KAKAO_ID");
			parserData.user.LOGIN_TYPE = (String) mapData.get("LOGIN_TYPE");
			parserData.user.FB_TOKEN = (String) mapData.get("FB_TOKEN");
			parserData.user.KAKAO_TOKEN = (String) mapData.get("KAKAO_TOKEN");
			parserData.user.GENDER = (String) mapData.get("GENDER");

			if(mapData.get("AGE") != null) {

				parserData.user.AGE = (Integer)mapData.get("AGE");
			}

			return new ResponseEntity<DetailUserParserData>(parserData, HttpStatus.OK);

		} 
		catch (Exception e) {

			e.printStackTrace();

			parserData.code =  Constants.API_ERROR_CODE_TOTAL_1;
			parserData.message =   e.toString();

			return new ResponseEntity<DetailUserParserData>(parserData, HttpStatus.OK);
		}

	}


}
