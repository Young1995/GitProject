package org.svtcc.online.management.dto;

public class UploadFile {
	private int id;
	private String storeFolder;
	private String originName;
	private String realName;
	private String type;
	private Long size;
	private boolean uploadSuccess;
	private String message;

	public UploadFile() {

	}

	public UploadFile(String originName,
			boolean uploadSuccess, String message) {
		super();
		this.originName = originName;
		this.uploadSuccess = uploadSuccess;
		this.message = message;
	}

	public UploadFile(int id, String storeFolder, String originName,
			String realName, String type, Long size, boolean uploadSuccess,
			String message) {
		super();
		this.id = id;
		this.storeFolder = storeFolder;
		this.originName = originName;
		this.realName = realName;
		this.type = type;
		this.size = size;
		this.uploadSuccess = uploadSuccess;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStoreFolder() {
		return storeFolder;
	}

	public void setStoreFolder(String storeFolder) {
		this.storeFolder = storeFolder;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
