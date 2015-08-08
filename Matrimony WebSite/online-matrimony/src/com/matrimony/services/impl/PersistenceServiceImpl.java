package com.matrimony.services.impl;

import javax.jdo.PersistenceManager;

import com.matrimony.exceptions.AppException;
import com.matrimony.services.factory.PMF;
import com.matrimony.services.interfaces.PersistenceService;
import com.matrimony.vo.UserVO;

public class PersistenceServiceImpl implements PersistenceService {

	public void persist(Object vo) throws AppException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(vo);
		} finally {
			pm.close();
		}
	}

}
