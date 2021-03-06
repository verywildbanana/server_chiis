package com.verywildbanana.chiis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	Logger log = Logger.getLogger(this.getClass());

	//    @Resource(name="fileUtils")
	//    private FileUtils fileUtils;

	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;

	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);

	}

	//    @Override
	//    public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
	//        sampleDAO.insertBoard(map);
	//        int count = sampleDAO.selectBoardListCount(map);
	//         
	//        map.put("IDX", "" +count);
	//        
	//        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	//        for(int i=0, size=list.size(); i<size; i++){
	//            sampleDAO.insertFile(list.get(i));
	//        }
	//    }

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		sampleDAO.updateHitCnt(map);

		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);

		List<Map<String,Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);

		return resultMap;
	}

	@Override
	public void updateBoard(Map<String, Object> map) throws Exception{
		sampleDAO.updateBoard(map);
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}


	@Override
	public void insertBoard(Map<String, Object> map) throws Exception {

		sampleDAO.insertBoard(map);
	}

	public int selectBoardLikeIdCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectBoardLikeIdCount(map);
		return count;
	} 

	public void updateImg(int position, Map<String, Object> map) throws Exception {

		sampleDAO.updateImg(position, map);
		
	} 

	public void updateDentistThemes(Map<String, Object> map) throws Exception {

		sampleDAO.updateDentistThemes(map);
		
	} 
	
	public void updateDentistHashTags(Map<String, Object> map) throws Exception {

		sampleDAO.updateDentistHashTags(map);
		
	} 
	
	
	public Map<String, Object> selectIdDentist(Map<String, Object> map) throws Exception {
		return sampleDAO.selectIdDentist(map);

	}
	
	public List<Map<String, Object>> selectDentistList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectDentistList(map);

	}
	
	public List<Map<String, Object>> selectDentistTheme1List(Map<String, Object> map) throws Exception {
		return sampleDAO.selectDentistTheme1List(map);

	}
	
	public List<Map<String, Object>> selectDentistTheme2List(Map<String, Object> map) throws Exception {
		return sampleDAO.selectDentistTheme2List(map);

	}
	
	public List<Map<String, Object>> selectDentistTheme3List(Map<String, Object> map) throws Exception {
		return sampleDAO.selectDentistTheme3List(map);

	}
	
	
	public List<Map<String, Object>> selectDentistTheme4List(Map<String, Object> map) throws Exception {
		return sampleDAO.selectDentistTheme4List(map);

	}
	
	
	
	public int selectDentistListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectDentistListCount(map);
		return count;
	} 

	public int selectDentistTheme1ListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectDentistTheme1ListCount(map);
		return count;
	} 
	
	public int selectDentistTheme2ListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectDentistTheme2ListCount(map);
		return count;
	} 
	
	public int selectDentistTheme3ListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectDentistTheme3ListCount(map);
		return count;
	} 
	
	public int selectDentistTheme4ListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectDentistTheme4ListCount(map);
		return count;
	} 
	
	
	public int searchDentistListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.searchDentistListCount(map);
		return count;
	} 
	
	public List<Map<String, Object>> searchDentistList(Map<String, Object> map) throws Exception {
		return sampleDAO.searchDentistList(map);

	}
	
	
	public int searchTagDentistListCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.searchTagDentistListCount(map);
		return count;
	} 
	
	public List<Map<String, Object>> searchTagDentistList(Map<String, Object> map) throws Exception {
		return sampleDAO.searchTagDentistList(map);

	}
	
	

/*	
*  user	
*/	
	
	public void insertUser(Map<String, Object> map) throws Exception {

		sampleDAO.insertUser(map);
	}
	
	public Map<String, Object> selectIdUser(Map<String, Object> map) throws Exception {
		return sampleDAO.selectIdUser(map);

	}
	
	public Map<String, Object> selectFBIdUser(Map<String, Object> map) throws Exception {
		return sampleDAO.selectFBIdUser(map);

	}
	
	public Map<String, Object> selectKAKAOIdUser(Map<String, Object> map) throws Exception {
		return sampleDAO.selectKAKAOIdUser(map);

	}
	
	public int selectUserLikeIdCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectUserLikeIdCount(map);
		return count;
	} 
	
	public int selectLikeFBIdCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectLikeFBIdCount(map);
		return count;
	} 
	
	public int selectLikeKAKAOIdCount(Map<String, Object> map) throws Exception {

		int count = sampleDAO.selectLikeKAKAOIdCount(map);
		return count;
	} 
	
	/*
	 *  inquiry reply 
	*/
	
	public void insertUserInquiry(Map<String, Object> map) throws Exception {

		sampleDAO.insertUserInquiry(map);
	}
	
	public void insertDentistReply(Map<String, Object> map) throws Exception {

		sampleDAO.insertDentistReply(map);
	}
	
	
	
	
	
}