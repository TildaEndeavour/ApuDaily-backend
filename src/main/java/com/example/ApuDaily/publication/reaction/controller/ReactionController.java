package com.example.ApuDaily.publication.reaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.basePath}/${api.version}/reactions")
public class ReactionController {
}
