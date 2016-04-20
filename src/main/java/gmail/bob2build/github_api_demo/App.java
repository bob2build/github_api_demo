package gmail.bob2build.github_api_demo;

import java.util.ArrayList;

import org.apache.cxf.jaxrs.client.WebClient;

/**
 * Hello world!
 *
 */

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage java App <command> [options]");
            System.exit(1);
        }
        String command = args[0];
        String baseAddress = "https://api.github.com/";
        WebClient client = null;
        String githubUser = System.getenv("GITHUB_USERNAME");
        String githubToken = System.getenv("GITHUB_API_TOKEN");
        if (githubToken != null) {
            client = WebClient.create(baseAddress, githubUser, githubToken, null);
        } else {
            System.out.println("Warning Using No Authentication. GITHUB_USERNAME and GITHUB_API_TOKEN can be specified via enviornment variables");
            client = WebClient.create(baseAddress);
        }
        client.accept("application/vnd.github.v3+json");
        GithubRestHelper helper = new GithubRestHelper(client, "/ruby/ruby");

        if (command.equals("branch")) {
            processBranchCommand(args, helper);
        } else if (command.equals("tag")) {
            processTagCommand(args, helper);
        } else if (command.equals("branch_wo_travis")) {
            processBranchTravisCommand(args, helper);
        } else if (command.equals("tag_wo_travis")) {
            processTagTravisCommand(args, helper);
        } else {
            System.err.println(String.format("Command %s not found", command));
        }

    }

    static void processBranchCommand(String[] args, GithubRestHelper helper) {
        System.out.println(helper.readBranches());
    }

    static void processTagCommand(String[] args, GithubRestHelper helper) {
        System.out.println(helper.readTags());
    }

    static void processBranchTravisCommand(String[] args,
        GithubRestHelper helper) {
        System.out.println(helper.readBranchesWithoutTravisConf());
    }

    static void processTagTravisCommand(String[] args,
        GithubRestHelper helper) {
        System.out.println(helper.readTagsWithoutTravisConf());
    }

}
