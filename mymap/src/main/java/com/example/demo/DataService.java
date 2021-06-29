package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class DataService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<MapData> getmapAll() {
		return (List<MapData>)entityManager.createQuery("from MapData").getResultList();
	}
	public List<String> addressAll() {
		return (List<String>)entityManager.createQuery("select address from MapData").getResultList();
	}
	public List<String> mapnameAll() {
		return (List<String>)entityManager.createQuery("select mapname from MapData").getResultList();
	}
	
	public MapData getmapId(int id) {
		return (MapData)entityManager.createQuery("from MapData where id =" + id).getSingleResult();
	}
	public String getmapaddressId(int id) {
		return (String)entityManager.createQuery("select address from MapData where id =" + id).getSingleResult();
	}
	
	public List<MapData> searchmap(String searchtext) {
		Query query = entityManager.createQuery("from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<MapData>)query.getResultList();
	}
	public List<String> searchaddress(String searchtext) {
		Query query = entityManager.createQuery("select address from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<String>)query.getResultList();
	}
	public List<String> searchmapname(String searchtext) {
		Query query = entityManager.createQuery("select mapname from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<String>)query.getResultList();
	}
	
	public List<kutikomi> kutikomiAll() {
		return (List<kutikomi>)entityManager.createQuery("from kutikomi").getResultList();
	}
}
