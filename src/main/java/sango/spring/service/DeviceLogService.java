package sango.spring.service;

import java.sql.Timestamp;
import java.util.List;

import sango.spring.model.DeviceLog;

public interface DeviceLogService {

	List<DeviceLog> findByMacAddr(String macAddr);

	List<DeviceLog> findByMacAddrCheckTime(String macAddr, Timestamp checkTime);

	List<DeviceLog> findAll();

	void save(DeviceLog deviceLog);

}
