package com.demo.config;


import java.lang.reflect.Method;
import lombok.Data;


@Data
public class SmData {
    private Method method;
    private Object instance;
}
