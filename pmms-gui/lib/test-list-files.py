# -*- coding: utf-8 -*-
__author__ = 'gexin'

from os import listdir
from os.path import isfile, join

mypath = "C:\\cets_pmms\\xls-template"

onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]

print(onlyfiles)
