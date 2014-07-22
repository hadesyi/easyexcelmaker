package kr.pe.lahuman.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 날짜 관련 Class
 * @author <a href='gate7711@nowonplay.com'><b>Kim Jin Hyug</b></a>
 * @vaersion DateUtil.java 2009. 1. 11
 */
/**
 * @author sora
 *
 */
public class DateUtil {
	public static final StringBuffer weekNames1 = new StringBuffer("\uC77C\uC6D4\uD654\uC218\uBAA9\uAE08\uD1A0");
    public static final StringBuffer weekNames2 = new StringBuffer("\u65E5\u6708\u706B\u6C34\u6728\uF90A\u571F");
    public static final String weekNames3[] = {
        "sun", "mon", "tue", "wed", "thu", "fri", "sat"
    };
    public static final String monthNames[] = {
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", 
        "NOV", "DEC"
    };

	/**
	 * 지금 날짜를 return 한다. 형식은 yyyymmdd
	 * 
	 * @return
	 */
	public static synchronized String toDate() {
		Calendar cal = Calendar.getInstance();
		String month = "";
		String day = "";

		if ((cal.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (cal.get(Calendar.MONTH) + 1);
		} else {
			month = (cal.get(Calendar.MONTH) + 1) + "";
		}

		if (cal.get(Calendar.DATE) < 10) {
			day = "0" + cal.get(Calendar.DATE);

		} else {
			day = cal.get(Calendar.DATE) + "";
		}

		return cal.get(Calendar.YEAR) + month + day;
	}
	
	/**
	 * 지금 날짜와 시간까지 를 return 한다. 형식은 yyyymmddhh
	 * 
	 * @return
	 */
	public static synchronized String toDateHour() {
		Calendar cal = Calendar.getInstance();
		String hour = "";
		String min = "";
		String sec = "";
		String msec = "";

		if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
			hour = "0" + cal.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = cal.get(Calendar.HOUR_OF_DAY) + "";
		}

		if (cal.get(Calendar.MINUTE) < 10) {
			min = "0" + cal.get(Calendar.MINUTE);
		} else {
			min = cal.get(Calendar.MINUTE) + "";
		}

		if (cal.get(Calendar.SECOND) < 10) {
			sec = "0" + cal.get(Calendar.SECOND);
		} else {
			sec = cal.get(Calendar.SECOND) + "";
		}

		if (cal.get(Calendar.MILLISECOND) < 10) {
			msec = "0" + cal.get(Calendar.MILLISECOND);
		} else {
			msec = cal.get(Calendar.MILLISECOND) + "";
		}

		return toDate() + hour + min + sec + msec;
	}

	/**
	 * String 형식의 날자를 Calendar 로 변환 해준다.
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static synchronized Calendar converterDate(String yyyymmdd) {
		Calendar cal = Calendar.getInstance(); // 양력 달력
		if (yyyymmdd == null)
			return cal;

		String date = yyyymmdd.trim();
		if (date.length() != 8) {
			if (date.length() == 4)
				date = date + "0101";
			else if (date.length() == 6)
				date = date + "01";
			else if (date.length() > 8)
				date = date.substring(0, 8);
			else
				return cal;
		}

		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

		return cal;
	}
	
	public static String addMonth(String yyyymm){
		int year = Integer.valueOf(yyyymm.substring(0,4));
		int month = Integer.valueOf(yyyymm.substring(4, 6));
		
		GregorianCalendar date = new GregorianCalendar();
		
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMM");
		
		return smf.format(date.getTime());
	}
	
	/**
	 * 주어진 날짜의 이전 월을 리턴 합니다.
	 * 200905이라면 200904월을 반환
	 * @param yyyymm
	 * @return
	 */
	public static String prevMonth(String yyyymm){
		int year = Integer.valueOf(yyyymm.substring(0,4));
		int month = Integer.valueOf(yyyymm.substring(4, 6))-2;
		
		GregorianCalendar date = new GregorianCalendar();
		
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMM");
		
		return smf.format(date.getTime());
	}

	/**
	 * 주어진 날짜의 이전 월을 리턴 합니다.
	 * 200905이라면 200904월을 반환
	 * 위에 메소드가 좀 이상해서 다시 만듬..
	 * @param yyyymm
	 * @return
	 */
	public static String prevMonth2(String yyyymm){
		int year = Integer.valueOf(yyyymm.substring(0,4));
		int month = Integer.valueOf(yyyymm.substring(4, 6));
		
		GregorianCalendar date = new GregorianCalendar(year,month,0);
		date.set(Calendar.MONTH, month-1);
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMM");
		return smf.format(date.getTime());
	}
	
	/**
     * GregorianCalendar 객체를 반환함
     *
     * @param yyyymmdd 날짜 인수
     * @return GregorianCalendar
     */
    public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
    	yyyymmdd=yyyymmdd.replace("-", "");
        int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int dd = Integer.parseInt(yyyymmdd.substring(6));

        GregorianCalendar calendar =
                new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

        return calendar;

    }
	
	/**
	 * 두 날짜의 차이를 구한다.
	 * 기준은 Date 형식이다
	 * @param curr
	 * @param next
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static synchronized long getDiffDays(String curr, String next) throws ParseException {
		
		GregorianCalendar StartDate = getGregorianCalendar(curr);
        GregorianCalendar EndDate = getGregorianCalendar(next);    
        

        long diffDays =
                ((EndDate.getTime().getTime() - StartDate.getTime().getTime())
                / 86400000)*-1;

        return diffDays;
	}
	
	/**
     * 두 날짜간의 날짜수를 반환(윤년을 감안함)
     *
     * @param startDate 시작 날짜
     * @param endDate 끝 날짜
     * @return 날수
     */
    public static long getDifferDays(String startDate, String endDate) {

        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);

        long difer =
                (EndDate.getTime().getTime() - StartDate.getTime().getTime())
                / 86400000;

        return difer;

    }
    
    /**
     * 두날짜간의 월차이를 구한다.
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDifferMonths(String startDate, String endDate){
    	String sDate=removeSplit(startDate);
    	String eDate=removeSplit(endDate);
    	
    	String fromYear = (sDate+"").substring(0,4);
    	String fromMonth = (sDate+"").substring(4,6);

    	String toYear = (eDate+"").substring(0,4);
    	String toMonth = (eDate+"").substring(4,6);
    	// 년월을 월로 변환한다. 그 다음에 서로 뺄셈을 하면 차이를 구할 수 있다.
    	long intFromDate = Integer.parseInt(fromYear) * 12 + Integer.parseInt(fromMonth);

    	long intToDate = Integer.parseInt(toYear) * 12 + Integer.parseInt(toMonth);
    	long monthDiff =intToDate -  intFromDate;
    	return monthDiff;

    }
    
    /**
     * 두 날짜간의 연차를 반환(윤년을 감안함)
     *
     * @param startDate 시작일
     * @param endDate 끝일
     * @return a <code>double</code> value
     */
    public static double getDifferYears(String startDate, String endDate) {

        double startDateD = Double.parseDouble(startDate);
        double endDateD = Double.parseDouble(endDate);
        double deltaYear = Math.round((endDateD - startDateD) / 10000d);
        double newStartDate = startDateD + 10000d * deltaYear;

        String strNewStartDate =
                String.valueOf(StringUtil.parseInt((String.valueOf(newStartDate))));
        String strNewMan =
                String.valueOf(
                		StringUtil.parseInt((String.valueOf(newStartDate +
                10000d))));

        double year =
                deltaYear
                + (double) getDifferDays(strNewStartDate, endDate)
                / getDifferDays(strNewStartDate, strNewMan);

        return year;

    }
    

	/**
	 * 오늘 요일의 숫자를 리턴
	 * 0:일요일 ~ 6:토요일
	 * @return
	 */
	public static synchronized int dayOfWeek() {
		Calendar cal = Calendar.getInstance();
		
		return cal.get(Calendar.DAY_OF_WEEK) - 1; 	  
	}
	
	/**
	 * 특정날짜의  요일의 숫자를 리턴
	 * 0:일요일 ~ 6:토요일
	 * @return
	 */
	public static synchronized int dayOfWeek(String sYear, String sMonth, String sDay) {		
		
		int iYear = Integer.parseInt(sYear);
		int iMonth = Integer.parseInt(sMonth) - 1;
		int isDay = Integer.parseInt(sDay);
		
		GregorianCalendar gc = new GregorianCalendar(iYear, iMonth, isDay); 

		return gc.get(gc.DAY_OF_WEEK) - 1;
	}

	/**
	 * 특정날짜의  요일의 숫자를 리턴
	 * 0:일요일 ~ 6:토요일
	 * @return
	 */
	public static synchronized int dayOfWeek(String date) {		
		
		date=date.replaceAll("-", "");
		date=date.replaceAll(" ", "");
        date=date.replaceAll("/", "");
        date=date.replaceAll(":", "");
        date=date.replaceAll(".", "");

        Calendar cal = Calendar.getInstance();
        String year = date.substring(0, 4);
        String mon = date.substring(4, 6);
        String day = date.substring(6, 8);
		
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(mon) - 1;
		int isDay = Integer.parseInt(day);
		
		GregorianCalendar gc = new GregorianCalendar(iYear, iMonth, isDay); 
		
		return gc.get(gc.DAY_OF_WEEK) - 1;				
	}
	
	/**
	 * 요일을 한글로 리턴함
	 * 0:일요일 ~ 6:토요일
	 * Sample.
	 * DateUtils.getTextDayOfWeek(DateUtils.dayOfWeek(yyyy, mm, dd))
	 * DateUtils.getTextDayOfWeek(DateUtils.dayOfWeek())
	 * @return
	 */
	public static synchronized String getTextDayOfWeek(int noDay) {
		String textDayOfWeek = "";
		if (noDay == 0){
			textDayOfWeek = "일";
		} else if (noDay == 1){
			textDayOfWeek = "월";
		} else if (noDay == 2){
			textDayOfWeek = "화";
		} else if (noDay == 3){
			textDayOfWeek = "수";
		} else if (noDay == 4){
			textDayOfWeek = "목";
		} else if (noDay == 5){
			textDayOfWeek = "금";
		} else if (noDay == 6){
			textDayOfWeek = "토";
		}
		return textDayOfWeek; 	  
	}
	
    /**
     * 모든 형식의 문자열을 Date 형식으로 반환
     * @param ymd
     * @return
     */
    public static Date toDateHour(String ymd) {
        if (ymd == null || ymd.trim().length() < 1) {
            return new Date();
        }
        ymd=ymd.replaceAll( "-", "");
        ymd=ymd.replaceAll( " ", "");
        ymd=ymd.replaceAll( "/", "");
        ymd=ymd.replaceAll( ":", "");
        ymd=ymd.replaceAll( ".", "");
        int year, mon, day, h, m, s = 0;

        Calendar cal = Calendar.getInstance();
        year = Integer.parseInt(ymd.substring(0, 4));
        if(ymd.length()==4){
        	ymd=ymd+"0101";
        }
        mon = Integer.parseInt(ymd.substring(4, 6));
        if(ymd.length()==6){
        	ymd=ymd+"01";
        }
        day = Integer.parseInt(ymd.substring(6, 8));
        if(ymd.length()<=9){
        	h=00;
        	m=00;
        	s=00;
        }else{
	        h=Integer.parseInt(ymd.substring(8, 10));
	        m=Integer.parseInt(ymd.substring(10, 12));
	        s=Integer.parseInt(ymd.substring(12, 14));
        }
        cal.set(year, mon - 1, day, h, m, s);

        return cal.getTime();
    }
    

	/**
	 * 날짜 포멧 Method
	 * @param strDate
	 * @param strFormat yyyy. MM. dd yyyyMMddhhmmss
	 * @return
	 */
	public static String dateFormat(String strDate, String strFormat) {
		if(StringUtil.isEmpty(strDate)){
			return "";
		}
		SimpleDateFormat smf = new SimpleDateFormat(strFormat);
		return smf.format(toDateHour(strDate));
	}
	
	/**
	 * 특수문자를 제거한 후 yyyyMMddhhmmss 형식으로 반환한다.
	 * @param ymd
	 * @return
	 */
	public static String removeSplit(String ymd){
		if (ymd == null || ymd.trim().length() < 1) {
            return "";
        }
		ymd=ymd.replaceAll( "-", "");
        ymd=ymd.replaceAll( " ", "");
        ymd=ymd.replaceAll( "/", "");
        ymd=ymd.replaceAll( ":", "");
        ymd=ymd.replaceAll( ".", "");
        if(ymd.length()<=6){
        	ymd=ymd+"01000000";
		}
        if(ymd.length()>6 &&ymd.length()<9){
        	ymd=ymd+"000000";
		}
        ymd=DateUtil.dateFormat(ymd,"yyyyMMddHHmmss");
        return ymd;
	}
	
	//어떤날이 오늘과 비교 하여 한달 이내의 날짜 인지를 비교 한다. compare는 yyyyMMdd 포멧이어야 함..
	public static boolean comperaMonthToMonth(String compare){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");		
		
		//오늘날짜 구하기
		Calendar today =Calendar.getInstance();
		//오늘날짜에 한달 더 하기
		today.add(Calendar.MONTH, +1);
		Calendar someday = converterDate(compare);
		boolean result = today.after(someday);
		return result; 
	}
	
	//어떤날이 오늘과 비교 하여 한달 이내의 날짜 인지를 비교 한다. compare는 yyyyMMdd 포멧이어야 함..
	public static boolean comperaMonthToMonth(String compare, String format){
		
		SimpleDateFormat df = new SimpleDateFormat(format);		
		
		//오늘날짜 구하기
		Calendar today =Calendar.getInstance();
		//오늘날짜에 한달 더 하기
		today.add(Calendar.MONTH, +1);
		Calendar someday = converterDate(compare);
		boolean result = today.after(someday);
		return result; 
	}

	/**
	 * 해당 년도의 일수를 구한다.
	 * @param y
	 * @return
	 */
	public static int getDaysInYear(int y)
    {
        if(y > 1582)
        {
            if(y % 400 == 0)
                return 366;
            if(y % 100 == 0)
                return 365;
            return y % 4 != 0 ? 365 : 366;
        }
        if(y == 1582)
            return 355;
        if(y > 4)
            return y % 4 != 0 ? 365 : 366;
        return y <= 0 ? 0 : 365;
    }
	
	
	/**
	 * 해당년의 월의 일수를 가지고 온다.
	 * @param m
	 * @param y
	 * @return
	 */
	public static int getDaysInMonth(int m, int y)
    {
        if(m < 1 || m > 12)
            throw new RuntimeException((new StringBuilder("Invalid month: ")).append(m).toString());
        int b[] = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
            30, 31
        };
        if(m != 2 && m >= 1 && m <= 12 && y != 1582)
            return b[m - 1];
        if(m != 2 && m >= 1 && m <= 12 && y == 1582)
            if(m != 10)
                return b[m - 1];
            else
                return b[m - 1] - 10;
        if(m != 2)
            return 0;
        if(y > 1582)
        {
            if(y % 400 == 0)
                return 29;
            if(y % 100 == 0)
                return 28;
            return y % 4 != 0 ? 28 : 29;
        }
        if(y == 1582)
            return 28;
        if(y > 4)
            return y % 4 != 0 ? 28 : 29;
        if(y > 0)
            return 28;
        else
            throw new RuntimeException((new StringBuilder("Invalid year: ")).append(y).toString());
    }
	
	public static int getDaysFromYearFirst(int d, int m, int y)
    {
        if(m < 1 || m > 12)
            throw new RuntimeException((new StringBuilder("Invalid month ")).append(m).append(" in ").append(d).append("/").append(m).append("/").append(y).toString());
        int max = getDaysInMonth(m, y);
        if(d >= 1 && d <= max)
        {
            int sum = d;
            for(int j = 1; j < m; j++)
                sum += getDaysInMonth(j, y);

            return sum;
        } else
        {
            throw new RuntimeException((new StringBuilder("Invalid date ")).append(d).append(" in ").append(d).append("/").append(m).append("/").append(y).toString());
        }
    }
	
	
	/**
	 * 년월일을 받아 숫자로 반환한 값을 날짜로 보여준다.
	 * @param days
	 * @param delimiter
	 * @return
	 */
	public static String getDateStringFrom21Century(int days, String delimiter)
    {
        int y = 2000;
        int m = 1;
        int d = 1;
        int n = days + 1;
        int j = 2000;
        if(n > 0)
        {
            for(; n > getDaysInYear(j); j++)
                n -= getDaysInYear(j);

            y = j;
            int i;
            for(i = 1; n > getDaysInMonth(i, y); i++)
                n -= getDaysInMonth(i, y);

            m = i;
            d = n;
        } else
        if(n < 0)
        {
            while(n < 0) 
            {
                n += getDaysInYear(j - 1);
                j--;
            }
            y = j;
            int i;
            for(i = 1; n > getDaysInMonth(i, y); i++)
                n -= getDaysInMonth(i, y);

            m = i;
            d = n;
        }
        String strY = (new StringBuilder()).append(y).toString();
        String strM = "";
        String strD = "";
        if(m < 10)
            strM = (new StringBuilder("0")).append(m).toString();
        else
            strM = (new StringBuilder()).append(m).toString();
        if(d < 10)
            strD = (new StringBuilder("0")).append(d).toString();
        else
            strD = (new StringBuilder()).append(d).toString();
        return (new StringBuilder(String.valueOf(strY))).append(delimiter).append(strM).append(delimiter).append(strD).toString();
    }
	
	/**
	 * 년월일을 받아 숫자로 반환한다.
	 * @param d
	 * @param m
	 * @param y
	 * @return
	 */
	public static int getDaysFrom21Century(int d, int m, int y)
	    {
	        if(y >= 2000)
	        {
	            int sum = getDaysFromYearFirst(d, m, y);
	            for(int j = y - 1; j >= 2000; j--)
	                sum += getDaysInYear(j);

	            return sum - 1;
	        }
	        if(y > 0 && y < 2000)
	        {
	            int sum = getDaysFromYearFirst(d, m, y);
	            for(int j = 1999; j >= y; j--)
	                sum -= getDaysInYear(y);

	            return sum - 1;
	        } else
	        {
	            throw new RuntimeException((new StringBuilder("Invalid year ")).append(y).append(" in ").append(d).append("/").append(m).append("/").append(y).toString());
	        }
	    }
	
	/**
	 * yyyyMMdd 문자열을 받아 숫자로 반환한다.
	 * @param s
	 * @return
	 */
	public static int getDaysFrom21Century(String s)
    {
        if(s.length() == 8 || s.length() == 14)
        {
            int y = Integer.parseInt(s.substring(0, 4));
            int m = Integer.parseInt(s.substring(4, 6));
            int d = Integer.parseInt(s.substring(6, 8));
            return getDaysFrom21Century(d, m, y);
        }
        if(s.length() == 10)
        {
            int y = Integer.parseInt(s.substring(0, 4));
            int m = Integer.parseInt(s.substring(5, 7));
            int d = Integer.parseInt(s.substring(8));
            return getDaysFrom21Century(d, m, y);
        }
        if(s.length() == 11)
        {
            int d = Integer.parseInt(s.substring(0, 2));
            String strM = s.substring(3, 6).toUpperCase();
            int m = 0;
            for(int j = 1; j <= 12; j++)
            {
                if(!strM.equals(monthNames[j - 1]))
                    continue;
                m = j;
                break;
            }

            if(m < 1 || m > 12)
            {
                throw new RuntimeException((new StringBuilder("Invalid month name: ")).append(strM).append(" in ").append(s).toString());
            } else
            {
                int y = Integer.parseInt(s.substring(7));
                return getDaysFrom21Century(d, m, y);
            }
        } else
        {
            throw new RuntimeException((new StringBuilder("Invalid date format: ")).append(s).toString());
        }
    }
	
	/**
	 * today="20090101";
	 * Ex>DateUtil.addDate(7,today); //일주일 후  -7값을 주면 일주인 전
	 * return> 20090108;
	 * @param offset
	 * @param date
	 * @return
	 */
	public static String addDate(int offset, String date)
    {
        return addDate(offset, date, "");
    }

    /**
     * today="20090101";
	 * Ex>DateUtil.addDate(7,today,"-"); //일주일 후   -7값을 주면 일주인 전
	 * return> 2009-01-08;
     * @param offset
     * @param date
     * @param delimiter
     * @return
     */
    public static String addDate(int offset, String date, String delimiter)
    {
        int z = getDaysFrom21Century(date);
        int n = z + offset;
        return getDateStringFrom21Century(n, delimiter);
    }
	
    /**
     * 현재일에서 1년후의 날짜를 가져온다.
     * @return
     */
    public static String addYear(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar today =Calendar.getInstance();
		today.add(Calendar.YEAR, +1);
		return (String)df.format(today.getTime());	 
	}
    
    /**
     * 매개 변수로 받은 날짜를 기준으로 
     * 전년, 후년 날짜를 가져온다.(yyyyMM)
     * @return
     */
    public static String getYear(String sYear, String sMonth, String sDay, int someday){
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");	
    		
		int iYear = Integer.parseInt(sYear);
		int iMonth = Integer.parseInt(sMonth) - 1;
		int isDay = Integer.parseInt(sDay);
    		
   		GregorianCalendar gc = new GregorianCalendar(iYear, iMonth, isDay);
   		gc.add(Calendar.YEAR, someday);
    	return (String)df.format(gc.getTime());	 
    }
    
    /**
     * 매개 변수로 받은 날짜를 기준으로 
     * 전년, 후년 날짜를 가져온다.(yyyyMM)
     * @return
     */
    public static String getMonth(String sYear, String sMonth, String sDay, int someday){
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");	
    		
		int iYear = Integer.parseInt(sYear);
		int iMonth = Integer.parseInt(sMonth) - 1;
		int isDay = Integer.parseInt(sDay);
    		
   		GregorianCalendar gc = new GregorianCalendar(iYear, iMonth, isDay);
   		gc.add(Calendar.MONTH, someday);
    	return (String)df.format(gc.getTime());	 
    }
   
    public static String setDate(String date){
    	date=DateUtil.removeSplit(date);
		if(date.length()<9){
			date=date+"000000";
		}
		return DateUtil.dateFormat(date,"yyyyMMddHHmmss");
    }
    
    /**
     * 오늘 날짜를 취득한다.
     * @return
     */
    public static String getToday(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar today =Calendar.getInstance();
		return df.format(today.getTime());
    }
    
    /**
     * 오늘날짜를 전달한 날짜형식(포멧)으로 취득한다.
     * "yyyyMMddHHmmss"
     * @param formet
     * @return
     */
    public static String getToday(String formet){
    	SimpleDateFormat df = new SimpleDateFormat(formet);	
		Calendar today =Calendar.getInstance();
		return df.format(today.getTime());
    }
    
    
    /**
     * Converter format date.
     * 
     * @param date the date yyyyMMdd 이상 있어야 함
     * @param symbol the symbol
     * 
     * @return the string
     */
    public static String converterFormatDate(String date, String symbol){
    	if(date != null && date.length() > 8){
    		return date.substring(0, 4) + symbol + date.subSequence(4, 6) + symbol + date.subSequence(6, 8);  
    	}
    	return "";
    }
}
