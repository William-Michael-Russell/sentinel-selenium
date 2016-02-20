# sentinel-selenium  Selenium/Appium

Sentinel Selenium is a framework brought to you by Testaholic.net
This framework is targeted for both cross browser and appium native and browser testing.

This framework will have video guides and step by step tutorials to build from scratch. 
It's still in early development, and expect major changes and features.


### Install Instructions:

- Clone the project
- Open your command prompt or terminal and navigate to this repository
- Change your directory the repository
- Enter: mvn clean install verify
All maven dependencies will download, and the tests will execute... you'll search for beer.


### What Parameters can I provide in the command line?
All parameters are listed in net.testaholic.core.config.SeleniumConfig
Few examples:
You can override the browser from the command line:

-  mvn clean test -Dbrowser=chrome  -- this will override configuration and run on chromes
- mvn clean test -Dbrowser=firefox -- this will override configuration and run on firefox


### Any test which includes "Test" will be picked up by maven, for example:
SimpleGoogleSearchTest.java would be picked up.

- "**/Test*.java" - includes all of its subdirectories and all Java filenames that start with "Test".
- "**/*Test.java" - includes all of its subdirectories and all Java filenames that end with "Test".
- "**/*TestCase.java" - includes all of its subdirectories and all Java filenames that end with "TestCase"



### Predefined DesiredCapabilities: You can specify which browser or device you would like to run.

Small List:

- -Dbrowser=ANDROID_NEXUS_7
- -Dbrowser=IOS_9_1
- -Dbrowser=firefox
- -Dbrowser=chrome
- -Dbrowser=safari  (Double click and install the included safariDriver.safariextz, which is located in src/test/java/resources/SafariDriver)
- -Dbrowser=ie
- -Dbrowser=edge
- -Dbrowser=opera
- -Dbrowser=phantomjs



### You can connect to saucelabs, specify browser, platform and versions.

- -Dremote=true
- -Dplatform=OS X 10.11
- -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub
- -Dbrowser=firefox
- -DbrowserVersion=43
- -Dthreads=4

Example:
mvn clean test -Dremote=true
               -Dplatform=OS X 10.11
               -DseleniumGridURL=http://{username}:{accessKey}@ondemand.saucelabs.com:80/wd/hub
               -Dbrowser=firefox
               -DbrowserVersion=43
               -Dthreads=4 
               
               


Expect much more documentation and features over the upcoming months.
Few planned features:

-   .bat and .sh scripts to automatically update and install the latest webDrivers
-   Screenshot listeners
-   Video listeners
-   Caching
-   Page Object Examples
-   browser-mob (proxy inspector)
-   Custom user-agents
-   ExtentReport
-   BasePageObject
-  Document the code
-  EventFiringWebDriver reporting
-  Test @Annotations to simplify everything
-  Image recognition
-  Complex test examples
-  Add logging client
-  Update Wiki, and readme.md markdown :)