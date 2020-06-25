package com.programlearning.learning.spring.applicationListener.diyApplicationEvent;

import org.springframework.context.ApplicationEvent;

public class TestCreateEvent extends ApplicationEvent {

    private String string;

    public TestCreateEvent(Object source) {
        super(source);
    }

    public TestCreateEvent(Object source, String s) {
        super(source);
        this.string = s;
    }

    public String getString() {
        return string;
    }
}
