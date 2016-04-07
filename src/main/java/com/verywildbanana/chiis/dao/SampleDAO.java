package com.verywildbanana.chiis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("sample.selectBoardList", map);
	}

	public int selectBoardListCount(Map<String, Object> map) throws Exception{
		return (Integer)selectOne("sample.selectBoardCount", map);
	}

	public void insertBoard(Map<String, Object> map) throws Exception{
		insert("sample.insertBoard", map);
	}

	public void updateHitCnt(Map<String, Object> map) throws Exception{
		update("sample.updateHitCnt", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
	}

	public void updateBoard(Map<String, Object> map) throws Exception{
		update("sample.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) throws Exception{
		update("sample.deleteBoard", map);
	}

	public void insertFile(Map<String, Object> map) throws Exception{
		insert("sample.insertFile", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
	}

	public int selectBoardLikeIdCount(Map<String, Object> map) throws Exception{
		return (Integer)selectOne("sample.selectBoardLikeIDCount", map);
	}

	public void updateImg(int position, Map<String, Object> map) throws Exception{

		if(position == 1) {

			update("sample.updateImg1", map);
			
		}
		else if(position == 2) {

			update("sample.updateImg2", map);
		}
		else if(position == 3) {

			update("sample.updateImg3", map);
		}
		else if(position == 4) {

			update("sample.updateImg4", map);
		}
		else if(position == 5) {

			update("sample.updateImg5", map);
		}
		else if(position == 6) {

			update("sample.updateImg6", map);
		}
		else if(position == 7) {

			update("sample.updateImg7", map);
			
		}
		else if(position == 8) {

			update("sample.updateImg8", map);
		}

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectIdDentist(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectOne("sample.selectIdDentist", map);
	}
	
	public int selectLikeFBIdCount(Map<String, Object> map) throws Exception{
		return (Integer)selectOne("sample.selectLikFBIDCount", map);
	}
	
	
	public int selectLikeKAKAOIdCount(Map<String, Object> map) throws Exception{
		return (Integer)selectOne("sample.selectLikKAKAOIDCount", map);
	}
	
	public int selectUserLikeIdCount(Map<String, Object> map) throws Exception{
		return (Integer)selectOne("sample.selectUserLikIDCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectIdUser(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectOne("sample.selectIdUser", map);
	}
	
	public Map<String, Object> selectFBIdUser(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectOne("sample.selectFBIdUser", map);
	}
	
	public Map<String, Object> selectKAKAOIdUser(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectOne("sample.selectKAKAOIdUser", map);
	}
	
	public void insertUser(Map<String, Object> map) throws Exception{
		insert("sample.insertUser", map);
	}
	
}
