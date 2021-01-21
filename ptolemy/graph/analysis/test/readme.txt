
Tests for the synthesis.graph.analysis package.

@author Shahrooz Shahparnia based on a file by S. Bhattacharyya
@version $Id: readme.txt 28706 2003-06-27 21:58:35Z shuvra $

If a test is giving errors write the content of the buffer to a file and
compare. Following code can be used for this purpose.

    set fileChannel [open temp.txt w+]
    puts $fileChannel $fileBuffer 
    close $fileChannel