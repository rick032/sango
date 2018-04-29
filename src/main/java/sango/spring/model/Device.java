package sango.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE")
public class Device {
	@Id
	@Column(name = "MACADDR")
	private String macAddr;

	@Column(name = "IMEI")
	private String imei;

	@Column(name = "DEVICEID")
	private String deviceID;

	@Column(name = "GAMENAME", nullable = false)
	private String gamename;

	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String toString() {
		return "MACADDR:" + getMacAddr() + ",IMEI:" + getImei() + ",gameName" + getGamename() + ",UserName:"
				+ getUsername() + ",enable:" + isEnabled();
	}
}
