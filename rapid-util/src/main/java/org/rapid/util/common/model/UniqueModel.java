package org.rapid.util.common.model;

import java.io.Serializable;

/**
 * 表示有全局唯一 ID 的对象
 * 
 * @author ahab
 */
public interface UniqueModel<KEY> extends Serializable {

	KEY key();
}
