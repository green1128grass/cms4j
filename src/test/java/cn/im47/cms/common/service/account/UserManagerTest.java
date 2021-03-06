package cn.im47.cms.common.service.account;

import cn.im47.cms.common.dao.account.UserMapper;
import cn.im47.cms.common.entity.account.User;
import cn.im47.cms.common.service.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springside.modules.test.security.shiro.ShiroTestUtils;

import static org.junit.Assert.fail;

/**
 * 类功能简介
 * <p/>
 * User: baitao.jibt (dreambt@gmail.com)
 * Date: 12-3-28
 * Time: 下午11:23
 */
public class UserManagerTest {

    private UserManager userManager;

    @Mock
    private UserMapper mockUserMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ShiroTestUtils.mockSubject("Baitao.jibt");

        userManager = new UserManagerImpl();
        userManager.setUserMapper(mockUserMapper);
    }

    @After
    public void tearDown() throws Exception {
        ShiroTestUtils.clearSubject();
    }

    @Ignore
    @Test
    public void testSaveUser() throws Exception {
        User admin = new User();
        admin.setId(1L);

        User user = new User();
        user.setId(2L);

        //正常保存用户
        userManager.save(user);

        //保存超级管理用户抛出异常
        try {
            userManager.save(admin);
            fail("expected ServicExcepton not be thrown");
        } catch (ServiceException se) {
            //expected exception
        }
    }

}