package com.example.ApuDaily.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("${api.basePath}/${api.version}/admin")
public class AdminController {}
