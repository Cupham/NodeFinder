# NodeFinder

Java source code to automatically generate corresponding ECHONET device objects. Futher actions (convert to semantic resource, hand over resource to another server ...) can be easily done by adding your own handling functions.

The basic flow to handle ECHONET Lite devices is as follow (refer: App.java):
  1. Detect and Monitor Node (I am using the echowand library (https://github.com/ymakino/echowand))
  2. Device recognition and Attribute monitoring (this source code)
  3. Create Application (feel free to add)

# Install echowand library
All device classes are created base on the ECHONET Lite Specification (release J). 
All mandatory and observerable attributes are supported (feel free to add more attribute on your own).

The echowand lirary has been used as a local maven dependency (Make it public? Not sure! contact the author).
Jar library of the echowand is included. Install it into your local maven by running the following command:

  mvn install:install-file -Dfile=echowand-1.1.jar -DgroupId=jaist.tanlab -DartifactId=echowand -Dpackaging=jar -Dversion=1.1
 
# How to run

  java -jar {executablejar}.jar -i {network interface (eno1, en1...)}
  
# What's next
Converting device resource into semantic resources and connect to oneM2M ecosystem will come next!

# Contact
cupham@jaist.ac.jp
# License 
Apache 2
