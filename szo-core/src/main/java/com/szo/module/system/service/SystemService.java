package com.szo.module.system.service;

import com.szo.core.common.service.CommonService;
import com.szo.module.system.pojo.base.TSFunction;
import com.szo.module.system.pojo.base.TSType;
import com.szo.module.system.pojo.base.TSTypegroup;
import com.szo.module.system.pojo.base.TSUser;

import java.util.Set;


public interface SystemService extends CommonService {
    /**
     * 登陆用户检查
     *
     * @param user
     * @return
     * @throws Exception
     */
    public TSUser checkUserExits(TSUser user) throws Exception;

    /**
     * 日志添加
     *
     * @param LogContent  内容
     * @param loglevel    级别
     * @param operatetype 类型
     * @param TUser       操作人
     */
    public void addLog(String LogContent, Short loglevel, Short operatetype);

    /**
     * 根据类型编码和类型名称获取Type,如果为空则创建一个
     *
     * @param typecode
     * @param typename
     * @return
     */
    public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup);

    /**
     * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
     *
     * @param typecode
     * @param typename
     * @return
     */
    public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename);

    /**
     * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId);

    /**
     * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId);

    /**
     * 根据编码获取字典组
     *
     * @param typegroupCode
     * @return
     */
    public TSTypegroup getTypeGroupByCode(String typegroupCode);

    /**
     * 对数据字典进行缓存
     */
    public void initAllTypeGroups();

    /**
     * 刷新字典缓存
     *
     * @param type
     */
    public void refleshTypesCach(TSType type);

    /**
     * 刷新字典分组缓存
     */
    public void refleshTypeGroupCach();

    /**
     * 刷新菜单
     *
     * @param id
     */
    public void flushRoleFunciton(String id, TSFunction newFunciton);

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    /**
     * 在线人员添加
     *
     * @param ipString Ip地址字符串
     * @param user     登录人
     */
    public void addOnline(String ipString, TSUser user);

    /**
     * 在线人员删除
     *
     * @param ipString Ip地址字符串
     * @param user     登录人
     */
    public void deleteOnline(String ipString, TSUser user);

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

}
