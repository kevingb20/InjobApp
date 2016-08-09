package com.injob.injob.injobapp.tests;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void este_si_paso(){
        assertTrue(5>1);
    }
    public void este_no_paso(){
        assertTrue(5<1);
    }
}