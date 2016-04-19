package gmail.bob2build.github_api_demo;

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
        String baseAddress = "https://api.github.com";
        WebClient client = WebClient.create(baseAddress);
        client.accept("application/vnd.github.v3+json");
        GithubRestHelper helper = new GithubRestHelper(client, "/chef/chef");

        if (command.equals("branch")) {
            processBranchCommand(args, helper);
        } else {
            System.err.println(String.format("Command %s not found", command));
        }

    }

    static void processBranchCommand(String[] args, GithubRestHelper helper) {
        System.out.println(helper.readBranches());
    }
}
