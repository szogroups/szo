package com.szo.module.demo.service.test;

import com.szo.core.common.model.json.DataGrid;
import com.szo.core.common.service.CommonService;
import com.szo.module.demo.entity.test.SzoJdbcEntity;
import net.sf.json.JSONObject;

public interface SzoJdbcServiceI extends CommonService {
    public void getDatagrid1(SzoJdbcEntity pageObj, DataGrid dataGrid);

    public void getDatagrid2(SzoJdbcEntity pageObj, DataGrid dataGrid);

    public JSONObject getDatagrid3(SzoJdbcEntity pageObj, DataGrid dataGrid);

    public void listAllByJdbc(DataGrid dataGrid);
}
