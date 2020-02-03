package com.djn.cn.auth.token.test;





import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.djn.cn.auth.token.app.dao.IAppInfoDAO;
public class AppInfoTest extends AbstractTestCase{
    @Autowired
    private IAppInfoDAO iAppInfoDAO;
	@Test
	public void findByKeyDAOTest() {
	    String key = "43724fac7aa148dda6eb69188089125c";
	    System.out.println(iAppInfoDAO.findByKey(key));
	}
}