package com.matrimony.services.interfaces;

import com.matrimony.exceptions.AppException;
import com.matrimony.vo.UserVO;

public interface PersistenceService {
	public void persist(Object vo) throws AppException;

}
