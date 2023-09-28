build: 
	javac -d bin *.java

run: 
	cd bin && java Main 

clean: 
	rm bin/*.class