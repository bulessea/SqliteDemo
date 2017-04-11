package com.spreadwin.sqlit.demo.bean;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="stud")
public class Subscriber extends Model implements Serializable{
	public Subscriber(){
		super();
	}
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	
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
	public String toString(){
		return "Subscriber [name=" + name + ", age=" + age + "]";
	}
	
}
