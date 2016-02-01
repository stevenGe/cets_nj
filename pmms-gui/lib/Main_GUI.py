# -*- coding: utf-8 -*-

import Tkinter
import tkMessageBox
from xml.dom import minidom
import commands
import subprocess
import shutil
import errno
import sys
import os

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
        doc = minidom.parse("/Users/gexinlu/Downloads/cets_pmms/conf/users.xml")
        # doc = minidom.parse(os.getcwd() + os.sep + "../conf/users.xml")
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
            tkMessageBox.showerror("ERROR: Invalid Username or UserPassword.")

    def drawMainPage(self):
        maintop = Tkinter.Toplevel(self)
        maintop.title('PMMS MAIN MENU')
        maintop.geometry('600x400')
        self.frame_left = Tkinter.Frame(maintop, width=150, height=390, bg='white')
        self.frame_right = Tkinter.Frame(maintop, width=450, height=390, bg='white')
        self.frame_left.grid_propagate(0)
        self.frame_left.grid(row=0, column=0, padx=2, pady=5)
        self.frame_right.grid(row=0, column=1, rowspan=3, padx=4, pady=5)

        # button_importNeutralFile = Tkinter.Button(frame_left, text=u'Import Neutral File  ')
        # button_importMaterialFile = Tkinter.Button(frame_left, text=u'Import Material File ')
        button_generateMaterialTable = Tkinter.Button(self.frame_left, text=u'Material Generator  ',command=self.drawGenerateMT)
        button_editExcelTemplate = Tkinter.Button(self.frame_left, text=u'Edit XLS Template    ',command=self.drawEditExcelTemplate)
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
        # self.frame_right.grid_forget()
        # TODO: call batch command to generate material table
        # command_params = ' '.join(['-importData', self.neutralFilePathVariable.get(), self.materialDirPathVariable.get()])
        return_code = subprocess.check_call(['pmmsAdmin.bat', '-importData', self.neutralFilePathVariable.get(), self.materialDirPathVariable.get()])
        if return_code:
            tkMessageBox.showerror("Import Data Failed.")
        else:
            tkMessageBox.showinfo("Import Data Successfully.")

        # import_data_command = 'pmmsAdmin.bat ' + command_params
        # (status, output) = commands.getstatusoutput(import_data_command)
        # if status:
        #     sys.stderr.write(output)
        #     tkMessageBox.showerror("Import Data Failed.")
        # else:
        #     tkMessageBox.showinfo("Import Data Successfully.")

        # p = Popen(["C:/cets_pmms/bin/pmmsAdmin.bat", command_params], stdout=PIPE, stderr=PIPE)
        # output, errors = p.communicate()
        # p.wait() # wait for process to terminate
        # if p.returncode:
        #     sys.stderr.write(output)
        #     tkMessageBox.showerror("Import Data Failed.")
        # else:
        #     tkMessageBox.showinfo("Import Data Successfully.")

        # os.system("pmmsAdmin.bat", "command_params")


    def drawEditExcelTemplate(self):
        # self.frame_right.grid_forget()
        tkMessageBox.showinfo("drawEditExcelTemplate")

    def drawExportXLSFile(self):
        for child in self.frame_right.winfo_children():
            child.destroy()
        # self.frame_right.grid_forget()

        self.frame_inside_left = Tkinter.Frame(self.frame_right, width=80, height=40, bg='white')
        xlsTemplateFilePathLabel = Tkinter.Label(self.frame_inside_left, text=u'XLS Template File Path: ')
        xlsTemplateFilePathLabel.grid(row=0, column=0, sticky='W', padx=12, pady=30)

        # self.xlsTemplateFilePathVariable = Tkinter.StringVar()
        # self.xlsTemplateFileEntry = Tkinter.Entry(self.frame_right, textvariable=self.xlsTemplateFilePathVariable, bd=5, width=25)
        # self.xlsTemplateFileEntry.grid(row=0, column=1, sticky='W', padx=12, pady=


        scrollbar = Tkinter.Scrollbar(self.frame_inside_left)

        xlsTemplateListBox = Tkinter.Listbox(self.frame_inside_left,height=1,yscrollcommand=scrollbar.set)
        xlsTemplateListBox.insert("end", "one")
        xlsTemplateListBox.insert("end", "two")
        xlsTemplateListBox.insert("end", "three")


        scrollbar.pack(side='right', fill='y')
        xlsTemplateListBox.grid(row=0, column=1, sticky='W', padx=12, pady=30)

        button_editTemplate = Tkinter.Button(self.frame_inside_left, text=u'Edit',command=self.generateMaterialTable)
        button_editTemplate.grid(row=0, column=2, sticky='W', padx=12, pady=30)

        self.frame_left.grid(row=0, column=0, sticky='W', padx=12, pady=30)

        XLSFilePathLabel = Tkinter.Label(self.frame_right, text=u'Output XLS File PATH: ')
        XLSFilePathLabel.grid(row=1, column=0, sticky='W', padx=12, pady=30)

        self.XLSFilePathVariable = Tkinter.StringVar()
        self.XLSFilePathEntry = Tkinter.Entry(self.frame_right, textvariable=self.XLSFilePathVariable, bd=5, width=25)
        self.XLSFilePathEntry.grid(row=1, column=1, sticky='W', padx=12, pady=30)

        button_generateMTable = Tkinter.Button(self.frame_right, text=u'  Export   ',command=self.exportMaterialTable)
        button_generateMTable.grid(row=2, column=0, sticky='W', padx=12, pady=30)

    def exportMaterialTable(self):
        # TODO: call batch command to generate material table
        return_code = subprocess.check_call(['pmmsAdmin.bat', '-exportData', self.xlsTemplateFilePathVariable.get(), self.XLSFilePathVariable.get()])
        if return_code:
            tkMessageBox.showerror("Export Data Failed.")
        else:
            tkMessageBox.showinfo("Export Data Successfully.")
        # command_params = ' '.join(['-exportData', self.XLSFilePathVariable.get()])
        # import_data_command = 'pmmsAdmin.bat ' + command_params
        # (status, output) = commands.getstatusoutput(import_data_command)
        # if status:
        #     sys.stderr.write(output)
        #     tkMessageBox.showerror("Export Data Failed.")
        # else:
        #     tkMessageBox.showinfo("Export Data Successfully.")

    def center(self):
        self.update_idletasks()
        w = self.winfo_screenwidth()
        h = self.winfo_screenheight()
        size = tuple(int(_) for _ in self.geometry().split('+')[0].split('x'))
        x = w/2 - size[0]/2
        y = h/2 - size[1]/2
        self.geometry("%dx%d+%d+%d" % (size + (x, y)))

if __name__ == "__main__":
    app = login_tk(None)
    app.title("NJ CETS PMMS")
    app.mainloop()