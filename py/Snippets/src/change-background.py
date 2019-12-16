#!/usr/bin/env python
#
# change-background.py
#
#
# A script to change to a random background image
#
#(c) 2004, Davyd Madeley <davyd@madeley.id.au>
#
#   This program is free software; you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation; either version 2, or(at your option)
#   any later version.
#
#   This program is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with this program; if not, write to the Free Software Foundation,
#   Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
#

backgrounds = "/home/carlos/pics/wallpapers"

import sys
import gconf
import os
import random
import mimetypes

def get_files_recursively(rootdir):
    """Recursively get a list of files from a folder."""
    fileList = []

    for root, subFolders, files in os.walk(rootdir):
        for file in files:
            fileList.append(os.path.join(root,file))

    return fileList

# Get the files from the backgrounds folder.
dir_items = get_files_recursively(backgrounds)

# Check if the background items are actually images. Approved files are
# put in 'items'.
items = []
for item in dir_items:
    mimetype = mimetypes.guess_type(item)[0]
    if mimetype and mimetype.split('/')[0] == "image":
        items.append(item)

# Get a random background item from the file list.
item = random.randint(0, len(items) - 1)

# Create a gconf object.
client = gconf.client_get_default()

# Get the current background used by GNOME.
current_bg = client.get_string("/desktop/gnome/background/picture_filename")

# Make sure the random background item isn't the same as the background
# currently being used.
while(items[item] == current_bg):
    item = random.randint(0, len(items) - 1)

# Finally, set the new background.
client.set_string("/desktop/gnome/background/picture_filename", items[item])
sys.exit()

