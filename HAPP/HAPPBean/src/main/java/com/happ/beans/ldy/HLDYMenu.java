package com.happ.beans.ldy;

import java.sql.Timestamp;

import com.happ.webcore.base.HBaseBean;
import com.happ.webcore.db.mybatis.annotation.HMybatisColumn;
import com.happ.webcore.db.mybatis.annotation.HMybatisId;
import com.happ.webcore.db.mybatis.annotation.HMybatisIncremental;
import com.happ.webcore.db.mybatis.annotation.HMybatisTable;


@HMybatisTable("TLDY_MENU")
public class HLDYMenu extends HBaseBean {

	
	@HMybatisIncremental
	@HMybatisId
	private Long uuid;
	
	@HMybatisColumn("parentId")
	private Long parentId;
	
	private String name_zh;
	private String name_tw;
	private String name_en;
	private String description_zh;
	private String description_tw;
	private String description_en;
	private String icon;
	private String url;
	private Long sortId;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Long enable;
	private String addtion0;
	private String addtion1;
	private String addtion2;

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName_zh() {
		return name_zh;
	}

	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	public String getName_tw() {
		return name_tw;
	}

	public void setName_tw(String name_tw) {
		this.name_tw = name_tw;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getDescription_zh() {
		return description_zh;
	}

	public void setDescription_zh(String description_zh) {
		this.description_zh = description_zh;
	}

	public String getDescription_tw() {
		return description_tw;
	}

	public void setDescription_tw(String description_tw) {
		this.description_tw = description_tw;
	}

	public String getDescription_en() {
		return description_en;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Long getEnable() {
		return enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getAddtion0() {
		return addtion0;
	}

	public void setAddtion0(String addtion0) {
		this.addtion0 = addtion0;
	}

	public String getAddtion1() {
		return addtion1;
	}

	public void setAddtion1(String addtion1) {
		this.addtion1 = addtion1;
	}

	public String getAddtion2() {
		return addtion2;
	}

	public void setAddtion2(String addtion2) {
		this.addtion2 = addtion2;
	}

}
