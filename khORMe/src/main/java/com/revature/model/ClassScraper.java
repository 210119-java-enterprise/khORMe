package com.revature.model;

import com.revature.util.AccessDB;

public class ClassScraper {

    static{
        //TODO scrape all classes
        ClassInspector classInspector=new ClassInspector();
        // no paramater
        Class<?> noparams[] = {};

        // String parameter
        Class[] paramString = new Class[1];
        paramString[0] = String.class;

        try {

            // Load Class at runtime
            Class<?> cls = Class.forName("com.revature.forum.models.Board");
            AccessDB accessDB=new AccessDB();
            //accessDB.selectStar(cls);
            //accessDB.listPublicFields(cls);
            classInspector.inspectClass(cls);
//            Object obj = cls.newInstance();
//
//            Method method = cls.getDeclaredMethod("getCompany", noparams);
//            method.invoke(obj);
//
//            method = cls.getDeclaredMethod("getCompanyName", paramString);
//            method.invoke(obj, new String("Google"));
//
//            method = cls.getDeclaredMethod("getCompanyPhone", paramString);
//            method.invoke(obj, new String("408.111.1111"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
