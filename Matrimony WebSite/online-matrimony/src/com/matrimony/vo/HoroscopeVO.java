package com.matrimony.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.matrimony.vo.enums.Charan;
import com.matrimony.vo.enums.Gan;
import com.matrimony.vo.enums.HoroscopeSign;
import com.matrimony.vo.enums.Nadi;
import com.matrimony.vo.enums.Nakshtra;
import com.matrimony.vo.enums.OptionsEnum;

@PersistenceCapable
@EmbeddedOnly
public class HoroscopeVO implements Serializable {

	private static final long serialVersionUID = 992574362233128721L;

	@Persistent
	private OptionsEnum hvHoroscopeMatching = OptionsEnum.NO_ANSWER;

	@Persistent
	private OptionsEnum hvMangal = OptionsEnum.NO_ANSWER;

	@Persistent
	private Nakshtra hvNakshtra = Nakshtra.NO_ANSWER;

	@Persistent
	private Gan hvGan = Gan.NO_ANSWER;

	@Persistent
	private Nadi hvNadi = Nadi.NO_ANSWER;

	@Persistent
	private Charan hvCharan = Charan.NO_ANSWER;

	@Persistent
	private HoroscopeSign hvHoroscopeSign = HoroscopeSign.NO_ANSWER;

	@Persistent
	private int hvBirthHour;

	@Persistent
	private int hvBirthMin;

	@Persistent
	private String hvBirthPlace;

	@Persistent
	private String hvHouse1;

	@Persistent
	private String hvHouse2;

	@Persistent
	private String hvHouse3;

	@Persistent
	private String hvHouse4;

	@Persistent
	private String hvHouse5;

	@Persistent
	private String hvHouse6;

	@Persistent
	private String hvHouse7;

	@Persistent
	private String hvHouse8;

	@Persistent
	private String hvHouse9;

	@Persistent
	private String hvHouse10;

	@Persistent
	private String hvHouse11;

	@Persistent
	private String hvHouse12;

	@Persistent
	private Date hvLastChangedOn = null;

	public HoroscopeVO copy(HoroscopeVO horoscope) {
		this.hvHoroscopeMatching = horoscope.hvHoroscopeMatching;
		this.hvMangal = horoscope.hvMangal;
		this.hvNakshtra = horoscope.hvNakshtra;
		this.hvGan = horoscope.hvGan;
		this.hvNadi = horoscope.hvNadi;
		this.hvCharan = horoscope.hvCharan;
		this.hvHoroscopeSign = horoscope.hvHoroscopeSign;
		this.hvBirthHour = horoscope.hvBirthHour;
		this.hvBirthMin = horoscope.hvBirthMin;
		this.hvBirthPlace = horoscope.hvBirthPlace;
		this.hvHouse1 = horoscope.hvHouse1;
		this.hvHouse2 = horoscope.hvHouse2;
		this.hvHouse3 = horoscope.hvHouse3;
		this.hvHouse4 = horoscope.hvHouse4;
		this.hvHouse5 = horoscope.hvHouse5;
		this.hvHouse6 = horoscope.hvHouse6;
		this.hvHouse7 = horoscope.hvHouse7;
		this.hvHouse8 = horoscope.hvHouse8;
		this.hvHouse9 = horoscope.hvHouse9;
		this.hvHouse10 = horoscope.hvHouse10;
		this.hvHouse11 = horoscope.hvHouse11;
		this.hvHouse12 = horoscope.hvHouse12;
		this.hvLastChangedOn = horoscope.hvLastChangedOn;
		return this;
	}

	public OptionsEnum getHoroscopeMatching() {
		return hvHoroscopeMatching;
	}

	public void setHoroscopeMatching(OptionsEnum horoscopeMatching) {
		this.hvHoroscopeMatching = horoscopeMatching;
	}

	public String getHoroscopeMatchingCode() {
		return hvHoroscopeMatching.getOption();
	}

	public void setHoroscopeMatchingCode(String code) {
		this.hvHoroscopeMatching = OptionsEnum.getOption(code);
	}

	public OptionsEnum getMangal() {
		return hvMangal;
	}

	public void setMangal(OptionsEnum mangal) {
		this.hvMangal = mangal;
	}

	public String getMangalCode() {
		return hvMangal.getOption();
	}

	public void setMangalCode(String code) {
		this.hvMangal = OptionsEnum.getOption(code);
	}

	public Nakshtra getNakshtra() {
		return (hvNakshtra != null) ? hvNakshtra : Nakshtra.NO_ANSWER;
	}

	public void setNakshtra(Nakshtra nakshtra) {
		this.hvNakshtra = nakshtra;
	}

	public String getNakshtraCode() {
		return getNakshtra().getCode();
	}

	public void setNakshtraCode(String code) {
		this.hvNakshtra = Nakshtra.getNakshtra(code);
	}

	public Gan getGan() {
		return hvGan;
	}

	public void setGan(Gan gan) {
		this.hvGan = gan;
	}

	public String getGanCode() {
		return this.hvGan.getCode();
	}

	public void setGanCode(String code) {
		this.hvGan = Gan.getGan(code);
	}

	public Nadi getNadi() {
		return hvNadi;
	}

	public void setNadi(Nadi nadi) {
		this.hvNadi = nadi;
	}

	public String getNadiCode() {
		return hvNadi.getCode();
	}

	public void setNadiCode(String code) {
		this.hvNadi = Nadi.getNadi(code);
	}

	public Charan getCharan() {
		return hvCharan;
	}

	public void setCharan(Charan charan) {
		this.hvCharan = charan;
	}

	public String getCharanCode() {
		return hvCharan.getCode();
	}

	public void setCharanCode(String code) {
		this.hvCharan = Charan.getCharan(code);
	}

	public HoroscopeSign getHoroscopeSign() {
		return hvHoroscopeSign;
	}

	public void setHoroscopeSign(HoroscopeSign horoscopeSign) {
		this.hvHoroscopeSign = horoscopeSign;
	}

	public String getHoroscopeSignCode() {
		return hvHoroscopeSign.getSign();
	}

	public void setHoroscopeSignCode(String sign) {
		this.hvHoroscopeSign = HoroscopeSign.getHoroscopeSign(sign);
	}

	public int getBirthHour() {
		return hvBirthHour;
	}

	public void setBirthHour(int birthHour) {
		this.hvBirthHour = birthHour;
	}

	public int getBirthMin() {
		return hvBirthMin;
	}

	public void setBirthMin(int birthMin) {
		this.hvBirthMin = birthMin;
	}

	public String getBirthPlace() {
		return hvBirthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.hvBirthPlace = birthPlace;
	}

	public String getHouse1() {
		return hvHouse1;
	}

	public void setHouse1(String house1) {
		this.hvHouse1 = house1;
	}

	public String getHouse2() {
		return hvHouse2;
	}

	public void setHouse2(String house2) {
		this.hvHouse2 = house2;
	}

	public String getHouse3() {
		return hvHouse3;
	}

	public void setHouse3(String house3) {
		this.hvHouse3 = house3;
	}

	public String getHouse4() {
		return hvHouse4;
	}

	public void setHouse4(String house4) {
		this.hvHouse4 = house4;
	}

	public String getHouse5() {
		return hvHouse5;
	}

	public void setHouse5(String house5) {
		this.hvHouse5 = house5;
	}

	public String getHouse6() {
		return hvHouse6;
	}

	public void setHouse6(String house6) {
		this.hvHouse6 = house6;
	}

	public String getHouse7() {
		return hvHouse7;
	}

	public void setHouse7(String house7) {
		this.hvHouse7 = house7;
	}

	public String getHouse8() {
		return hvHouse8;
	}

	public void setHouse8(String house8) {
		this.hvHouse8 = house8;
	}

	public String getHouse9() {
		return hvHouse9;
	}

	public void setHouse9(String house9) {
		this.hvHouse9 = house9;
	}

	public String getHouse10() {
		return hvHouse10;
	}

	public void setHouse10(String house10) {
		this.hvHouse10 = house10;
	}

	public String getHouse11() {
		return hvHouse11;
	}

	public void setHouse11(String house11) {
		this.hvHouse11 = house11;
	}

	public String getHouse12() {
		return hvHouse12;
	}

	public void setHouse12(String house12) {
		this.hvHouse12 = house12;
	}

	public Date getLastChangedOn() {
		return hvLastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.hvLastChangedOn = lastChangedOn;
	}

}
