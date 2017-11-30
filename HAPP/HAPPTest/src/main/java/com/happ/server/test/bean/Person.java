package com.happ.server.test.bean;

import com.happ.webcore.base.HRequest;

public class Person extends HRequest {

	private Integer age;
	private String name;
	private Integer sex;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Person(Integer age, String name, Integer sex) {
		this.age = age;
		this.name = name;
		this.sex = sex;
	}

	public Person() {
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + ", sex=" + sex + "]";
	}

}
