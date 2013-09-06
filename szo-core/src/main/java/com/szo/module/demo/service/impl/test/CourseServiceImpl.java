package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.entity.test.CourseEntity;
import com.szo.module.demo.entity.test.StudentEntity;
import com.szo.module.demo.service.test.CourseServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseService")
@Transactional
public class CourseServiceImpl extends CommonServiceImpl implements
        CourseServiceI {

    @Override
    public void saveCourse(CourseEntity course) {
        this.save(course.getTeacher());
        this.save(course);
        for (StudentEntity s : course.getStudents()) {
            s.setCourse(course);
        }
        this.batchSave(course.getStudents());

    }

}