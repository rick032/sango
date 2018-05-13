/**
 * 
 */
package sango.spring.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Rick
 *
 */
@Entity
@Table(name = "DEVICELOG")
public class DeviceLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceLog() {
	}
	public DeviceLog(Device device, String result) {
		this.device = device;		
		this.checkTime = new Timestamp(System.currentTimeMillis());//Utils.getTWTimestamp();
		this.checkResult = result;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@ManyToOne(targetEntity = Device.class)
	@JoinColumn(name = "DEVICE_MAC", nullable = false)
	private Device device;

	@Column(name = "CHECKTIME")
	private Timestamp checkTime;

	@Column(name = "CHECKRESULT")
	private String checkResult;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

}
