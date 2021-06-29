package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class hobbyMapController {
	
	@Autowired
	MapDataRepository maprepos;
	
	@Autowired
	kutikomiRepository kutikomirepos;
	
	@Autowired
	DataService service;
	
	
	//////////////////////////////
	// 初期表示コントローラ
	// アクセスURL：/
	// メソッド：GET
	// 設定html：index.html
	//////////////////////////////
	@GetMapping("/")
	public ModelAndView index(ModelAndView mav) throws IOException{
		// index.htmlを設定
		mav.setViewName("index");
		// マップリスト全部取得
		List<MapData> maplistAll = service.getmapAll();
		// マップアドレス全部取得
		List<String> mapaddressAll = service.addressAll();
		// マップネーム全部取得
		List<String> mapnameAll = service.mapnameAll();
		
		// 経度緯度情報のリスト
		List<String> geoinfoList = new ArrayList<String>();
		// 経度緯度情報の取得
		for(int i=0; i<mapaddressAll.size(); i++) {
			geoinfoList.add(geo(mapaddressAll.get(i),mapnameAll.get(i)));
		}
		mav.addObject("geoinfoList",geoinfoList);
		mav.addObject("maplist",maplistAll);
		return mav;
	}
	
	//////////////////////////////
	// マップ検索のコントローラ
	// アクセスURL：/search
	// メソッド：GET
	// 設定html：index.html
	//////////////////////////////
	@GetMapping("/search")
	public ModelAndView searchMap(@RequestParam String searchtext, ModelAndView mav) throws IOException{
		// index.htmlを設定
		mav.setViewName("index");
		// マップ検索のリストを取得
		List<MapData> searchMapList = service.searchmap(searchtext);
		// マップ検索のアドレスリストを取得
		List<String> searchAddressList = service.searchaddress(searchtext);
		// マップ検索のマップネームリストを取得
		List<String> searchMapnameList = service.searchmapname(searchtext);
		
		// 経度緯度情報のリスト
		List<String> geoinfoList = new ArrayList<String>();
		// 経度緯度情報の取得
		for(int i=0; i<searchAddressList.size(); i++) {
			System.out.println(i);
			geoinfoList.add(geo(searchAddressList.get(i),searchMapnameList.get(i)));
		}
		mav.addObject("geoinfoList",geoinfoList);
		mav.addObject("maplist",searchMapList);
		return mav;
	}
	
	//////////////////////////////
	// マップ追加完了時のコントローラ
	// アクセスURL：/
	// メソッド：POST
	// 設定html：mapAddComp.html
	//////////////////////////////
	@PostMapping("/")
	public ModelAndView index(@ModelAttribute("formAddmap") MapData mapdata,ModelAndView mav) {
		// mapAddComp.htmlを設定
		mav.setViewName("mapAddComp");
		// 入力されたMapData情報を保存
		maprepos.saveAndFlush(mapdata);
		return mav;
	}
	
	//////////////////////////////
	// マップ追加のコントローラ
	// アクセスURL：/add
	// メソッド：GET
	// 設定html：mapAddComp.html
	//////////////////////////////
	@GetMapping("/add")
	public ModelAndView mapadd(ModelAndView mav) {
		// mapAdd.htmlを設定
		mav.setViewName("mapAdd");
		// 新規追加用の領域を準備
		MapData newmap = new MapData();
		mav.addObject("formAddmap",newmap);
		return mav;
	}
	
	//////////////////////////////
	// マップ詳細のコントローラ
	// アクセスURL：/detail
	// メソッド：GET
	// 設定html：detail.html
	//////////////////////////////
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam int mapid, ModelAndView mav) throws IOException{
		// detail.htmlを設定
		mav.setViewName("detail");
		// マップIDからマップ情報を取得
		MapData mapdataDetail = service.getmapId(mapid);
		// マップIDから住所を取得
		String mapaddress = service.getmapaddressId(mapid);
		// クチコミ情報を取得
		List<kutikomi> kutikomiList = service.kutikomipart(mapid);
		mav.addObject("kutikomiList",kutikomiList);
		// 新規クチコミ用の領域を準備
		kutikomi newkutikomi = new kutikomi();
		mav.addObject("formkutikomi",newkutikomi);
		
		mav.addObject("getinfo",geo(mapaddress));
		mav.addObject("mapdataDetail",mapdataDetail);
		return mav;
	}
	
	//////////////////////////////
	// クチコミ投稿用コントローラ
	// アクセスURL：/kutikomi
	// メソッド：POST
	// 設定html：detail.html
	//////////////////////////////
	@PostMapping("/kutikomi")
	public ModelAndView kutikomi(@ModelAttribute("formkutikomi") kutikomi kutikomiData,@RequestParam int mapid, ModelAndView mav) throws IOException{
		// クチコミ情報を保存
		kutikomirepos.saveAndFlush(kutikomiData);
		
		// detail.htmlを設定
		mav.setViewName("detail");
		// マップIDからマップ情報を取得
		MapData mapdataDetail = service.getmapId(mapid);
		// マップIDから住所を取得
		String mapaddress = service.getmapaddressId(mapid);
		// クチコミ情報を取得
		List<kutikomi> kutikomiList = service.kutikomipart(mapid);
		mav.addObject("kutikomiList",kutikomiList);
		// 新規クチコミ用の領域を準備
		kutikomi newkutikomi = new kutikomi();
		mav.addObject("formkutikomi",newkutikomi);
		
		mav.addObject("getinfo",geo(mapaddress));
		mav.addObject("mapdataDetail",mapdataDetail);
		return mav;
	}
	
	
	//////////////////////////////
	// データベース初期情報の設定
	// アクセスURL：-
	// メソッド：-
	// 設定html：-
	//////////////////////////////
//	@PostConstruct
//	public void init() {
//		MapData map = new MapData();
//		map.setMapname("テストマップ１");
//		map.setAddress("東京ドーム");
//		map.setDescript("これはテストデータになります。");
//		maprepos.saveAndFlush(map);
//	}
	
	//////////////////////////////
	// ジオコーディング変換メソッド
	// 引数：address 住所 mapname マップネーム
	// 戻り値：geoinfoList 経度緯度情報リスト
	//////////////////////////////
	public String geo(String address,String mapname) throws IOException {
		// 経度緯度変換APIアクセス
		Document document = Jsoup.connect("http://www.geocoding.jp/api/").data("q", address).get();
		// 経度緯度取得
        Elements latData = document.select("lat");
        Elements lngData = document.select("lng");
        String geoinfoList = latData.text() + "," + lngData.text() + "," + mapname;
        
        return geoinfoList;
	}
	
	//////////////////////////////
	// ジオコーディング変換メソッド
	// 引数：address 住所
	// 戻り値：geoinfoList 経度緯度情報リスト
	//////////////////////////////
	public String geo(String address) throws IOException {
		// 経度緯度変換APIアクセス
		Document document = Jsoup.connect("http://www.geocoding.jp/api/").data("q", address).get();
		// 経度緯度取得
        Elements latData = document.select("lat");
        Elements lngData = document.select("lng");
        String geoinfoList = latData.text() + "," + lngData.text();
        
        return geoinfoList;
	}

}
