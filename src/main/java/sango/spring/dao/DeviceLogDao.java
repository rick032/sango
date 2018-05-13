/**
 * 
 */
package sango.spring.dao;

import java.sql.Timestamp;
import java.util.List;

import sango.spring.model.Device;
import sango.spring.model.DeviceLog;

/**
 * @author Rick
 *
 */
public interface DeviceLogDao {
	
	DeviceLog findById(String id);

	List<DeviceLog> findAll();

	void save(DeviceLog deviceLog);

	List<DeviceLog> findByDeviceCheckTime(Device device, Timestamp checkTime);

	List<DeviceLog> findByDevice(Device device);

}
