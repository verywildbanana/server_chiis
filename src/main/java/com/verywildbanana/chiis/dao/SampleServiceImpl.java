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
    public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
        
    	sampleDAO.insertBoard(map);
        int count = sampleDAO.selectBoardListCount(map);
    }
    

}