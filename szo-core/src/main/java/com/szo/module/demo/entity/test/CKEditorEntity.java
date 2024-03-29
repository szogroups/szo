package com.szo.module.demo.entity.test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author zhangdaihao
 * @version V1.0
 * @Title: Entity
 * @Description: HTML 编辑器
 * @date 2013-07-08 16:19:21
 */
@Entity
@Table(name = "ck_editor", schema = "")
@SuppressWarnings("serial")
public class CKEditorEntity implements java.io.Serializable {
    /**
     * id
     */
    private java.lang.String id;
    /**
     * contents
     */
    private byte[] contents;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String id
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
     * @param: java.lang.String id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得byte[]
     *
     * @return: byte[] contents
     */
    @Column(name = "CONTENTS", nullable = true, length = 65535)
    public byte[] getContents() {
        return this.contents;
    }

    /**
     * 方法: 设置byte[]
     *
     * @param: byte[] contents
     */
    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
