package com.ericsson.eus.plcfg.plugin.util;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.eus.plcfg.plugin.LoadConfigMapProp;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoadConfigMapProp.class)
public class PLCFGUtilTest {

	@Mock
	private LoadConfigMapProp loadConfigMapProp;
	private PLCFGUtil pLCFGUtil;
	@Before
	public void setUp() {
		mockStatic(LoadConfigMapProp.class);
		Mockito.when(LoadConfigMapProp.getInstance()).thenReturn(loadConfigMapProp);
		pLCFGUtil = new PLCFGUtil();
	}

	@After
	public void tearDown() {
		this.loadConfigMapProp = null;
	}

	/**
	 * User: eznraps , Date: Feb 5, 2020 3:01:52 PM 2020
	 *
	 * Purpose: This method test for feature toggle true.
	 *
	 * US/D/F Number:F96456 :ATTPRE-96674
	 * 
	 * Return Type: void
	 *
	 */
	@Test
	public void testIsFeatureToggleEnabledFromConfigMapON() {
		PowerMockito.when(LoadConfigMapProp.getInstance().getProp(Mockito.anyString())).thenReturn("ON");
		boolean toggleValue = pLCFGUtil.isFeatureToggleEnabledFromConfigMap("F22222");
		Assert.assertTrue(toggleValue);
	}

	/**
	 * User: eznraps , Date: Feb 5, 2020 3:01:52 PM 2020
	 *
	 * Purpose: This method test for feature toggle false.
	 *
	 * US/D/F Number:F96456 :ATTPRE-96674
	 * 
	 * Return Type: void
	 *
	 */
	@Test
	public void testIsFeatureToggleEnabledFromConfigMapOFF() {
		PowerMockito.when(LoadConfigMapProp.getInstance().getProp(Mockito.anyString())).thenReturn("OFF");
		boolean toggleValue = pLCFGUtil.isFeatureToggleEnabledFromConfigMap("F22222");
		Assert.assertFalse(toggleValue);
	}

	/**
	 * User: eznraps , Date: Feb 5, 2020 3:01:52 PM 2020
	 *
	 * Purpose: This method test for feature toggle false.
	 *
	 * US/D/F Number:F96456 :ATTPRE-96674
	 * 
	 * Return Type: void
	 *
	 */
	@Test
	public void testIsFeatureToggleEnabledFromConfigMapInvalidValue() {
		PowerMockito.when(LoadConfigMapProp.getInstance().getProp(Mockito.anyString())).thenReturn("READY");
		boolean toggleValue = pLCFGUtil.isFeatureToggleEnabledFromConfigMap("F22222");
		Assert.assertFalse(toggleValue);
	}

}
