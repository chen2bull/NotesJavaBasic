all:clean
	javac *.java
	mkdir -p corejava.corejava1/ch10
	mv *.class corejava.corejava1/ch10
	cp about.* corejava.corejava1/ch10
	jar cvfm Ch10_02_ResourceTest.jar Ch10_02_ResourceTest.mf corejava.corejava1/
	rm -Rf corejava.corejava1/
run:
	java -jar Ch10_02_ResourceTest.jar
clean:
	rm -rf *.jar *.class
