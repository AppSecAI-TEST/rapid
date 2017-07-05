package org.rapid.data;

import org.rapid.util.common.model.UniqueModel;

public class Mem implements UniqueModel<Integer> {

	private static final long serialVersionUID = 9014317126181330937L;

	private int id;
	private String name;
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Integer key() {
		return this.id;
	}
}
