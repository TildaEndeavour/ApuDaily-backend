package com.example.ApuDaily.security;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class HtmlSanitizerUtil {
    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowCommonBlockElements()
            .allowCommonInlineFormattingElements()
            .toFactory();

    public static String sanitize(String html){
        return POLICY.sanitize(html);
    }
}
