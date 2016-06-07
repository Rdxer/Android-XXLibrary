package com.firstcare.dylibrary.bean;



/**
 * 类名称：DevCtrlStream 类描述： 设备表 创建人：Ronrey 创建时间：2015年2月5日 上午11:26:30 修改人：Ronrey
 * 修改时间：2015年2月5日 上午11:26:30 修改备注：
 * 
 * @version
 * 
 */
public class Device extends BaseModel {
	private String id; //同步服务器的ID
	private String ip; // 设备ip
	private String name; // 设备名称
	private int status; // 设备状态，0：离线；1：在线；
	private String type; // 插座、窗帘、开关、
	private String kid; // 插座识别ID
	private String mac; // 设备的mac地址
	private String cir1; // 电路板上的接线口1
	private String cir2; // 电路板上的接线口2
	private String accountId; // 账户ID
	private String time; // 时间

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCir1() {
		return cir1;
	}

	public void setCir1(String cir1) {
		this.cir1 = cir1;
	}

	public String getCir2() {
		return cir2;
	}

	public void setCir2(String cir2) {
		this.cir2 = cir2;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
