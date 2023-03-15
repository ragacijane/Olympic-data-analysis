To do:

Optimise code

***********************
In cmd: javah package.name ( name of java class where native function is created )
When header file is created create dll C++ project in VS
Settings for VS proj:
properties -> VC++ -> include directories -> c -> prog files -> java -> include folder
- || - -> java -> win 32
properties -> c/c++ -> precompiled headers -> not using precompiles

Settings for Eclipse proj:
project -> properties -> java build path -> native lib -> src -> apply
system.loadLibrary("name") (name of dll file that is created )
