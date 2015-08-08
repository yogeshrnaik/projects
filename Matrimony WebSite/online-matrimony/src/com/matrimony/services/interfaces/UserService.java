package com.matrimony.services.interfaces;

import java.util.Date;

import com.matrimony.exceptions.AppException;
import com.matrimony.vo.UserVO;
import com.matrimony.vo.enums.Gender;

public interface UserService {
	public UserVO getUserByEmail(String email) throws AppException;

	// public UserVO getUserById(Long id) throws AppException;

	public UserVO persistUser(UserVO userVO) throws AppException;

	// public UserVO registerUser(String email, String firstName, String lastName, Gender gender, Date birthDate,
	// String password) throws AppException;

}
