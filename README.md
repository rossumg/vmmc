# VMMC

##### How to sign the app so it installs on most recent android versions
* Build -> Generate Signed APK...
  ![Alt text](readme/1.jpeg?raw=true "Step 1")
* Choose keystore (keystore2.jks is a dummy keystore),
  enter keystore password (12345678 for keystore2.jks),
  key alias (key0 for keystore2.jks),
  key password (12345678 for keystore2.jks),
  ![Alt text](readme/2.jpeg?raw=true "Step 2")
* Hit Next
* Choose Signature Versions V1 (Jar Signature) and V2 (Full APK Signature) -- this is very important for the apk to install on latest Android versions.
  ![Alt text](readme/3.jpeg?raw=true "Step 3")
* Hit finish
* Signed apk path is app/app-release.apk
