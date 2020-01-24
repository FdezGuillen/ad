from tkinter import *
from tkinter import ttk
import random

class MineManager():
    def add_label(self, widget):
        widget.bind("<Enter>", self.on_enter)
        widget.bind("<Leave>", self.on_leave)
        widget.bind("<ButtonPress-1>", self.on_click)
        
    def on_enter(self, event):
        r = event.widget.grid_info()['row']
        c = event.widget.grid_info()['column']
        labels_str_array[r][c].set(calculate_mines(r,c))

    def on_leave(self, event):
        r = event.widget.grid_info()['row']
        c = event.widget.grid_info()['column']
        labels_str_array[r][c].set("")

    def on_click(self, event):
        r = event.widget.grid_info()['row']
        c = event.widget.grid_info()['column']
        mine = mines_array[r][c]
        if mine == 1:
            event.widget['style'] = 'mine.TLabel'
            game_state.set("YOU LOOSER!")
        else:
            event.widget['style'] = 'notmine.TLabel'
        

window = Tk()
window.title("Minesweeper")
mm = MineManager()

#controller
def putMine(prob):
   if random.randint(1, prob) == 1:
       return 1
   else:
       return 0

def initialize_labels_background():
    for r in labels_array:
        for c in r:
            c['style'] = 'TLabel'
            
def initialize_mines(str_array, prob):
    initialize_labels_background()
    for r in str_array:
        row_mine = list()
        mines_array.append(row_mine)
        for c in r:
            mine = putMine(prob)
            row_mine.append(mine)
            c.set("")

def calculate_mines(r,c):
    num = 0
    
    minr = 0
    maxr = 0
    minc = 0
    maxc = 0

    if r >= 1:
        minr = r-1
        
    if r <= 7:
        maxr = r+1
    elif r == 8:
        maxr = r+1
    else:
        maxr = 9

    if c >= 1:
        minc = c-1
        
    if c <= 7:
        maxc = c+1
    elif c == 8:
        maxc = c+1
    else:
        maxc = 9

    for row in range(minr, maxr+1):
        for col in range(minc, maxc+1):
            if (mines_array[row][col] == 1):
                num += 1
                
    return num

def restart_game():
    initialize_mines(labels_str_array, mine_prob)
    game_state.set("Ready to play")
    
#model
labels_array = list()
labels_str_array = list()
mines_array = list()
game_state = StringVar()
mine_prob = 4

#view

"""Style"""
photo = PhotoImage(file="mariobrick.png")

style = ttk.Style()
style.theme_create('minesweeper')
style.theme_use('minesweeper')
style.configure('TButton',
                font='PressStart2P 8',
                background='ivory3',
                foreground='black',
                padding=(20,20))

style.configure('TLabel',
                font='PressStart2P 8',
                background='lightskyblue',
                foreground='black',
                relief='groove',
                anchor='center')

style.configure('state.TLabel', padding=(20,20))

style.configure('mine.TLabel',
                background='firebrick1',
                relief='sunken')
style.configure('notmine.TLabel',
                background='goldenrod1',
                relief='sunken')


window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)

frame = ttk.Frame(window)

frame.columnconfigure(0, weight=1)
frame.rowconfigure(0, weight=1)
frame.rowconfigure(10, weight=1)

for i in range(0,10):
    frame.columnconfigure(i, weight=1, minsize='35p')
    frame.rowconfigure(i, weight=1, minsize='35p')

frame.grid(column=0, row=0, padx=20, pady=20)

""" Generating list of lists of stringvars"""
for r in range(0,10):
    row_str = list()
    labels_str_array.append(row_str)
    for c in range(0,10):
        row_str.append(StringVar())
        
initialize_mines(labels_str_array, mine_prob)

""" Generating list of lists of labels"""
for r in range(0,10):
    row = list()
    labels_array.append(row)
    for c in range(0,10):
        label = ttk.Label(frame, textvariable = labels_str_array[r][c])
        label.grid(row=r, column=c, sticky='nwse')
        labels_array[r].append(label)
        mm.add_label(labels_array[r][c])

game_state.set("Ready to play")
state_label = ttk.Label(frame, textvariable=game_state, image=photo)
state_label['style'] = 'state.TLabel'
state_label.grid(row=10, column=0, columnspan=5, sticky='nswe')

newgame_button = ttk.Button(frame, text="New Game", command=lambda:restart_game())
newgame_button.grid(row=10, column=5, columnspan=5, sticky='nswe')

window.update()
window.minsize(window.winfo_width(), window.winfo_height())

window.mainloop()
