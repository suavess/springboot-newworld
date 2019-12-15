package com.suave.newworld.controller;


import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagsService tagsService;

    @GetMapping("")
    @Cacheable(value = "tagsList")
    public RespObj<List<Tags>> list(){
        return RespObj.success(tagsService.list());
    }
}
