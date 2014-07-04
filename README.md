Courage Wolf Generator
=====================

This is my Courage Wolf Generator implemented as a Clojure leiningen project.

Impress the ladies by using it to generate sweet videos like this one https://www.youtube.com/watch?v=nZGCnDSC4Rc

Usage
-----

Get an account at http://memegenerator.net/

Stick your credentials in src/courage_wolf_generator/core.clj

Fill the -main function with inspiring quotes.

 $ lein run

Now save a copy of O Fortuna, the Apotheosis remix to oh_fortuna.mp3,
or alternatively something else that's 4:53 long.

$ mencoder "mf://*.jpg" -mf fps=71/293 -audiofile o_fortuna.mp3 -o courage_wolf_rules.avi -ovc lavc -lavcopts vcodec=mjpeg -oac copy

to build the .avi.

Finally, upload to YouTube to cement greatness.

License
-------

Copyright (C) 2014 Michael Bacarella

Distributed under the Eclipse Public License, the same as Clojure.
