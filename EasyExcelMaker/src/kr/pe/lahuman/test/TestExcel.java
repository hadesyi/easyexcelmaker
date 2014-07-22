package kr.pe.lahuman.test;

import java.util.Map;

import kr.pe.lahuman.excel.ExcelUtil;

public class TestExcel {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ExcelUtil.readExcelFile("D:\\workspaces\\workspace_35\\ExcelUtils\\Book1.xls");
		
		/*
		 	StringBuffer searchText = new StringBuffer("시작일 : ");
		searchText.append(request.getParameter("startDate"));
		searchText.append(" 종료일 : ");
		searchText.append(request.getParameter("endDate"));

//		<th  class="board_header_c" >일자</th>
//		<th  class="board_header_c" >미평가</th>
//		<th  class="board_header_c" >배포</th>
//		<th  class="board_header_c" >재처리1</th>
//		<th  class="board_header_c" >배포불가요청</th>
//		<th  class="board_header_c" >배포불가</th>
//		<th  class="board_header_c" >재처리2</th>
//		<th  class="board_header_c" >Total</th>
		String[] titles = { "일자", "미평가", "배포", "재처리1", "배포불가요청", "배포불가", "재처리2", "Total"};
//
//		<td class="board_list_c"><%=DateUtil.dateFormat(data.getString("SCAN_DATE"), "MM.dd")%></td>
//		<td class="board_list_c"><%=data.getString("NON") %></td>
//		<td class="board_list_c"><%=data.getString("D1") %></td>
//		<td class="board_list_c"><%=data.getString("R1") %></td>
//		<td class="board_list_c"><%=data.getString("R2") %></td>
//		<td class="board_list_c"><%=data.getString("E1") %></td>
//		<td class="board_list_c"><%=data.getString("E2") %></td>
//		<td class="board_header_c"><%=data.getString("TOT") %></td>
		String[] keys = { "SCAN_DATE$MM.dd", "NON$TOT",
				"D1$TOT", "R1$TOT", 
				"E1$TOT", "E2$TOT", "R2$TOT","TOT$TOT" };
		ExcelUtil.makeExcelTotal(response, "staticstics_"+request.getParameter("startDate")+"~"+request.getParameter("endDate")+".xls",  searchText.toString(), titles, keys,
				(Map[]) stService.getEvaluation(paramMap)); 
		 
		 */
	}

}
