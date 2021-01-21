# Template makefile from $PTII/ptolemy/codegen/c/makefile.in
# $Id: makefile.in 56182 2009-11-18 00:31:47Z cxh $
# The CodeGenerator class subsitutes strings like "Ramp" with
# the value of parameters and writes the results in the directory
# where the source code is created.
#
# To use your _own_ makefile, create a makefile named "ModelName.mk.in".
# For example, if the model is called Foo, then the code generator
# looks for a makefile template file called "Foo.mk.in" and then
# looks for $PTII/ptolemy/codegen/c/makefile.in.
#
# To compile using this makefile after substitution, run:
#    make -f Ramp.mk

PTCGLIBRARIES = 
PTCGINCLUDES = 

# Mac OS X, need when we call the plotter from generated C code. 
PTJNI_PLATFORM_LDFLAG = 	 

# Under Windows, to create a binary that does not pop up a console window,
# compile with: make -f Ramp.mk CC_FLAGS=-mwindows
CC_FLAGS=

# If the user would like to add compile time options, run with
# make -f Ramp.mk USER_CC_FLAGS=xxx
USER_CC_FLAGS =

# We use -ggdb because -g fails with very large .c files
DEBUG = -ggdb

# Flags for warnings
# Use -Wall so we have better code.
WARNING_CC_FLAGS = -Wall

# To compile very large C files, try:
# make -f Ramp.mk WARNING_CC_FLAGS= USER_CC_FLAGS="-pipe -O0 --verbose -Q"
# Each of the options above:
#   No -Wall:  avoid any optimization
#   -pipe: avoid temporary files
#   -O0: avoid optimization
#   --verbose: print out steps
#   -Q: print out what functions are being compiled and timing stats.


# We need -lm so we can get floor() for ftoi() 
# We need -D__int64="long long" when invoking jni interface under cygwin
Ramp: Ramp.c
	gcc -D__int64="long long" $(WARNING_CC_FLAGS) $(CC_FLAGS) $(USER_CC_FLAGS) $(DEBUG) Ramp.c $(PTCGINCLUDES) -o Ramp -lm $(PTCGLIBRARIES) $(PTJNI_PLATFORM_LDFLAG)

run:
	Ramp

# Rule to compile with different optimizers
O_FLAGS=-O0 -O -Os -O2 -O3
optimize:
	for O_FLAG in $(O_FLAGS); do \
	    echo "Compiling with $$O_FLAG"; \
	    time gcc $$O_FLAG -D__int64="long long" $(CC_FLAGS) $(USER_CC_FLAGS) $(DEBUG) Ramp.c $(PTCGINCLUDES) -o Ramp_$$O_FLAG -lm $(PTCGLIBRARIES); \
	    cp Ramp_$${O_FLAG}.exe Ramp_$${O_FLAG}_stripped.exe; \
	    strip Ramp_$${O_FLAG}_stripped.exe; \
	    ls -l Ramp_$${O_FLAG}.exe Ramp_$${O_FLAG}_stripped.exe; \
	    echo "Running Ramp_$${O_FLAG}.exe"; \
	    time ./Ramp_$${O_FLAG}.exe; \
	    time ./Ramp_$${O_FLAG}.exe; \
	    time ./Ramp_$${O_FLAG}.exe; \
	    echo "Running Ramp_$${O_FLAG}_stripped.exe"; \
	    time ./Ramp_$${O_FLAG}_stripped.exe; \
	    time ./Ramp_$${O_FLAG}_stripped.exe; \
	    time ./Ramp_$${O_FLAG}_stripped.exe; \
	done	

# Generate code coverage using gcov
coverage:
	rm -f Ramp Ramp.exe
	make -f Ramp.mk USER_CC_FLAGS="-fprofile-arcs -ftest-coverage"
	./Ramp
	#gcov Ramp.c
