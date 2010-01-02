#!/bin/bash
ant release
mv bin/.Yadoroid-unsigned.apk bin/Yadoroid.apk
jarsigner -keystore ~/.android/ngsdev.keystore -verbose bin/Yadoroid.apk ngsdev