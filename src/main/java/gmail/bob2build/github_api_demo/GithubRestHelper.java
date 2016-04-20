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

    public List<String> readTags() {
        ArrayList<String> tagsList = new ArrayList<String>();
        String path = String.format("repos%s/tags", repositoryPath);
        client.replacePath(path);
        Response r = client.get();
        if (r.getStatus() != 200) {
            try {
                String responseStr = IOUtils.toString((InputStream) r.getEntity());
                System.out.println(responseStr);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            throw new RuntimeException(
                String.format("{%s} returned response code {%s}", path, r.getStatus()));
        }
        try {
            String responseStr = IOUtils.toString((InputStream) r.getEntity());
            JSONArray branches = new JSONArray(responseStr);
            for (int i = 0; i < branches.length(); i++) {
                tagsList.add(branches.getJSONObject(i).getString("name"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tagsList;
    }

    public List<String> readBranchesWithoutTravisConf() {
        List<String> branchList = readBranches();
        List<String> branchesWOTravis = new ArrayList<String>();
        for (String branch : branchList) {
            String path = String.format("/repos%s/contents/.travis.yml", repositoryPath);
            client.replacePath(path);
            client.replaceQueryParam("ref", branch);
            Response r = client.get();
            if (r.getStatus() == 404) {
                branchesWOTravis.add(branch);
            } else if (r.getStatus() == 200) {
                String responseStr;
                try {
                    responseStr = IOUtils.toString((InputStream) r.getEntity());
                    //System.out.println(responseStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format(
                    "{%s} returned response code {%s}", path, r.getStatus()));
            }
        }
        return branchesWOTravis;
    }
    
    public List<String> readTagsWithoutTravisConf() {
        List<String> tagList = readTags();
        List<String> tagsWOTravis = new ArrayList<String>();
        for (String tag : tagList) {
            String path = String.format("/repos%s/contents/.travis.yml", repositoryPath);
            client.replacePath(path);
            client.replaceQueryParam("ref", tag);
            Response r = client.get();
            String responseStr = null;;
            try {
                responseStr = IOUtils.toString((InputStream) r.getEntity());
                //System.out.println(responseStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        
            if (r.getStatus() == 404) {
                tagsWOTravis.add(tag);
            } else if (r.getStatus() == 200) {
                
            } else {
                throw new RuntimeException(String.format(
                    "{%s} returned response code {%s}", path, r.getStatus()));
            }
        }
        return tagsWOTravis;
    }
}
