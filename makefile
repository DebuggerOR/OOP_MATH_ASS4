
# 318732484
# foglero

compile: bin
	javac -cp biuoop-1.4.jar:.src -d bin src/*.java

run:
	java -cp biuoop-1.4.jar:bin ExpressionsTest

bonus:
	java -cp biuoop-1.4.jar:bin SimplificationDemo

check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml -cp biuoop-1.4.jar src/*.java

bin:
	mkdir bin
