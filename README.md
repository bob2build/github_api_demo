## Github API demo using Java CXF Restclient

### Functionality
This project contains sample programs to demonstrate the API functionality of Github.com. It contains a single Main class App.java. It accepts the name of the command as a CLI argument. The following commands are supported

#### branch
Outputs all the branches of a repository (https://github.com/ruby/ruby) to stdout

### Usage

#### Running Tests
```
git clone https://github.com/bob2build/github_api_demo
cd github_api_demo
mvn test
```

### Running the application
```
git clone https://github.com/bob2build/github_api_demo
cd github_api_demo
mvn exec:java -Dexec.mainClass="gmail.bob2build.github_api_demo.App" -Dexec.args="<<command>>"
```

Supported Commands
* branch
* tag
* branch_wo_travis
* tag_wo_travis


###TODO
* Add option parser to parse arguments
* Add support for HTTP mocking. JMockit which is used now, is too naive for mocking http requests


