<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <form id="frm" name="frm" enctype="multipart/form-data">
        <table class="board_view">
            <colgroup>
                <col width="15%">
                <col width="*"/>
            </colgroup>
            <caption>치과 등록</caption>
            <tbody>
                <tr>
                    <th scope="row">계정아이디(예:dental1)</th>
                    <td><input type="text" size="70" id="ID" name="ID" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">비밀번호(10자)</th>
                    <td><input type="text" size="70" id="PASSWD " name="PASSWD " class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">병원명(5자)</th>
                    <td><input type="text" size="70" id="NAME" name="NAME" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">주소(시/도)</th>
                    <td><input type="text" size="70" id="ADDRESS1" name="ADDRESS1" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">주소(지역구)</th>
                    <td><input type="text" size="70" id="ADDRESS2" name="ADDRESS2" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">주소(동)</th>
                    <td><input type="text" size="70" id="ADDRESS3" name="ADDRESS3" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">주소(세부)</th>
                    <td><input type="text" size="70" id="ADDRESS4" name="ADDRESS4" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">좌표(latitude)</th>
                    <td><input type="text" size="70" id="LAT" name="LAT" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">좌표(longitude)</th>
                    <td><input type="text" size="70" id="LNG" name="LNG" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">전화번호(15자)</th>
                    <td><input type="text" size="70" id="PHONE" name="PHONE" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">진료시간(평일)</th>
                    <td><input type="text" size="70" id="ACTIVE_TIME1" name="ACTIVE_TIME1" class="wdp_90"></input></td>
                </tr>
                 <tr>
                   <th scope="row">진료시간(주말)</th>
                    <td><input type="text" size="70" id="ACTIVE_TIME2" name="ACTIVE_TIME2" class="wdp_90"></input></td>
                </tr>
                 <tr>
                   <th scope="row">진료시간(점심)</th>
                    <td><input type="text" size="70" id="ACTIVE_TIME3" name="ACTIVE_TIME3" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">병원소개(50자)</th>
                    <td><input type="text" size="70" id="DES" name="DES" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">의료진1(5자)</th>
                    <td><input type="text" size="70" id="DT_1_NAME" name="DT_1_NAME" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">의료진1내용(50자)</th>
                    <td><input type="text" size="70" id="DT_1_DES" name="DT_1_DES" class="wdp_90"></input></td>
                </tr>
                 <tr>
                   <th scope="row">의료진2(5자)</th>
                    <td><input type="text" size="70" id="DT_2_NAME" name="DT_2_NAME" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">의료진2내용(50자)</th>
                    <td><input type="text" size="70" id="DT_2_DES" name="DT_2_DES" class="wdp_90"></input></td>
                </tr>
                 <tr>
                   <th scope="row">의료진3(5자)</th>
                    <td><input type="text" size="70" id="DT_3_NAME" name="DT_3_NAME" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">의료진3내용(50자)</th>
                    <td><input type="text" size="70" id="DT_3_DES" name="DT_3_DES" class="wdp_90"></input></td>
                </tr>
                <tr>
                   <th scope="row">이벤트소개(50자)</th>
                    <td><input type="text" size="70" id="EVENT_DES" name="EVENT_DES" class="wdp_90"></input></td>
                </tr>
            </tbody>
        </table>
        
        
        <input type="file" name="file">
        
        <br/><br/>
         
        <a href="#this" class="btn" id="write">작성하기</a>
        <a href="#this" class="btn" id="list">목록으로</a>
        
    </form>
    
     
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#list").on("click", function(e){ //목록으로 버튼
                e.preventDefault();
                fn_openBoardList();
            });
             
            $("#write").on("click", function(e){ //작성하기 버튼
                e.preventDefault();
                fn_insertBoard();
            });
            
          /*   $("#home").on("click", function(e){ //홈으로  버튼
                e.preventDefault();
                fn_home();
            }); */
            
        });
         
        function fn_openBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
            comSubmit.submit();
        }
         
        function fn_insertBoard(){
            var comSubmit = new ComSubmit("frm");
            
            comSubmit.setUrl("<c:url value='/sample/insertBoard.do' />");
            comSubmit.submit();
        }
        
        function fn_home(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/home.do' />");
            comSubmit.submit();
        }
        
    </script>
</body>
</html>