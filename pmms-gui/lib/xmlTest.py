__author__ = 'gexin'


from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import hashlib

# create XML
root = Element('users')
userElement = Element('user')
root.append(userElement)
nameElement = Element('name')
passwordElement = Element('password')
userElement.append(nameElement)
userElement.append(passwordElement)
nameElement.text = 'admin'

password_text = 'admin111'
hash_password_obj = hashlib.sha256(password_text)
hex_dig = hash_password_obj.hexdigest()
passwordElement.text = hex_dig


# pretty string
with open("testXML2File", "w") as f:
    f.write(tostring(root))