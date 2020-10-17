package com.atguigu.crowd.funding.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private Integer id; // 数据库主键

	private Integer pid; // 对应父节点（如果pid为null，说明当前节点为根节点）

	private String name; // 节点名称

	private String icon; // 节点图标

	private String url; // 节点URL地址

	private List<Menu> children = new ArrayList<>(); // 当前节点的子节点集合

	private Boolean open = true; // 控制菜单节点是否展开/折叠，默认整个树形菜单是展开的

	public Menu() {
		super();
	}

	public Menu(Integer id, Integer pid, String name, String icon, String url) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.icon = icon;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", pid=" + pid + ", name=" + name + ", icon=" + icon + ", url=" + url + "]";
	}

}