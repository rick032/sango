/**
 * 
 */
package sango.spring.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sango.spring.model.Device;
import sango.spring.model.DeviceLog;

/**
 * @author Rick
 *
 */
@Repository
public class DeviceLogDaoImp implements DeviceLogDao, Serializable {

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

	@Override
	public DeviceLog findById(String id) {
		return getSession().get(DeviceLog.class, id);
	}

	@Override
	public List<DeviceLog> findByDevice(Device device) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DeviceLog> criteria = builder.createQuery(DeviceLog.class);
		Root<DeviceLog> root = criteria.from(DeviceLog.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("device"), device));
		criteria.orderBy(builder.desc(root.get("checkTime")));
		List<DeviceLog> deviceLogs = null;
		try {
			deviceLogs = (List<DeviceLog>) getSession().createQuery(criteria).getResultList();
		} catch (NoResultException nre) {
			// 找不到
		}
		return deviceLogs;
	}

	@Override
	public List<DeviceLog> findAll() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DeviceLog> criteria = builder.createQuery(DeviceLog.class);
		Root<DeviceLog> root = criteria.from(DeviceLog.class);
		criteria.select(root);
		criteria.orderBy(builder.desc(root.get("checkTime")));
		TypedQuery<DeviceLog> typedQuery = getSession().createQuery(criteria);
		typedQuery.setMaxResults(100);
		return typedQuery.getResultList();
	}

	@Override
	public void save(DeviceLog deviceLog) {
		getSession().save(deviceLog);
	}

	@Override
	public List<DeviceLog> findByDeviceCheckTime(Device device, Timestamp checkTime) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DeviceLog> criteria = builder.createQuery(DeviceLog.class);
		Root<DeviceLog> root = criteria.from(DeviceLog.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("device"), device));
		Path<Timestamp> path = root.get("checkTime");
		criteria.where(builder.greaterThan(path, checkTime));
		criteria.where(builder.lessThan(path, checkTime));
		criteria.orderBy(builder.desc(path));
		List<DeviceLog> deviceLogs = null;
		try {
			TypedQuery<DeviceLog> typedQuery = getSession().createQuery(criteria);
			typedQuery.setMaxResults(100);
			deviceLogs = (List<DeviceLog>) typedQuery.getResultList();
		} catch (NoResultException nre) {
			// 找不到
		}
		return deviceLogs;
	}

}
