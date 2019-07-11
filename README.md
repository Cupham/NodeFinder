# NodeFinder

Java source code to automatically generate corresponding ECHONET device objects. Futher actions (convert to semantic resource, hand over resource to another server ...) can be easily done by adding your own handling functions.

The basic flow to handle ECHONET Lite devices is as follow:
  1. Detect and Monitor Node (I am using the echowand library (https://github.com/ymakino/echowand))
  2. Device recognition and Attribute monitoring (this source code)
  3. Create Application (feel free to add)

All device classes are created base on the ECHONET Lite Specification (release J). 
All mandatory and observerable attributes are supported (feel free to add more attribute on your own).

