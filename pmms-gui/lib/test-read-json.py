# -*- coding: utf-8 -*-

__author__ = 'gexin'

import json


with open('C:\\cets_pmms\\xls-template\\test_xls_template.json') as data_file:
    data = json.load(data_file)

for item in data["templateItems"]:
    print(item["columnNames"])
    print(item["filters"])
    print(item["orderBySequence"])
