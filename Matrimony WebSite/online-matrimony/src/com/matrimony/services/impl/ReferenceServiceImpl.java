package com.matrimony.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.matrimony.services.interfaces.ReferenceService;
import com.matrimony.vo.KeyValueVO;
import com.matrimony.vo.enums.Addiction;
import com.matrimony.vo.enums.BloodGroup;
import com.matrimony.vo.enums.Build;
import com.matrimony.vo.enums.Charan;
import com.matrimony.vo.enums.Diet;
import com.matrimony.vo.enums.Education;
import com.matrimony.vo.enums.Gan;
import com.matrimony.vo.enums.Gotra;
import com.matrimony.vo.enums.HoroscopeSign;
import com.matrimony.vo.enums.Looks;
import com.matrimony.vo.enums.MaritalStatus;
import com.matrimony.vo.enums.Nadi;
import com.matrimony.vo.enums.Nakshtra;
import com.matrimony.vo.enums.OptionsEnum;
import com.matrimony.vo.enums.PetPreference;
import com.matrimony.vo.enums.Profession;
import com.matrimony.vo.enums.SkinTone;
import com.matrimony.vo.enums.SpecialCondition;

public class ReferenceServiceImpl implements ReferenceService {

	public ReferenceServiceImpl() {
	}

	public List<KeyValueVO> readAllHeights() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (int i = 1; i < 50; i++) {
			result.add(new KeyValueVO("my_profile.height." + String.valueOf(i), String.valueOf(i)));
		}
		return result;
	}

	public List<KeyValueVO> readAllYearsOfExperience() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (int i = 1; i <= 6; i++) {
			result.add(new KeyValueVO("my_profile.experience." + String.valueOf(i), String.valueOf(i)));
		}
		return result;
	}

	public List<KeyValueVO> readAllAnnualIncome() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (int i = 1; i <= 6; i++) {
			result.add(new KeyValueVO("my_profile.annual_income." + String.valueOf(i), String.valueOf(i)));
		}
		return result;
	}
	public List<KeyValueVO> readAllAge() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (int i = 1; i <= 23; i++) {
			result.add(new KeyValueVO("my_profile.age." + String.valueOf(i), String.valueOf(i)));
		}
		return result;
	}
	
	public List<KeyValueVO> readAllAddication() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Addiction addication : Addiction.values()) {
			String key = "my_profile.addiction." + addication.toString();
			String value = addication.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllBloodGroup() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (BloodGroup bloodGrp : BloodGroup.values()) {
			String key = "my_profile.blood_group." + bloodGrp.toString();
			String value = bloodGrp.getGroup();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllBuild() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Build build : Build.values()) {
			String key = "my_profile.build." + build.toString();
			String value = build.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllCharan() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Charan charan : Charan.values()) {
			String key = "my_profile.charan." + charan.toString();
			String value = charan.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllDiet() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Diet diet : Diet.values()) {
			String key = "my_profile.diet." + diet.toString();
			String value = diet.getDietType();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllEducation() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Education education : Education.values()) {
			String key = "my_profile.education.long_desc." + education.toString();
			String value = education.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;

	}

	public List<KeyValueVO> readAllGan() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Gan gan : Gan.values()) {
			String key = "my_profile.gan." + gan.toString();
			String value = gan.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllGender() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		// TODO
		return result;
	}

	public List<KeyValueVO> readAllGotra() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Gotra gotra : Gotra.values()) {
			String key = "my_profile.gotra." + gotra.toString();
			String value = gotra.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllHoroscopeSign() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (HoroscopeSign sign : HoroscopeSign.values()) {
			String key = "my_profile.horoscope_sign." + sign.toString();
			String value = sign.getSign();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllLooks() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Looks looks : Looks.values()) {
			String key = "my_profile.looks." + looks.toString();
			String value = looks.getLookType();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllMaritalStatus() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (MaritalStatus marital : MaritalStatus.values()) {
			String key = "my_profile.marital_status." + marital.toString();
			String value = marital.getStatus();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllNadi() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Nadi nadi : Nadi.values()) {
			String key = "my_profile.nadi." + nadi.toString();
			String value = nadi.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllNakshtra() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Nakshtra nakshtra : Nakshtra.values()) {
			String key = "my_profile.nakshtra." + nakshtra.toString();
			String value = nakshtra.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllOptions() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (OptionsEnum opt : OptionsEnum.values()) {
			String key = "general.options." + opt.toString();
			String value = opt.getOption();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;

	}

	public List<KeyValueVO> readAllPetPreferences() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (PetPreference petPref : PetPreference.values()) {
			String key = "my_profile.pet_preference." + petPref.toString();
			String value = petPref.getPreference();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllProfession() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (Profession profession : Profession.values()) {
			String key = "my_profile.profession.long_desc." + profession.toString();
			String value = profession.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllSkinTone() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (SkinTone skinTone : SkinTone.values()) {
			String key = "my_profile.skin_tone." + skinTone.toString();
			String value = skinTone.getTone();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

	public List<KeyValueVO> readAllSpecialCondition() {
		List<KeyValueVO> result = new ArrayList<KeyValueVO>();
		for (SpecialCondition specCondition : SpecialCondition.values()) {
			String key = "my_profile.special_condition.long_desc." + specCondition.toString();
			String value = specCondition.getCode();
			KeyValueVO vo = new KeyValueVO(key, value);
			result.add(vo);
		}
		return result;
	}

}
