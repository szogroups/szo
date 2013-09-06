package com.szo.module.demo.service.test;

import com.szo.core.common.service.CommonService;
import com.szo.module.demo.entity.test.CourseEntity;

public interface CourseServiceI extends CommonService {

    void saveCourse(CourseEntity course);

}
