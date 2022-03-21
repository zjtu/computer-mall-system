package com.cy.springboot.service.impl;

import com.cy.springboot.entity.District;
import com.cy.springboot.mapper.DistrictMapper;
import com.cy.springboot.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;


    @Override
    public List<District> getByParent(String parent) {
        List<District> districts = districtMapper.findByParent(parent);
        for(District district:districts){
            district.setId(null);
            district.setParent(null);
        }
        return districts;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
