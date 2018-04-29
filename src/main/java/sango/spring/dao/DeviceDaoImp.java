/**
 * 
 */
package sango.spring.dao;

import java.util.List;

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
public class DeviceDaoImp implements DeviceDao {

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
	public Device findById(String deviceId) {
		return getSession().get(Device.class, deviceId);
	}

	@Override
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

	
}
