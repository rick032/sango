/**
 * 
 */
package sango.spring.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sango.spring.model.Device;

/**
 * @author Rick
 *
 */
@Repository
public class DeviceDaoImp implements DeviceDao, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	public Device findByMacAddr(String macAddr) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Device> criteria = builder.createQuery(Device.class);
		Root<Device> root = criteria.from(Device.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("macAddr"), macAddr));
		Device device = null;
		try {
			device = (Device) getSession().createQuery(criteria).getSingleResult();
		} catch (NoResultException nre) {
			// 找不到
		}
		return device;
	}

	@Override
	public List<Device> findAll() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Device> criteria = builder.createQuery(Device.class);
		Root<Device> root = criteria.from(Device.class);
		criteria.select(root);
		criteria.orderBy(builder.desc(root.get("lastCheckTime")));
		return getSession().createQuery(criteria).getResultList();
	}

	@Override
	public void save(Device device) {
		getSession().save(device);
	}

	@Override
	public void update(Device device) {
		getSession().update(device);
	}

	@Override
	public Device findByMacAddrCheckTime(String macAddr, Timestamp checkTime) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Device> criteria = builder.createQuery(Device.class);
		Root<Device> root = criteria.from(Device.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("macAddr"), macAddr));
		criteria.where(builder.greaterThan(root.get("startTime"), checkTime));
		criteria.where(builder.lessThan(root.get("endTime"), checkTime));
		criteria.orderBy(builder.desc(root.get("lastCheckTime")));
		Device device = null;
		try {
			device = getSession().createQuery(criteria).getSingleResult();
		} catch (NoResultException nre) {
			// 找不到
		}
		return device;
	}

	@Override
	public Device findByOid(String oid) {
		return getSession().get(Device.class, UUID.fromString(oid));
	}

}
