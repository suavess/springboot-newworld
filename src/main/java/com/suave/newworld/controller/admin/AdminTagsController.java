package com.suave.newworld.controller.admin;

import com.suave.newworld.annotation.Auth;
import com.suave.newworld.beans.Page;
import com.suave.newworld.beans.RespObj;
import com.suave.newworld.beans.Tags;
import com.suave.newworld.service.AdminTagsService;
import com.suave.newworld.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理端管理标签
 *
 * @author: Suave
 * @date: 2019-12-27 14:14
 */
@RestController
@RequestMapping("/admin/tags")
public class AdminTagsController {

    @Autowired
    private TagsService tagsService;

    @Autowired
    private AdminTagsService adminTagsService;

    /**
     * "/admin/tags"(GET)返回标签列表，分页
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @GetMapping("")
    public RespObj<Page<Tags>> list(Page input) {
        return RespObj.success(adminTagsService.list(input));
    }

    /**
     * "/admin/tags"(Delete)通过id删除标签
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @DeleteMapping("")
    public RespObj del(@RequestBody Map<String,Integer> input) {
        adminTagsService.del(input.get("id"));
        return RespObj.success();
    }

    /**
     * "/admin/tags"(PUT)更新标签
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @PutMapping("")
    public RespObj update(@RequestBody Tags input) {
        tagsService.updateById(input);
        return RespObj.success();
    }

    /**
     * "/admin/tags"(POST)添加标签
     *
     * @param input
     * @return
     */
    @Auth("ADMIN")
    @PostMapping("")
    public RespObj add(@RequestBody Tags input) {
        tagsService.save(input);
        return RespObj.success();
    }
}
