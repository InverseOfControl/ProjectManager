package com.ezendai.credit2.sign.util;

import java.util.Collection;
import java.util.List;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezendai.credit2.sign.lcb.service.ContractGenerateService;

public class DwrUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DwrUtil.class);

	/**
	 * 调用页面javascript函数
	 * @param functionName
	 * @param args
	 */
	public void invokeJavascriptFunction (String _funcName, List _args){
		final String funcName = _funcName;
		final List args = _args;
		logger.error("电子签章调用前端js开始");
		Browser.withAllSessions(new Runnable(){ 
			private ScriptBuffer script = new ScriptBuffer(); 
			public void run(){ 
				//拼接javascript
				script = script.appendScript(funcName+"(");
				for(int i=0; i<args.size(); i++){
					if(i != 0){
						script = script.appendScript(",");
					}
					script = script.appendData(args.get(i));
				}
				script.appendScript(")"); 
				logger.info("电子签章调用前端js="+script.toString());
				Collection<ScriptSession> sessions = Browser.getTargetSessions(); 
				for (ScriptSession scriptSession : sessions){ 
					scriptSession.addScript(script); 
				} 
			} 
		});
		logger.error("电子签章调用前端js结束");
	}
}
