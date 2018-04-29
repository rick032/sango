/**
 * 
 */
package sango.spring.dao;

import java.util.List;

import sango.spring.model.Device;

/**
 * @author Rick
 *
 */
public interface DeviceDao {

	Device findById(String deviceId);

	Device findByMacAddr(String macAddr);

	List<Device> findAll();

	void save(Device device);

	void update(Device device);

}
