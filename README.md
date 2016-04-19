## Github API demo using Java CXF Restclient

### Functionality
This project contains sample programs to demonstrate the API functionality of Github.com. It contains a single Main class App.java. It accepts the name of the command as a CLI argument. The following commands are supported

#### branch
Outputs all the branches of a repository (https://github.com/chef/chef) to stdout

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
mvn exec:java -Dexec.mainClass="gmail.bob2build.github_api_demo.App" -Dexec.args="branch"
```


###TODO
* Add command for listing tags
* Add command for listing branches which doesn't contain .travis.yml file
* Add command for listing tags which doesn't contain .travis.yml file
