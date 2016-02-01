# -*- coding: utf-8 -*-

__author__ = 'gexin'

from Tkinter import *
import ttk

class App:

    value_of_combo = 'X'


    def __init__(self, parent):
        self.parent = parent
        self.combo()

    def combo(self):
        self.box_value = StringVar()
        self.box = ttk.Combobox(self.parent, textvariable=self.box_value)
        self.box.bind("<<ComboboxSelected>>", self.newselection)
        self.box['values'] = ('X', 'Y', 'Z')
        self.box.current(0)
        self.box.grid(column=0, row=0)

    def newselection(self, event):
        self.value_of_combo = self.box.get()
        print(self.value_of_combo)

if __name__ == '__main__':
    root = Tk()
    app = App(root)
    root.mainloop()





