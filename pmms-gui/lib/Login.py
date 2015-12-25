# -*- coding: utf-8 -*-

import Tkinter

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
        self.withdraw() # hide the login window
        self.drawMainPage()

    def drawMainPage(self):
        maintop = Tkinter.Toplevel(self)
        maintop.title('PMMS MAIN MENU')
        maintop.geometry('600x400')
        frame_left = Tkinter.Frame(maintop, width=150, height=390, bg='white')
        frame_right = Tkinter.Frame(maintop, width=450, height=390, bg='white')
        frame_left.grid_propagate(0)
        frame_left.grid(row=0, column=0, padx=2, pady=5)
        frame_right.grid(row=0, column=1, rowspan=3, padx=4, pady=5)

        button_importNeutralFile = Tkinter.Button(frame_left, text=u'Import Neutral File  ')
        button_importMaterialFile = Tkinter.Button(frame_left, text=u'Import Material File ')
        button_editExcelTemplate = Tkinter.Button(frame_left, text=u'Edit XLS Template ')
        button_exportToXLSFile = Tkinter.Button(frame_left, text=u'Export Data To XLS  ')
        button_importNeutralFile.grid(row=0, column=0, sticky='W', padx=12, pady=30)
        button_importMaterialFile.grid(row=1, column=0, sticky='W', padx=12, pady=30)
        button_editExcelTemplate.grid(row=2, column=0, sticky='W', padx=12, pady=30)
        button_exportToXLSFile.grid(row=3, column=0, sticky='W', padx=12, pady=30)

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