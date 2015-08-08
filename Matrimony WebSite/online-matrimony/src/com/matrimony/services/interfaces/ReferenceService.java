package com.matrimony.services.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.matrimony.vo.KeyValueVO;

public interface ReferenceService {
	public List<KeyValueVO> readAllHeights();

	public List<KeyValueVO> readAllYearsOfExperience();

	public List<KeyValueVO> readAllAnnualIncome();

	public List<KeyValueVO> readAllAge();

	public List<KeyValueVO> readAllAddication();

	public List<KeyValueVO> readAllBloodGroup();

	public List<KeyValueVO> readAllBuild();

	public List<KeyValueVO> readAllCharan();

	public List<KeyValueVO> readAllDiet();

	public List<KeyValueVO> readAllEducation();

	public List<KeyValueVO> readAllGan();

	public List<KeyValueVO> readAllGender();

	public List<KeyValueVO> readAllGotra();

	public List<KeyValueVO> readAllHoroscopeSign();

	public List<KeyValueVO> readAllLooks();

	public List<KeyValueVO> readAllMaritalStatus();

	public List<KeyValueVO> readAllNadi();

	public List<KeyValueVO> readAllNakshtra();

	public List<KeyValueVO> readAllOptions();

	public List<KeyValueVO> readAllPetPreferences();

	public List<KeyValueVO> readAllProfession();

	public List<KeyValueVO> readAllSkinTone();

	public List<KeyValueVO> readAllSpecialCondition();
}
