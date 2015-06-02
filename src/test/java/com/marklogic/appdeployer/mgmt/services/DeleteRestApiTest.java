package com.marklogic.appdeployer.mgmt.services;

import org.junit.Before;
import org.junit.Test;

import com.marklogic.appdeployer.mgmt.AbstractMgmtTest;

/**
 * This test ensures that the convenience methods for creating and deleting a sample application work properly, and thus
 * they can be used in other tests that depend on having an app in place.
 */
public class DeleteRestApiTest extends AbstractMgmtTest {

    @Before
    public void setup() {
        initializeAppManager();
    }

    @Test
    public void createAndDelete() {
        ServiceManager mgr = new ServiceManager(manageClient);

        createSampleAppRestApi();
        assertTrue("The REST API server should exist", mgr.restApiServerExists(SAMPLE_APP_NAME));

        deleteSampleApp();
        assertFalse("The REST API server should have been deleted", mgr.restApiServerExists(SAMPLE_APP_NAME));
    }
}
