package com.nsu.photo_anthropology.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping("/")
    public ModelAndView home() {
        List<Groups> listGroup = groupService.listAll();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("listGroup", listGroup);
        return mav;
    }
}
