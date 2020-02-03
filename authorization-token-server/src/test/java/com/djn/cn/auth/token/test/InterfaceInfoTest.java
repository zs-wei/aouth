package com.djn.cn.auth.token.test;





import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.djn.cn.auth.token.app.dao.IInterfaceInfoDAO;
import com.djn.cn.auth.token.app.service.IInterfaceInfoService;

public class InterfaceInfoTest extends AbstractTestCase{
    @Autowired
    private IInterfaceInfoDAO iInterfaceInfoDAO;
    @Autowired
    private IInterfaceInfoService iInterfaceInfoService;
	@Test
	public void listAllDAOTest() {
		System.out.println("listAllTest:"+iInterfaceInfoDAO.listAll());
	}
	@Test
	public void listAllServiceTest() {
	    System.out.println("listAllTest:"+iInterfaceInfoService.listAll());
	}
}