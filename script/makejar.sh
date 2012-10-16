#!/bin/sh

cp ../gpl.txt .

rm -f game.jar

javafxpackager  -createjar -nocss2bin -appclass tetris.core.Main -srcdir . \
-outdir . -outfile game -v 

pack200 --repack --effort=9 --segment-limit=-1 --modification-time=latest \
--strip-debug ./game.jar


