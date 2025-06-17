package com.demo;

import java.util.concurrent.ConcurrentHashMap;

public class Data {
	
	 // Global ConcurrentHashMap to store session states
    private static final ConcurrentHashMap<String, String> sessionMap = new ConcurrentHashMap<>();

    // Method to add a session state
    public static void addSession(String sessionId, String state) {
        sessionMap.put(sessionId, state);
    }

    // Method to get a session state
    public static String getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    // Method to update a session state
    public static void updateSession(String sessionId, String newState) {
        sessionMap.replace(sessionId, newState);
    }

 // Method to delete a session
    public static void deleteSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    // Method to check if a session exists
    public static boolean containsSession(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }


}
