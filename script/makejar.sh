#!/bin/sh

javafxpackager  -createjar -appclass tetris.core.Main -srcdir . \
-outdir .. -outfile game -v \
