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
	
	////////////////////////////////////
	// 全マップ情報取得
	// 引数 なし
	// 戻り値 全マップデータ List<MapData>
	// SELECT *
	// FROM mapdata
	// WHERE なし
	// /////////////////////////////////
	@SuppressWarnings("unchecked")
	public List<MapData> getmapAll() {
		return (List<MapData>)entityManager.createQuery("from MapData").getResultList();
	}
	
	////////////////////////////////////
	// 全マップ住所情報取得
	// 引数 なし
	// 戻り値 全マップ住所データ List<String>
	// SELECT address
	// FROM mapdata
	// WHERE なし
	// /////////////////////////////////
	public List<String> addressAll() {
		return (List<String>)entityManager.createQuery("select address from MapData").getResultList();
	}
	
	////////////////////////////////////
	// 全マップ名称情報取得
	// 引数 なし
	// 戻り値 全マップ名称データ List<String>
	// SELECT mapname
	// FROM mapdata
	// WHERE なし
	// /////////////////////////////////
	public List<String> mapnameAll() {
		return (List<String>)entityManager.createQuery("select mapname from MapData").getResultList();
	}
	
	////////////////////////////////////
	// 指定IDマップ情報取得
	// 引数 id
	// 戻り値 マップデータ MapData
	// SELECT *
	// FROM mapdata
	// WHERE id=id
	// /////////////////////////////////
	public MapData getmapId(int id) {
		return (MapData)entityManager.createQuery("from MapData where id =" + id).getSingleResult();
	}
	
	////////////////////////////////////
	// 指定IDマップ住所情報取得
	// 引数 id
	// 戻り値 マップ住所文字列String
	// SELECT address
	// FROM mapdata
	// WHERE id=id
	// /////////////////////////////////
	public String getmapaddressId(int id) {
		return (String)entityManager.createQuery("select address from MapData where id =" + id).getSingleResult();
	}
	
	////////////////////////////////////
	// 検索文字列からマップ情報取得
	// 引数 String searchtext
	// 戻り値 マップデータ　List<MapData>
	// SELECT *
	// FROM mapdata
	// WHERE mapname like searchtext
	// /////////////////////////////////
	public List<MapData> searchmap(String searchtext) {
		Query query = entityManager.createQuery("from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<MapData>)query.getResultList();
	}
	
	////////////////////////////////////
	// 検索文字列からマップ住所情報取得
	// 引数 String searchtext
	// 戻り値 マップ住所データ　List<String>
	// SELECT address
	// FROM mapdata
	// WHERE mapname like searchtext
	// /////////////////////////////////
	public List<String> searchaddress(String searchtext) {
		Query query = entityManager.createQuery("select address from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<String>)query.getResultList();
	}
	
	////////////////////////////////////
	// 検索文字列からマップ名称情報取得
	// 引数 String searchtext
	// 戻り値 マップ名称データ　List<String>
	// SELECT mapname
	// FROM mapdata
	// WHERE mapname like searchtext
	// /////////////////////////////////
	public List<String> searchmapname(String searchtext) {
		Query query = entityManager.createQuery("select mapname from MapData where mapname LIKE :searchtext");
		query.setParameter("searchtext", "%" + searchtext + "%");
		return (List<String>)query.getResultList();
	}
	
	////////////////////////////////////
	// 全クチコミ情報取得
	// 引数 なし
	// 戻り値 クチコミデータ　List<kutikomi>
	// SELECT *
	// FROM kutikomi
	// WHERE なし
	////////////////////////////////////
	public List<kutikomi> kutikomiAll() {
		return (List<kutikomi>)entityManager.createQuery("from kutikomi").getResultList();
	}
	
	////////////////////////////////////
	// 指定IDクチコミ情報取得
	// 引数 int mapid
	// 戻り値 クチコミデータ　List<kutikomi>
	// SELECT *
	// FROM kutikomi
	// WHERE mapid = mapid
	////////////////////////////////////
	public List<kutikomi> kutikomipart(int mapid) {
		return (List<kutikomi>)entityManager.createQuery("from kutikomi where mapid =" + mapid).getResultList();
	}
}
