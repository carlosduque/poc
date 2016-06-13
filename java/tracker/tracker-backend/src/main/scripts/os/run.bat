@COMMENT-BEGIN@

JAR_FILE=dir /b /O:N tracker*.jar
#java -cp @CLASSPATH@;lib\compile\tracker-business-delegate-1.0.072.jar;lib\compile\tracker-common-1.0.072.jar;dist\%JAR_FILE% cl.internetmedia.tracker.backend.main.Main

java -jar %JAR_FILE%
