# -*- coding: utf-8 -*-

__author__ = 'gexin'

import Tkinter
import ttk
import tkMessageBox
import os


class authorization_tk(Tkinter.Tk):
    def __init__(self, parent):
        Tkinter.Tk.__init__(self, parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        # self.geometry('500x400')
        # self.center()

        self.frame_top = Tkinter.Frame(self, width=280, height=60, bg='white')
        self.frame_center = Tkinter.Frame(self, width=280, height=390, bg='white')

        self.frame_top.grid_propagate(0)
        self.frame_top.grid(row=0, column=0, padx=2, pady=5)
        self.frame_center.grid(row=1, column=0, rowspan=3, padx=4, pady=5)

        logo = Tkinter.PhotoImage(file="logo.gif")
        logoLab = Tkinter.Label(self.frame_top, image=logo)
        logoLab.image = logo
        logoLab.grid(row=1, padx=5,sticky='', pady=5)


        projectIdLabel = Tkinter.Label(self.frame_center, text=u'ProjectId: ')
        projectIdLabel.grid(row=1, column=0, sticky='W', padx=5, pady=5)

        self.projectId = Tkinter.StringVar()
        ProjectIdEntry = Tkinter.Entry(self.frame_center, textvariable=self.projectId, bd=5, width=25)
        ProjectIdEntry.grid(row=1, column=1, sticky='W', padx=5, pady=5)

        expireDateLabel = Tkinter.Label(self.frame_center, text=u'ExpireDate: ')
        expireDateLabel.grid(row=2, column=0, sticky='W', padx=5, pady=5)

        self.expireDate = Tkinter.StringVar()
        ExpireDateEntry = Tkinter.Entry(self.frame_center, textvariable=self.expireDate, bd=5, width=25)
        ExpireDateEntry.grid(row=2, column=1, sticky='W', padx=5, pady=5)

        authorizerLabel = Tkinter.Label(self.frame_center, text=u'Authorizer: ')
        authorizerLabel.grid(row=3, column=0, sticky='W', padx=5, pady=5)

        self.authorizer = Tkinter.StringVar()
        AuthorizerEntry = Tkinter.Entry(self.frame_center, textvariable=self.authorizer, bd=5, width=25)
        AuthorizerEntry.grid(row=3, column=1, sticky='W', padx=5, pady=5)

        generateKeyFileButton = Tkinter.Button(self.frame_center, text=u"Generate Key", command=self.generateKey)
        generateKeyFileButton.grid(row=4, column=1, sticky='W', padx=5, pady=5)

    def generateKey(self):
        projectIdStr = "ProjectId=" + self.projectId.get() + "\n"
        expireDateStr = "expireDate=" + self.expireDate.get() + "\n"
        authorizerStr = "authorizer=" + self.authorizer.get() + "\n"

        keyFileStr = projectIdStr + expireDateStr + authorizerStr
        #with open(os.getcwd() + os.sep + "../conf/users.xml", "w") as f:
        with open(os.getcwd() + os.sep + "/authorized.key", "w") as f:
                f.write(keyFileStr)

    def center(self):
        self.update_idletasks()
        w = self.winfo_screenwidth()
        h = self.winfo_screenheight()
        size = tuple(int(_) for _ in self.geometry().split('+')[0].split('x'))
        x = w/2 - size[0]/2
        y = h/2 - size[1]/2
        self.geometry("%dx%d+%d+%d" % (size + (x, y)))


if __name__ == "__main__":
    app = authorization_tk(None)
    app.title("NJ CETS Authorization Tool")
    app.mainloop()


