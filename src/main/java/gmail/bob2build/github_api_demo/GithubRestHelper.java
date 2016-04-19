package gmail.bob2build.github_api_demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;

public class GithubRestHelper {
    private WebClient client;

    private String repositoryPath;

    public GithubRestHelper(WebClient client, String repositoryPath) {
        this.client = client;
        this.repositoryPath = repositoryPath;
    }

    public List<String> readBranches() {
        ArrayList<String> branchList = new ArrayList<String>();
        String path = String.format("repos%s/branches", repositoryPath);
        client.replacePath(path);
        Response r = client.get();
        if (r.getStatus() != 200) {
            throw new RuntimeException(
                String.format("{%s} returned response code {%s}", path, r));
        }
        try {
            String responseStr = IOUtils.toString((InputStream) r.getEntity());
            JSONArray branches = new JSONArray(responseStr);
            for (int i = 0; i < branches.length(); i++) {
                branchList.add(branches.getJSONObject(i).getString("name"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return branchList;
    }
}
