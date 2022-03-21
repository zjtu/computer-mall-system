package com.cy.springboot.controller;

import com.cy.springboot.entity.District;
import com.cy.springboot.service.IDistrictService;
import com.cy.springboot.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController {

    @Autowired
    private IDistrictService districtService;

    @GetMapping({"", "/"})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
