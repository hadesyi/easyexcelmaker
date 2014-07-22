package kr.pe.lahuman.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kr.pe.lahuman.bean.DataMap;
import kr.pe.lahuman.utils.DateUtil;

/**
 * The Class ExcelUtil.
 * 
 * @author : lahuman
 */
public class ExcelUtil {

	/**
	 * Make excel. title에서 TITLEVAL$0,0-1 : $x,y값 1개 이상일 경우 startVal-endVal
	 * keys에서 KEYVAL$type : TYPE 종류 - XY:x,y값 1개 이상일 경우 startVal-endVal - H 시간 만
	 * 뽑을 경우 - dateType yyyy-MM-dd HH:mm 등등... 주의 사항 : 띄어쓰기 금지!!!
	 * 
	 * @param response
	 *            the response
	 * @param fileName
	 *            the file name
	 * @param searchText
	 *            the search text
	 * @param titles
	 *            the titles
	 * @param keys
	 *            the keys
	 * @param dataMap
	 *            the data map
	 */
	public static void makeExcel(HttpServletResponse response, String fileName,
			String searchText, String[] titles, String[] keys, Map[] dataMap) {
		makeExcelCommon(response, fileName, searchText, titles, keys, dataMap,
				false);
	}

	public static void makeExcel(HttpServletResponse response, String fileName,
			String searchText, String[] titles, String[] keys, List<Map<String, String>> dataMap) {
		makeExcelCommon(response, fileName, searchText, titles, keys, dataMap,
				false);
	}

	/**
	 * Make excel total.
	 * 
	 * @param response
	 *            the response
	 * @param fileName
	 *            the file name
	 * @param searchText
	 *            the search text
	 * @param titles
	 *            the titles
	 * @param keys
	 *            the keys
	 * @param dataMap
	 *            the data map
	 */
	public static void makeExcelTotal(HttpServletResponse response,
			String fileName, String searchText, String[] titles, String[] keys,
			Map[] dataMap) {
		makeExcelCommon(response, fileName, searchText, titles, keys, dataMap,
				true);
	}

	/**
	 * Make excel common.
	 * 
	 * @param response
	 *            the response
	 * @param fileName
	 *            the file name
	 * @param searchText
	 *            the search text
	 * @param titles
	 *            the titles
	 * @param keys
	 *            the keys
	 * @param dataMap
	 *            the data map
	 * @param isTotal
	 *            the is total
	 */
	private static void makeExcelCommon(HttpServletResponse response,
			String fileName, String searchText, String[] titles, String[] keys,
			Map[] dataMap, boolean isTotal) {
		WritableWorkbook workbook = null;
		try {
			// Total 관련 Map
			DataMap<String, Integer> totalMap = new DataMap<String, Integer>();

			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);

			workbook = Workbook.createWorkbook(response.getOutputStream());

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			jxl.write.WritableCellFormat format = new WritableCellFormat();
			jxl.write.WritableCellFormat format0 = new WritableCellFormat();

			format.setBackground(jxl.format.Colour.GRAY_25);
			format.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format0.setBackground(jxl.format.Colour.WHITE);
			format0.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format0.setAlignment(jxl.format.Alignment.CENTRE);
			format0.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			sheet.setColumnView(0, 8);

			jxl.write.Label label = null;
			jxl.write.Blank blank = null;

			int heightTemp = 0;

			// 검색 조건 삽입
			label = new jxl.write.Label(0, 0, searchText);
			sheet.addCell(label);

			// 해더 만들기
			for (int i = 0; i < titles.length; i++) {
				if (titles[i].indexOf("$") == -1) {
					label = new jxl.write.Label(i, 3, titles[i], format);
					heightTemp = 3;
				} else {
					String[] strs = titles[i].split("\\$");
					String[] xy = strs[1].split(",");
					// 셀 병합 작업
					int startX = 0;
					int endX = 0;
					int startY = 3;
					int endY = 3;

					if (xy[0].indexOf("-") != -1 || xy[1].indexOf("-") != -1) {
						String[] sxNex = resultScope(xy[0]);
						startX = Integer.parseInt(sxNex[0]);
						endX = Integer.parseInt(sxNex[1]);

						String[] syNey = resultScope(xy[1]);
						startY += Integer.parseInt(syNey[0]);
						endY += Integer.parseInt(syNey[1]);
						if (endY > heightTemp) {
							heightTemp = endY;
						}
						sheet.mergeCells(startX, startY, endX, endY);
					} else {
						startX = Integer.parseInt(xy[0]);
						startY += Integer.parseInt(xy[1]);
					}

					// 타이틀 삽입
					label = new jxl.write.Label(startX, startY, strs[0], format);
				}
				sheet.addCell(label);
			}
			heightTemp += 1;
			// 내용 붙이기
			for (int h = heightTemp; h < (dataMap.length + heightTemp); h++) {
				for (int i = 0; i < keys.length; i++) {
					DataMap<String, String> data = (DataMap<String, String>) dataMap[h
							- heightTemp];
					if (keys[i].indexOf("$") == -1) {
						label = new jxl.write.Label(i, h, data
								.getString(keys[i]), format0);
					} else {
						String[] strs = keys[i].split("\\$");
						if ("H".equals(strs[1])) {
							label = new jxl.write.Label(i, h, data.getString(
									strs[0]).substring(8, 10)
									+ "H", format0);
						} else if ("TOT".equals(strs[1])) {
							label = new jxl.write.Label(i, h, data
									.getString(strs[0]), format0);
							totalMap.put(strs[0], totalMap.getInt(strs[0])
									+ data.getInt(strs[0]));
						} else if (strs[1].indexOf("XY:") != -1) {
							if ("".equals(data.getString(strs[0]))) {
								continue;
							}
							String[] xy = strs[1].replaceAll("XY:", "").split(
									",");
							// 셀 병합 작업
							int startX = i;
							int endX = i;
							int startY = h;
							int endY = h;

							if (xy[0].indexOf("-") != -1
									|| xy[1].indexOf("-") != -1) {
								String[] sxNex = resultScope(xy[0]);
								startX += Integer.parseInt(sxNex[0]);
								endX += Integer.parseInt(sxNex[1]);

								String[] syNey = resultScope(xy[1]);
								startY += Integer.parseInt(syNey[0]);
								endY += Integer.parseInt(syNey[1]);

								sheet.mergeCells(startX, startY, endX, endY);
							}

							label = new jxl.write.Label(i, h, data
									.getString(strs[0]), format0);
						} else {
							label = new jxl.write.Label(i, h, DateUtil
									.dateFormat(data.getString(strs[0]),
											strs[1]), format0);
						}
					}
					sheet.addCell(label);
				}
			}

			// total 붙이가
			if (isTotal) {
				label = new jxl.write.Label(0, dataMap.length + heightTemp,
						"Total", format);
				sheet.addCell(label);
				for (int i = 0; i < keys.length; i++) {
					String[] strs = keys[i].split("\\$");
					if ("TOT".equals(strs[1])) {
						label = new jxl.write.Label(i, dataMap.length
								+ heightTemp, totalMap.getString(strs[0]),
								format);
						sheet.addCell(label);
					}
				}
			}
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.flushBuffer();
				workbook.close();
			} catch (Exception e) {
			}
		}
	}
	private static void makeExcelCommon(HttpServletResponse response,
			String fileName, String searchText, String[] titles, String[] keys,
			List<Map<String, String>> dataList, boolean isTotal) {
		WritableWorkbook workbook = null;
		try {
			// Total 관련 Map
			DataMap<String, Integer> totalMap = new DataMap<String, Integer>();
			
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			
			workbook = Workbook.createWorkbook(response.getOutputStream());
			
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			jxl.write.WritableCellFormat format = new WritableCellFormat();
			jxl.write.WritableCellFormat format0 = new WritableCellFormat();
			
			format.setBackground(jxl.format.Colour.GRAY_25);
			format.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format0.setBackground(jxl.format.Colour.WHITE);
			format0.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format0.setAlignment(jxl.format.Alignment.CENTRE);
			format0.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			sheet.setColumnView(0, 8);
			
			jxl.write.Label label = null;
			jxl.write.Blank blank = null;
			
			int heightTemp = 0;
			
			// 검색 조건 삽입
			label = new jxl.write.Label(0, 0, searchText);
			sheet.addCell(label);
			
			// 해더 만들기
			for (int i = 0; i < titles.length; i++) {
				if (titles[i].indexOf("$") == -1) {
					label = new jxl.write.Label(i, 3, titles[i], format);
					heightTemp = 3;
				} else {
					String[] strs = titles[i].split("\\$");
					String[] xy = strs[1].split(",");
					// 셀 병합 작업
					int startX = 0;
					int endX = 0;
					int startY = 3;
					int endY = 3;
					
					if (xy[0].indexOf("-") != -1 || xy[1].indexOf("-") != -1) {
						String[] sxNex = resultScope(xy[0]);
						startX = Integer.parseInt(sxNex[0]);
						endX = Integer.parseInt(sxNex[1]);
						
						String[] syNey = resultScope(xy[1]);
						startY += Integer.parseInt(syNey[0]);
						endY += Integer.parseInt(syNey[1]);
						if (endY > heightTemp) {
							heightTemp = endY;
						}
						sheet.mergeCells(startX, startY, endX, endY);
					} else {
						startX = Integer.parseInt(xy[0]);
						startY += Integer.parseInt(xy[1]);
					}
					
					// 타이틀 삽입
					label = new jxl.write.Label(startX, startY, strs[0], format);
				}
				sheet.addCell(label);
			}
			heightTemp += 1;
			// 내용 붙이기
			for (int h = heightTemp; h < (dataList.size()+ heightTemp); h++) {
				for (int i = 0; i < keys.length; i++) {
					DataMap<String, String> data = (DataMap<String, String>) dataList.get(h- heightTemp);
					if (keys[i].indexOf("$") == -1) {
						label = new jxl.write.Label(i, h, data
								.getString(keys[i]), format0);
					} else {
						String[] strs = keys[i].split("\\$");
						if ("H".equals(strs[1])) {
							label = new jxl.write.Label(i, h, data.getString(
									strs[0]).substring(8, 10)
									+ "H", format0);
						} else if ("TOT".equals(strs[1])) {
							label = new jxl.write.Label(i, h, data
									.getString(strs[0]), format0);
							totalMap.put(strs[0], totalMap.getInt(strs[0])
									+ data.getInt(strs[0]));
						} else if (strs[1].indexOf("XY:") != -1) {
							if ("".equals(data.getString(strs[0]))) {
								continue;
							}
							String[] xy = strs[1].replaceAll("XY:", "").split(
							",");
							// 셀 병합 작업
							int startX = i;
							int endX = i;
							int startY = h;
							int endY = h;
							
							if (xy[0].indexOf("-") != -1
									|| xy[1].indexOf("-") != -1) {
								String[] sxNex = resultScope(xy[0]);
								startX += Integer.parseInt(sxNex[0]);
								endX += Integer.parseInt(sxNex[1]);
								
								String[] syNey = resultScope(xy[1]);
								startY += Integer.parseInt(syNey[0]);
								endY += Integer.parseInt(syNey[1]);
								
								sheet.mergeCells(startX, startY, endX, endY);
							}
							
							label = new jxl.write.Label(i, h, data
									.getString(strs[0]), format0);
						} else {
							label = new jxl.write.Label(i, h, DateUtil
									.dateFormat(data.getString(strs[0]),
											strs[1]), format0);
						}
					}
					sheet.addCell(label);
				}
			}
			
			// total 붙이가
			if (isTotal) {
				label = new jxl.write.Label(0, dataList.size() + heightTemp,
						"Total", format);
				sheet.addCell(label);
				for (int i = 0; i < keys.length; i++) {
					String[] strs = keys[i].split("\\$");
					if ("TOT".equals(strs[1])) {
						label = new jxl.write.Label(i, dataList.size()
								+ heightTemp, totalMap.getString(strs[0]),
								format);
						sheet.addCell(label);
					}
				}
			}
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.flushBuffer();
				workbook.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Result scope.
	 * 
	 * @param val
	 *            the val
	 * @return the string[]
	 */
	private static String[] resultScope(String val) {
		if (val.indexOf("-") != -1) {
			return val.split("-");
		} else {
			return new String[] { val, val };
		}
	}

	public static List<List<String>> readExcelFile(String filePath) throws Exception {
		Workbook workbook = null;
		Sheet sheet = null;
		List<List<String>> resultList = new ArrayList<List<String>>();

		try {
			workbook = Workbook.getWorkbook(new File(filePath)); // 존재하는 엑셀파일 경로를 지정
			sheet = workbook.getSheet(0); // 첫번째 시트를 지정합니다.

			int rowCount = sheet.getRows(); // 총 로우수를 가져옵니다.
			int colCount = sheet.getColumns(); // 총 열의 수를 가져옵니다.

			if (rowCount <= 0) {
				throw new Exception("Read 할 데이터가 엑셀에 존재하지 않습니다.");
			}

			

			// 엑셀데이터를 배열에 저장
			for (int i = 0; i < rowCount; i++) {
				List<String> rowList = new ArrayList<String>();
				for (int k = 0; k < colCount; k++) {
					Cell cell = sheet.getCell(k, i); // 해당위치의 셀을 가져오는 부분입니다.
					if (cell == null)
						continue;
					
					// 가져온 셀의 실제 콘텐츠 즉
					// 데이터(문자열)를 가져오는 부분입니다.
					rowList.add(cell.getContents());
				}
				resultList.add(rowList);
			}

			// 데이터 검증 테스트
			for(List<String> row : resultList){
				for(String cell : row){
					System.out.print(cell);
					System.out.print("\t");
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception e) {
			}
		}

		return resultList;
	}
	
}
