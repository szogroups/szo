package com.szo.module.demo.entity.test;

import com.szo.core.annotation.excel.Excel;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author jueyue
 * @version V1.0
 * @Title: Entity
 * @Description: 学生
 * @date 2013-08-31 22:53:34
 */
@Entity
@Table(name = "szo_demo_student", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class StudentEntity implements java.io.Serializable {
    /**
     * id
     */
    private java.lang.String id;
    /**
     * 学生姓名
     */
    @Excel(exportName = "学生姓名")
    private java.lang.String name;
    /**
     * 学生性别
     */
    @Excel(exportName = "学生性别", exportConvertSign = 1)
    private java.lang.String sex;
    /**
     * 课程主键
     */
    private CourseEntity course;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  id
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
     * @param: java.lang.String  id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  学生姓名
     */
    @Column(name = "NAME", nullable = true, length = 32)
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  学生姓名
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  学生性别
     */
    @Column(name = "SEX", nullable = true, length = 1)
    public java.lang.String getSex() {
        return this.sex;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  学生性别
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public String convertGetSex() {
        return this.sex.equals("0") ? "男生" : "女生";
    }
}
