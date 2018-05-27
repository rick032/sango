package sango.spring.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DEVICE", uniqueConstraints = @UniqueConstraint(columnNames = "oid"))
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "oid", updatable = false, nullable = false)
	private UUID oid;

	@Column(name = "MACADDR")
	private String macAddr;

	@Column(name = "IMEI")
	private String imei;

	@Column(name = "DEVICEID")
	private String deviceID;

	@Column(name = "GAMENAME")
	private String gamename;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "STARTTIME")
	private Timestamp startTime;

	@Column(name = "ENDTIME")
	private Timestamp endTime;

	@Column(name = "LASTCHECKTIME")
	private Timestamp lastCheckTime;

	@OneToMany(mappedBy = "device")
	private Set<DeviceLog> deviceLogs;

	public UUID getOid() {
		return oid;
	}

	public void setOid(UUID oid) {
		this.oid = oid;
	}

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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Set<DeviceLog> getDeviceLogs() {
		return deviceLogs;
	}

	public void setDeviceLogs(Set<DeviceLog> deviceLogs) {
		this.deviceLogs = deviceLogs;
	}

	public Timestamp getLastCheckTime() {
		return lastCheckTime;
	}

	public void setLastCheckTime(Timestamp lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}

	public String toString() {
		return "MACADDR:" + getMacAddr() + ",lastCheckTime:" + getLastCheckTime() + ",StartTime:" + getStartTime()
				+ ",EndTime:" + getEndTime() + ",IMEI:" + getImei() + ",IMEI:" + getImei() + ",gameName" + getGamename()
				+ ",UserName:" + getUsername() + ",enable:" + isEnabled();
	}
}
