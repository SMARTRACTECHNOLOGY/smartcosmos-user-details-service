package net.smartcosmos.cluster.userdetails.config;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UserDetailsDevelopmentServiceTestConfiguration.class })
public class UserDetailsDevelopmentServicePropertiesTest {

    @Autowired
    private UserDetailsDevelopmentServiceProperties properties;

    @Test
    public void getUsers() throws Exception {

        System.out.println("junky");
    }

    @Test
    public void setUsers() throws Exception {

    }

    @Test
    public void thatPropertiesAreSet() {

        Assert.assertNotNull(properties);
    }

}