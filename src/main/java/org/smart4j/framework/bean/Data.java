package org.smart4j.framework.bean;

/**
 *返回数据对象
 *@author Garwen
 *@date 2019-12-4 15:27
 */

public class Data {
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
