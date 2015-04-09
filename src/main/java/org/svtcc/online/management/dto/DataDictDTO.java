package org.svtcc.online.management.dto;

/**
 * 
 * @author Kevin
 *
 */
public class DataDictDTO {
	
	private String name;
	
	private String value;
	
	private String groupId;
	
	private String code;
	
	private Integer enabled = 0; // 是否可用  0.停用 1.启用 
	
	private Integer order;   //排序

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
