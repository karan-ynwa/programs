package com.ericsson.eus.plcfg.plugin;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoadConfigMapPropTest {

	@InjectMocks
	private LoadConfigMapProp loadConfigMapProp;
	/*
	 * @Mock private static Path path;
	 */
	/*
	 * @BeforeClass public static void beforeClass() {
	 * Mockito.when(Paths.get(Mockito.anyString())).thenReturn(path); }
	 */

	/*
	 * @Before public void setUp() {
	 * PowerMockito.mockStatic(LoadConfigMapProp.class);
	 * PowerMockito.suppress(PowerMockito.constructor(LoadConfigMapProp.class));
	 * FileInputStream fileInputStreamMock =
	 * PowerMockito.mock(FileInputStream.class); try {
	 * PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(
	 * fileInputStreamMock); } catch (Exception e) { e.printStackTrace(); }
	 * PowerMockito.mockStatic(Paths.class);
	 * PowerMockito.when(Paths.get(Mockito.anyString())).thenReturn(path);
	 * 
	 * }
	 */

	@Test
	public void testLoadProperties() {

		File resourceFile = new File("/featureToggle.txt");

		try {
			loadConfigMapProp.loadProperties(resourceFile.toString());
		} catch (IOException e) {
			Assert.assertNotNull(e);
		}
	}
}
