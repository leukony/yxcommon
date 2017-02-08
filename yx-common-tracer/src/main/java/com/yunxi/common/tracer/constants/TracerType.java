package com.yunxi.common.tracer.constants;

/**
 * Tracer日志上下文类型
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: TracerType.java, v 0.1 2017年1月12日 下午3:59:48 leukony Exp $
 */
public enum TracerType {

    /** HTTPClient日志 */
    HTTP_CLIENT('1'),

    /** HTTPServer日志 */
    HTTP_SERVER('2'), ;

    private char type;

    TracerType(char type) {
        this.type = type;
    }

    /**
      * Getter method for property <tt>type</tt>.
      * 
      * @return property value of type
      */
    public char getType() {
        return type;
    }

    /**
      * Setter method for property <tt>type</tt>.
      * 
      * @param type value to be assigned to property type
      */
    public void setType(char type) {
        this.type = type;
    }
}