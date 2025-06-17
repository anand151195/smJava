package com.demo.service;

import org.springframework.stereotype.Service;

import com.demo.Data;

@Service
public class EventActions {
	
	public String init(String sessionId, Data data) {
		System.out.println("==========INIT====AK======");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("==========Next==State==== "+data.getSession(sessionId));
		return "START";
	}
	
	public String start(String sessionId, Data data) {
		System.out.println("============START==============");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("==========Next==State==== "+data.getSession(sessionId));
		return "PROCESSING";
		
	}
	
	public String processing(String sessionId, Data data) {
		
		System.out.println("============PROCESSING===============");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "TERMINATE";
	}
	
	public String terminate(String sessionId, Data data) {
		System.out.println("=============TERMINATE====================");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "EXIT";
	}
	
	public String exit(String sessionId, Data data) {
		System.out.println("============EXIT===============");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "STOP";
	}
	public String stop(String sessionId, Data data) {
		System.out.println("============STOPPED===============");
		
		return null;

	}


}
