package sango.spring.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sango.spring.dao.DeviceDao;
import sango.spring.dao.DeviceLogDao;
import sango.spring.model.Device;
import sango.spring.model.DeviceLog;

/**
 * 
 * @author Rick
 *
 */
@Service("deviceLogService")
public class DeviceLogServiceImp implements DeviceLogService {

	@Autowired
	DeviceLogDao deviceLogDao;

	@Autowired
	DeviceDao deviceDao;

	@Transactional(readOnly = true)
	@Override
	public List<DeviceLog> findByMacAddr(String macAddr) {

		return deviceLogDao.findByDevice(deviceDao.findByMacAddr(macAddr));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DeviceLog> findAll() {
		return deviceLogDao.findAll();
	}

	@Transactional
	@Override
	public void save(DeviceLog deviceLog) {
		deviceLogDao.save(deviceLog);
		Device device = deviceLog.getDevice();
		device.setLastCheckTime(deviceLog.getCheckTime());
		deviceDao.update(device);
	}

	@Transactional(readOnly = true)
	@Override
	public List<DeviceLog> findByMacAddrCheckTime(String macAddr, Timestamp checkTime) {
		return deviceLogDao.findByDeviceCheckTime(deviceDao.findByMacAddr(macAddr), checkTime);
	}

}
