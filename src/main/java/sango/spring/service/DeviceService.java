package sango.spring.service;

import java.sql.Timestamp;
import java.util.List;

import sango.spring.model.Device;

public interface DeviceService {

	Device findByMacAddr(String macAddr);

	Device findByMacAddrCheckTime(String macAddr,Timestamp checkTime);
	
	List<Device> findAll();

	void save(Device device);
	void update(Device device);

}
