/**
 * 
 */
package com.ezendai.credit2.master.enumerate;

import java.util.List;

import com.ezendai.credit2.framework.util.CreaditBeanFactory;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.SysEnumerateService;



/**
 * @author liyuepeng
 * 2014-7-21
 *
 */
public class EnumUtil {

	static SysEnumerateService service = (SysEnumerateService) CreaditBeanFactory.getBean("sysEnumerateService");
	
	/**
	 * 
	 * <pre>
	 * 以数据类型为条件加载数据字典数据
	 * </pre>
	 *
	 * @param type
	 * @return
	 */
	public static List<SysEnumerate> findEnumListByType(String type) {
		return service.findSysEnumerateListByType(type);
	}
	
	/**
	 * 
	 * <pre>
	 * 根据数据字典类型和Code取得备注
	 * </pre>
	 *
	 * @param type
	 * @param code
	 * @return
	 */
	public static String findEnumValue(String type, Integer code) {
		return service.findEnumValue(type, code);
	}
	
	
	/**
	 * 
	 * <pre>
	 * 通过字典类型，与包含代码数组获取 List<SysEnumerate>
	 * </pre>
	 *
	 * @param type
	 * @param codeStr 格式:code,code,.....
	 * @return
	 */
	public static List<SysEnumerate> findEnumListByIncludeCode(String type, String codeStr) {
		String[] enumCodes = StringUtil.split(codeStr, ",");
		if (enumCodes == null || enumCodes.length == 0) {
			return null;
		}
		Integer[] enumCodeArr = new Integer[enumCodes.length];
		for (int i = 0; i < enumCodes.length; i++) {
			enumCodeArr[i] = Integer.parseInt(enumCodes[i]);
		}
		return service.findEnumList(type, enumCodeArr);
	}
	
	/**
	 * 
	 * <pre>
	 * 通过字典类型，与排除代码数组获取 List<SysEnumerate>
	 * </pre>
	 *
	 * @param type
	 * @param codeStr 格式:code,code,.....
	 * @return
	 */
	public static List<SysEnumerate> findEnumListByExclusionCode(String type, String codeStr){
		String[] enumCodes = StringUtil.split(codeStr, ",");
		if (enumCodes == null || enumCodes.length == 0) {
			return null;
		}
		Integer[] enumCodeArr = new Integer[enumCodes.length];
		for (int i = 0; i < enumCodes.length; i++) {
			enumCodeArr[i] = Integer.parseInt(enumCodes[i]);
		}
		return service.findEnumListExclusionEnumCodes(type, enumCodeArr);
	}
}
