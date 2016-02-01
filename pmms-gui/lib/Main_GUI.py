# -*- coding: utf-8 -*-

import Tkinter
import ttk
import tkMessageBox
from xml.dom import minidom
import subprocess
import os
from os import listdir
from os.path import isfile, join
import Tkconstants
import sqlite3
import json

class login_tk(Tkinter.Tk):
    def __init__(self, parent):
        Tkinter.Tk.__init__(self, parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        self.geometry('400x300')
        self.center()

        userNameLabel = Tkinter.Label(self, text=u'Username')
        userNameLabel.place(x=100, y=50)

        self.userNameEntryVariable = Tkinter.StringVar()
        self.userNameEntry = Tkinter.Entry(self, textvariable=self.userNameEntryVariable, bd=5, width=25)
        self.userNameEntry.place(x=100, y=70)

        passwdLabel = Tkinter.Label(self, text=u'Password')
        passwdLabel.place(x=100, y=100)

        self.passwdEntryVariable = Tkinter.StringVar()
        self.passwdEntry = Tkinter.Entry(self, textvariable=self.passwdEntryVariable, show='*', bd=5, width=25)
        self.passwdEntry.place(x=100, y=120)

        loginButton = Tkinter.Button(self, text=u"Log in", command=self.onLogin)
        loginButton.place(x=100, y=160)

        changePWButton = Tkinter.Button(self, text=u"Change Password")
        changePWButton.place(x=155, y=160)

    def onLogin(self):
        isUserValid = False
        # self.withdraw() # hide the login window
        # TODO: use hash to encrypt password
        # doc = minidom.parse("C:\cets_pmms\conf\users.xml")
        doc = minidom.parse(os.getcwd() + os.sep + "../conf/users.xml")
        users = doc.getElementsByTagName("users")
        for user in users:
            userName = user.getElementsByTagName("name")[0]
            userPassword = user.getElementsByTagName("password")[0]
            if userName.firstChild.data == self.userNameEntryVariable.get():
                if userPassword.firstChild.data == self.passwdEntryVariable.get():
                    isUserValid = True
        if isUserValid:
            self.drawMainPage()
        else:
            tkMessageBox.showerror("ERROR","Invalid Username or UserPassword.")

    def drawMainPage(self):
        maintop = Tkinter.Toplevel(self)
        maintop.title('PMMS MAIN MENU')
        maintop.geometry('600x400')
        self.frame_left = Tkinter.Frame(maintop, width=150, height=390, bg='white')
        self.frame_right = Tkinter.Frame(maintop, width=600, height=390, bg='white')
        self.frame_left.grid_propagate(0)
        self.frame_left.grid(row=0, column=0, padx=2, pady=5)
        self.frame_right.grid(row=0, column=1, rowspan=3, padx=4, pady=5)

        # button_importNeutralFile = Tkinter.Button(frame_left, text=u'Import Neutral File  ')
        # button_importMaterialFile = Tkinter.Button(frame_left, text=u'Import Material File ')
        button_generateMaterialTable = Tkinter.Button(self.frame_left, text=u'Material Generator  ',command=self.drawGenerateMT)
        button_editExcelTemplate = Tkinter.Button(self.frame_left, text=u'Edit XLS Template    ',command=self.drawCreateExcelTemplate)
        button_exportToXLSFile = Tkinter.Button(self.frame_left, text=u'Export Data To XLS     ',command=self.drawExportXLSFile)
        # button_importNeutralFile.grid(row=0, column=0, sticky='W', padx=12, pady=30)
        # button_importMaterialFile.grid(row=1, column=0, sticky='W', padx=12, pady=30)
        button_generateMaterialTable.grid(row=0, column=0, sticky='W', padx=12, pady=30)
        button_editExcelTemplate.grid(row=2, column=0, sticky='W', padx=12, pady=30)
        button_exportToXLSFile.grid(row=3, column=0, sticky='W', padx=12, pady=30)


    def drawGenerateMT(self):
        for child in self.frame_right.winfo_children():
            child.destroy()
        neutralFilePathLabel = Tkinter.Label(self.frame_right, text=u'Neutral File Path: ')
        neutralFilePathLabel.grid(row=0, column=0, sticky='W', padx=12, pady=30)

        self.neutralFilePathVariable = Tkinter.StringVar()
        self.neutralFileEntry = Tkinter.Entry(self.frame_right, textvariable=self.neutralFilePathVariable, bd=5, width=25)
        self.neutralFileEntry.grid(row=0, column=1, sticky='W', padx=12, pady=30)

        materialDirPathLabel = Tkinter.Label(self.frame_right, text=u'Material Directory Path: ')
        materialDirPathLabel.grid(row=1, column=0, sticky='W', padx=12, pady=30)

        self.materialDirPathVariable = Tkinter.StringVar()
        self.materialDirPathEntry = Tkinter.Entry(self.frame_right, textvariable=self.materialDirPathVariable, bd=5, width=25)
        self.materialDirPathEntry.grid(row=1, column=1, sticky='W', padx=12, pady=30)

        button_generateMTable = Tkinter.Button(self.frame_right, text=u'Generate',command=self.generateMaterialTable)
        button_generateMTable.grid(row=2, column=0, sticky='W', padx=12, pady=30)

    def generateMaterialTable(self):
        return_code = subprocess.check_call(['pmmsAdmin.bat', '-importData', self.neutralFilePathVariable.get(), self.materialDirPathVariable.get()])
        if return_code:
            tkMessageBox.showerror("Failed","Import Data Failed.")
        else:
            tkMessageBox.showinfo("Success","Import Data Successfully.")

    def drawCreateExcelTemplate(self):
        for child in self.frame_right.winfo_children():
            child.destroy()

        # create frames
        frame_top_template  = Tkinter.Frame(self.frame_right,width=380, height=380, bg='white')
        frame_top_template.grid(row=0, column=0,sticky='W', padx=2, pady=5)

        # draw template name path
        XLSTemplateFileLabel = Tkinter.Label(frame_top_template, text=u'Template Name: ')
        XLSTemplateFileLabel.grid(row=0, column=0, sticky='W', padx=5, pady=5)

        self.newXLSTemplateName = Tkinter.StringVar()
        XLSFilePathEntry = Tkinter.Entry(frame_top_template, textvariable=self.newXLSTemplateName, bd=5, width=25)
        XLSFilePathEntry.grid(row=0, column=1, sticky='W', padx=5, pady=5)

        # draw generate template item
        generateTemplateItemLabel = Tkinter.Label(frame_top_template, text=u'********Generate Template Item:********')
        generateTemplateItemLabel.grid(row=1, column=0, sticky='W', padx=5, pady=5)

        addColumnLabel = Tkinter.Label(frame_top_template, text=u'add columns: ')
        addColumnLabel.grid(row=2, column=0, sticky='W', padx=5, pady=5)

        self.xlsColumnComboboxValue = Tkinter.StringVar()
        self.xlsColumnCombobox = ttk.Combobox(frame_top_template, textvariable=self.xlsColumnComboboxValue)
        self.xlsColumnCombobox.bind("<<ComboboxSelected>>", self.addColumnToTemplateItemText)
        self.xlsColumnCombobox['values'] = self.readColumnNamesFromDB()
        self.xlsColumnCombobox.current(0)
        self.xlsColumnCombobox.grid(row=2, column=1, sticky='W', padx=5, pady=5)

        button_add_column = Tkinter.Button(frame_top_template, text=u'Add',command=self.addColumnToTemplate)
        button_add_column.grid(row=2, column=2, sticky='W', padx=5, pady=5)

        showColumnLabel = Tkinter.Label(frame_top_template, text=u'Selected Column Names: ')
        showColumnLabel.grid(row=3, column=0, sticky='W', padx=5, pady=5)

        self.columnNamesLabel = Tkinter.Label(frame_top_template, text=u'')
        self.columnNamesLabel.grid(row=3, column=1, sticky='W', padx=5, pady=5)

        button_clear_column = Tkinter.Button(frame_top_template, text=u'Clear',command=self.clearColumnToTemplate)
        button_clear_column.grid(row=3, column=2, sticky='W', padx=1, pady=1)

        filterLabel = Tkinter.Label(frame_top_template, text=u'add filters: ')
        filterLabel.grid(row=4, column=0, sticky='W', padx=5, pady=5)

        self.newXLSTemplateFilter = Tkinter.StringVar()
        XLSTemplateFilterEntry = Tkinter.Entry(frame_top_template, textvariable=self.newXLSTemplateFilter, bd=5, width=25)
        XLSTemplateFilterEntry.grid(row=4, column=1, sticky='W', padx=5, pady=5)

        orderByLabel = Tkinter.Label(frame_top_template, text=u'add sort sequence: ')
        orderByLabel.grid(row=5, column=0, sticky='W', padx=5, pady=5)

        self.newXLSTemplateOrderBy = Tkinter.StringVar()
        XLSTemplateOrderByEntry = Tkinter.Entry(frame_top_template, textvariable=self.newXLSTemplateOrderBy, bd=5, width=25)
        XLSTemplateOrderByEntry.grid(row=5, column=1, sticky='W', padx=5, pady=5)

        button_generate = Tkinter.Button(frame_top_template, text=u'Generate Template',command=self.generateTemplate)
        button_generate.grid(row=6, column=0, sticky='W', padx=5, pady=5)

    def readColumnNamesFromDB(self):
        conn = sqlite3.connect(os.getcwd() + os.sep + "../db/pmms.db")
        print "Opened database successfully";

        cursor = conn.execute("SELECT * FROM PMMS_RESULT;")
        columnNamesArray = []
        for columnName in cursor.description:
            columnNamesArray.append(columnName[0])
        print "Operation done successfully";
        conn.close()
        return columnNamesArray

    def templateItem2Dict(self,templateItem):
        return {
            "columnNames" : templateItem.columnNames,
            "filters" : templateItem.filters,
            "orderBySequence" : templateItem.orderBySequence
        }


    def generateTemplate(self):
        jsonData = {"templateItems" : []}
        jsonTemplateFilePath = os.getcwd() + os.sep + "../xls-template" + os.sep + self.newXLSTemplateName.get()

        if os.path.isfile(jsonTemplateFilePath):
            with open(jsonTemplateFilePath) as data_file:
                data = json.load(data_file)
            for item in data["templateItems"]:
                oneTemplateItemInFile = TemplateItem(item["columnNames"],item["filters"],item["orderBySequence"])
                jsonData["templateItems"].append(self.templateItem2Dict(oneTemplateItemInFile))


        # jsonData = {"templateItems" : [self.templateItem2Dict(oneTemplateItem)]}
        oneTemplateItem = TemplateItem(self.columnNamesLabel["text"], self.newXLSTemplateFilter.get(), self.newXLSTemplateOrderBy.get())
        jsonData["templateItems"].append(self.templateItem2Dict(oneTemplateItem))
        fileOutput = open(jsonTemplateFilePath, 'w')
        fileOutput.write(json.dumps(jsonData).encode('utf-8') + '\n')
        tkMessageBox.showinfo("Success", "Template Saved")


    def addColumnToTemplateItemText(self, event):
        self.xlsTemplateItemColumn = self.xlsColumnComboboxValue.get()
        print "You choose column to Item: " + self.xlsTemplateItemColumn

    def addColumnToTemplate(self):
        if self.columnNamesLabel['text'] == "":
            self.columnNamesLabel["text"] = self.columnNamesLabel["text"] + self.xlsColumnComboboxValue.get()
        else:
            self.columnNamesLabel["text"] = self.columnNamesLabel["text"] + "," + self.xlsColumnComboboxValue.get()

    def clearColumnToTemplate(self):
        self.columnNamesLabel['text'] = ""




    def drawExportXLSFile(self):
        for child in self.frame_right.winfo_children():
            child.destroy()
        xlsTemplateFilePathLabel = Tkinter.Label(self.frame_right, text=u'XLS Template File Path: ')
        xlsTemplateFilePathLabel.grid(row=0, column=0, sticky='W', padx=12, pady=30)

        # self.xlsTemplateFilePathVariable = Tkinter.StringVar()
        # self.xlsTemplateFileEntry = Tkinter.Entry(self.frame_right, textvariable=self.xlsTemplateFilePathVariable, bd=5, width=25)
        # self.xlsTemplateFileEntry.grid(row=0, column=1, sticky='W', padx=12, pady=30)

        self.xlsTemplateFileDirPath = os.getcwd() + os.sep + "../xls-template"
        self.xlsTemplateFileList = [f for f in listdir(self.xlsTemplateFileDirPath) if isfile(join(self.xlsTemplateFileDirPath, f))]
        self.xlsTemplateFileList.append(u' * export all data')

        self.value_of_xlsCombobox = "Please select a xls template"
        self.comboboxValue = Tkinter.StringVar()
        self.xlsCombobox = ttk.Combobox(self.frame_right, textvariable=self.comboboxValue)
        self.xlsCombobox.bind("<<ComboboxSelected>>", self.comboboxSelection)
        # self.xlsCombobox['values'] = ('X', 'Y', 'Z')
        self.xlsCombobox['values'] = self.xlsTemplateFileList
        self.xlsCombobox.current(0)
        self.xlsCombobox.grid(row=0, column=1, sticky='W', padx=12, pady=30)

        button_editTemplate = Tkinter.Button(self.frame_right, text=u'Edit',command=self.modifyXlsTemplate)
        button_editTemplate.grid(row=0, column=2, sticky='W', padx=12, pady=30)

        button_deleteTemplate = Tkinter.Button(self.frame_right, text=u'Delete',command=self.deleteXlsTemplate)
        button_deleteTemplate.grid(row=0, column=3, sticky='W', padx=12, pady=30)

        XLSFilePathLabel = Tkinter.Label(self.frame_right, text=u'Output XLS File PATH: ')
        XLSFilePathLabel.grid(row=1, column=0, sticky='W', padx=12, pady=30)

        self.XLSFilePathVariable = Tkinter.StringVar()
        self.XLSFilePathEntry = Tkinter.Entry(self.frame_right, textvariable=self.XLSFilePathVariable, bd=5, width=25)
        self.XLSFilePathEntry.grid(row=1, column=1, sticky='W', padx=12, pady=30)

        button_generateMTable = Tkinter.Button(self.frame_right, text=u'  Export   ',command=self.exportMaterialTable)
        button_generateMTable.grid(row=2, column=0, sticky='W', padx=12, pady=30)

    def modifyXlsTemplate(self):
        self.drawTopWinToModifyXlsTemplate()

    def deleteXlsTemplate(self):
        xlsFileTemplateToDelete = os.getcwd() + os.sep + "../xls-template" + os.sep + self.comboboxValue.get()
        os.remove(xlsFileTemplateToDelete)
        tkMessageBox.showinfo("Success", "Delete Template Successfully")


    def drawTopWinToModifyXlsTemplate(self):
        self.xlsModifyTopWin = Tkinter.Toplevel(width=450,height=400)
        self.xlsModifyTopWin.title('Modify XLS Template')
        self.xlsFileTemplateToModify = "XLS Template File:\t[" + self.comboboxValue.get() + "]"
        xlsTemplateFileToModifyLabel = Tkinter.Label(self.xlsModifyTopWin, text=self.xlsFileTemplateToModify)
        xlsTemplateFileToModifyLabel.grid(row=0, column=0,sticky='W')
        frame_left_center  = Tkinter.Frame(self.xlsModifyTopWin,width=380, height=200, bg='white')
        frame_left_center.grid(row=1, column=0, padx=2, pady=5)
        self.xlsFileTextMsg = Tkinter.Text(frame_left_center);
        frame_left_center.grid(row=1, column=0, padx=2, pady=5)
        self.xlsFileTextMsg.grid()

        frame_left_bottom  = Tkinter.Frame(self.xlsModifyTopWin,width=380, height=150)
        button_saveTemplate   = Tkinter.Button(frame_left_bottom, text=unicode('Save Template','eucgb2312_cn'), command=self.saveXlsTemplateToFile)
        frame_left_bottom.grid(row=2, column=0)
        button_saveTemplate.grid(sticky='E')

        originalXlsTemplateStr = self.getOriginalXlsTemplateContent(self.comboboxValue.get())
        self.xlsFileTextMsg.insert(Tkconstants.END, originalXlsTemplateStr)

    def getOriginalXlsTemplateContent(self, xlsTemplateFile):
        templateFilePath = os.getcwd() + os.sep + "../xls-template" + os.sep + self.comboboxValue.get()
        templateFile = open(templateFilePath,'r')
        result = ''
        for line in templateFile:
            result = result + line + "\n"
        templateFile.close()
        return result

    def saveXlsTemplateToFile(self):
        newXlsTemplateContent = self.xlsFileTextMsg.get("1.0",Tkconstants.END)
        xlsTemplateFile = open(os.getcwd() + os.sep + "../xls-template" + os.sep + self.comboboxValue.get(), 'w')
        xlsTemplateFile.write(newXlsTemplateContent)
        xlsTemplateFile.close()
        tkMessageBox.showinfo("Success", "Saved")

    def exportMaterialTable(self):
        # TODO: call batch command to generate material table
        xlsTemplateFileInComboBox = self.comboboxValue.get()
        if xlsTemplateFileInComboBox == u' * export all data':
            xlsTemplateFileInComboBox = ""
        else:
            xlsTemplateFileInComboBox = os.getcwd() + os.sep + "../xls-template" + os.sep + xlsTemplateFileInComboBox
        return_code = subprocess.check_call(['pmmsAdmin.bat', '-exportData', xlsTemplateFileInComboBox, self.XLSFilePathVariable.get()])
        if return_code:
            tkMessageBox.showerror("Failed","Export Data Failed.")
        else:
            tkMessageBox.showinfo("Success","Export Data Successfully.")

    def comboboxSelection(self, event):
        self.value_of_xlsCombobox = self.xlsCombobox.get()
        print('You choose xls template: ' + self.value_of_xlsCombobox)

    def center(self):
        self.update_idletasks()
        w = self.winfo_screenwidth()
        h = self.winfo_screenheight()
        size = tuple(int(_) for _ in self.geometry().split('+')[0].split('x'))
        x = w/2 - size[0]/2
        y = h/2 - size[1]/2
        self.geometry("%dx%d+%d+%d" % (size + (x, y)))

class TemplateItem(object):
    def __init__(self, columnNames, filters, orderBySequence):
        self.columnNames = columnNames
        self.filters = filters
        self.orderBySequence = orderBySequence


if __name__ == "__main__":
    app = login_tk(None)
    app.title("NJ CETS PMMS")
    app.mainloop()