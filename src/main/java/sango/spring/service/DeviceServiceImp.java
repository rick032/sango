package sango.spring.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sango.spring.dao.DeviceDao;
import sango.spring.model.Device;

/**
 * 
 * @author Rick
 *
 */
@Service("deviceService")
public class DeviceServiceImp implements DeviceService {

	@Autowired
	DeviceDao deviceDao;

	@Transactional(readOnly = true)
	@Override
	public Device findByMacAddr(String macAddr) {

		return deviceDao.findByMacAddr(macAddr);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Device> findAll() {
		
		return deviceDao.findAll();
	}

	@Transactional
	@Override
	public void save(Device device) {
		deviceDao.save(device);		
	}

	@Transactional
	@Override
	public void update(Device device) {
		deviceDao.update(device);	
	}

	@Transactional(readOnly = true)
	@Override
	public Device findByMacAddrCheckTime(String macAddr, Timestamp checkTime) {
		return deviceDao.findByMacAddrCheckTime(macAddr,checkTime);
	}

	@Transactional(readOnly = true)
	@Override
	public Device findByOid(String oid) {

		return deviceDao.findByOid(oid);
	}

}
