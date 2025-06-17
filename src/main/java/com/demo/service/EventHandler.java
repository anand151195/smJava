package com.demo.service;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import com.demo.Data;
import com.demo.config.SmCSVConfigs;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.beans.factory.annotation.Value;


@Service
public class EventHandler {
    private static ConcurrentHashMap<String, SmCSVConfigs> eventMap = new ConcurrentHashMap();
    
    private static ConcurrentHashMap<String, Method> methodsMap = new ConcurrentHashMap();

	
	 @Autowired
	 private EventActions eventActions;
	 
	 @Value("${sm.path}")
	 private String directoryPath;
	 
	 static {

	        // Use reflection to populate the map with methods from ServiceAction class
	        Class<EventActions> serviceActionClass = EventActions.class;

	        // Get all declared methods of the ServiceAction class
	        Method[] methods = serviceActionClass.getDeclaredMethods();

	        // Iterate through the methods and populate the map
	        for (Method method : methods) {
	            // Assuming you want to use the method name as the key in the map
	            String methodName = method.getName();
	            methodsMap.put(methodName, method);
	        }
	    }
	 
	 
	 
	 // Static method to get a Method object based on method name
	    public static Method getSmDet(String methodName) {
	        // Check if the method name exists in the map
	        if (methodsMap.containsKey(methodName)) {
	            return methodsMap.get(methodName);
	        } else {
	        	System.out.println("Method '" + methodName + "' not found");
	            return null;
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
	    public void generateEvent(String sid, Data ocfSessionContextData, String event) {
	        String actionName = null;
	        try {
	            String key = null;
	            // Get the method name from the new Map

	            if (event.equals("INITIAL_EVT")) {
	                key = event + "_START";
	                System.out.println("=================INITIAL_EVT==============");
	    		   

	            } else {
	                key = event + "_" + ocfSessionContextData.getSession(sid);

	            }


//		    log.atInfo().log("SID:%s invoke notification Event:%s ServiceName:%s", sid, event, ocfSessionContextData.getOcfSessionData().getServiceName());
		  System.out.println("SID: "+sid+" invoke notification Event: "+event);
	            if(event.equals("STOP"))
//			    notification.SndNotiEvt(sid, event, ocfSessionContextData.getOcfSessionData().getServiceName());
		    	System.out.println("==========ITS=======STOP========");

		    SmCSVConfigs smCSVConfigs = EventHandler.eventMap.get(key);


		    if (smCSVConfigs != null) {
			    actionName = smCSVConfigs.getAction();
			    System.out.println("SID: "+sid+" key: "+key+" State: "+ocfSessionContextData.getSession(sid)+" action: "+actionName);
//			    log.atInfo().log("SID:%s Key:%s StateActivity:: State:%s action:%s", sid, key,
//					    ocfSessionContextData.getOcfSessionData().getCurrentState(), actionName);
		    } else {
		    	System.out.println("SID: "+sid+ " SmCSVConfigs not found for key: "+key);
//			    log.atSevere().log("SID:%s SmCSVConfigs not found for key: %s, processing stopped", sid, key);
			    return;
		    }

		    Method method = getSmDet(actionName);

		    // Get the method name from the new Map
		    /* SmData smData = getSmDet(actionName);
		       Method method = smData.getMethod();
		       Object obj = smData.getInstance(); */

		    // String evt = (String) method.invoke(obj, sid, gxData);
		    String evt = (String) method.invoke(eventActions, sid, ocfSessionContextData);
		    
		    

		    System.out.println("SID: "+sid+" Action :"+actionName+" evt :"+evt+" NextState :"+smCSVConfigs.getNextState());
//		    log.atFine().log("SID:%s Action:%s response:%s NextState:%s", sid, actionName, evt,
//				    smCSVConfigs.getNextState());
		    if (evt != null) {
//			    if (evt.equals("NO_CHANGE_ST")) {
//				    dataStore.updateChargeData(sid, sid, ocfSessionContextData.getOcfChargeData(), "$");
//				    log.atInfo().log("SID:%s No State change required ", sid);
//				    return;
//			    }

			    key = evt + "_" + smCSVConfigs.getNextState();
			    SmCSVConfigs nextEventDetails = EventHandler.eventMap.get(key);

			    if (nextEventDetails != null) {
				    // Update currentState with State from nextEventDetails
			    	ocfSessionContextData.updateSession(sid, nextEventDetails.getState());
//				    ocfSessionContextData.getOcfSessionData().setCurrentState(nextEventDetails.getState());

				    // Call generateEvent recursively with updated Data and the received evt
				    generateEvent(sid, ocfSessionContextData, evt);
			    } else {
			    	System.out.println("SID: "+sid+" Details not found in EventMap for event: "+evt+" State: "+smCSVConfigs.getNextState());
//				    log.atSevere().log("SID:%s Details not found in EventMap for event: %s State:%s", sid, evt, smCSVConfigs.getNextState());
			    }
		    } else {
			    if (smCSVConfigs != null) {
			    	ocfSessionContextData.updateSession(sid, smCSVConfigs.getNextState());
//				    ocfSessionContextData.getOcfSessionData().setCurrentState(smCSVConfigs.getNextState());
			    	System.out.println("SID: "+sid+" nextstate StateActivity :"+ocfSessionContextData.getSession(sid)+" action: "+actionName);
//				    log.atInfo().log("SID:%s nextstate StateActivity:: State:%s action:%s", sid,
//						    ocfSessionContextData.getOcfSessionData().getCurrentState(),
//						    actionName);
				    if (!ocfSessionContextData.getSession(sid).equals("STOP")) {
					   System.out.println("===================AK===aaaa==============");
//					    dataStore.update(sid, ocfSessionContextData);
				    }
			    }
			    	return;
		    }

		} catch (Exception e) {
			System.out.println("===============EXCEPTION==========="+e.getMessage());
//			log.atSevere().log("SID:%s action:%s invoke Exception:%s StackTrace:%s", sid, actionName,
//					e.getMessage(), e.getStackTrace());
		}
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	 
	 
	 
	 
	 @PostConstruct
	    public void initializeEventMap() {

		    File folder = new File(directoryPath);

		    if (folder.isDirectory()) {

			    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
			    if (files != null) {

				    for (File file : files) {

					    try (Reader reader = new FileReader(file)) {

						    CsvToBean<SmCSVConfigs> csvToBean = new CsvToBeanBuilder<SmCSVConfigs>(reader)
							    .withType(SmCSVConfigs.class)
							    .withIgnoreLeadingWhiteSpace(true)
							    .build();

						    List<SmCSVConfigs> list = csvToBean.parse();

						    for (SmCSVConfigs row : list) {
							    // Combine event and state to form the key
							    String key = row.getEvent() + "_" + row.getState();
							    System.out.println("KEY: "+key+" Action: "+row.getAction());

	                            // Put action as the value in the map
	                            EventHandler.eventMap.put(key, row);

	                        
	                        }
						   

	                    } catch (Exception e) {
	                    	System.out.println("Load statemachine Exception"+e.getMessage());
	                    }
	                }
	            }
	        }
	        System.out.println("AAAAAAAAAAAAAAAAAA "+eventMap);

	    }
	  
	 

}
