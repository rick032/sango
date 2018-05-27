/**
 * 
 */
package sango.spring.dao;

import java.sql.Timestamp;
import java.util.List;

import sango.spring.model.Device;

/**
 * @author Rick
 *
 */
public interface DeviceDao {

	Device findByMacAddr(String macAddr);

	List<Device> findAll();

	void save(Device device);

	void update(Device device);
	
	Device findByMacAddrCheckTime(String macAddr, Timestamp checkTime);

	Device findByOid(String oid);

}
