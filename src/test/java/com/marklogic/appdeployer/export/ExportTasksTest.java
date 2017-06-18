package com.marklogic.appdeployer.export;

import com.marklogic.appdeployer.command.tasks.DeployScheduledTasksCommand;
import com.marklogic.mgmt.selector.PrefixResourceSelector;
import com.marklogic.mgmt.selector.ResourceSelection;
import com.marklogic.mgmt.resource.tasks.TaskManager;
import org.junit.After;
import org.junit.Test;

public class ExportTasksTest extends AbstractExportTest {

	@After
	public void teardown() {
		undeploySampleApp();
	}

	@Test
	public void test() {
		initializeAppDeployer(new DeployScheduledTasksCommand());
		deploySampleApp();

		String taskPath = "/path/to/query.xqy";

		// Get some test coverage of selectors, even though we don't care about users here
		PrefixResourceSelector selector = new PrefixResourceSelector("/path");
		selector.setIncludeTypesAsString(ResourceSelection.TASKS + "," + ResourceSelection.USERS);

		ExportedResources resources = new Exporter(manageClient).select(selector).export(exportDir);
		assertEquals(1, resources.getFiles().size());
		assertEquals("query.xqy.json", resources.getFiles().get(0).getName());

		TaskManager mgr = new TaskManager(manageClient);
		mgr.deleteTaskWithPath(taskPath);
		assertFalse(mgr.exists(taskPath));

		appConfig.getConfigDir().setBaseDir(exportDir);
		deploySampleApp();
		assertTrue(mgr.exists(taskPath));

		mgr.deleteTaskWithPath(taskPath);
		assertFalse(mgr.exists(taskPath));
	}
}
