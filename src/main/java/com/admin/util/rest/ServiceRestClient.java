package com.admin.util.rest;


import java.lang.reflect.Type;

public interface ServiceRestClient {

    <T> T get(String uri, Type type);

    <T> T get(String uri, Object parameter, Type type);

    <T> T delete(String uri, Type type);

    <T> T post(String uri, Object parameter, Type type);

    Object getRest(String uri, Class type);

    <T> T put(String uri, Object parameter, Type type);

}