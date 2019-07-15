package com.sample.services;

import org.hibernate.annotations.Filter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

@Component
public class CustomCharsetFilter extends CharacterEncodingFilter {
    public static CharacterEncodingFilter getFilter(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);
        return filter;
    }
}
