#!/bin/sh

javafxpackager  -createjar -nocss2bin -appclass tetris.core.Main -srcdir . \
-outdir .. -outfile game -v \
