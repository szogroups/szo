package com.szo.module.demo.entity.test;

import com.szo.core.common.entity.IdEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SzoDemo测试表
 */
@Entity
@Table(name = "szo_demo")
@Inheritance(strategy = InheritanceType.JOINED)
public class SzoDemo extends IdEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    private java.lang.String mobilePhone;
    /**
     * 办公电话
     */
    private java.lang.String officePhone;
    /**
     * 电子邮箱
     */
    private java.lang.String email;
    /**
     * age
     */
    private java.lang.Integer age;
    /**
     * 工资
     */
    private BigDecimal salary;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 性别
     */
    private java.lang.Integer sex;
    /**
     * 部门ID
     */
    private java.lang.String depId;
    /**
     * 用户名
     */
    private java.lang.String userName;
    /**
     * 状态
     */
    private java.lang.String status;
    /**
     * 备注
     */
    private java.lang.String content;


    public java.lang.String getStatus() {
        return status;
    }

    @Column(name = "status", nullable = true)
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  手机号码
     */
    @Column(name = "MOBILE_PHONE", nullable = true)
    public java.lang.String getMobilePhone() {
        return this.mobilePhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  手机号码
     */
    public void setMobilePhone(java.lang.String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  办公电话
     */
    @Column(name = "OFFICE_PHONE", nullable = true)
    public java.lang.String getOfficePhone() {
        return this.officePhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  办公电话
     */
    public void setOfficePhone(java.lang.String officePhone) {
        this.officePhone = officePhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  电子邮箱
     */
    @Column(name = "EMAIL", nullable = true)
    public java.lang.String getEmail() {
        return this.email;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  电子邮箱
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  age
     */
    @Column(name = "AGE", nullable = true)
    public java.lang.Integer getAge() {
        return this.age;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  age
     */
    public void setAge(java.lang.Integer age) {
        this.age = age;
    }

    /**
     * 方法: 取得BigDecimal
     *
     * @return: BigDecimal  工资
     */
    @Column(name = "SALARY", nullable = true)
    public BigDecimal getSalary() {
        return this.salary;
    }

    /**
     * 方法: 设置BigDecimal
     *
     * @param: BigDecimal  工资
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * 方法: 取得Date
     *
     * @return: Date  生日
     */
    @Column(name = "BIRTHDAY", nullable = true)
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * 方法: 设置Date
     *
     * @param: Date  生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 方法: 取得java.sql.Timestamp
     *
     * @return: java.sql.Timestamp  创建时间
     */
    @Column(name = "CREATE_TIME", nullable = true)
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 方法: 设置Date
     *
     * @param: Date  创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  性别
     */
    @Column(name = "SEX", nullable = true)
    public java.lang.Integer getSex() {
        return this.sex;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  性别
     */
    public void setSex(java.lang.Integer sex) {
        this.sex = sex;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  部门ID
     */
    @Column(name = "DEP_ID", nullable = true)
    public java.lang.String getDepId() {
        return this.depId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  部门ID
     */
    public void setDepId(java.lang.String depId) {
        this.depId = depId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  用户名
     */
    @Column(name = "USER_NAME", nullable = false)
    public java.lang.String getUserName() {
        return this.userName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  用户名
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getContent() {
        return content;
    }

    public void setContent(java.lang.String content) {
        this.content = content;
    }

}