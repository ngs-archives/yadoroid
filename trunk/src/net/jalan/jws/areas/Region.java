package net.jalan.jws.areas;
import net.jalan.jws.areas.Area;
import net.jalan.jws.areas.Prefecture;

public class Region extends Area {
	public Prefecture[] prefectures;
	public Region(String code) {
		String n;
		switch(Integer.parseInt(code)) {
			case  1: n = "北海道"; break;
			case  5: n = "東北"; break;
			case 10: n = "北関東"; break;
			case 15: n = "首都圏"; break;
			case 20: n = "甲信越"; break;
			case 25: n = "北陸"; break;
			case 30: n = "東海"; break;
			case 35: n = "近畿"; break;
			case 40: n = "山陰・山陽"; break;
			case 45: n = "四国"; break;
			case 50: n = "九州"; break;
			case 55: n = "沖縄"; break;
			default: /* invalid code */ return;
		}
		this.code = code;
		this.name = n;
	}
}
