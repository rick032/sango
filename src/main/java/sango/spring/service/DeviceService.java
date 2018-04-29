package sango.spring.service;

import java.util.List;

import sango.spring.model.Device;

public interface DeviceService {

	Device findByMacAddr(String macAddr);

	List<Device> findAll();

	void save(Device device);
	void update(Device device);

}
