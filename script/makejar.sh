#!/bin/sh

javafxpackager  -createjar -appclass tetris.core.EntryPoint -srcdir . \
-outdir .. -outfile game -v \
