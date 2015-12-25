# -*- coding: utf-8 -*-

import Tkinter

class main_tk(Tkinter.Tk):
    def __init__(self, parent):
        Tkinter.Tk.__init__(self, parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        self.geometry('600x500')
        self.center()



