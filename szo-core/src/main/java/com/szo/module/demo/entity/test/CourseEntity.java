package com.szo.module.demo.entity.test;

import com.szo.core.annotation.excel.Excel;
import com.szo.core.annotation.excel.ExcelCollection;
import com.szo.core.annotation.excel.ExcelEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @author jueyue
 * @version V1.0
 * @Title: Entity
 * @Description: 课程
 * @date 2013-08-31 22:53:07
 */
@Entity
@Table(name = "szo_demo_course", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CourseEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 课程名称
     */
    @Excel(exportName = "课程名称", orderNum = "1", needMerge = true)
    private java.lang.String name;
    /**
     * 老师主键
     */
    @ExcelEntity()
    private TeacherEntity teacher;

    @ExcelCollection(exportName = "选课学生", orderNum = "4")
    private List<StudentEntity> students;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 课程名称
     */
    @Column(name = "NAME", nullable = true, length = 25)
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 课程名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 老师主键
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    public TeacherEntity getTeacher() {
        return teacher;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 老师主键
     */
    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }
}
