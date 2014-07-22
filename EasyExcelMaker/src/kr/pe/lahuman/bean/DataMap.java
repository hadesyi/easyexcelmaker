package kr.pe.lahuman.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



/**
 * The Class DataMap.
 *
 * <b>ORACLE 용으로 Key 값을 무조건 대문자로 변경하여 준다.</b>
 *  
 * @param <K> the key type
 * @param <V> the value type
 */
public class DataMap<K, V> implements Map<K, V>, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6773954545833958213L;
	/** The data map. */
	private Map<K, V> dataMap;

	/**
	 * Instantiates a new data map.
	 */
	public DataMap(){
		this.dataMap = new HashMap<K, V>();
	}

	/**
	 * Instantiates a new data map.
	 *
	 * @param srcMapParam the src map param
	 */
	public DataMap (Map<K, V> srcMapParam){
		this();
		if( srcMapParam != null ){
			this.dataMap.clear();
			Iterator<K> it = srcMapParam.keySet().iterator();
			while( it.hasNext() ){
				K key = it.next();
				V val = srcMapParam.get(key);
				this.dataMap.put(key, val);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		this.dataMap.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		if(key instanceof String){
				return this.dataMap.containsKey(((String)key).toUpperCase());
		}else{
			return this.dataMap.containsKey(key);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		if(value instanceof String){
			return this.dataMap.containsValue(((String)value).toUpperCase());
		}else{
			return this.dataMap.containsValue(value);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return this.dataMap.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		if(key instanceof String){
			if(this.dataMap.get(((String)key).toUpperCase()) == null){
				return (V) null;
			}else{
				return this.dataMap.get(((String)key).toUpperCase());
			}
		}else{
			return this.dataMap.get(key);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return this.dataMap.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet() {
		return this.dataMap.keySet();
	}
	
	/**
	 * DataMap에 현재 담겨있는 키값을 String[] 배열로 리턴한다.
	 * 만약 값이 없을 경우는 null를 반환한다.
	 *
	 * @return the keys
	 */
	public String[] getKeys(){
		Set<K> keySet = this.keySet();
		if(keySet == null) return null;
		String[] keys = new String[keySet.size()]; 
		Iterator<K> iter = keySet.iterator();
		int count = 0;
		while(iter.hasNext()){
			keys[count++] = (String)iter.next();
		}
		return keys;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		if(key instanceof String){
			return dataMap.put((K) ((String)key).toUpperCase(), value );
		}else{
			return dataMap.put(key, value );
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		this.dataMap.putAll(m);

	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		return this.dataMap.remove(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return this.dataMap.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection<V> values() {
		return this.dataMap.values();
	}

	/**
	 * Gets the string.
	 *
	 * @param key the key
	 *
	 * @return the string
	 */
	public String getString( String key ) {
		String rtn = "";
		try {
//			rtn = ServletUtil.removeHtml(getString(key, ""));
			rtn = (getString(key, ""));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * Gets the string.
	 * 키값에 해당하는 스트링 값을 반환합니다.</br>
	 * 해당 문자가 없을 null일 경우 대체 문자열로 변경합니다.
	 *
	 * @param key key value
	 * @param replaceValue replace string value
	 * @return the string
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String[] getStringValues(String key){
		String[] returnVal = null;
		
		Object obj = get( key );
		if( obj instanceof String[] ){
			String[] strArr = (String[])obj;
			returnVal = (String[])obj;
		}
		
		return returnVal;
	}	
	
	/**
	 * return 값을 String 형으로 변환 하여 return 한다.<br />String[] 이 return 될 경우 String[0] 값이 return 된다.
	 * 
	 *
	 * @param key the key
	 * @param replaceValue the replace value
	 * @return the string
	 * @throws SQLException the sQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getString(String key, String replaceValue) throws SQLException, IOException{
		String returnVal = null;
		Object obj = get( key );
		if( obj instanceof String[] ){
			String[] strArr = (String[])obj;
			returnVal = strArr[0];
		}else if(obj instanceof String) {
			returnVal = (String)obj;
		}else if(obj instanceof Clob){
			Clob clob = (Clob)obj;
			StringBuffer strOut = new StringBuffer();
			String str = "";
			BufferedReader br = new BufferedReader(clob.getCharacterStream());
			
			while ((str = br.readLine()) != null) {
			 strOut.append(str+"\r\n");
			}
			returnVal = strOut.toString();
		}else{
			returnVal = (obj==null?replaceValue:obj.toString());
		}
		return returnVal == null?replaceValue:returnVal ;
	}

	/**
	 * Gets the int.
	 *
	 * @param key the key
	 *
	 * @return the int
	 */
	public int  getInt( String key ){
		return getInt(key, 0);
	}
	
	/**
	 * 키값에 해당하는 int 값을 반환합니다.</br>
	 * 해당 key값에 해당하는 값이 null일 경우 대체 숫자값으로 변경합니다.
	 *
	 * @param key the key
	 * @param replaceValue the replace value
	 * @return the int
	 */
	public int getInt(String key, int replaceValue){
		Object obj = get(key);
		try{
			if( obj == null ) {
				return replaceValue;
			}else if( obj instanceof String ){
				return Integer.parseInt( (String)obj );
			}else if( obj instanceof Integer ){
				return ((Integer) obj ).intValue();
			}else if( obj instanceof BigDecimal ){
				return ((BigDecimal) obj ).intValue();
			}else if( obj instanceof String[] ){
				String[] strArr = (String[])obj;
				return Integer.parseInt( strArr[0] );
			}else if( obj instanceof Long ){
				return ((Long)obj).intValue() ;
			}else{
				return replaceValue;
			}
		}catch(Exception e){
			return replaceValue;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String key;
		String val;
		Iterator<K> it = dataMap.keySet().iterator();
		while( it.hasNext() ){
			key = (String) it.next();
			val = getString( key );
			sb.append("(").append(key).append(",").append( val  ).append(")");
		}
		sb.append("}");
		return sb.toString();
	}
}
