package com.example.ApuDaily.security;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class HtmlSanitizerUtil {

  private static final PolicyFactory POLICY =
      new HtmlPolicyBuilder()
          // Allow basic text blocks
          .allowElements("p", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "br")
          // Allow formatting
          .allowElements("strong", "em", "u", "span")
          // Allow lists
          .allowElements("ul", "ol", "li")
          // Allow images
          .allowElements("img")
          .allowAttributes("src")
          .onElements("img")
          // Allow links
          .allowElements("a")
          .allowAttributes("href", "title")
          .onElements("a")
          .allowUrlProtocols("http", "https")
          // Global attributes
          .allowAttributes("class")
          .globally()
          .allowStyling()
          .toFactory();

  public static String sanitize(String html) {
    return POLICY.sanitize(html);
  }
}
