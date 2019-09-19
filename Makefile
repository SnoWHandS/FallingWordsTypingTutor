# Assignment 4 Makefile
# Dillon Heald
# 16 September 2019

#For use on Linux laptop
JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
JAVADOC=/usr/bin/javadoc
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOC=doc
$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -sourcepath $(SRCDIR) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= Score.class WordDictionary.class WordRecord.class WordPanel.class Animate.class WordApp.class

JAVAS= Score.java WordDictionary.java WordRecord.java WordPanel.java Animate.java WordApp.java

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class
	
javadoc:
	#No idea why this wont work. running it manually in ./src does work. - I accept defeat.
	#$(JAVADOC) -d ./Docs -sourcepath $(SRCDIR) $(JAVAS)
	$(JAVADOC) -d $(DOC) $(SRCDIR)/*.java

run:
	$(JAVA) -cp $(BINDIR) WordApp 1 3 "example_dict.txt"