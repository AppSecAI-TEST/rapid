package org.rapid.util.common.db;

import java.io.Serializable;

/**
 * 表示一个条数据库数据
 * 
 * @author ahab
 */
public interface Entity<KEY> extends Serializable {

	KEY key();
}
