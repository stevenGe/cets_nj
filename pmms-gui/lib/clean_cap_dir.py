import os
import sys
import shutil

# content_pack_name = str(sys.argv[1]).strip()

cap_languages_folder = os.sep.join(['INBUILT', 'BI', 'CAP', 'LANGUAGES'])

if os.path.exists(cap_languages_folder):
  language_folders = os.listdir(cap_languages_folder)
  print language_folders


for one_language_folder in language_folders:
    if one_language_folder != "en_US":
        one_cap_languages_folder = os.sep.join([cap_languages_folder, one_language_folder])
        shutil.rmtree(one_cap_languages_folder)
        os.mkdir(one_cap_languages_folder)