package gmail.bob2build.github_api_demo;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Assert;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;

public class GithubRestHelperTest {

    @Mocked
    private WebClient client;

    @Mocked
    Response response;

    @Test
    public void testReadBranches() {
        String repoPath = "/chef/chef";
        final String branchesResponse =
            "[{\"name\":\"br1\", \"commit\": {\"sha\":\"rand1\", \"url\":\"rand2\"}},{\"name\":\"br2\", \"commit\": {\"sha\":\"rand3\", \"url\":\"rand3\"}}]";

        new Expectations() {
            {
                client.replacePath("repos/chef/chef/branches");
                result = client;
                client.get();
                result = response;
                response.getStatus();
                result = 200;
                response.getEntity();
                result = new ByteArrayInputStream(branchesResponse.getBytes());
            }
        };

        GithubRestHelper helper = new GithubRestHelper(client, repoPath);
        List<String> branches = helper.readBranches();
        Assert.assertTrue(Arrays.asList("br1", "br2").equals(branches));
    }
    
    @Test
    public void testReadTags() {
        String repoPath = "/chef/chef";
        final String tagsResponse =
            "[{\"name\":\"tag1\", \"commit\": {\"sha\":\"rand1\", \"url\":\"rand2\"}},{\"name\":\"tag2\", \"commit\": {\"sha\":\"rand3\", \"url\":\"rand3\"}}]";

        new Expectations() {
            {
                client.replacePath("repos/chef/chef/tags");
                result = client;
                client.get();
                result = response;
                response.getStatus();
                result = 200;
                response.getEntity();
                result = new ByteArrayInputStream(tagsResponse.getBytes());
            }
        };

        GithubRestHelper helper = new GithubRestHelper(client, repoPath);
        List<String> branches = helper.readTags();
        Assert.assertTrue(Arrays.asList("tag1", "tag2").equals(branches));
    }

}
