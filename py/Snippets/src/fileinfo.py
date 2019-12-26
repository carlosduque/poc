#!/usr/bin/env python
#
#  A bunch of convenience functions for testing file attributes.
#
#  Written by Sean Reifschneider <jafo-python-fileinfo@tummy.com>
#  Please direct all comments to him at the above address.
#
#  VERSION: 1.0 1999-01-04

#TODO:
#  Does not implement -O and -G tests from "test" (is file effectively
#    uid or gid you)?
#  Find a common naming scheme (or two -- posix-like and "other"?) for methods
#    f_ok() => exists()
#    r_ok() => readable()
#    x_ok() => executeable()
#    ifreg() => is_reg()?
#    ifdir() => is_dir()?
#    is_woth() => s_iwoth() or write_other()?
#    is_rgrp() => s_irgrp() or read_group()?
#    is_suid() => s_isuid() or setuid()?

import os
import stat
import types


def _ok(name, mode):
	try: os.access(name, mode)
	except: return(0)
	return 1

def f_ok(name): return(_ok(name, os.F_OK))
def r_ok(name): return(_ok(name, os.R_OK))
def w_ok(name): return(_ok(name, os.W_OK))
def x_ok(name): return(_ok(name, os.X_OK))

#  stat routine that automaticly selects stat/fstat/lstat (if followsymlink)

def mstat(name, followsymlink = 1):
	if type(name) == types.StringType:
		info = os.stat(name)
		if followsymlink and stat.S_ISLNK(info[stat.ST_MODE]):
			return(os.lstat(name))
		return(info)
	return(os.fstat(name))

def _mode(name): return(mstat(name)[stat.ST_MODE])

def mode(name): return(stat.S_IMODE(_mode(name)))
def ifreg(name): return(stat.S_ISREG(_mode(name)))
def ifdir(name): return(stat.S_ISDIR(_mode(name)))
def ifchr(name): return(stat.S_ISCHR(_mode(name)))
def ifblk(name): return(stat.S_ISBLK(_mode(name)))
def iffifo(name): return(stat.S_ISFIFO(_mode(name)))
def iflnk(name): return(stat.S_ISLNK(_mode(name)))
def ifsock(name): return(stat.S_ISSOCK(_mode(name)))

def is_suid(name): return(_mode(name) & stat.S_ISUID == stat.S_ISUID)
def is_sgid(name): return(_mode(name) & stat.S_ISGID == stat.S_ISGID)
def is_svtx(name): return(_mode(name) & stat.S_ISVTX == stat.S_ISVTX)
def is_read(name): return(_mode(name) & stat.S_IREAD == stat.S_IREAD)
def is_write(name): return(_mode(name) & stat.S_IWRITE == stat.S_IWRITE)
def is_exec(name): return(_mode(name) & stat.S_IEXEC == stat.S_IEXEC)
def is_rwxu(name): return(_mode(name) & stat.S_IRWXU == stat.S_IRWXU)
def is_rusr(name): return(_mode(name) & stat.S_IRUSR == stat.S_IRUSR)
def is_wusr(name): return(_mode(name) & stat.S_IWUSR == stat.S_IWUSR)
def is_xusr(name): return(_mode(name) & stat.S_IXUSR == stat.S_IXUSR)
def is_rwxg(name): return(_mode(name) & stat.S_IRWXG == stat.S_IRWXG)
def is_rgrp(name): return(_mode(name) & stat.S_IRGRP == stat.S_IRGRP)
def is_wgrp(name): return(_mode(name) & stat.S_IWGRP == stat.S_IWGRP)
def is_xgrp(name): return(_mode(name) & stat.S_IXGRP == stat.S_IXGRP)
def is_rwxo(name): return(_mode(name) & stat.S_IRWXO == stat.S_IRWXO)
def is_roth(name): return(_mode(name) & stat.S_IROTH == stat.S_IROTH)
def is_woth(name): return(_mode(name) & stat.S_IWOTH == stat.S_IWOTH)
def is_xoth(name): return(_mode(name) & stat.S_IXOTH == stat.S_IXOTH)

def is_empty(name): return(mstat(name)[stat.ST_SIZE] == 0)
def size(name): return(mstat(name)[stat.ST_SIZE])
def isatty(file):
	try:
		os.ttyname(file)
		return(1)
	except OSError, e:
		if e == '[Errno 2] No such file or directory':
			return(0)
		raise

#  returns age difference between lhs and rhs files (in seconds):
#    <0 lhs older than rhs
#    >0 lhs newer than rhs
#     0 lhs same age as rhs
def mtime_cmp(lhs, rhs): return(mtime(lhs) - mtime(rhs))
def mtime(file): return(mstat(lhs)[stat.ST_MTIME])
def mtime_newer(lhs, rhs): return(mtime_cmp(lhs, rhs) > 0)
def mtime_older(lhs, rhs): return(mtime_cmp(lhs, rhs) < 0)
def mtime_equal(lhs, rhs): return(mtime_cmp(lhs, rhs) == 0)
