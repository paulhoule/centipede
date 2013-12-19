package com.ontology2.centipede.util;

public class ExitStatus {
    final public static int OK= 0;             /* successful termination */
    final public static int USAGE = 64;        /* command line usage error */
    final public static int DATAERR = 65;      /* data format error */
    final public static int NOINPUT = 66;      /* cannot open input */
    final public static int NOUSER = 67;       /* addressee unknown */
    final public static int NOHOST = 68;       /* host name unknown */
    final public static int UNAVAILABLE = 69;  /* service unavailable */
    final public static int SOFTWARE = 70;     /* internal software error */
    final public static int OSERR =71;         /* system error (e.g., can't fork) */
    final public static int OSFILE = 72;       /* critical OS file missing */
    final public static int CANTCREAT = 73;    /* can't create (user) output file */
    final public static int IOERR = 74;        /* input/output error */
    final public static int TEMPFAIL = 75;     /* temp failure; user is invited to retry */
    final public static int PROTOCOL = 76;     /* remote error in protocol */
    final public static int NOPERM = 77;       /* permission denied */
    final public static int CONFIG = 78;       /* configuration error */
}
