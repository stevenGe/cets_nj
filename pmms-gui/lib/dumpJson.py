import json

__author__ = 'gexin'


class TemplateItem(object):
    def __init__(self, columnNames, filters, orderBySequence):
        self.columnNames = columnNames
        self.filters = filters
        self.orderBySequence = orderBySequence


def templateItem2Dict(templateItem):
    return {
        "columnNames" : templateItem.columnNames,
        "filters" : templateItem.filters,
        "orderBySequence" : templateItem.orderBySequence
    }

s = TemplateItem('record_type,line_number_label', "record_type='COMP'", "record_type")
s1 = TemplateItem('12321312', "12413413", "1232131")
print(json.dumps(s, default=templateItem2Dict))

data = {"templateItems" : [templateItem2Dict(s), templateItem2Dict(s1)]}
print(json.dumps(data))


